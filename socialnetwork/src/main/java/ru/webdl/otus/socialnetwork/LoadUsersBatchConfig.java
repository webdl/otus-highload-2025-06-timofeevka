package ru.webdl.otus.socialnetwork;

import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.boot.autoconfigure.batch.JobLauncherApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@EnableBatchProcessing
@Profile("dev")
@DependsOn("springBatchDataSourceInitializer")
@Log4j2
public class LoadUsersBatchConfig {
    private static final String USERS_CSV_INPUT_PATH = "target/people.v2-downloaded.csv";
    private static final String USERS_CSV_OUTPUT_PATH = "target/people.v2.csv";

    @Bean
    public Step downloadFileStep(JobRepository jobRepository,
                                 PlatformTransactionManager transactionManager) {
        Tasklet downloadTasklet = new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                String fileUrl = "https://raw.githubusercontent.com/OtusTeam/highload/master/homework/people.v2.csv";

                try (BufferedInputStream in = new BufferedInputStream(new URL(fileUrl).openStream());
                     FileOutputStream fileOutputStream = new FileOutputStream(USERS_CSV_INPUT_PATH)) {
                    byte[] dataBuffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                        fileOutputStream.write(dataBuffer, 0, bytesRead);
                    }
                }
                log.info("File downloaded to {}", USERS_CSV_INPUT_PATH);
                return RepeatStatus.FINISHED;
            }
        };

        return new StepBuilder("downloadFileStep", jobRepository)
                .tasklet(downloadTasklet, transactionManager)
                .build();
    }

    @Bean
    public Step removeLastLineStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("removeLastLineStep", jobRepository)
                .tasklet(removeLastLineTasklet(), transactionManager)
                .build();
    }

    @Bean
    @StepScope
    public Tasklet removeLastLineTasklet() {
        return (contribution, chunkContext) -> {
            Path inputPath = Paths.get(USERS_CSV_INPUT_PATH);
            Path outputPath = Paths.get(USERS_CSV_OUTPUT_PATH);

            int processedLines = 0;
            int skippedLines = 0;
            String line = null;

            try (BufferedReader reader = Files.newBufferedReader(inputPath, StandardCharsets.UTF_8);
                 BufferedWriter writer = Files.newBufferedWriter(outputPath, StandardCharsets.UTF_8)) {
                while ((line = reader.readLine()) != null) {
                    processedLines++;
                    try {
                        writer.write(line);
                        writer.newLine();
                    } catch (Exception e) {
                        skippedLines++;
                        log.error("Skipping line {}: {}", processedLines, e.getMessage());
                    }
                }
                log.info("Conversion completed: {} lines processed, {} lines skipped", processedLines, skippedLines);
            } catch (IOException e) {
                log.error("Conversion is not fully completed: {} lines processed, {} lines skipped, Last line '{}'. Error: {}",
                        processedLines, skippedLines, line, e.getMessage());
            }
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Job importUserJob(JobRepository jobRepository, Step downloadFileStep, Step removeLastLineStep) {
        return new JobBuilder("importUserJob", jobRepository)
                .start(downloadFileStep)
                .next(removeLastLineStep)
                .build();
    }

    @Bean
    public JobLauncherApplicationRunner jobRunner(JobLauncher jobLauncher, JobExplorer jobExplorer, JobRepository jobRepository) {
        return new JobLauncherApplicationRunner(jobLauncher, jobExplorer, jobRepository);
    }
}

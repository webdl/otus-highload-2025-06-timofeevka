package ru.webdl.otus.socialnetwork;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
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
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.URL;

@Configuration
@EnableBatchProcessing
@Profile("dev")
public class LoadUsersBatchConfig {

    @Bean
    public Step downloadFileStep(JobRepository jobRepository,
                                 PlatformTransactionManager transactionManager) {
        Tasklet downloadTasklet = new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                String fileUrl = "https://raw.githubusercontent.com/OtusTeam/highload/master/homework/people.v2.csv";
                String localFile = "target/people.v2.csv";

                try (BufferedInputStream in = new BufferedInputStream(new URL(fileUrl).openStream());
                     FileOutputStream fileOutputStream = new FileOutputStream(localFile)) {
                    byte[] dataBuffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                        fileOutputStream.write(dataBuffer, 0, bytesRead);
                    }
                }

                System.out.println("File downloaded to " + localFile);
                return RepeatStatus.FINISHED;
            }
        };

        return new StepBuilder("downloadFileStep", jobRepository)
                .tasklet(downloadTasklet, transactionManager)
                .build();
    }

    @Bean
    public Job importUserJob(JobRepository jobRepository, Step downloadFileStep) {
        return new JobBuilder("importUserJob", jobRepository)
                .start(downloadFileStep)
                .build();
    }

    @Bean
    public JobLauncherApplicationRunner jobRunner(JobLauncher jobLauncher, JobExplorer jobExplorer, JobRepository jobRepository) {
        return new JobLauncherApplicationRunner(jobLauncher, jobExplorer, jobRepository);
    }
}

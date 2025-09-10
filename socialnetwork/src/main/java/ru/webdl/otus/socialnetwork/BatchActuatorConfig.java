package ru.webdl.otus.socialnetwork;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class BatchActuatorConfig {

    @Component
    @Endpoint(id = "batchjobs")
    public class BatchJobsEndpoint {
        private final JobLauncher jobLauncher;
        private final JobRegistry jobRegistry;
        private final JobExplorer jobExplorer;

        @Autowired
        public BatchJobsEndpoint(JobLauncher jobLauncher, JobRegistry jobRegistry, JobExplorer jobExplorer) {
            this.jobLauncher = jobLauncher;
            this.jobRegistry = jobRegistry;
            this.jobExplorer = jobExplorer;
        }

        @ReadOperation
        public Map<String, Object> getJobs() {
            Map<String, Object> result = new HashMap<>();
            result.put("availableJobs", jobRegistry.getJobNames());
            result.put("jobExecutions", getJobExecutions());
            return result;
        }

        @WriteOperation
        public String restartJob(@Selector String jobName) {
            try {
                Job job = jobRegistry.getJob(jobName);
                JobParameters jobParameters = new JobParametersBuilder()
                        .addLong("timestamp", System.currentTimeMillis())
                        .toJobParameters();

                JobExecution execution = jobLauncher.run(job, jobParameters);
                return "Job '" + jobName + "' restarted. Execution ID: " + execution.getId();
            } catch (Exception e) {
                return "Failed to restart job: " + e.getMessage();
            }
        }

        private List<Map<String, Object>> getJobExecutions() {
            List<Map<String, Object>> executions = new ArrayList<>();
            for (String jobName : jobRegistry.getJobNames()) {
                List<JobInstance> instances = jobExplorer.getJobInstances(jobName, 0, 10);
                for (JobInstance instance : instances) {
                    List<JobExecution> jobExecutions = jobExplorer.getJobExecutions(instance);
                    for (JobExecution execution : jobExecutions) {
                        Map<String, Object> execInfo = new HashMap<>();
                        execInfo.put("jobName", jobName);
                        execInfo.put("executionId", execution.getId());
                        execInfo.put("status", execution.getStatus());
                        execInfo.put("startTime", execution.getStartTime());
                        execInfo.put("endTime", execution.getEndTime());
                        executions.add(execInfo);
                    }
                }
            }
            return executions;
        }
    }
}

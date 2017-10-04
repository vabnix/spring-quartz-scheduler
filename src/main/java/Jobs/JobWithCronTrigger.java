package Jobs;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.*;

public class JobWithCronTrigger implements Job {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${cron.frequency.jobwithcrontrigger}")
    private String frequency;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("Running JobWithCronTrigger | frequency {}", frequency);
    }

    @Bean(name = "jobWithCronTriggerBean")
    public MethodInvokingJobDetailFactoryBean sampleJob() {
        return ConfigureQuartz.createJobDetail(this.getClass());
    }

    @Bean(name = "jobWithCronTriggerBeanTrigger")
    public CronTriggerFactoryBean sampleJobTrigger(@Qualifier("jobWithCronTriggerBean") JobDetail jobDetail) {
        return ConfigureQuartz.createCronTrigger(jobDetail, frequency);
    }

}

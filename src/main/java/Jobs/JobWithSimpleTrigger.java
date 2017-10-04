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

public class JobWithSimpleTrigger implements Job {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${cron.frequency.jobwithsimpletrigger}")
    private long frequency;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("Running JobWithSimpleTrigger | frequency {}", frequency);
    }

    @Bean(name = "jobWithSimpleTriggerBean")
    public JobDetailFactoryBean sampleJob() {
        return ConfigureQuartz.createJobDetail(this.getClass());
    }

    @Bean(name = "jobWithSimpleTriggerBeanTrigger")
    public SimpleTriggerFactoryBean sampleJobTrigger(@Qualifier("jobWithSimpleTriggerBean") JobDetail jobDetail) {
        return ConfigureQuartz.createTrigger(jobDetail,frequency);
    }
}

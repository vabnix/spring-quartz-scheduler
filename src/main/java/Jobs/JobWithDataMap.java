package Jobs;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class JobWithDataMap implements Job {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${cron.frequency.jobwithdatamap}")
    private String frequency;

    JobDataMap jobDataMap = new JobDataMap();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("Running JobWithDataMap | frequency {}", frequency);
        JobDataMap dataMap = jobContext.getJobDetail().getJobDataMap();
        String key1 = dataMap.get("key1").toString();
        String key2 = dataMap.get("key2").toString();
        logger.info("Running JobWithDataMap | Key1 = {}, Key2 = {}", key1, key2 );
    }

    @Bean(name = "jobWithDataMapBean")
    public JobDetailFactoryBean sampleJob() {
        JobDetailFactoryBean jBean = ConfigureQuartz.createJobDetail(this.getClass());
        jobDataMap.put("key1", "value1");
        jobDataMap.put("key2", "value2");
        jBean.setJobDataMap(jobDataMap);
        return jBean;
    }

    @Bean(name = "jobWithDataMapTrigger")
    public CronTriggerFactoryBean sampleJobTrigger(@Qualifier("jobWithDataMapBean") JobDetail jobDetail) {
        return ConfigureQuartz.createCronTrigger(jobDetail,frequency);
    }
}

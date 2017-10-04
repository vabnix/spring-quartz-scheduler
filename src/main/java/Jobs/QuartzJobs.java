package Jobs;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@DisallowConcurrentExecution
public class QuartzJobs implements Job {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${cron.frequency.jobwithsimpletrigger}")
    private long frequency;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("Running JobWithSimpleTrigger | frequency {}", frequency);
    }

}

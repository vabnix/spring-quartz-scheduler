package Controller;

import Configuration.ConfigureQuartz;
import Jobs.DynamicJob;
import org.quartz.JobKey;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import util.AppUtil;
import util.PropertiesUtils;

import java.util.HashMap;
import java.util.Map;

@RestController
public class QuartzController {

    @Value("${con.key2}")
    String conKey2;

    @Autowired
    private SchedulerFactoryBean schedFactory;

    private static final String JOBS = "/jobs";
    private static final String SCHEDULE = "/schedule";
    private static final String UNSCHEDULE = "/unscheduled";

    @RequestMapping(path = JOBS, method = RequestMethod.GET)
    public String getVal(@RequestParam(value="key", defaultValue="World") String key) {
        Map<String, String> mapOfKeyValue = new HashMap<>();
        mapOfKeyValue.put(key, PropertiesUtils.getProperty(key));
        mapOfKeyValue.put("con.key2", conKey2);
        return AppUtil.getBeanToJsonString(mapOfKeyValue);
    }

    @RequestMapping(path = SCHEDULE, method = RequestMethod.POST)
    public String schedule() {
        String scheduled = "Job is Scheduled!!";
        try { JobDetailFactoryBean jdfb = ConfigureQuartz.createJobDetail(DynamicJob.class);
            jdfb.setBeanName("dynamicJobBean");
            jdfb.afterPropertiesSet();

            SimpleTriggerFactoryBean stfb = ConfigureQuartz.createTrigger(jdfb.getObject(),5000L);
            stfb.setBeanName("dynamicJobBeanTrigger");
            stfb.afterPropertiesSet();

            schedFactory.getScheduler().scheduleJob(jdfb.getObject(), stfb.getObject());

        } catch (Exception e) {
            scheduled = "Could not schedule a job. " + e.getMessage();
        }
        return scheduled;
    }

    @RequestMapping(path = UNSCHEDULE, method = RequestMethod.POST)
    public String unschedule() {
        String scheduled = "Job is Unscheduled!!";
        TriggerKey tkey = new TriggerKey("dynamicJobBeanTrigger");
        JobKey jkey = new JobKey("dynamicJobBean");
        try {
            schedFactory.getScheduler().unscheduleJob(tkey);
            schedFactory.getScheduler().deleteJob(jkey);
        } catch (SchedulerException e) {
            scheduled = "Error while unscheduling " + e.getMessage();
        }
        return scheduled;
    }

}

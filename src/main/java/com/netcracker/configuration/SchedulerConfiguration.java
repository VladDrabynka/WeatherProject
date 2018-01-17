package com.netcracker.configuration;


import com.netcracker.service.impl.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.web.context.support.StandardServletEnvironment;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@PropertySource("classpath:/scheduler.properties")
public class SchedulerConfiguration implements SchedulingConfigurer {

    @Autowired
    Environment env;

    @Bean(destroyMethod = "shutdown")
    public Executor taskExecutor() {
        //return Executors.newScheduledThreadPool(100);
        return Executors.newSingleThreadScheduledExecutor();
    }

    @Bean
    public SchedulerService schedulerService(){
        return new SchedulerService();
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
        taskRegistrar.addTriggerTask(
                () -> schedulerService().refreshDatabase(),
                triggerContext -> {
                    Calendar nextExecutionTime =  new GregorianCalendar();
                    Date lastActualExecutionTime = triggerContext.lastActualExecutionTime();
                    nextExecutionTime.setTime(lastActualExecutionTime != null ? lastActualExecutionTime : new Date());
                    nextExecutionTime.add(Calendar.MILLISECOND, env.getProperty("fixed_delay", Integer.class));
                    return nextExecutionTime.getTime();
                }
        );
    }
}
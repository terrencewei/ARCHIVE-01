package com.terrencewei.schedule.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by terrence on 2018/05/23.
 */
@Configuration
public class ScheduledConfig implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        // taskRegistrar.setScheduler(setTaskExecutors());
    }



    /*@Bean(destroyMethod = "shutdown")
    public Executor setTaskExecutors() {
        // 3 threads thread pool
        return Executors.newScheduledThreadPool(3);
    }*/
}
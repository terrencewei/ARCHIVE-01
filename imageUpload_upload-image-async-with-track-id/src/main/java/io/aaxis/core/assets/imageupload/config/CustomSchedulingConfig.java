package io.aaxis.core.assets.imageupload.config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import io.aaxis.core.assets.imageupload.properties.CustomSchedulingProperties;

/**
 * Add custom schedule config
 *
 * Created by terrence on 2019/04/08.
 */
@Configuration
public class CustomSchedulingConfig implements SchedulingConfigurer {

    private static int DEFAULT_TASK_SCHEDULER_THREAD_POOL_SIZE = 3;

    @Autowired
    private CustomSchedulingProperties customSchedulingProperties;



    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        // using custom executor
        taskRegistrar.setScheduler(getTaskScheduler());
    }



    /**
     * Using scheduled thread pool instead of default single thread pool
     *
     * @return
     * @see org.springframework.scheduling.config.ScheduledTaskRegistrar#scheduleTasks()
     */
    @Bean(destroyMethod = "shutdown")
    public Executor getTaskScheduler() {
        return Executors.newScheduledThreadPool(customSchedulingProperties.getTaskSchedulerThreadPoolSize() != null ?
                customSchedulingProperties.getTaskSchedulerThreadPoolSize() :
                DEFAULT_TASK_SCHEDULER_THREAD_POOL_SIZE);
    }
}
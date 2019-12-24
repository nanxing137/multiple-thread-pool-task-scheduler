package com.acouchis.multisheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * 设置定时任务并行数
 * Created by gaopeng09 on 2019-04-28
 */
@Configuration
@EnableScheduling
public class MultipleThreadPoolScheduledConfiguration implements SchedulingConfigurer {

    @Autowired
    private TaskScheduler taskScheduler;

    @Bean
    public TaskScheduler getTaskSchedulerProxy() {
        return new MultipleThreadPoolTaskScheduler();
    }


    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

        taskRegistrar.setTaskScheduler(taskScheduler);


    }
}

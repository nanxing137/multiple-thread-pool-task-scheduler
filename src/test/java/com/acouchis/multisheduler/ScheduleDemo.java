package com.acouchis.multisheduler;

import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by gaopeng09 on 2019-12-24
 */
@EnableMultiScheduler
public class ScheduleDemo {
    @ScheduledThreadPool(cron = "0 0 8 * * ? ", name = "alarmPool", size = 2)
    public void alarm1() {
        // do something for you
    }

    @ScheduledThreadPool(cron = "0 0 8 * * ? ", name = "alarmPool")
    public void alarm2() {
        // do something for you
    }
}

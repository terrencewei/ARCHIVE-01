package com.terrencewei.schedule.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by terrence on 2018/05/23.
 */
@Component
public class Task5 {

    private static final Logger           log        = LoggerFactory.getLogger(Task5.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");



    @Scheduled(cron = "0 0/5 * * * ?")
    public void reportCurrentTime() {
        log.info("The time is now {}", dateFormat.format(new Date()));
    }
}
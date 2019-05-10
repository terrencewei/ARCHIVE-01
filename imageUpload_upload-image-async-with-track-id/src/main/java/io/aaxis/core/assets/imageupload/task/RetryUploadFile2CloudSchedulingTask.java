package io.aaxis.core.assets.imageupload.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.stereotype.Component;

import io.aaxis.core.assets.imageupload.service.UploadService;
import lombok.extern.slf4j.Slf4j;

/**
 * The schedule task of retry to upload file to cloud
 *
 * Created by terrence on 2019/04/08.
 *
 * @see ScheduledAnnotationBeanPostProcessor#postProcessAfterInitialization(java.lang.Object, java.lang.String)
 */
@Component
@Slf4j
public class RetryUploadFile2CloudSchedulingTask {

    @Autowired
    private UploadService uploadService;



    @Scheduled(cron = "0/10 * * * * *") // every 10 seconds
    public void work() {
        // task execution logic
        log.debug("work() retry upload file to cloud task work!");
        uploadService
                .findAllUploadErrorImages()
                .subscribe(pImage -> uploadService.startRetryUploadFile2CloudPipeline(pImage));
    }

}
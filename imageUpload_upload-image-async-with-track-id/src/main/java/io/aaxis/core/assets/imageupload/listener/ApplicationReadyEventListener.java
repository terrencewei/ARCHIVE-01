package io.aaxis.core.assets.imageupload.listener;

import java.util.Date;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Listen application ready event
 *
 * Created by terrence on 2019/04/02.
 */
@Component
@Slf4j
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("");
        log.info("");
        log.info("        Upload Application is ready");
        log.info("");
        log.info("");
        log.info(new Date().toString());
    }
}
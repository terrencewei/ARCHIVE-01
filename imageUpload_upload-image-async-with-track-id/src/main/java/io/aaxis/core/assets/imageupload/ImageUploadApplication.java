package io.aaxis.core.assets.imageupload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.aaxis.core.assets.imageupload.listener.ApplicationReadyEventListener;

/**
 * The image upload spring boot application
 */
@SpringBootApplication
@EnableScheduling
public class ImageUploadApplication {

    public static void main(String[] args) {
        SpringApplication
                .run(ImageUploadApplication.class, args)
                .addApplicationListener(new ApplicationReadyEventListener());
    }

}

package io.aaxis.core.assets.imageupload.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The custom schedule properties
 *
 * Created by terrence on 2019/04/08.
 *
 * @see io.aaxis.imageupload.config.CustomSchedulingConfig
 */
@Component
@ConfigurationProperties(prefix = "io.aaxis.imageupload.config.schedule")
@Getter
@Setter
@ToString
public class CustomSchedulingProperties {

    private Integer taskSchedulerThreadPoolSize;
}
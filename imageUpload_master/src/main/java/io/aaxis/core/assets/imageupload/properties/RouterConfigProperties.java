package io.aaxis.core.assets.imageupload.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The properties of router config
 *
 * Created by terrence on 2019/03/29.
 */
@Component
@ConfigurationProperties(prefix = "io.aaxis.imageupload.config.router-config")
@Getter
@Setter
@ToString
public class RouterConfigProperties {

    private String mediaUploadUrl;
    private String mediaSearchUrl;

}
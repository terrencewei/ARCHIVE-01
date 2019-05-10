package io.aaxis.core.assets.imageupload.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The properties of file part validator
 *
 * Created by terrence on 2019/04/02.
 */
@Component
@ConfigurationProperties(prefix = "io.aaxis.imageupload.config.file-part-validator")
@Getter
@Setter
@ToString
public class FilePartValidatorProperties {

    private String fileNameRegexp;

}
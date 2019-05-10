package io.aaxis.core.assets.imageupload.properties.invalid;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The error properties of files cast
 *
 * Created by terrence on 2019/04/11.
 */
@Component
@ConfigurationProperties(prefix = "io.aaxis.imageupload.config.validation.error.files-cast")
@Getter
@Setter
@ToString
public class UploadFilesCastErrProps {

    private String field;
    private String errorCode;
    private String defaultMessage;

}
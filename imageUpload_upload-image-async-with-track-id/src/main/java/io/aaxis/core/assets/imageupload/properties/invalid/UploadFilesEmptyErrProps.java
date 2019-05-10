package io.aaxis.core.assets.imageupload.properties.invalid;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The error properties of files empty
 *
 * Created by terrence on 2019/04/11.
 */
@Component
@ConfigurationProperties(prefix = "io.aaxis.imageupload.config.validation.error.files-empty")
@Getter
@Setter
@ToString
public class UploadFilesEmptyErrProps {

    private String errorCode;
    private String defaultMessage;

}
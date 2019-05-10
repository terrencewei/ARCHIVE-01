package io.aaxis.core.assets.imageupload.properties.invalid;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The error properties of files list empty
 *
 * Created by terrence on 2019/04/11.
 */
@Component
@ConfigurationProperties(prefix = "io.aaxis.imageupload.config.validation.error.files-list-empty")
@Getter
@Setter
@ToString
public class UploadFilesListEmptyErrProps {

    private String field;
    private String errorCode;
    private String defaultMessage;

}
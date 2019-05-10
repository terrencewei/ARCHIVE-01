package io.aaxis.core.assets.imageupload.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The properties of router handler
 *
 * Created by terrence on 2019/03/29.
 *
 * @see io.aaxis.imageupload.handler.UploadHandler
 */
@Component
@ConfigurationProperties(prefix = "io.aaxis.imageupload.config.upload-handler")
@Getter
@Setter
@ToString
public class UploadHandlerProperties {

    private String handleUploadRequestFilesKey;
    private String handleUploadRequestEntityIdKey;
    private String handleUploadRequestEntityTypeKey;
    private String handleUploadRequestMediaTypeKey;
    private String handleUploadRequestNameKey;
    private String handleUploadRequestAccountIdKey;
    private String handleUploadResponseSuccessMessage;

}
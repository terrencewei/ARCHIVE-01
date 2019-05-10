package io.aaxis.core.assets.imageupload.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The properties of upload file storage
 *
 * Created by terrence on 2019/03/29.
 *
 * @see io.aaxis.imageupload.util.FileUtil
 */
@Component
@ConfigurationProperties(prefix = "io.aaxis.imageupload.config.upload-file-storage")
@Getter
@Setter
@ToString
public class UploadFileStorageProperties {

    protected static final String BACKUP_FILE_LOCATION_DEFAULT = "E:\\webflux\\tmp\\upload-default\\";

    private String backupFileLocation = BACKUP_FILE_LOCATION_DEFAULT;
    private String imageClouldLocation;
    private String successUploadFileSuffix;

}
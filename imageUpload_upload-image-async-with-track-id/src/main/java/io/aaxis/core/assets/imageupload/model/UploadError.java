package io.aaxis.core.assets.imageupload.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Error type enum
 *
 * Created by terrence on 2019/04/02.
 */
@Getter
@AllArgsConstructor
public enum UploadError {

    BACKUP_FILE_ERROR, UPLOAD_CLOUD_ERROR, SAVE_MONGODB_ERROR;

}
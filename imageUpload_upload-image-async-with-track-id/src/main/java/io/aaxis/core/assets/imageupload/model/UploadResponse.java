package io.aaxis.core.assets.imageupload.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Upload Response
 */
@Getter
@AllArgsConstructor
@Setter
public class UploadResponse {

    private String traceId;
    private String path;
    private int    status;
    private String message;
    private String error;
    private Date   timestamp;



    public UploadResponse() {
        timestamp = new Date();
    }

}
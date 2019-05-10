package io.aaxis.core.assets.imageupload.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import io.aaxis.core.assets.imageupload.model.UploadResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * Some common response process logic
 *
 * Created by terrence on 2019/04/01.
 */
@Service
@Slf4j
public class ResponseService {

    /**
     * Build success response
     *
     * @param path        request url
     * @param pSuccessMsg success message
     * @return the resposne
     */
    public UploadResponse buildSuccess(String path, String pSuccessMsg ) {
        UploadResponse response = new UploadResponse();
        response.setPath(path);
        response.setStatus(HttpStatus.ACCEPTED.value());
        response.setMessage(pSuccessMsg);
        return response;
    }



    /**
     * Build error response
     *
     * @param path      request url
     * @param pErrorMsg error message
     * @return the resposne
     */
    public UploadResponse buildError(String path, String pErrorMsg) {
        UploadResponse response = new UploadResponse();
        response.setPath(path);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage(pErrorMsg);
        return response;
    }



    /**
     * Get http status code from response map
     *
     * @param pResponse the upload response
     * @return http status code
     */
    public int getStatus(UploadResponse pResponse) {
        Assert.notNull(pResponse, "pResponse is required");
        int status = pResponse.getStatus();
        Assert.notNull(status, "status in pResponse is required");
        return status;
    }

}
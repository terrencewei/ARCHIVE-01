package io.aaxis.core.assets.imageupload.validation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.stereotype.Component;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import io.aaxis.core.assets.imageupload.model.UploadRequest;
import io.aaxis.core.assets.imageupload.properties.UploadHandlerProperties;
import io.aaxis.core.assets.imageupload.properties.invalid.UploadFilesCastErrProps;
import io.aaxis.core.assets.imageupload.properties.invalid.UploadFilesEmptyErrProps;
import io.aaxis.core.assets.imageupload.properties.invalid.UploadFilesListEmptyErrProps;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by terrence on 2019/04/11.
 */
@Component
@Slf4j
public class UploadRequestValidator implements Validator {

    @Autowired
    private UploadHandlerProperties      uploadHandlerProperties;
    @Autowired
    private UploadFilesEmptyErrProps     uploadFilesEmptyErrProps;
    @Autowired
    private UploadFilesListEmptyErrProps uploadFilesListEmptyErrProps;
    @Autowired
    private UploadFilesCastErrProps      uploadFilesCastErrProps;
    @Autowired
    private FilePartValidator            filePartValidator;



    @Override
    public boolean supports(Class<?> clazz) {
        return UploadRequest.class.isAssignableFrom(clazz);

    }



    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, uploadHandlerProperties.getHandleUploadRequestFilesKey(),
                uploadFilesEmptyErrProps.getErrorCode(), uploadFilesEmptyErrProps.getDefaultMessage());
        if (errors.hasErrors()) {
            return;
        }
        UploadRequest request = (UploadRequest) target;
        List<Part> files = request.getFiles();
        if (files.isEmpty()) {
            errors.rejectValue(uploadFilesListEmptyErrProps.getField(), uploadFilesListEmptyErrProps.getErrorCode(),
                    uploadFilesListEmptyErrProps.getDefaultMessage());
        }

        try {
            files
                    .stream()
                    .map(pPart -> (FilePart) pPart)
                    .forEach(pFilePart -> {
                        Errors filepartErrors = new DirectFieldBindingResult(pFilePart, errors.getObjectName());
                        filePartValidator.validate(pFilePart, filepartErrors);
                        if (filepartErrors != null && filepartErrors.hasErrors()) {
                            errors.addAllErrors(filepartErrors);
                        }
                    });
        } catch (ClassCastException e) {
            errors.rejectValue(uploadFilesCastErrProps.getField(), uploadFilesCastErrProps.getErrorCode(),
                    uploadFilesCastErrProps.getDefaultMessage());
        }

    }
}
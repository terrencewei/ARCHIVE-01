package io.aaxis.core.assets.imageupload.validation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.stereotype.Component;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import io.aaxis.core.assets.imageupload.model.UploadRequest;
import io.aaxis.core.assets.imageupload.util.ACValidationUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by terrence on 2019/04/11.
 */
@Component
@Slf4j
public class UploadRequestValidator implements Validator {

    @Autowired
    private FilePartValidator filePartValidator;
    @Autowired
    private ACValidationUtils ACValidationUtils;



    @Override
    public boolean supports(Class<?> clazz) {
        return UploadRequest.class.isAssignableFrom(clazz);

    }



    @Override
    public void validate(Object target, Errors errors) {
        //  accountId
        ACValidationUtils.rejectIfEmpty(errors, "account-id");

        //  entityId
        ACValidationUtils.rejectIfEmpty(errors, "entity-id");

        //  entityType
        ACValidationUtils.rejectIfEmpty(errors, "entity-type");

        //  mediaType
        ACValidationUtils.rejectIfEmpty(errors, "media-type");

        // files
        ACValidationUtils.rejectIfEmpty(errors, "files-empty");
        if (errors.hasErrors()) {
            return;
        }
        UploadRequest request = (UploadRequest) target;
        List<Part> files = request.getFiles();
        if (files.isEmpty()) {
            ACValidationUtils.rejectValue(errors, "files-list-empty");
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
            ACValidationUtils.rejectValue(errors, "files-cast");
        }

    }
}
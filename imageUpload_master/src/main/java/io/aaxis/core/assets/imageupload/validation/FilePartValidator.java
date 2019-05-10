package io.aaxis.core.assets.imageupload.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import io.aaxis.core.assets.imageupload.properties.FilePartValidatorProperties;
import io.aaxis.core.assets.imageupload.util.ACValidationUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * File part validator
 *
 * Created by terrence on 2019/04/02.
 */
@Component
@Slf4j
public class FilePartValidator implements Validator {

    @Autowired
    private FilePartValidatorProperties filePartValidatorProperties;
    @Autowired
    private ACValidationUtils           ACValidationUtils;



    @Override
    public boolean supports(Class<?> pClass) {
        log.debug("supports() pClass:{}", pClass);
        return FilePart.class.isAssignableFrom(pClass);
    }



    @Override
    public void validate(Object pObj, Errors pErrors) {
        String regexp = filePartValidatorProperties.getFileNameRegexp();
        log.debug("validate() filePart:{}, regexp:{}", pObj, regexp);
        FilePart filePart = (FilePart) pObj;
        String fileName = filePart.filename();
        if (StringUtils.isBlank(fileName) || !fileName.matches(regexp)) {
            ACValidationUtils.rejectValue(pErrors, "file-name", fileName);
        }
    }
}
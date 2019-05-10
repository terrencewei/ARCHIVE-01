package io.aaxis.core.assets.imageupload.util;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * Validation utils to handle validation properties
 *
 * Created by terrence on 2019/04/12.
 */
@Component
@Slf4j
public class ACValidationUtils {

    private static final String PROPS_BASE_VALIDATION_ERROR = "io.aaxis.imageupload.config.validation.error";

    @Autowired
    private Environment env;



    /**
     * reject if empty
     *
     * @param errors validation errors
     * @param pKey   property key
     */
    public void rejectIfEmpty(Errors errors, String pKey) {
        ValidationUtils.rejectIfEmpty(errors, getEnvValue(pKey, "field"), getEnvValue(pKey, "errorCode"),
                getEnvValue(pKey, "defaultMessage"));
    }



    /**
     * reject value
     *
     * @param errors validation errors
     * @param pKey   property key
     */
    public void rejectValue(Errors errors, String pKey) {
        errors.rejectValue(getEnvValue(pKey, "field"), getEnvValue(pKey, "errorCode"),
                getEnvValue(pKey, "defaultMessage"));
    }



    /**
     * reject value
     *
     * @param errors validation errors
     * @param pKey   property key
     * @param pArgs  arguments
     */
    public void rejectValue(Errors errors, String pKey, String... pArgs) {
        errors.rejectValue(getEnvValue(pKey, "field"), getEnvValue(pKey, "errorCode"),
                MessageFormat.format(getEnvValue(pKey, "defaultMessage"), (Object[]) pArgs));
    }



    private String getEnvValue(String pKey, String pName) {
        return env.getProperty(PROPS_BASE_VALIDATION_ERROR + "." + pKey + "." + pName);
    }
}
package io.aaxis.core.assets.imageupload.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import io.aaxis.core.assets.imageupload.model.UploadConstant;

/**
 * Handling errors
 *
 * Created by terrence on 2019/04/11.
 */
public class ErrorUtils {

    /**
     * If has errors
     *
     * @param pErrors the validation error
     * @return true if has validation error
     */
    public static boolean hasErrors(Errors pErrors) {
        return pErrors == null || pErrors
                .getAllErrors()
                .isEmpty();
    }



    /**
     * Format validation errors to JSON String
     *
     * @param pErrors validation errors
     * @return the formatted error string
     */
    public static String toJson(Errors pErrors) {
        if (pErrors == null) {
            return UploadConstant.EMPTY;
        }
        Map<String, Object> errors = new HashMap<>();
        if (pErrors.hasFieldErrors()) {
            Map<String, Object> fieldErrors = new HashMap<>();
            for (FieldError fieldError : pErrors.getFieldErrors()) {
                fieldErrors.put(fieldError.getCode(), fieldError.getDefaultMessage());
            }
            errors.put(UploadConstant.ERROR_MSG_KEY_FIELD_ERRORS, fieldErrors);
        }
        if (pErrors.hasGlobalErrors()) {
            Map<String, Object> globalErrors = new HashMap<>();
            for (ObjectError objectError : pErrors.getFieldErrors()) {
                globalErrors.put(objectError.getObjectName(), objectError.toString());
            }
            errors.put(UploadConstant.ERROR_MSG_KEY_FIELD_ERRORS, globalErrors);
        }
        return JsonUtils.toJson(errors);
    }

}
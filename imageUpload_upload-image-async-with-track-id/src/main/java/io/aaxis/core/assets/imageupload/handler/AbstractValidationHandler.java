package io.aaxis.core.assets.imageupload.handler;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.Part;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;

import io.aaxis.core.assets.imageupload.util.ErrorUtils;
import reactor.core.publisher.Mono;

/**
 * The abstract balidation handler to handle request and return response
 *
 * Created by terrence on 2019/04/11.
 *
 * @param <T> the validation class type
 * @param <U> the validators
 */
public abstract class AbstractValidationHandler<T, U extends Validator> {

    private final Class<T> validationClass;
    private final U        validator;



    protected AbstractValidationHandler(Class<T> clazz, U validator) {
        this.validationClass = clazz;
        this.validator = validator;
    }



    /**
     * Handle the request body
     *
     * @param pRequest        the request
     * @param pAdditionalData the additional data holder when process validation event
     * @return the server response
     */
    public final Mono<ServerResponse> handleRequestBody(final ServerRequest pRequest,
            Map<String, Object> pAdditionalData) {
        return pRequest
                .bodyToMono(this.validationClass)
                .flatMap(pValidation -> doValidation(pRequest, pAdditionalData, pValidation,
                        new BeanPropertyBindingResult(pValidation, this.validationClass.getName())));
    }



    /**
     * Handle the request multipart data
     *
     * MUST implements the method multipartDataToMono()
     *
     * @param pRequest        the request
     * @param pAdditionalData the additional data holder when process validation event
     * @return the server response
     * @see AbstractValidationHandler#multipartDataToMono(org.springframework.util.MultiValueMap, java.lang.Class)
     */
    public final Mono<ServerResponse> handleRequestMultipartData(final ServerRequest pRequest,
            Map<String, Object> pAdditionalData) {
        return pRequest
                .multipartData()
                .flatMap(pMultiValueMap -> multipartDataToMono(pMultiValueMap, this.validationClass))
                .flatMap(pValidation -> doValidation(pRequest, pAdditionalData, pValidation,
                        new DirectFieldBindingResult(pValidation, this.validationClass.getName())));
    }



    /**
     * NOTICE: sub validation handler should implements this method if wants to handle multi part data
     *
     * @param pMultiValueMap   the multipart data map
     * @param pValidationClass the validation class
     * @return the mono of validation class
     */
    protected Mono<? extends T> multipartDataToMono(MultiValueMap<String, Part> pMultiValueMap,
            Class<T> pValidationClass) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "multipartDataToMono() is not implemented yet");
    }



    /**
     * The Validation passed event, process success response
     *
     * @param pValidation     the validation object
     * @param pRequest        the request
     * @param pAdditionalData the additional data holder when process validation event
     * @return server response
     */
    protected abstract Mono<ServerResponse> onValidationPassed(T pValidation, final ServerRequest pRequest,
            Map<String, Object> pAdditionalData);



    /**
     * The default implementation of validation error event
     *
     * @param pErrors         the validation errors
     * @param pValidation     the validation object
     * @param pRequest        the request
     * @param pAdditionalData the additional data holder when process validation event
     * @return server response
     */
    protected Mono<ServerResponse> onValidationErrors(Errors pErrors, T pValidation, final ServerRequest pRequest,
            Map<String, Object> pAdditionalData) {
        return ServerResponse
                .badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(ErrorUtils.toJson(pErrors)), String.class);
    }



    private Mono<? extends ServerResponse> doValidation(ServerRequest pRequest, Map<String, Object> pAdditionalData,
            T pValidation, Errors pErrors) {
        this.validator.validate(pValidation, pErrors);
        return ErrorUtils.hasErrors(pErrors) ?
                onValidationPassed(pValidation, pRequest, pAdditionalData) :
                onValidationErrors(pErrors, pValidation, pRequest, pAdditionalData);
    }

}
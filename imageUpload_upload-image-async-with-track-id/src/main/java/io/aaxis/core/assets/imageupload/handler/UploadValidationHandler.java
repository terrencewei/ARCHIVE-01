package io.aaxis.core.assets.imageupload.handler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.Errors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import io.aaxis.core.assets.imageupload.model.UploadConstant;
import io.aaxis.core.assets.imageupload.model.UploadRequest;
import io.aaxis.core.assets.imageupload.model.UploadResponse;
import io.aaxis.core.assets.imageupload.properties.UploadHandlerProperties;
import io.aaxis.core.assets.imageupload.service.ResponseService;
import io.aaxis.core.assets.imageupload.service.UploadService;
import io.aaxis.core.assets.imageupload.util.ErrorUtils;
import io.aaxis.core.assets.imageupload.validation.UploadRequestValidator;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The image upload validation handler
 *
 * Created by terrence on 2019/04/11.
 */
@Component
@Slf4j
public class UploadValidationHandler extends AbstractValidationHandler<UploadRequest, UploadRequestValidator> {

    @Autowired
    private ResponseService         responseService;
    @Autowired
    private UploadHandlerProperties uploadHandlerProperties;
    @Autowired
    private UploadService           uploadService;



    protected UploadValidationHandler(@Autowired UploadRequestValidator pValidator) {
        super(UploadRequest.class, pValidator);
    }



    @Override
    protected Mono<? extends UploadRequest> multipartDataToMono(MultiValueMap<String, Part> pMultiValueMap,
            Class<UploadRequest> pValidationClass) {
        return Mono.just(
                new UploadRequest(pMultiValueMap.get(uploadHandlerProperties.getHandleUploadRequestFilesKey())));
    }



    @Override
    protected Mono<ServerResponse> onValidationPassed(UploadRequest pValidation, ServerRequest pRequest,
            Map<String, Object> pAdditionalData) {

        String trackId = (String) pAdditionalData.get(UploadConstant.ADDITIONAL_DATA_TRACK_ID);

        UploadResponse response = responseService.buildSuccess(pRequest.path(),
                uploadHandlerProperties.getHandleUploadResponseSuccessMessage(), trackId);

        Flux<FilePart> inputFileParts = Flux
                .fromIterable(pValidation.getFiles())
                .cast(FilePart.class);

        log.debug("upload() inputFileParts begin to publish");

        inputFileParts.subscribe(
                pFilePart -> uploadService.startUploadPipeline(pFilePart.filename(), pFilePart.content(), trackId),
                uploadService.addException("upload() inputFileParts.subscribe, error occurs."));

        return ServerResponse
                .status(responseService.getStatus(response))
                .body(inputFileParts.then(Mono.just(response)), UploadResponse.class);
    }



    @Override
    protected Mono<ServerResponse> onValidationErrors(Errors pErrors, UploadRequest pValidation, ServerRequest pRequest,
            Map<String, Object> pAdditionalData) {
        UploadResponse response = responseService.buildError(pRequest.path(), ErrorUtils.toJson(pErrors));

        return ServerResponse
                .status(responseService.getStatus(response))
                .body(Mono.just(response), UploadResponse.class);
    }
}
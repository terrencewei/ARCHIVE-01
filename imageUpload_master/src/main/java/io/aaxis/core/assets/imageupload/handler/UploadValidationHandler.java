package io.aaxis.core.assets.imageupload.handler;


import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.FormFieldPart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.Errors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import io.aaxis.core.assets.imageupload.model.MediaType;
import io.aaxis.core.assets.imageupload.model.Media;
import io.aaxis.core.assets.imageupload.model.UploadRequest;
import io.aaxis.core.assets.imageupload.model.UploadRequestEntityType;
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
    protected UploadHandlerProperties uploadHandlerProperties;
    @Autowired
    private UploadService           uploadService;



    protected UploadValidationHandler(@Autowired UploadRequestValidator pValidator) {
        super(UploadRequest.class, pValidator);
    }


    @Override
    protected Mono<? extends UploadRequest> multipartDataToMono(MultiValueMap<String, Part> pMultiValueMap) {
    	
    	UploadRequest uploadRequest = new UploadRequest();
    	uploadRequest.setFiles(pMultiValueMap.get(uploadHandlerProperties.getHandleUploadRequestFilesKey()));
    	FormFieldPart entityTypePart = (FormFieldPart) pMultiValueMap.getFirst(uploadHandlerProperties.getHandleUploadRequestEntityTypeKey());
    	if (null != entityTypePart) {
    		uploadRequest.setEntityType(UploadRequestEntityType.valueOf(entityTypePart.value()));
		}
    	FormFieldPart entityIdPart = (FormFieldPart) pMultiValueMap.getFirst(uploadHandlerProperties.getHandleUploadRequestEntityIdKey());
    	if (null != entityIdPart) {
    		uploadRequest.setEntityId(entityIdPart.value());
    	}
    	FormFieldPart mediaTypePart = (FormFieldPart) pMultiValueMap.getFirst(uploadHandlerProperties.getHandleUploadRequestMediaTypeKey());
    	if (null != mediaTypePart) {
    		uploadRequest.setType(MediaType.valueOf(mediaTypePart.value()));
    	}
    	FormFieldPart accountIdPart = (FormFieldPart) pMultiValueMap.getFirst(uploadHandlerProperties.getHandleUploadRequestAccountIdKey());
    	if (null != accountIdPart) {
    		uploadRequest.setAccountId(accountIdPart.value());
    	}
    	
    	FormFieldPart namePart = (FormFieldPart) pMultiValueMap.getFirst(uploadHandlerProperties.getHandleUploadRequestNameKey());
    	if (null != namePart) {
    		uploadRequest.setName(namePart.value());
    	}
        return Mono.just(uploadRequest);
    }



    @Override
    protected Mono<ServerResponse> onValidationPassed(UploadRequest uploadRequest, ServerRequest pRequest,
            Map<String, Object> pAdditionalData) {


        Flux<FilePart> inputFileParts = Flux
                .fromIterable(uploadRequest.getFiles())
                .cast(FilePart.class);

        log.debug("upload() inputFileParts begin to publish");

        Flux<Media> media = inputFileParts.flatMap(
                pFilePart -> uploadService.startUploadPipeline(pFilePart, uploadRequest));
        
        return ok()
                .contentType(org.springframework.http.MediaType.APPLICATION_STREAM_JSON)
                .body(media, Media.class);
        

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
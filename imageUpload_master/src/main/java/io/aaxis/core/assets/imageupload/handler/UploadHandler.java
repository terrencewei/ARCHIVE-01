package io.aaxis.core.assets.imageupload.handler;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import io.aaxis.core.assets.imageupload.model.SearchRequest;
import io.aaxis.core.assets.imageupload.model.SearchResponse;
import io.aaxis.core.assets.imageupload.service.UploadService;
import io.aaxis.core.assets.imageupload.validation.UploadRequestValidator;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * The router handler to handle each router request and return response
 */
@Component
@Slf4j
public class UploadHandler extends UploadValidationHandler{

	protected UploadHandler(@Autowired UploadRequestValidator pValidator) {
		super(pValidator);
		
	}
	@Autowired
	private UploadService           uploadService;
	
    /**
     * Handling media files upload action
     *
     * @param request the WebFlux reactive server request
     * @return the WebFlux reactive server response
     */
    public Mono<ServerResponse> upload(ServerRequest request) {

        log.debug("upload() entered");
        Map<String, Object> additionalData = new HashMap<>();
        return handleRequestMultipartData(request, additionalData);
    }



    /**
     * Handling media files search action
     *
     * @param request the WebFlux reactive server request
     * @return the WebFlux reactive server response, media type is text/event-stream
     */
    public Mono<ServerResponse> search(ServerRequest request) {
    	
    	Mono<SearchRequest> searchRequest = request.bodyToMono(SearchRequest.class);
    	Mono<SearchResponse> response = searchRequest.flatMap(search -> uploadService.handleMediaSearch(search));
        return ok()
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(response, SearchResponse.class);
              
    }


}
package io.aaxis.core.assets.imageupload.handler;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import io.aaxis.core.assets.imageupload.model.Image;
import io.aaxis.core.assets.imageupload.model.UploadConstant;
import io.aaxis.core.assets.imageupload.service.UploadService;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * The router handler to handle each router request and return response
 */
@Component
@Slf4j
public class UploadHandler {

    @Autowired
    private UploadService           uploadService;
    @Autowired
    private UploadValidationHandler uploadValidationHandler;



    /**
     * Handling image files upload action
     *
     * @param request the WebFlux reactive server request
     * @return the WebFlux reactive server response
     */
    public Mono<ServerResponse> upload(ServerRequest request) {

        log.debug("upload() entered");

        String trackId = UUID
                .randomUUID()
                .toString();

        Map<String, Object> additionalData = new HashMap<>();
        additionalData.put(UploadConstant.ADDITIONAL_DATA_TRACK_ID, trackId);
        return uploadValidationHandler.handleRequestMultipartData(request, additionalData);
    }



    /**
     * Handling image files download action
     *
     * @param request the WebFlux reactive server request
     * @return the WebFlux reactive server response, media type is text/event-stream
     */
    public Mono<ServerResponse> download(ServerRequest request) {
        return ok()
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(uploadService.findAllFromMongoDB(), Image.class);
    }



    /**
     * Track image upload by track id
     *
     * @param request the WebFlux reactive server request
     * @return the WebFlux reactive server response, media type is text/event-stream
     */
    public Mono<ServerResponse> track(ServerRequest request) {
        String trackId = request
                .pathVariable("trackId")
                .trim();
        return ok()
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(uploadService.findAllByTrackId(trackId), Image.class);
    }

}
package io.aaxis.core.assets.imageupload.config;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;


import io.aaxis.core.assets.imageupload.handler.UploadHandler;
import io.aaxis.core.assets.imageupload.properties.RouterConfigProperties;

/**
 * The router config
 */
@Configuration
public class RouterConfig {

    @Autowired
    private UploadHandler uploadHandler;

    @Autowired
    private RouterConfigProperties routerConfigProperties;



    /**
     * The router of image upload API
     *
     * @return
     */
    @Bean
    public RouterFunction<ServerResponse> imageUploadRouter() {
    	
    	return
    		    route(POST(routerConfigProperties.getMediaUploadUrl()), uploadHandler::upload)
    		        .andRoute(POST(routerConfigProperties.getMediaSearchUrl()).and(accept(MediaType.APPLICATION_JSON)), uploadHandler::search);
        
    }
}
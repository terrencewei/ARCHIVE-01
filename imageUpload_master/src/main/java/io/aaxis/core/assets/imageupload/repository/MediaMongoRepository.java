package io.aaxis.core.assets.imageupload.repository;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import io.aaxis.core.assets.imageupload.model.Media;
import reactor.core.publisher.Flux;

/**
 * The media MongoDB repository
 */
public interface MediaMongoRepository extends ReactiveMongoRepository<Media, String> {

    // NOTICE: this annotation is used to continually query all data from MongoDB
    // to use this feature, the corresponding collection MUST be "capped" 
    //    @Tailable
    //    Flux<Image> findBy();

    Flux<Media> findAllById(String pTrackId);
    

}

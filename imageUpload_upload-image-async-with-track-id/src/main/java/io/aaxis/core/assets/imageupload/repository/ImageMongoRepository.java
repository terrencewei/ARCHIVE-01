package io.aaxis.core.assets.imageupload.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import io.aaxis.core.assets.imageupload.model.Image;
import reactor.core.publisher.Flux;

/**
 * The image MongoDB repository
 */
public interface ImageMongoRepository extends ReactiveMongoRepository<Image, String> {

    // NOTICE: this annotation is used to continually query all data from MongoDB
    // to use this feature, the corresponding collection MUST be "capped" 
    //    @Tailable
    //    Flux<Image> findBy();

    Flux<Image> findAllByTrackId(String pTrackId);

    Flux<Image> findAllByUploadErrorNotNull();
}

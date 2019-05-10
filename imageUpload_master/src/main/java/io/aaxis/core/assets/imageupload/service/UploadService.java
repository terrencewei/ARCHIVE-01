package io.aaxis.core.assets.imageupload.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import io.aaxis.core.assets.imageupload.model.Media;
import io.aaxis.core.assets.imageupload.model.SearchRequest;
import io.aaxis.core.assets.imageupload.model.SearchResponse;
import io.aaxis.core.assets.imageupload.model.UploadRequest;
import io.aaxis.core.assets.imageupload.repository.MediaMongoRepository;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class UploadService {
    
    @Autowired
    private GenericStorageService diskStorageService;
    
    @Autowired
    private MediaMongoRepository imageMongoRepository;
    
    @Autowired
    private ReactiveMongoTemplate mongoTemplate;



    /**
     * Start upload file pipeline
     *
     * 1.Upload media file
     * 2.Save mongdb
     * 
     *
     * @param filePart    upload file
     * @param uploadRequest    the request wrapper object
     */
    public Mono<Media> startUploadPipeline(FilePart filePart, UploadRequest uploadRequest) {
    	
        log.debug("startUploadPipeline() for file :{}", filePart.filename());
        
        return doUploadFiles(filePart, uploadRequest).flatMap(media -> doSaveImageInMongoDB(media));
    }

    /**
     * Upload file to storage
     * 
     * @param filePart
     * @param uploadRequest
     * @return
     */
	private Mono<Media> doUploadFiles(FilePart filePart, UploadRequest uploadRequest) {
		String mediaId = UUID.randomUUID().toString();
        
        Media media = new Media(mediaId, filePart.filename(), uploadRequest.getEntityType().name(), uploadRequest.getEntityId(), uploadRequest.getAccountId());
        media.setMimeType(filePart.headers().getContentType().getType());
        media.setType(uploadRequest.getType().name());
        media.setName(uploadRequest.getName());
        diskStorageService.writeMediaFile(media, filePart.content());
        //media.setPath(path);
        return Mono.just(media);
	}

    /**
     * Save image to mongodb
     *
     * @param media
     * @return
     */
    private Mono<Media> doSaveImageInMongoDB(Media media) {
    	media.setActive(true);
    	media.setLastModifiedDate(new Date());

        return imageMongoRepository.save(media);
    }
	

	
	private Query getQuery(SearchRequest searchRequest) {
		
		Criteria criteria = new Criteria();
		if (!StringUtils.isEmpty(searchRequest.getAccountId())) {
			criteria.and("accountId").is(searchRequest.getAccountId());
		}
		if (!StringUtils.isEmpty(searchRequest.getType())) {
			criteria.and("type").is(searchRequest.getType());
		}
		if (!StringUtils.isEmpty(searchRequest.getEntityId())) {
			criteria.and("entityId").is(searchRequest.getEntityId());
		}
		if (!StringUtils.isEmpty(searchRequest.getEntityType())) {
			criteria.and("entityType").is(searchRequest.getEntityType());
		}
        Query query = new Query(criteria)
                .skip((searchRequest.getPage() - 1) * searchRequest.getSize())
                .limit(searchRequest.getSize());
        return query;
    }
	
	/**
	 * Search media
	 *  
	 * @param searchRequest
	 * @return
	 */
	public Mono<SearchResponse> handleMediaSearch(SearchRequest searchRequest) {

        Query query = getQuery(searchRequest);
        Flux<Media> list = mongoTemplate.find(query, Media.class);
        Mono<SearchResponse> searchResponseMono = list
                .map(pMedia -> {
                    SearchResponse searchResponse = new SearchResponse(searchRequest.getPage(),searchRequest.getSize());
                    searchResponse.setTotalCount(1);
                    List<Media> media = new ArrayList<Media>();
                    media.add(pMedia);
                    searchResponse.setEntities(media);
                    return searchResponse;
                })
                .reduce((pSearchResponse1, pSearchResponse2) -> {
                    pSearchResponse2.setTotalCount(pSearchResponse2.getTotalCount() + pSearchResponse1.getTotalCount());
                    List<Media> medias = pSearchResponse2.getEntities();
                    medias.addAll(pSearchResponse1.getEntities());
                    pSearchResponse2.setEntities(medias);
                    return pSearchResponse2;
                }).map(response -> {
                    mongoTemplate.count(query,Media.class).map(pLong -> {
                        response.setTotalCount(pLong);
                        return pLong;
                    }).block();
                    // paging result
		        	response.setTotalPage(response.getTotalCount() / response.getSize());
		        	if(response.getTotalCount() % response.getSize() != 0) {
		        		response.setTotalPage(response.getTotalCount() / response.getSize() + 1);
		        	}
		        	return response;
                });
        return searchResponseMono;
    }



}
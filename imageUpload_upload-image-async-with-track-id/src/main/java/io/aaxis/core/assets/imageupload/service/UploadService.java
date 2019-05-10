package io.aaxis.core.assets.imageupload.service;

import java.util.Date;
import java.util.function.Consumer;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Service;

import io.aaxis.core.assets.imageupload.model.Image;
import io.aaxis.core.assets.imageupload.model.UploadError;
import io.aaxis.core.assets.imageupload.repository.ImageMongoRepository;
import io.aaxis.core.assets.imageupload.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class UploadService {
    @Autowired
    private FileUtil             fileUtil;
    @Autowired
    private ImageMongoRepository imageMongoRepository;



    /**
     * Start upload file pipeline
     *
     * 1.Save uploading media to disk
     * 2.Upload media to cloud
     * 3.Save media that have uploaded to cloud successfully.
     * 4.Save uploaded result
     *
     * @param pFileName    the input file name
     * @param pFileContent the input file content
     * @param pTrackId     the track id
     */
    public void startUploadPipeline(String pFileName, Flux<DataBuffer> pFileContent, String pTrackId) {
        log.debug("startUploadPipeline() for fileName:{}, pTrackId:{}", pFileName, pTrackId);
        doInsertImageInMongoDB(pFileName, pTrackId).subscribe(
                pImage -> postInsertImageInMongoDB(pFileName, pFileContent, pImage),
                addException("error occurs in startUploadPipeline() doInsertImageInMongoDB"));
    }



    /**
     * Start retry upload file to cloud pipeline
     *
     * 1.Find all failed image in disk
     * 2.Upload failed disk media to cloud
     * 3.Save media that have uploaded to cloud successfully.
     * 4.Save uploaded result
     */
    public void startRetryUploadFile2CloudPipeline(Image pImage) {
        log.debug("startRetryUploadFile2CloudPipeline() for pImage:{}", pImage);
        String fileName = pImage.getName();
        doUploadFile2Cloud(pImage, fileName).subscribe(image -> postUploadFile2Cloud(image, fileName),
                addException("startUploadPipeline() doUploadFile2Cloud.subscribe, error occurs.",
                        UploadError.UPLOAD_CLOUD_ERROR, pImage));
    }



    /**
     * Find all image from mongo DB
     *
     * @return the images
     */
    public Flux<Image> findAllFromMongoDB() {
        return imageMongoRepository.findAll();
    }



    /**
     * Find all image from mongo DB by track id
     *
     * @return the images
     */
    public Flux<Image> findAllByTrackId(String pTrackId) {
        return imageMongoRepository.findAllByTrackId(pTrackId);
    }



    /**
     * Find all upload error images
     *
     * @return the upload error images
     */
    public Flux<Image> findAllUploadErrorImages() {
        return imageMongoRepository
                .findAllByUploadErrorNotNull()
                .filter(pImage -> StringUtils.isNotBlank(pImage.getTrackId()));
    }



    private Disposable postInsertImageInMongoDB(String pFileName, Flux<DataBuffer> pFileContent, Image pImage) {
        return doBackupFile2Disk(pImage, pFileName, pFileContent).subscribe(
                image -> postBackupFile2Disk(image, pFileName),
                addException("startUploadPipeline() doBackupFile2Disk.subscribe, error occurs.",
                        UploadError.BACKUP_FILE_ERROR, pImage));
    }



    private Disposable postBackupFile2Disk(Image pImage, String pFileName) {
        return doUploadFile2Cloud(pImage, pFileName).subscribe(image -> postUploadFile2Cloud(image, pFileName),
                addException("startUploadPipeline() doUploadFile2Cloud.subscribe, error occurs.",
                        UploadError.UPLOAD_CLOUD_ERROR, pImage));
    }



    private Disposable postUploadFile2Cloud(Image pImage, String pFileName) {
        return doBackupCloudUploadSuccessImage2Disk(pImage, pFileName).subscribe(
                successImage -> postBackupCloudUploadSuccessImage2Disk(pFileName, successImage),
                addException("startUploadPipeline() doBackupCloudUploadSuccessImage2Disk.subscribe, error occurs.",
                        UploadError.BACKUP_FILE_ERROR, pImage));
    }



    private Disposable postBackupCloudUploadSuccessImage2Disk(String pFileName, Image successImage) {
        return doSaveImageInMongoDB(successImage).subscribe(image -> postSaveImageInMongoDB(image, pFileName),
                addException("startUploadPipeline() doUpdateImageInMongoDB.subscribe, error occurs.",
                        UploadError.SAVE_MONGODB_ERROR, successImage));
    }



    private void postSaveImageInMongoDB(Image pImage, String pFileName) {
        log.debug("postSaveImageInMongoDB() enter {}", pImage);
        fileUtil.deleteBackupFile(pImage.getTrackId(), pFileName);
    }



    /**
     * Do create file in mongo DB
     *
     * @param pFileName image file name
     * @param pTrackId  track id
     * @return created image in mongo DB
     */
    private Mono<Image> doInsertImageInMongoDB(String pFileName, String pTrackId) {
        return imageMongoRepository.insert(new Image(pFileName, pTrackId));
    }



    /**
     * Backup file to disk as temp file
     *
     * @param pImage    the image created in mongo DB
     * @param pFileName the image file image
     * @param pFileName the image file content
     * @return pImage
     */
    private Mono<Image> doBackupFile2Disk(Image pImage, String pFileName, Flux<DataBuffer> pFileContent) {
        try {
            fileUtil.writeFile2Disk(pImage.getTrackId(), pFileName, pFileContent);
        } catch (Exception e) {
            log.error("doBackupFile2Disk() error occurs.", e);
        }
        return Mono.just(pImage);
    }



    /**
     * upload file part to cloud
     *
     * @param pImage    the created image in mongo DB
     * @param pFileName the uploaded file name
     * @return the created image with uploaded image path generated by cloud server
     */
    private Mono<Image> doUploadFile2Cloud(Image pImage, String pFileName) {
        try {
            // mock cloud path
            pImage.setPath(fileUtil.uploadFileStorageProperties.getImageClouldLocation() + pImage.getName());
            pImage.setExtension(pFileName.substring(pFileName.indexOf(".")));
        } catch (Exception e) {
            log.error("doUploadFile2Cloud() error occurs", e);
            return Mono.error(e);
        }
        return Mono.just(pImage);
    }



    /**
     * Create .done file for uploaded image, file content is image infos from cloud service(e.g. image path)
     *
     * @param pImage    the upload image
     * @param pFileName the upload file name
     * @return
     */
    private Mono<Image> doBackupCloudUploadSuccessImage2Disk(Image pImage, String pFileName) {
        if (StringUtils.isEmpty(pImage.getPath())) {
            return Mono.empty();
        }
        fileUtil.writeFile2Disk(pImage.getTrackId(),
                pFileName + fileUtil.uploadFileStorageProperties.getSuccessUploadFileSuffix(),
                fileUtil.content(pImage.getPath()));
        return Mono.just(pImage);
    }



    /**
     * Save image to mongodb
     *
     * @param pImage
     * @return
     */
    private Mono<Image> doSaveImageInMongoDB(Image pImage) {
        pImage.setActive(true);
        pImage.setLastUpdateDate(new Date());
        pImage.setUploadError(null);
        return imageMongoRepository.save(pImage);
    }



    /**
     * Add Exception
     *
     * @param pMsg the error msg
     * @return exception consumer
     */
    public Consumer<Throwable> addException(String pMsg) {
        return e -> {
            log.error(pMsg, e);
        };
    }



    /**
     * Add Exception and mark image as invalid
     *
     * @param pMsg   the error msg
     * @param pError the error
     * @param pImage the corresponding image
     * @return exception consumer
     */
    public Consumer<Throwable> addException(String pMsg, UploadError pError, Image pImage) {
        return e -> {
            log.error(pMsg, e);
            pImage.setUploadError(pError);
            imageMongoRepository
                    .save(pImage)
                    .subscribe(pSavedImage -> log.error("Image saved with error:{}", pSavedImage));
        };
    }

}
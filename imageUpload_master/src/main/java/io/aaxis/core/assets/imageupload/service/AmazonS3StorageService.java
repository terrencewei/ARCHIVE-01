package io.aaxis.core.assets.imageupload.service;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Service;

import io.aaxis.core.assets.imageupload.model.Media;
import io.aaxis.core.assets.imageupload.properties.UploadFileStorageProperties;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class AmazonS3StorageService implements GenericStorageService {

	@Autowired
	public UploadFileStorageProperties uploadFileStorageProperties;

	@Override
	public void writeMediaFile(Media media, Flux<DataBuffer> fileData) {
		//return uploadFileStorageProperties.getImageClouldLocation() + media.getName();

	}

	@Override
	public void writeFile(Path path, Flux<DataBuffer> fileData) {
		// TODO Auto-generated method stub
	}

}

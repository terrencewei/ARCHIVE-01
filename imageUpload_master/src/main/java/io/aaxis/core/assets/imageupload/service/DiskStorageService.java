package io.aaxis.core.assets.imageupload.service;

import java.io.IOException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.stereotype.Service;

import io.aaxis.core.assets.imageupload.model.Media;
import io.aaxis.core.assets.imageupload.util.DiskStorageUtil;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * 
 * Use to write file to disk device.
 *
 */
@Slf4j
@Service
public class DiskStorageService implements GenericStorageService {

	@Autowired
	public DiskStorageUtil diskStorageUtil;

	@Override
	public void writeMediaFile(Media media, Flux<DataBuffer> fileData) {
		try {
			Path filePath = diskStorageUtil.getMediaFilePath(media);
			writeFile(filePath, fileData);
		} catch (Exception e) {
			log.error("doBackupFile2Disk() error occurs.", e);
		}
	}




	@Override
	public void writeFile(Path filePath, Flux<DataBuffer> fileData) {

		try {
			Files.deleteIfExists(filePath);
			Path tempFile = Files.createFile(filePath);
			AsynchronousFileChannel channel = AsynchronousFileChannel.open(tempFile, StandardOpenOption.WRITE);
			DataBufferUtils.write(fileData, channel, 0).doOnComplete(() -> {
				// need close channel to avoid java.nio.file.AccessDeniedException when upload
				// the same file again
				try {
					channel.close();
					log.info("writeFile() save file success to:{}", filePath);
				} catch (IOException e) {
					log.error("writeFile() doOnComplete error occurs.", e);
				}
			}).subscribe();
			
		} catch (Exception e) {
			log.error("writeFile() error occurs.", e);
		}
	}


}

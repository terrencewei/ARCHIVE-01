package io.aaxis.core.assets.imageupload.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import io.aaxis.core.assets.imageupload.model.Media;
import io.aaxis.core.assets.imageupload.properties.UploadFileStorageProperties;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 
 *
 */
@Service
@Slf4j
public class DiskStorageUtil {

	@Autowired
	public UploadFileStorageProperties uploadFileStorageProperties;

	/**
	 * Create media file path
	 * e.g. /accountId/assetsType/assetsId/mediatype/assetsName
	 * @param media
	 * @return
	 */
	public Path getMediaFilePath(Media media) {
		
		StringBuilder urlBuild = new StringBuilder();
		urlBuild.append("items/");
		if (!StringUtils.isEmpty(media.getEntityType())) {
			urlBuild.append(media.getEntityType()+"/");
		}
		if (!StringUtils.isEmpty(media.getType())) {
			urlBuild.append(media.getType()+"/");
		}
		if (!StringUtils.isEmpty(media.getEntityId())) {
			urlBuild.append(media.getEntityId()+"/");
		}
		urlBuild.append(media.getEntityName());
		media.setPath(urlBuild.toString());
		String base = uploadFileStorageProperties.getBackupFileLocation();
		Path path = Paths.get(base, media.getAccountId(), "items/", media.getEntityType(), media.getType(), media.getEntityId());
		if (!Files.exists(path)) {
			try {
				Files.createDirectories(path);
			} catch (IOException e) {
				log.error("getMediaFilePath() error occurs.", e);
			}
		}
		return Paths.get(base, media.getAccountId(), urlBuild.toString());

	}

}

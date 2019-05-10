package io.aaxis.core.assets.imageupload.service;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;

import io.aaxis.core.assets.imageupload.model.Media;
import reactor.core.publisher.Flux;

/**
 * 
 * This interface defines the interactions with various storage services
 *
 */
public interface GenericStorageService {

	/**
	 * 
	 * 
	 * @param media
	 * @param fileData
	 * @return
	 */
	void writeMediaFile(Media media, Flux<DataBuffer> fileData);

	/**
	 * Write file content to path
	 * 
	 * @param path
	 * @param fileData
	 * @return
	 */
	void writeFile(Path path, Flux<DataBuffer> fileData);

	/**
	 * Create a flux base on the given content
	 * 
	 * @param content 
	 * @return
	 */
	default Flux<DataBuffer> content(String content) {
		byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
		DataBuffer buffer = new DefaultDataBufferFactory().allocateBuffer(bytes.length);
		buffer.write(bytes);
		return Flux.just(buffer);
	}

}

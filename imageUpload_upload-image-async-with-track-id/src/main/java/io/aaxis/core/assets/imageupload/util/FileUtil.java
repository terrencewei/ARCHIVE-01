package io.aaxis.core.assets.imageupload.util;

import java.io.IOException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.stereotype.Service;

import io.aaxis.core.assets.imageupload.properties.UploadFileStorageProperties;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Service
@Slf4j
public class FileUtil {

    @Autowired
    public UploadFileStorageProperties uploadFileStorageProperties;



    /**
     * Write file to local disk
     *
     * @param trackId
     * @param fileName
     * @param fileData
     */
    public void writeFile2Disk(String trackId, String fileName, Flux<DataBuffer> fileData) {
        try {
            Path filePath = getBackupFilePath(trackId, fileName);
            Path tempFile = Files.createFile(filePath);
            AsynchronousFileChannel channel = AsynchronousFileChannel.open(tempFile, StandardOpenOption.WRITE);
            DataBufferUtils
                    .write(fileData, channel, 0)
                    .doOnComplete(() -> {
                        // need close channel to avoid java.nio.file.AccessDeniedException when upload the same file again
                        try {
                            channel.close();
                        } catch (IOException e) {
                            log.error("doSaveFileAsDiskTempfileByAsynchronousFileChannel() doOnComplete error occurs.",
                                    e);
                        }
                    })
                    .subscribe();
        } catch (Exception e) {
            log.error("doBackupFile2Disk() error occurs.", e);
        }

    }



    public Flux<DataBuffer> content(String content) {
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = new DefaultDataBufferFactory().allocateBuffer(bytes.length);
        buffer.write(bytes);
        return Flux.just(buffer);
    }



    /**
     * Get back up file path
     *
     * @param trackId
     * @param fileName
     * @return
     * @throws IOException
     */
    public Path getBackupFilePath(String trackId, String fileName) throws IOException {
        String base = uploadFileStorageProperties.getBackupFileLocation();
        Path path = Paths.get(base, trackId);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        return Paths.get(base, trackId, fileName);

    }



    /**
     * Delete file
     *
     * @param trackId
     * @param fileName
     */
    public void deleteBackupFile(String trackId, String fileName) {

        log.debug("deleteBackupFile() enter:{}", trackId);
        // delete the backup file

        String base = uploadFileStorageProperties.getBackupFileLocation();
        try {
            Files.deleteIfExists(Paths.get(base, trackId, fileName));
            Files.deleteIfExists(
                    Paths.get(base, trackId, fileName + uploadFileStorageProperties.getSuccessUploadFileSuffix()));
        } catch (Exception e) {
            log.error("deleteBackupFile() error occurs.", e);
        }
    }

}

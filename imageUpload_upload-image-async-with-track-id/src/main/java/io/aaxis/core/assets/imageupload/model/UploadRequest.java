package io.aaxis.core.assets.imageupload.model;

import java.util.List;

import org.springframework.http.codec.multipart.Part;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The fule upload request entity
 *
 * Created by terrence on 2019/04/11.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UploadRequest {

    private List<Part> files;
}
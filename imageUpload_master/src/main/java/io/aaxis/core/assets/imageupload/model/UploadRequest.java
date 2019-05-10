package io.aaxis.core.assets.imageupload.model;

import java.util.List;

import org.springframework.http.codec.multipart.Part;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The file upload request entity
 *
 * Created by terrence on 2019/04/11.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UploadRequest {

	private String accountId;
    private String entityId;
    private UploadRequestEntityType entityType;
    private List<Part> files;
    private MediaType type;
    // json string {"en_US": "SDS","zn_CH": "SDS","es_ES": "SDS"}
    /*
    {
        "en_US": "SDS",
        "zn_CH":"SDS",
        "es_ES": "SDS"
     }
      */
    private String name;
    
}
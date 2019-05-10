package io.aaxis.core.assets.imageupload.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The image entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "image_collection")// the collection name in MongoDB
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Image {

    public Image(String pName, String pTrackId) {
        String imageId = pTrackId + UploadConstant.UNDERLINE + pName;
        Date date = new Date();

        this.id = imageId;
        this.name = pName;
        this.trackId = pTrackId;
        this.creationDate = date;
        this.lastUpdateDate = date;
    }



    @Id
    private String      id;
    //
    private String      trackId;
    // image name
    private String      name;
    // image url, generated by cloud server
    private String      path;
    // e.g. (image/jpeg, video/mpeg)
    private String      mimeType;
    // the suffix of image name, e.g. (.jpg)
    private String      extension;
    private String      description;
    private Integer     height;
    private Integer     width;
    private Boolean     active;
    private Date        creationDate;
    private String      creator;
    private Date        lastUpdateDate;
    private UploadError uploadError;

}
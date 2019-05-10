package io.aaxis.core.assets.imageupload.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The media entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "media_collection")// the collection name in MongoDB
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Media {

    public Media(String mediaId, String pName, String assetsType, String assetsId, String accountId) {  	
        Date date = new Date();
        this.id = mediaId;
        this.entityName = pName;
        this.entityType = assetsType;
        this.entityId = assetsId;
        this.accountId = accountId;
        this.creationDate = date;
        this.lastModifiedDate = date;
    }
    



    // media id
    @Id
    private String      id;
    private String      accountId;
    // media name
    private String entityName;
    
    // json string 
    private String      name;
    // image url, generated by cloud server
    private String      path;
    
    // image, video or document
    private String      type;
    // product, brand or sku.
    private String      entityType;
    private String entityId;
    // e.g. (image/jpeg, video/mpeg)
    private String mimeType;
    private String      description;
    private Boolean     active;
    private Date        creationDate;
    private Date        lastModifiedDate;

}

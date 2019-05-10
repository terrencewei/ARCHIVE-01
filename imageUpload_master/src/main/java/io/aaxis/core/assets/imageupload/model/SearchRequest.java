package io.aaxis.core.assets.imageupload.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The search request entity
 *
 * 
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SearchRequest {

	private String accountId;
    private String entityId;
    private String entityType;
    private String type;
    private int page;
    private int size;
    
}
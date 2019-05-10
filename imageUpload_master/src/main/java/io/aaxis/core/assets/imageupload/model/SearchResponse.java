package io.aaxis.core.assets.imageupload.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Search Response
 */
@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class SearchResponse {

	private long totalCount;
    private long totalPage;
    // current page
    private long page;
    // page size
    private int size;
    private List<Media> entities;
    
    public SearchResponse(int current, int pageSize) {
    	this.page = current;
    	this.size = pageSize;
    }

}
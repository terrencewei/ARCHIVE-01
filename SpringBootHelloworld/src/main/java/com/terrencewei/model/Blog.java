package com.terrencewei.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by terrencewei on 3/10/17.
 */
@Entity
public class Blog {

    @Id
    @GeneratedValue
    private Long   id;
    private String title;
    private String content;
    private Date   createdDate;



    public Blog() {
    }



    public Blog(Long id, String title, String content, Date createdDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
    }



    public Long getId() {
        return id;
    }



    public void setId(Long pId) {
        id = pId;
    }



    public String getTitle() {
        return title;
    }



    public void setTitle(String pTitle) {
        title = pTitle;
    }



    public String getContent() {
        return content;
    }



    public void setContent(String pContent) {
        content = pContent;
    }



    public Date getCreatedDate() {
        return createdDate;
    }



    public void setCreatedDate(Date pCreatedDate) {
        createdDate = pCreatedDate;
    }

}

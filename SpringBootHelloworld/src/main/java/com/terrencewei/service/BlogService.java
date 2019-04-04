package com.terrencewei.service;

import com.terrencewei.model.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by terrencewei on 3/10/17.
 */
public interface BlogService {

    public boolean resetBlogData();

    public boolean saveBlog();

    public Page<Blog> findBlogs(Pageable pageable);
}

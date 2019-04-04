package com.terrencewei.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.terrencewei.dao.BlogDao;
import com.terrencewei.model.Blog;
import com.terrencewei.service.BlogService;

/**
 * Created by terrencewei on 3/10/17.
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogDao blogDao;



    @Override
    public Page<Blog> findBlogs(Pageable pageable) {

        List<Blog> blogs = new ArrayList<Blog>() {
        };
        for (int i = 0; i < 100; i++) {
            blogs.add(new Blog(Long.valueOf(i), "this is title" + i, "this is content" + i, new Date()));
        }
        Page<Blog> results = blogDao.findAll(new PageRequest(1, 20));
        return results;
    }



    @Override
    public boolean resetBlogData() {
        blogDao.deleteAll();
        List<Blog> blogs = new ArrayList<Blog>();
        for (int i = 0; i < 100; i++) {
            Blog blog = new Blog(Long.valueOf(i), "this is blog title" + i, "this is blog content" + i, new Date());
            blogs.add(blog);
        }
        blogDao.save(blogs);
        return true;
    }



    @Override
    public boolean saveBlog() {
        // TODO:
        return true;
    }
}

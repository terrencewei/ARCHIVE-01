package com.terrencewei.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.terrencewei.model.Blog;

/**
 * Created by terrencewei on 3/13/17.
 */
@Repository
public interface BlogDao extends PagingAndSortingRepository<Blog, Long> {

    public List<Blog> findById(Long id);

}

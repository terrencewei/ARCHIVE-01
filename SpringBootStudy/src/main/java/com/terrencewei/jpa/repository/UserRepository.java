package com.terrencewei.jpa.repository;

import com.terrencewei.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by terrence on 2018/05/28.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    
    List<User> findByNameContaining(String name);
}
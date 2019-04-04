package com.terrencewei.api.controller;

import com.terrencewei.jpa.repository.UserRepository;
import com.terrencewei.mybatis.mapper.UserMapper;
import com.terrencewei.mybatis.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by terrence on 2018/05/23.
 */
@RestController
@RequestMapping(value = "/api/user")
@Order(value = 2)
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;



    @GetMapping("")
    public Object findAllUser() {
        return userMapper.findAllUser();
    }



    @PostMapping("")
    public Object addUser(@RequestBody @Validated User user) {
        return userMapper.addUser(user);
    }



    @GetMapping("/name/{name}")
    public Object findUserByName(@PathVariable String name) {
        return userMapper.findUserByName(name);
    }



    @GetMapping("/name/containing/{name}")
    public Object findByUsernameContaining(@PathVariable String name) {
        return userRepository.findByNameContaining(name);
    }



    @DeleteMapping("/{id}")
    public Object deleteUser(@PathVariable String id) {
        return userMapper.deleteUser(id);
    }
}
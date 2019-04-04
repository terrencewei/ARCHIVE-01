package com.example.webfluxdemo.reactive_spring_data;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Mono;

/**
 * 1. 同样的，ReactiveCrudRepository的泛型分别是User和ID的类型；
 * 2. ReactiveCrudRepository已经提供了基本的增删改查的方法，根据业务需要，我们增加四个方法（在此膜拜一下Spring团队的牛人们，使得我们仅需按照规则定义接口方法名即可完成DAO层逻辑的开发，牛~）
 */
public interface UserRepository extends ReactiveCrudRepository<User, String> {  // 1
    Mono<User> findByUsername(String username);     // 2

    Mono<Long> deleteByUsername(String username);
}
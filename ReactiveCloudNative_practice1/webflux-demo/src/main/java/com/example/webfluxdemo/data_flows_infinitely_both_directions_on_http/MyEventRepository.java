package com.example.webfluxdemo.data_flows_infinitely_both_directions_on_http;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;

import reactor.core.publisher.Flux;

/**
 * 1. @Tailable注解的作用类似于linux的tail命令，被注解的方法将发送无限流，需要注解在返回值为Flux这样的多个元素的Publisher的方法上；
 * 2. findAll()是想要的方法，但是在ReactiveMongoRepository中我们够不着，所以使用findBy()代替。
 */
public interface MyEventRepository extends ReactiveMongoRepository<MyEvent, Long> {
    @Tailable
        // 1
    Flux<MyEvent> findBy(); // 2
}
       
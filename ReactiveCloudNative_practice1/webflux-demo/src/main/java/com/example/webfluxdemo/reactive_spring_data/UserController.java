package com.example.webfluxdemo.reactive_spring_data;

import java.time.Duration;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;



    /**
     * 保存或更新。
     * 如果传入的user没有id属性，由于username是unique的，在重复的情况下有可能报错，
     * 这时找到以保存的user记录用传入的user更新它。
     *
     * 1. onErrorResume进行错误处理；
     * 2. 找到username重复的记录；
     * 3. 拿到ID从而进行更新而不是创建；
     * 4. 由于函数式为User -> Publisher，所以用flatMap。
     */
    @PostMapping("")
    public Mono<User> save(@Valid @RequestBody User user) {
        return userRepository
                .save(user)
                .onErrorResume(e ->     // 1
                        userRepository.findByUsername(user.getUsername())   // 2
                                      .flatMap(originalUser -> {      // 4
                                          user.setId(originalUser.getId());
                                          return userRepository.save(user);   // 3
                                      }));
    }



    @DeleteMapping("/{username}")
    public Mono<Long> deleteByUsername(@PathVariable String username) {
        return userRepository.deleteByUsername(username);
    }



    @GetMapping("/{username}")
    public Mono<User> findByUsername(@PathVariable String username) {
        return userRepository.findByUsername(username);
    }



    @GetMapping(value = "", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<User> findAll() {
        return userRepository
                .findAll()
                .delayElements(Duration.ofSeconds(2));
    }
}
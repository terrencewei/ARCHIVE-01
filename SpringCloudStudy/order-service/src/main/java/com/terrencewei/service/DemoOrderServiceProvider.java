package com.terrencewei.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;

/**
 * Created by terrence on 2018/06/12.
 */

@RestController
public class DemoOrderServiceProvider {

    @Value("${server.port}")
    String port;



    @GetMapping(path = "/demo-order/{id}")
    public String demo(@PathVariable String id) {
        return MessageFormat.format("This is response from /order-service/demo-order/{0}, port:{1}", id, port);
    }
}
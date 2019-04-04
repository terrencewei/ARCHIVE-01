package com.terrencewei.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;

/**
 * Created by terrence on 2018/06/12.
 */
@Service
public class DemoUserRibbonService {

    @Autowired
    private RestTemplate restTemplate;

    String SERVICE_NAME = "order-service";



    @GetMapping(path = "/demo-order/{id}")
    public String getOrder(@PathVariable("id") String id) {
        return restTemplate
                .getForEntity(MessageFormat.format("http://{0}/demo-order/{1}", SERVICE_NAME, id), String.class)
                .getBody();
    }
}
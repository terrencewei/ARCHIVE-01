package com.example.demo;

import java.text.MessageFormat;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/smstest")
public class DemoController {

    @GetMapping("/get")
    public String smstest(HttpServletRequest request) {
        if (request == null) {
            return "";
        }
        Enumeration enums = request.getParameterNames();
        while (enums.hasMoreElements()) {
            String paramName = (String) enums.nextElement();
            String paramValue = request.getParameter(paramName);
            System.out.println(MessageFormat.format("request param key:[{0}], value:[{1}]", paramName, paramValue));
        }
        return "";
    }



    @PostMapping(value = "/post/json", consumes = "application/json")
    public String smstest2(@RequestBody Object body) {
        System.out.println(MessageFormat.format("request body:[{0}]", body));
        return "";
    }

}

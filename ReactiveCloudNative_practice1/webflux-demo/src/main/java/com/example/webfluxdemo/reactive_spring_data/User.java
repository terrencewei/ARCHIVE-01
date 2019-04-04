package com.example.webfluxdemo.reactive_spring_data;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    private String id; // 注解属性id为ID
    @Indexed(unique = true) // 注解属性username为索引，并且不能重复
    private String username;
    private String phone;
    private String email;
    private String name;
    private Date   birthday;



    public User(String pUsername, String pPhone, String pEmail, String pName, Date pBirthday) {
        this.username = pUsername;
        this.phone = pPhone;
        this.email = pEmail;
        this.name = pName;
        this.birthday = pBirthday;
    }
}
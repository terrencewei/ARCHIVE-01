package com.terrencewei.mybatis.model;

import javax.validation.constraints.Size;

/**
 * Created by terrence on 2018/05/23.
 */
public class User {

    private Integer id;
    @Size(max = 5, min = 2)
    private String  name;
    private String  password;
    private String  phone;



    public Integer getId() {
        return id;
    }



    public void setId(Integer pId) {
        id = pId;
    }



    public String getName() {
        return name;
    }



    public void setName(String pName) {
        name = pName;
    }



    public String getPassword() {
        return password;
    }



    public void setPassword(String pPassword) {
        password = pPassword;
    }



    public String getPhone() {
        return phone;
    }



    public void setPhone(String pPhone) {
        phone = pPhone;
    }
}
package com.terrencewei.jpa.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

/**
 * Created by terrence on 2018/05/28.
 */
@Entity
@Table(name = "t_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long   id;
    @Column
    private String name;
    @Column
    private String password;
    @Column
    private String phone;



    public Long getId() {
        return id;
    }



    public void setId(Long pId) {
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



    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
package com.terrencewei.batch.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by terrence on 2018/05/23.
 */
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Size(max = 5, min = 2)
    private String name;
    private String password;



    public Person() {
    }



    public Person(String name, String password) {
        this.name = name;
        this.password = password;
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



    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
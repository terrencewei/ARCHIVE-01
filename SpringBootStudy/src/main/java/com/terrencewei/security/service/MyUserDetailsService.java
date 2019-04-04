package com.terrencewei.security.service;

import com.terrencewei.batch.dto.Person;
import com.terrencewei.mybatis.mapper.PersonMapper;
import com.terrencewei.security.dto.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * Created by terrence on 2018/05/22.
 */
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private PersonMapper personMapper;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUserDetails userDetails = new MyUserDetails();
        List<Person> personList = personMapper.findPersonByName(username);
        if (personList != null && !personList.isEmpty()) {
            Person person = personList.get(0);
            userDetails.setName(person.getName());
            userDetails.setPassword(person.getPassword());
        }
        return userDetails;
    }
}
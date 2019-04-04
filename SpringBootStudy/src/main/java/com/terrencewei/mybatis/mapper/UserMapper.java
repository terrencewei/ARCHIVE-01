package com.terrencewei.mybatis.mapper;

import com.terrencewei.mybatis.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by terrence on 2018/05/23.
 */
public interface UserMapper {

    @Select("SELECT * FROM t_user order by id desc")
    List<User> findAllUser();

    @Select("SELECT * FROM t_user WHERE name = #{name} order by id desc")
    List<User> findUserByName(@Param("name") String name);

    @Insert("INSERT INTO t_user(name, password, phone) VALUES(#{user.name}, #{user.password}, #{user.phone})")
    @Options(useGeneratedKeys = true)
    int addUser(@Param("user") User user);

    @Delete("DELETE from t_user where id=#{id}")
    int deleteUser(@Param("id") String id);

}
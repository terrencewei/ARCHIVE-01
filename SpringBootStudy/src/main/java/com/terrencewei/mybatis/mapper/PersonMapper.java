package com.terrencewei.mybatis.mapper;

import com.terrencewei.batch.dto.Person;
import com.terrencewei.mybatis.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by terrence on 2018/05/23.
 */
public interface PersonMapper {

    @Select("SELECT * FROM people WHERE name = #{name} order by person_id desc")
    List<Person> findPersonByName(@Param("name") String name);

}
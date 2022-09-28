package com.demo.community.mapper;

import com.demo.community.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    @Insert("insert into user values (#{name}, #{number}, #{password},null);")
    int InsertUser(@Param("name") String name,@Param("number") int number,@Param("password") String password);

    @Select("SELECT name,number,password,uid from user where number=#{number}")
    User SelectByNumber(@Param("number")int number);

    @Select("SELECT name,number,password,uid from user where name=#{name}")
    User SelectByName(@Param("name")String name);

    @Select("SELECT name,number,password,uid from user where uid=#{uid}")
    User SelectByUid(@Param("uid")int uid);

    @Select("SELECT name,number,password,uid from user where uid in #{userIds}")
    List<User> SelectByUidInList(@Param("userIds")List<Integer> userIds);
}

package com.demo.community.mapper;

import com.demo.community.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @author foofoo3
 */
@Repository
public interface UserMapper {
    @Insert("insert into user values (#{name}, #{number}, #{password},null,#{description},#{face});")
    int InsertUser(@Param("name") String name,@Param("number") int number,@Param("password") String password,@Param("description") String description,@Param("face") String face);

    @Select("SELECT * from user where number=#{number}")
    User SelectByNumber(@Param("number")int number);

    @Select("SELECT * from user where name=#{name}")
    User SelectByName(@Param("name")String name);

    @Select("SELECT * from user where uid=#{uid}")
    User SelectByUid(@Param("uid")int uid);

    @Select("<script> "+
            "select * from user where uid in "+
            "<foreach item='item' index='index' collection='userIds' open='(' separator=',' close=')'> "+
            "   #{item} "+
            "</foreach>"+
            "</script> ")
    List<User> SelectByUidInList(@Param("userIds")List<Integer> userIds);

    @Update("update user set name = #{name},description = #{description},password = #{password} where uid = #{uid}")
    int updateUser(User user);
}

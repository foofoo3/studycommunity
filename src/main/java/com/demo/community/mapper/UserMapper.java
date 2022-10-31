package com.demo.community.mapper;

import com.demo.community.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @author foofoo3
 */
@Repository
public interface UserMapper {
    @Insert("insert into user values (#{name}, #{number}, #{password},null,#{description},#{face},1);")
    int InsertUser(@Param("name") String name,@Param("number") int number,@Param("password") String password,@Param("description") String description,@Param("face") String face);

    @Select("SELECT * from user where number=#{number}")
    User SelectByNumber(@Param("number")int number);

    @Select("SELECT * from user where name regexp #{name}")
    List<User> SelectByName(@Param("name")String name);

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

    @Update("update user set face = #{filePath} where uid = #{uid}")
    int updateUserFace(@Param("uid")int uid, @Param("filePath")String filePath);

    @Select("SELECT * from user where type = 0")
    List<User> getUserByType();

    @Update("update user set type = 0 where uid = #{uid}")
    int banUser(@Param("uid")int uid);

    @Update("update user set type = 1 where uid = #{uid}")
    int unbanUser(@Param("uid")int uid);

    @Delete("delete from user where uid = #{uid}")
    int cancellationUser(@Param("uid")int uid);
}

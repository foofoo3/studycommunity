package com.demo.community.mapper;

import com.demo.community.entity.Notification;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationMapper {

    @Insert("insert into notification(id,notifier,receiver,outerId,type,gmt_create,status,notifier_name,outer_title) values (null,#{notifier}, #{receiver},#{outerId},#{type}, #{gmt_create},#{status},#{notifier_name},#{outer_title});")
    void insert(Notification notification);

    @Select("select * from notification where receiver = #{uid} order by gmt_create desc limit #{offset},#{size} ")
    List<Notification> listByUid(@Param("uid") int uid, @Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from notification where receiver = #{uid}")
    Integer countByUid(@Param("uid") int uid);

    @Select("select count(1) from notification where receiver = #{uid} && status = 0")
    Long unreadCountByUid(int uid);
}

package com.demo.community.mapper;

import com.demo.community.entity.Notification;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @author foofoo3
 */
@Repository
public interface NotificationMapper {

    @Insert("insert into notification(id,notifier,receiver,outerId,type,gmt_create,status,notifier_name,outer_title) values (null,#{notifier}, #{receiver},#{outerId},#{type}, #{gmt_create},#{status},#{notifier_name},#{outer_title});")
    void insert(Notification notification);

    @Select("select * from notification where receiver = #{uid} order by gmt_create desc limit #{offset},#{size}")
    List<Notification> listByUid(@Param("uid") int uid, @Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from notification where receiver = #{uid}")
    int selectListCountByUid(@Param("uid") int uid);

    @Select("select count(1) from notification where receiver = #{uid}")
    Integer countByUid(@Param("uid") int uid);

    @Select("select count(1) from notification where receiver = #{uid} && status = 0 || status = 2")
    Long unreadCountByUid(@Param("uid") int uid);

    @Select("select * from notification where id = #{id}")
    Notification selectById(@Param("id")Long id);

    @Update("update notification set status = #{status} where id = #{id}")
    void updateStatus(Notification notification);

    @Delete("delete from notification where id = #{id}")
    int deleteById(@Param("id")int id);

    @Delete("delete from notification where notifier = #{notifier}")
    void deleteByNotifier(@Param("notifier")int notifier);
}

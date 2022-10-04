package com.demo.community.mapper;

import com.demo.community.entity.Comment;
import com.demo.community.entity.Notification;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationMapper {
    @Insert("insert into notification(id,notifier,receiver,outerId,type,gmt_create,status) values (null,#{notifier}, #{receiver},#{outerId},#{type}, #{gmt_create},#{status});")
    void insert(Notification notification);
}

package com.demo.community.mapper;

import com.demo.community.entity.Comment;
import com.demo.community.entity.Question;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentMapper {
    @Insert("insert into comment(id,parent_id,type,commentator,gmt_create,gmt_modified,content) values (null,#{parent_id}, #{type},#{commentator}, #{gmt_create},#{gmt_modified},#{content});")
    void creat(Comment comment);
}

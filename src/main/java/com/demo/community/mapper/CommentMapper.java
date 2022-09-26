package com.demo.community.mapper;

import com.demo.community.entity.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentMapper {
    @Insert("insert into comment(id,parent_id,type,commentator,gmt_create,gmt_modified,content) values (null,#{parent_id}, #{type},#{commentator}, #{gmt_create},#{gmt_modified},#{content});")
    void creat(Comment comment);

    @Select("select * from comment where parent_id = #{parent_id}")
    Comment selectByPrimaryKey(@Param("parent_id")int parent_id);
}

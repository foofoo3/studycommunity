package com.demo.community.mapper;

import com.demo.community.entity.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMapper {
    @Insert("insert into comment(id,parent_id,type,commentator,gmt_create,gmt_modified,content) values (null,#{parent_id}, #{type},#{commentator}, #{gmt_create},#{gmt_modified},#{content});")
    void creat(Comment comment);

    @Select("select * from comment where id = #{id}")
    Comment selectByParentId(@Param("id")Long id);

    @Select("select * from comment where parent_id = #{parent_id} && type = #{type} order by gmt_create desc")
    List<Comment> selectByPidAndType(@Param("parent_id")int parent_id,@Param("type")Integer type);

    @Update("update comment set comment_count = #{comment_count} + 1 where id = #{id}")
    int updateCommentCount(Comment comment);
}

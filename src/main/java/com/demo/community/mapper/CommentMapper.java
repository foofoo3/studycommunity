package com.demo.community.mapper;

import com.demo.community.entity.Comment;
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
public interface CommentMapper {
    @Insert("insert into comment(id,parent_id,type,commentator,gmt_create,gmt_modified,content) values (null,#{parent_id}, #{type},#{commentator}, #{gmt_create},#{gmt_modified},#{content});")
    void creat(Comment comment);

    @Select("select * from comment where id = #{id}")
    Comment selectById(@Param("id") Long id);

    @Select("select * from comment where parent_id = #{parent_id} && type = #{type} order by gmt_create desc")
    List<Comment> selectByPidAndType(@Param("parent_id") int parent_id, @Param("type") Integer type);

    @Update("update comment set comment_count = #{comment_count} + 1 where id = #{id}")
    int updateCommentCount(Comment comment);

    @Update("update comment set like_count = #{like_count} + 1 where id = #{id}")
    int likePlus(Comment comment);

    @Update("update comment set like_count = #{like_count} - 1 where id = #{id}")
    int likeReduce(Comment comment);

    @Select("select * from comment where parent_id = #{parent_id} && type = #{type} order by like_count desc")
    List<Comment> selectByPidAndTypeLike(@Param("parent_id") int parent_id, @Param("type") Integer type);

    @Select("select like_count from comment where commentator = #{uid}")
    List<Integer> selectListLikeCountByUid(int uid);
}

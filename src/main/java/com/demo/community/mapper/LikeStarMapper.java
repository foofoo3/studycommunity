package com.demo.community.mapper;


import com.demo.community.entity.LikeStar;
import com.demo.community.entity.Question;
import com.demo.community.enums.LikeOrStarTypeEnum;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: foofoo3
 */
@Repository
public interface LikeStarMapper {

    @Insert("insert into like_and_star values (#{type},#{uid},#{target_id},null,#{gmt_create},#{parent_id});")
    int insert(LikeStar likeStar);

    @Select("select target_id from like_and_star where uid = #{uid} and type = #{type} and parent_id = #{parent_id}")
    List<Long> selectCommentLikeByUid(@Param("uid") int uid,@Param("type") int type,@Param("parent_id") int parent_id);

    @Delete("delete from like_and_star where target_id = #{target_id} and uid = #{uid} and type = #{type}")
    int deleteLikeOrStar(@Param("target_id") Long target_id, @Param("uid") int uid, @Param("type") int type);

    @Select("select target_id from like_and_star where uid = #{uid} and type = #{type}")
    List<Integer> selectQuestionLikeOrStarByUid(@Param("uid")int uid, @Param("type")int type);

    @Select("select * from like_and_star where uid = #{uid} and type = #{type}")
    List<LikeStar> selectLikeOrStarByUid(@Param("uid")int uid,@Param("type")int type);

    @Select("select count(1) from like_and_star a join question b on a.uid = #{uid} and a.type = #{type} and a.target_id = b.id")
    Integer starCountByUid(@Param("uid")int uid, @Param("type")int type);

    @Select("select b.* from like_and_star a join question b on a.uid = #{uid} and a.type = #{type} and a.target_id = b.id order by a.gmt_create desc limit #{offset},#{size}")
    List<Question> starQuestionByUid(@Param("uid")int uid, @Param("type")int type,@Param("offset") Integer offset,@Param("size") Integer size);

    @Select("select gmt_create from like_and_star where target_id = #{target_id} and uid = #{uid} and type = #{type}")
    Long selectStarTime(@Param("target_id") Long target_id, @Param("uid") int uid, @Param("type") int type);
}

package com.demo.community.mapper;


import com.demo.community.entity.LikeStar;
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

    @Insert("insert into like_and_star values (#{type},#{uid},#{target_id},null,#{gmt_create});")
    int insert(LikeStar likeStar);

    @Select("select target_id from like_and_star where uid = #{uid} and type = #{type}")
    List<Long> selectCommentLikeByUid(@Param("uid") int uid,@Param("type") int type);
}

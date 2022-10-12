package com.demo.community.mapper;


import com.demo.community.entity.LikeStar;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

/**
 * @Author: foofoo3
 */
@Repository
public interface LikeStarMapper {

    @Insert("insert into like_and_star values (#{type},#{uid},#{target_id},null,#{gmt_create});")
    int insert(LikeStar likeStar);
}

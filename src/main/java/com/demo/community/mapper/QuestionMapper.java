package com.demo.community.mapper;

import com.demo.community.entity.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionMapper {
    @Insert("insert into question(id,title,description,gmt_create,gmt_modified,creator,tag) values (null,#{title}, #{description}, #{gmt_create},#{gmt_modified},#{creator},#{tag});")
    void create(Question question);

    @Select("select * from question limit #{offset},#{size}")
    List<Question> list(@Param("offset") Integer offset,@Param("size") Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select * from question where creator = #{uid} limit #{offset},#{size}")
    List<Question> listByUid(@Param("uid") int uid,@Param("offset") Integer offset,@Param("size") Integer size);

    @Select("select count(1) from question where creator = #{uid}")
    Integer countByUid(@Param("uid") int uid);

    @Select("select * from question where id = #{id}")
    Question getQuestionById(@Param("id") int id);

    @Update("update question set title = #{title},description = #{description},tag = #{tag},gmt_modified = #{gmt_modified} where id = #{id}")
    void update(Question questionById);
}

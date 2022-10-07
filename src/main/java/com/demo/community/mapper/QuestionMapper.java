package com.demo.community.mapper;

import com.demo.community.dto.QuestionQueryDTO;
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

    @Select("<script>" +
            "select * from question" +
            "<where> " +
            "   <if test= \"search != null\"> " +
            "       and title regexp #{search} " +
            "   </if> " +
            "</where>" +
            " order by gmt_create desc limit #{page},#{size}" +
            "</script>")
    List<Question> list(QuestionQueryDTO questionQueryDTO);

    @Select("<script>" +
            "select count(*) from question" +
            "<where> " +
            "   <if test= \"search != null\"> " +
            "      and title regexp #{search} " +
            "   </if> " +
            "</where>" +
            "</script>")
    Integer countBySearch(QuestionQueryDTO questionQueryDTO);

    @Select("select * from question where creator = #{uid} order by gmt_create desc limit #{offset},#{size} ")
    List<Question> listByUid(@Param("uid") int uid,@Param("offset") Integer offset,@Param("size") Integer size);

    @Select("select count(1) from question where creator = #{uid}")
    Integer countByUid(@Param("uid") int uid);

    @Select("select * from question where id = #{id}")
    Question getQuestionById(@Param("id") int id);

    @Update("update question set title = #{title},description = #{description},tag = #{tag},gmt_modified = #{gmt_modified} where id = #{id}")
    int update(Question questionById);

    @Update("update question set view_count = #{view_count} + 1 where id = #{id}")
    int updateViewCount(Question question);

    @Update("update question set comment_count = #{comment_count} + 1 where id = #{id}")
    int updateCommentCount(Question question);

    @Select("select * from question where id != #{id} and tag regexp #{tag}")
    List<Question> selectSimilarQuestion(Question question);
}

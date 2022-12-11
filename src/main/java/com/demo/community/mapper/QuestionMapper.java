package com.demo.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.community.dto.QuestionQueryDTO;
import com.demo.community.entity.Question;
import com.demo.community.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @author foofoo3
 */
@Repository
public interface QuestionMapper  extends BaseMapper<Question> {
//    @Insert("insert into question(id,title,description,gmt_create,gmt_modified,creator,tag) values (null,#{title}, #{description}, #{gmt_create},#{gmt_modified},#{creator},#{tag});")
//    void create(Question question);
//
//    @Select("<script>" +
//            "select * from question" +
//            "<where> " +
//            "   <if test= \"search != null\"> " +
//            "       and title regexp #{search} " +
//            "   </if> " +
//            "   <if test= \"tag != null\"> " +
//            "      and tag regexp #{tag} " +
//            "   </if> " +
//            "</where>" +
//            " order by gmt_create desc limit #{page},#{size}" +
//            "</script>")
//    List<Question> listTime(QuestionQueryDTO questionQueryDTO);
//
//    @Select("<script>" +
//            "select * from question" +
//            "<where> " +
//            "   <if test= \"search != null\"> " +
//            "       and title regexp #{search} " +
//            "   </if> " +
//            "   <if test= \"tag != null\"> " +
//            "      and tag regexp #{tag} " +
//            "   </if> " +
//            "</where>" +
//            " order by like_count desc limit #{page},#{size}" +
//            "</script>")
//    List<Question> listLike(QuestionQueryDTO questionQueryDTO);
//
//    @Select("<script>" +
//            "select * from question" +
//            "<where> " +
//            "   <if test= \"search != null\"> " +
//            "       and title regexp #{search} " +
//            "   </if> " +
//            "   <if test= \"tag != null\"> " +
//            "      and tag regexp #{tag} " +
//            "   </if> " +
//            "</where>" +
//            " order by star_count desc limit #{page},#{size}" +
//            "</script>")
//    List<Question> listStar(QuestionQueryDTO questionQueryDTO);
//
//    @Select("<script>" +
//            "select count(*) from question" +
//            "<where> " +
//            "   <if test= \"search != null\"> " +
//            "      and title regexp #{search} " +
//            "   </if> " +
//            "   <if test= \"tag != null\"> " +
//            "      and tag regexp #{tag} " +
//            "   </if> " +
//            "</where>" +
//            "</script>")
//    Integer countBySearch(QuestionQueryDTO questionQueryDTO);
//
//    @Select("select * from question where creator = #{uid} order by gmt_create desc limit #{offset},#{size}")
//    List<Question> listByUid(@Param("uid") int uid,@Param("offset") Integer offset,@Param("size") Integer size);
//
//    @Select("select count(1) from question where creator = #{uid}")
//    Integer countByUid(@Param("uid") int uid);
//
//    @Select("select * from question where id = #{id}")
//    Question getQuestionById(@Param("id") int id);
//
//    @Update("update question set title = #{title},description = #{description},tag = #{tag},gmt_modified = #{gmt_modified} where id = #{id}")
//    int update(Question questionById);
//
//    @Update("update question set view_count = #{view_count} + 1 where id = #{id}")
//    int updateViewCount(Question question);
//
//    @Update("update question set comment_count = #{comment_count} + 1 where id = #{id}")
//    int updateCommentCount(Question question);
//
//    @Select("select * from question where id != #{id} and tag regexp #{tag}")
//    List<Question> selectSimilarQuestion(Question question);
//
//    @Select("select * from question limit #{offset},#{size}")
//    List<Question> selectByRowbounds(@Param("offset") int offset,@Param("size") int size);
//
//    @Update("update question set like_count = #{like_count} + 1 where id = #{id}")
//    int likePlus(Question question);
//
//    @Update("update question set like_count = #{like_count} - 1 where id = #{id}")
//    int likeReduce(Question question);
//
//    @Update("update question set star_count = #{star_count} + 1 where id = #{id}")
//    int StarPlus(Question question);
//
//    @Update("update question set star_count = #{star_count} - 1 where id = #{id}")
//    int starCancel(Question question);
//
//    @Select("SELECT * FROM question WHERE DATE_SUB(CURDATE(), INTERVAL 1 DAY) <= date(from_unixtime(gmt_create/1000,'%Y-%m-%d')) ORDER BY like_count desc limit 0,10")
//    List<Question> selectHotQuestionByDay();
//
//    @Select("SELECT * FROM question WHERE DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(from_unixtime(gmt_create/1000,'%Y-%m-%d')) ORDER BY like_count desc limit 0,10")
//    List<Question> selectHotQuestionByWeek();
//
//    @Select("SELECT * FROM question WHERE DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= date(from_unixtime(gmt_create/1000,'%Y-%m-%d')) ORDER BY like_count desc limit 0,10")
//    List<Question> selectHotQuestionByMonth();
//
//    @Select("select like_count from question where creator = #{uid}")
//    List<Integer> selectListLikeCountByUid(int uid);
//
//    @Select("select star_count from question where creator = #{uid}")
//    List<Integer> selectListStarCountByUid(int uid);
//
//    @Delete("delete from question where id = #{id}")
//    int deleteById(@Param("id")int id);
//
//    @Update("update question set comment_count = #{comment_count} - 1 where id = #{id}")
//    int reduceCommentCount(Question question);
//
//    @Delete("delete from question where creator = #{creator}")
//    void deleteByCreator(@Param("creator")int creator);
}

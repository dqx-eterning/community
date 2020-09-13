package dqx.community.mapper;

import dqx.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values " +
            "(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag}) ")
    void creat(Question question);

    @Select("select * from question limit #{offset},#{size}")
    List<Question> findList(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select count(1) from question ")
    Integer count();

    @Select("select * from question where creator = #{userId} limit #{offset},#{size}")
    List<Question> findListByUserId(@Param(value = "userId") int userId,@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select count(1) from question where creator = #{userId} ")
    Integer countByUserId(@Param(value = "userId") int userId);

    @Select("select * from question where id = #{id} ")
    Question getById(@Param(value = "id")Integer id);

    @Update("update question set title=#{title},description=#{description},gmt_modified = #{gmtModified},tag=#{tag} where id = #{id}")
    void update(Question question);
}

package com.cpt202.cw1.findu.mapper;

import com.cpt202.cw1.findu.pojo.Topic;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TopicMapper{
    List<String> selectCreatorEmail();
    void creatNewTopic(String title, String post_time, String create_by, String gender, String routine, String location, int user_number,String body);
    Topic topic_findByCreator(String create_by);
    Topic topic_findById(int id);
    List<Topic> selectNoNull(String routine, String gender, String location);
    List<Topic> selectAllNull(String gender);
    List<Topic> selectRoutineNull(String gender, String location);
    List<Topic> selectLocationNull(String routine, String gender);
}

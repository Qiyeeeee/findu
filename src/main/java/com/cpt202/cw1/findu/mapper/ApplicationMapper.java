package com.cpt202.cw1.findu.mapper;

import java.util.List;

import com.cpt202.cw1.findu.pojo.Application;
import com.cpt202.cw1.findu.pojo.Topic;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ApplicationMapper {
    void apply(String email,int topic_id, String introduction,String applytime,int state);
    List<Integer> selecIdByEmail(String email);
    List<String> selecByTopicId(int topic_id);
    void approve(int topic_id, String email);
    void reject(int topic_id, String email);
    List<String> selectEmail();
    void deleteApplication(String email);
    List<Application> selecAllByEmail(String email);
    int selectState(int topic_id, String email);
    String selectApplytime(int topic_id, String email);
}

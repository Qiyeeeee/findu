package com.cpt202.cw1.findu.mapper;

import java.util.List;

public interface Topic_UserMapper {
    void confirm(String email,int topic_id);
    List<String> selectByTopicId(int topic_id);
    List<String> selectAllUser();
}

package com.cpt202.cw1.findu.pojo;

import java.util.List;
import java.io.Serializable;

public class Application implements Serializable {
    private static final long serialVersionUID = 1L;
    private String email;
    private int topic_id;
    private String introduction;
    private String applytime;
    private int state;

    public Application(int topic_id, String introduction, String applytime) {
        this.topic_id = topic_id;
        this.introduction = introduction;
        this.applytime = applytime;
    }
    public Application(){

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(int topic_id) {
        this.topic_id = topic_id;
    }

    public String getIntroduction() {
        return introduction;
    }

    public String getApplytime() {
        return applytime;
    }

    public void setApplytime(String applytime) {
        this.applytime = applytime;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}

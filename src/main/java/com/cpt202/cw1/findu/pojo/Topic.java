package com.cpt202.cw1.findu.pojo;

import java.io.Serializable;

public class Topic implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String title;
    private String gender;
    private String post_time;
    private String create_by;
    private String routine;
    private String location;
    private int user_number;
    private String body;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPost_time(String post_time) {
        this.post_time = post_time;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    public void setRoutine(String routine) {
        this.routine = routine;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setUser_number(int user_number) {
        this.user_number = user_number;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getTitle() {
        return title;
    }

    public String getGender() {
        return gender;
    }

    public String getPost_time() {
        return post_time;
    }

    public String getCreate_by() {
        return create_by;
    }

    public String getRoutine() {
        return routine;
    }

    public String getLocation() {
        return location;
    }

    public int getUser_number() {
        return user_number;
    }

    public String getBody() {
        return body;
    }

    public  Topic(){}

    public Topic(String title, String post_time, String gender, String routine, String location, int user_number, String body) {

        this.title = title;
        this.gender = gender;
        this.post_time = post_time;
        this.routine = routine;
        this.location = location;
        this.user_number = user_number;
        this.body = body;
    }



}

package com.cpt202.cw1.findu.pojo;

import java.io.Serializable;

public class Info implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String gender;
    private String department;
    private String grade;
    private String routine;
    private String contact;
    private String description;

    public Info() {
    }

    public Info(String name,  String gender, String department, String grade, String routine, String contact, String description) {
        this.name = name;
        this.gender = gender;
        this.department = department;
        this.grade = grade;
        this.routine = routine;
        this.contact = contact;
        this.description = description;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getRoutine() {
        return routine;
    }

    public void setRoutine(String routine) {
        this.routine = routine;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}


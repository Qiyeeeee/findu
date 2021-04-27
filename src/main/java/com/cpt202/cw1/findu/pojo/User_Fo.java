package com.cpt202.cw1.findu.pojo;

import java.io.Serializable;

public class User_Fo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String email;
    private String code;
    private String password;

    public User_Fo() {
    }

    public User_Fo(String email, String code, String password) {
        this.email = email;
        this.code = code;
        this.password = password;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public String getPassword() {
        return password;
    }


}
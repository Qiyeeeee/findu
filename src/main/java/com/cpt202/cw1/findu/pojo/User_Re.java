package com.cpt202.cw1.findu.pojo;

import java.io.Serializable;

public class User_Re implements Serializable {
    private static final long serialVersionUID = 1L;
    private String email;
    private String name;
    private String password;
    private String code;

    public User_Re() {
    }

    public User_Re(String email, String name, String password,String code) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.code = code;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode() {
        this.code = code;
    }


}

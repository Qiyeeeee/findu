package com.cpt202.cw1.findu.pojo;

import java.io.Serializable;

public class User_Cp implements Serializable {
    private static final long serialVersionUID = 1L;
    private String email;
    private String oldP;
    private String newP;

    public User_Cp() {
    }

    public User_Cp(String email, String oldP, String newP) {
        this.email = email;
        this.oldP = oldP;
        this.newP = newP;
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

    public String getOldP() {
        return oldP;
    }

    public String getNewP() {
        return newP;
    }


}

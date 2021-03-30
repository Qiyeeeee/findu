package com.cpt202.cw1.findu.pojo;

import java.io.Serializable;

public class Email implements Serializable {
    private static final long serialVersionUID = 1L;
    private String email;
    private Integer code;
    private String dateStr;

    public Email() {
    }

    public Email(String email, Integer code, String dateStr) {
        this.email = email;
        this.code = code;
        this.dateStr = dateStr;
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

    public Integer getCode() { return code; }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getdateStr() { return dateStr; }

    public void setdateStr(String dateStr) {
        this.dateStr = dateStr;
    }
}


package com.example.android.Retorfit.Model;

import java.io.Serializable;
public class SigninResponse implements Serializable {
    private String jwt;
    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
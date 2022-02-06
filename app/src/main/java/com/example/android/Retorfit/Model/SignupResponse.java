package com.example.android.Retorfit.Model;

import java.io.Serializable;
public class SignupResponse implements Serializable {
    private String status;
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

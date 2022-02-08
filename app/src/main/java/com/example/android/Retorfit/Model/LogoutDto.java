package com.example.android.Retorfit.Model;

public class LogoutDto {
    private String userEmail;
    private String appId;

    public LogoutDto(String userEmail, String appId) {
        this.userEmail = userEmail;
        this.appId = appId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}

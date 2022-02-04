package com.example.android.RecycleView.Model;

import android.text.BoringLayout;

import java.util.Date;

public class ApiQuestion {
    private String id;
    private String orgId;
    private String questionBy;
    private Long postedOn;
    private String content;
    private String shareableLink;
    private Boolean threadClosed;
    private String category;
    private String acceptedAnsId;
    private Boolean isModerated;


    public ApiQuestion() {
    }

    public ApiQuestion(String questionBy, String content)
    {
        this.questionBy=questionBy;
        this.content=content;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getQuestionBy() {
        return questionBy;
    }

    public void setQuestionBy(String questionBy) {
        this.questionBy = questionBy;
    }

    public Long getPostedOn() {
        return postedOn;
    }

    public void setPostedOn(Long postedOn) {
        this.postedOn = postedOn;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getShareableLink() {
        return shareableLink;
    }

    public void setShareableLink(String shareableLink) {
        this.shareableLink = shareableLink;
    }

    public Boolean getThreadClosed() {
        return threadClosed;
    }

    public void setThreadClosed(Boolean threadClosed) {
        this.threadClosed = threadClosed;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAcceptedAnsId() {
        return acceptedAnsId;
    }

    public void setAcceptedAnsId(String acceptedAnsId) {
        this.acceptedAnsId = acceptedAnsId;
    }

    public Boolean getModerated() {
        return isModerated;
    }

    public void setModerated(Boolean moderated) {
        isModerated = moderated;
    }
}

package com.example.android.Retorfit.Model;

public class AnswerDto {
    private String id;
    private String questionId;
    private String answerBy;
    private String message;
    private Boolean isAccepted=false;
    private Long postedOn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getAnswerBy() {
        return answerBy;
    }

    public void setAnswerBy(String answerBy) {
        this.answerBy = answerBy;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getAccepted() {
        return isAccepted;
    }

    public void setAccepted(Boolean accepted) {
        isAccepted = accepted;
    }

    public Long getPostedOn() {
        return postedOn;
    }

    public void setPostedOn(Long postedOn) {
        this.postedOn = postedOn;
    }
}

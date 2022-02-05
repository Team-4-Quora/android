package com.example.android.Retorfit.Model;

public class  ReactionDto {
    private String id;
    private String questionId;
    private String reactionBy;
    private Boolean Like;
    private String answerId;
    private Long reactedOn;


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

    public String getReactionBy() {
        return reactionBy;
    }

    public void setReactionBy(String reactionBy) {
        this.reactionBy = reactionBy;
    }

    public Boolean getLike() {
        return Like;
    }

    public void setLike(Boolean like) {
        Like = like;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public Long getReactedOn() {
        return reactedOn;
    }

    public void setReactedOn(Long reactedOn) {
        this.reactedOn = reactedOn;
    }
}

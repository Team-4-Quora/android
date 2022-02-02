package com.example.android.RecycleView.Model;

import java.util.Date;

public class ApiHome {
    private String questionBy;
    private Date quesPostedOn;
    private String question;
    private String answer;
    private String answerBy;
    private Date ansPostedOn;

    public ApiHome(String questionBy,String question)
    {
        this.questionBy=questionBy;
        this.question=question;
    }
    public String getQuestionBy() {
        return questionBy;
    }

    public void setQuestionBy(String questionBy) {
        this.questionBy = questionBy;
    }

    public Date getQuesPostedOn() {
        return quesPostedOn;
    }

    public void setQuesPostedOn(Date quesPostedOn) {
        this.quesPostedOn = quesPostedOn;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswerBy() {
        return answerBy;
    }

    public void setAnswerBy(String answerBy) {
        this.answerBy = answerBy;
    }

    public Date getAnsPostedOn() {
        return ansPostedOn;
    }

    public void setAnsPostedOn(Date ansPostedOn) {
        this.ansPostedOn = ansPostedOn;
    }
}

package com.example.android.Retorfit.Model;

public class PointRequest {
    private String email;
    private Long amount;
    private Boolean inc;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Boolean getInc() {
        return inc;
    }

    public void setInc(Boolean inc) {
        this.inc = inc;
    }
}

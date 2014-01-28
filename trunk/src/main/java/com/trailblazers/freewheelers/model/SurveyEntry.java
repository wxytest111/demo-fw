package com.trailblazers.freewheelers.model;

public class SurveyEntry extends Account {
    private long accountId;
    private int rating;
    private String comment;

    public SurveyEntry(long accountId, int rating, String comment) {
        this.accountId = accountId;
        this.rating = rating;
        this.comment = comment;
    }

    public long getAccountId() {
        return accountId;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }
}

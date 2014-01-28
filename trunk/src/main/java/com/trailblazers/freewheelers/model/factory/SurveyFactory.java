package com.trailblazers.freewheelers.model.factory;

import com.trailblazers.freewheelers.model.SurveyEntry;

public class SurveyFactory {
    public SurveyEntry create(Long accountId, int rating, String comment) {
        return new SurveyEntry(accountId,rating,comment);
    }
}

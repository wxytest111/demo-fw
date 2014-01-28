package com.trailblazers.freewheelers.mappers;

import com.trailblazers.freewheelers.model.SurveyEntry;
import org.apache.ibatis.annotations.*;

public interface SurveyMapper {

    @Insert(
        "INSERT INTO survey_entry (account_id, rating, comment) " +
        "VALUES (#{accountId}, #{rating}, #{comment})"
    )
    @Options(keyProperty = "survey_entry_id", useGeneratedKeys = true)
    Integer insert(SurveyEntry surveyEntry);

    @Select(
        "SELECT COUNT(*) FROM survey_entry WHERE rating > 8"
    )
    public int getPromoterCount();

    @Select(
        "SELECT COUNT(*) FROM survey_entry WHERE rating < 7"
    )
    public int getDetractorCount();

    @Select(
        "SELECT COUNT(*) FROM survey_entry WHERE rating > 6 AND rating < 9"
    )
    public int getPassiveCount();
}

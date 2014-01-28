package com.trailblazers.freewheelers.service;

import com.trailblazers.freewheelers.mappers.MyBatisUtil;
import com.trailblazers.freewheelers.mappers.SurveyMapper;
import com.trailblazers.freewheelers.model.SurveyEntry;
import com.trailblazers.freewheelers.model.factory.SurveyFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class SurveyService {

    private SqlSession sqlSession;
    private SurveyFactory surveyFactory;
    private SurveyMapper surveyMapper;
    public static final String PROMOTERS_PERCENTAGE = "promoters percentage";
    public static final String DETRACTORS_PERCENTAGE = "detractors percentage";
    public static final String PASSIVES_PERCENTAGE = "passives percentage";
    public static final String NPS_SCORE = "nps score";

    public SurveyService() {
        this(MyBatisUtil.getSqlSessionFactory().openSession(), new SurveyFactory());
    }

    public SurveyService(SqlSession sqlSession, SurveyFactory surveyFactory) {
        this.sqlSession = sqlSession;
        this.surveyFactory = surveyFactory;
        this.surveyMapper = sqlSession.getMapper(SurveyMapper.class);
    }

    public void submitSurvey(Long accountId, String rating, String comment) {
        SurveyEntry surveyEntry = surveyFactory.create(accountId, Integer.parseInt(rating), comment);
        surveyMapper.insert(surveyEntry);
        sqlSession.commit();
    }

    public HashMap<String, Double> fetchSurveyPercentagesAndNPSScore() {

        int promoters = surveyMapper.getPromoterCount();
        int detractors = surveyMapper.getDetractorCount();
        int passives = surveyMapper.getPassiveCount();

        double totalAmountOfSurveys = promoters + detractors + passives;
        Double promotersPercentage = (promoters / totalAmountOfSurveys) * 100;
        Double detractorsPercentage = (detractors / totalAmountOfSurveys) * 100;
        Double passivesPercentage = (passives / totalAmountOfSurveys) * 100;

        Double npsScore = promotersPercentage - detractorsPercentage;

        HashMap<String, Double> percentagesAndNPSScore = new HashMap<String, Double>();
        percentagesAndNPSScore.put(PROMOTERS_PERCENTAGE, promotersPercentage);
        percentagesAndNPSScore.put(DETRACTORS_PERCENTAGE, detractorsPercentage);
        percentagesAndNPSScore.put(PASSIVES_PERCENTAGE, passivesPercentage);
        percentagesAndNPSScore.put(NPS_SCORE, npsScore);

        return percentagesAndNPSScore;
    }
}

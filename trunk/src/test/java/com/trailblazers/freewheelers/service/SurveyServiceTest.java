package com.trailblazers.freewheelers.service;

import com.trailblazers.freewheelers.mappers.SurveyMapper;
import com.trailblazers.freewheelers.model.SurveyEntry;
import com.trailblazers.freewheelers.model.factory.SurveyFactory;
import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.HashMap;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;

public class SurveyServiceTest {

    SurveyService surveyService;

    @Mock
    SqlSession sqlSession;
    @Mock
    SurveyFactory surveyFactory;
    @Mock
    SurveyMapper surveyMapper;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        when(sqlSession.getMapper(SurveyMapper.class)).thenReturn(surveyMapper);
        surveyService = new SurveyService(sqlSession, surveyFactory);
    }

    @Test
    public void shouldSaveSurveyResults() throws Exception {
        SurveyEntry surveyEntry = new SurveyEntry(12L, 10, "no comment");
        when(surveyFactory.create(12L, 10, "no comment")).thenReturn(surveyEntry);

        surveyService.submitSurvey(12L, "10", "no comment");

        verify(surveyMapper).insert(surveyEntry);
        verify(sqlSession).commit();
    }

    @Test
    public void shouldFetchPercentagesAndNPSScore() {
        when(surveyMapper.getPromoterCount()).thenReturn(25);
        when(surveyMapper.getDetractorCount()).thenReturn(50);
        when(surveyMapper.getPassiveCount()).thenReturn(25);

        HashMap<String, Double> percentagesAndNPSScore = surveyService.fetchSurveyPercentagesAndNPSScore();
        Double promotersPercentage = percentagesAndNPSScore.get(SurveyService.PROMOTERS_PERCENTAGE);
        Double detractorPercentage = percentagesAndNPSScore.get(SurveyService.DETRACTORS_PERCENTAGE);
        Double passivePercentage = percentagesAndNPSScore.get(SurveyService.PASSIVES_PERCENTAGE);
        Double npsScore = percentagesAndNPSScore.get(SurveyService.NPS_SCORE);

        assertEquals(promotersPercentage,new Double(25));
        assertEquals(detractorPercentage, new Double(50));
        assertEquals(passivePercentage, new Double(25));
        assertEquals(npsScore, new Double(-25));
    }

}

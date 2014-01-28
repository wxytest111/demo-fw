package com.trailblazers.freewheelers.service;

import com.trailblazers.freewheelers.mappers.SurveyMapper;
import com.trailblazers.freewheelers.model.SurveyEntry;
import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;

import java.util.HashMap;

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;

public class SurveyServiceTest {

    SurveyService surveyService;

    @Mock
    SqlSession sqlSession;
    @Mock
    SurveyMapper surveyMapper;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        when(sqlSession.getMapper(SurveyMapper.class)).thenReturn(surveyMapper);
        surveyService = new SurveyService(sqlSession);
    }

    @Test
    public void shouldSaveSurveyResults() throws Exception {
        final SurveyEntry surveyEntry = new SurveyEntry(12L, 10, "no comment");

        surveyService.submitSurvey(12L, "10", "no comment");

        verify(surveyMapper).insert(argThat(new SurveyEntryMatcher(surveyEntry)));
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

        assertEquals(promotersPercentage, new Double(25));
        assertEquals(detractorPercentage, new Double(50));
        assertEquals(passivePercentage, new Double(25));
        assertEquals(npsScore, new Double(-25));
    }

    private class SurveyEntryMatcher extends ArgumentMatcher<SurveyEntry> {
        private SurveyEntry surveyEntry;

        public SurveyEntryMatcher(SurveyEntry surveyEntry) {

            this.surveyEntry = surveyEntry;
        }

        @Override
        public boolean matches(Object o) {
            SurveyEntry entry = (SurveyEntry) o;
            return surveyEntry.getAccountId() == entry.getAccountId() &&
                    surveyEntry.getComment().equals(entry.getComment()) &&
                    surveyEntry.getRating() == entry.getRating();
        }
    }

}

package com.trailblazers.freewheelers.web;

import com.trailblazers.freewheelers.model.Account;
import com.trailblazers.freewheelers.service.AccountService;
import com.trailblazers.freewheelers.service.SurveyService;
import org.apache.http.auth.BasicUserPrincipal;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.HashMap;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class SurveyControllerTest {

    @Mock
    SurveyService surveyService;
    @Mock
    AccountService accountService;
    @InjectMocks
    SurveyController surveyController;

    Principal principal = new BasicUserPrincipal("john");
    Account userAccount = new Account();
    String rating = "10";
    String comment = "comment";

    @Before
    public void setUp() {
        initMocks(this);
        userAccount.setAccount_id(12L);
        when(accountService.getAccountIdByName("john")).thenReturn(userAccount);
    }

    @Test
    public void shouldSubmitSurveyForUser() throws Exception {
        ModelAndView modelAndView = surveyController.post(principal, rating, comment);
        verify(surveyService).submitSurvey(12L, "10", "comment");

        assertEquals("surveyConfirmation", modelAndView.getViewName());
    }

    @Test
    public void shouldShowValidationMessageIfSurveyRatingIsNull() throws Exception {
        rating = null;
        ModelAndView modelAndView = surveyController.post(principal, rating, comment);
        verifyValidationHappens(modelAndView);
    }

    @Test
    public void shouldShowValidationMessageIfSurveyRatingIsEmpty() throws Exception {
        rating = "";
        ModelAndView modelAndView = surveyController.post(principal, rating, comment);
        verifyValidationHappens(modelAndView);
    }

    @Test
    public void shouldShowReport() {
        HashMap<String,Double> percentagesAndNPSScore = new HashMap<String, Double>();
        percentagesAndNPSScore.put(SurveyService.PROMOTERS_PERCENTAGE, 25D);
        percentagesAndNPSScore.put(SurveyService.DETRACTORS_PERCENTAGE, 30D);
        percentagesAndNPSScore.put(SurveyService.PASSIVES_PERCENTAGE, 35D);
        percentagesAndNPSScore.put(SurveyService.NPS_SCORE, -5D);
        when(surveyService.fetchSurveyPercentagesAndNPSScore()).thenReturn(percentagesAndNPSScore);

        ModelAndView modelAndView = surveyController.getReport();
        String viewName = modelAndView.getViewName();
        ModelMap npsReport = (ModelMap) modelAndView.getModelMap().get("npsReport");
        Double promoters = (Double) npsReport.get("promoters");
        Double detractors = (Double) npsReport.get("detractors");
        Double passives = (Double) npsReport.get("passives");
        Double npsScore = (Double) npsReport.get("npsScore");

        assertEquals("surveyReport", viewName);
        assertEquals(25D, promoters);
        assertEquals(30D, detractors);
        assertEquals(35D, passives);
        assertEquals(-5D, npsScore);
    }

    private void verifyValidationHappens(ModelAndView modelAndView) throws Exception {
        verify(surveyService, never()).submitSurvey(anyLong(), anyString(), anyString());
        ModelMap modelMap = (ModelMap) modelAndView.getModelMap().get("validation");
        Boolean mandatoryFieldMissing = (Boolean) modelMap.get("mandatoryFieldMissing");
        assertTrue(mandatoryFieldMissing);
        assertEquals("survey", modelAndView.getViewName());
    }
}

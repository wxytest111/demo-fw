package com.trailblazers.freewheelers.web;


import com.trailblazers.freewheelers.model.Account;
import com.trailblazers.freewheelers.service.AccountService;
import com.trailblazers.freewheelers.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.HashMap;

@Controller
@RequestMapping("/survey")
public class SurveyController {

    @Autowired
    SurveyService surveyService;
    @Autowired
    AccountService accountService;

    @RequestMapping(method = RequestMethod.GET)
    public void get() {
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView post(Principal principal,
                             @RequestParam(value = "survey_rating", required = false) String surveyRating,
                             @RequestParam(value = "survey_comment", required = false) String surveyComment) {
        String username = principal.getName();

        if (surveyRating == null || surveyRating.isEmpty()) {
            return showValidationError();
        }
        Account account = accountService.getAccountIdByName(username);
        surveyService.submitSurvey(account.getAccount_id(), surveyRating, surveyComment);
        return new ModelAndView("surveyConfirmation");
    }

    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public ModelAndView getReport() {
        HashMap<String,Double> npsReportMap = surveyService.fetchSurveyPercentagesAndNPSScore();

        ModelMap model = new ModelMap();
        model.addAttribute("promoters",npsReportMap.get(SurveyService.PROMOTERS_PERCENTAGE));
        model.addAttribute("detractors",npsReportMap.get(SurveyService.DETRACTORS_PERCENTAGE));
        model.addAttribute("passives",npsReportMap.get(SurveyService.PASSIVES_PERCENTAGE));
        model.addAttribute("npsScore",npsReportMap.get(SurveyService.NPS_SCORE));
        return new ModelAndView("surveyReport", "npsReport", model);
    }

    private ModelAndView showValidationError() {
        ModelMap model = new ModelMap();
        model.addAttribute("mandatoryFieldMissing", true);
        return new ModelAndView("survey", "validation", model);
    }

}

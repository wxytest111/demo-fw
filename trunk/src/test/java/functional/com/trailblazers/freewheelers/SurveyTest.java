package functional.com.trailblazers.freewheelers;

import org.junit.Test;

public class SurveyTest extends UserJourneyBase {

    private String promoter1 = "promoter1";
    private String promoter2 = "promoter2";
    private String detractor = "detractor";
    private String passive = "passive";

    private String adminUsername = "admin";
    private String password = "password";

    @Test
    public void shouldSubmitSurveyAndGetThankYouMessage() {

        admin.there_is_an_admin(adminUsername, password)
                .there_is_a_user(promoter1, password)
                .there_is_a_user(promoter2, password)
                .there_is_a_user(passive, password)
                .there_is_a_user(detractor, password);


        // FILLING OUT SURVEY

        //Scenario 1: unlogged in user
        user.is_logged_out()
                .visit_survey_page();
        screen.should_show_login_page();

        //Scenario 2: logged in user
        user.logs_in_with(promoter1, password);
        screen.should_show_survey_page();

        //Filling in survey
        user.fills_in_survey(10, "no comment");
        screen.should_show_thank_you_page();

        userLogInAndFillSurvey(promoter2, 9);
        userLogInAndFillSurvey(passive, 7);
        userLogInAndFillSurvey(detractor, 5);

        user.is_logged_out()
                .visit_survey_report_page();
        screen.should_show_login_page();

        user.logs_in_with(promoter1, password);
        screen.should_show_access_denied();

        user.is_logged_out()
                .logs_in_with(adminUsername, password)
                .visit_survey_report_page();
        screen.should_show_survey_report_page()
                .should_show_survey_promoter_percentage(50D)
                .should_show_survey_passive_percentage(25D)
                .should_show_survey_detractor_percentage(25D)
                .should_show_nps_score(25D);

    }

    private void userLogInAndFillSurvey(String username, int rating) {
        user.is_logged_out()
                .logs_in_with(username, password)
                .visit_survey_page()
                .fills_in_survey(rating, "no comment");
    }


}

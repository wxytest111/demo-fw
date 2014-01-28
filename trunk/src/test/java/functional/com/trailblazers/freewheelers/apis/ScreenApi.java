package functional.com.trailblazers.freewheelers.apis;

import functional.com.trailblazers.freewheelers.helpers.HomeTable;
import functional.com.trailblazers.freewheelers.helpers.ManageItemTable;
import functional.com.trailblazers.freewheelers.helpers.OrderTable;
import functional.com.trailblazers.freewheelers.helpers.URLs;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;

public class ScreenApi {
    private WebDriver driver;

    public ScreenApi(WebDriver driver) {
        this.driver = driver;
    }

    public void shows_error_alert(String expectedMessage) {
        expectMessageWithClass(expectedMessage, "error");
    }

    public void shows_error(String expectedMessage) {
        expectMessageWithClass(expectedMessage, "text-error");
    }

    public void shows_message(String expectedMessage) {
        expectMessageWithClass(expectedMessage, "page-action");
    }

    public void shows_in_navbar(String expectedMessage) {
        expectMessageWithClass(expectedMessage, "navbar-text");
    }

    public ScreenApi shows_profile_for(String name) {
        String userDetails = driver.findElement(By.id("user-details")).getText();

        assertThat(userDetails, containsString(name));
        return this;
    }

    public ScreenApi should_show_login_page() {
        assertThat(driver.getCurrentUrl(), startsWith(URLs.login()));
        return this;
    }

    public ScreenApi shows_admin_profile() {
        assertThat(driver.getCurrentUrl(), is(URLs.admin()));
        return this;
    }

    public ScreenApi should_show_survey_page() {
        assertThat(driver.getCurrentUrl(), is(URLs.survey()));
        return this;
    }

    public ScreenApi shows_in_manage_item_list(String item) {
        assertNumberOfRows(1, ManageItemTable.nameFieldFor(item));
        return this;
    }

    public ScreenApi shows_not_in_manage_item_list(String item) {
        assertNumberOfRows(0, ManageItemTable.nameFieldFor(item));
        return this;
    }

    public ScreenApi should_list_item(String item) {
        assertNumberOfRows(1, HomeTable.nameFieldFor(item));
        return this;
    }

    public ScreenApi should_not_list_item(String item) {
        assertNumberOfRows(0, HomeTable.nameFieldFor(item));
        return this;
    }

    public ScreenApi there_should_be_an_order(String item, String state) {
        WebElement select = driver.findElement(OrderTable.selectFor(item));
        String selected = new Select(select).getFirstSelectedOption().getText();

        assertThat(selected, is(state));

        return this;
    }

    private void assertNumberOfRows(int expectedRows, By selector) {
        List<WebElement> elements = driver.findElements(selector);
        assertThat(elements.size(), is(expectedRows));
    }

    private ScreenApi expectMessageWithClass(String expectedMessage, String messageClass) {
        String errorMessage = driver.findElement(By.className(messageClass)).getText();

        assertThat(errorMessage, containsString(expectedMessage));
        return this;
    }

    public ScreenApi should_show_thank_you_page() {
        assertThat(driver.findElements(By.id("surveyThankYou")).size(), is(1));
        return this;
    }

    public ScreenApi should_show_survey_report_page() {
        assertThat(driver.findElements(By.id("surveyReport")).size(), is(1));
        return this;
    }

    public ScreenApi should_show_survey_promoter_percentage(double percentage) {
        assertThat(driver.findElement(By.id("promoterPercentage")).getText(), is(Double.toString(percentage)));
        return this;
    }

    public ScreenApi should_show_survey_passive_percentage(double percentage) {
        assertThat(driver.findElement(By.id("passivePercentage")).getText(), is(Double.toString(percentage)));
        return this;
    }

    public ScreenApi should_show_survey_detractor_percentage(double percentage) {
        assertThat(driver.findElement(By.id("detractorPercentage")).getText(), is(Double.toString(percentage)));
        return this;
    }

    public ScreenApi should_show_nps_score(double npsScore) {
        assertThat(driver.findElement(By.id("npsScore")).getText(), is(Double.toString(npsScore)));
        return this;
    }

    public ScreenApi should_show_access_denied() {
        assertThat(driver.getPageSource(),containsString("403 Your access is denied"));
        return this;
    }
}

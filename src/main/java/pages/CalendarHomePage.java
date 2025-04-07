package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.qameta.allure.Step;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.GlobalVariables;
import util.Helpers;

public class CalendarHomePage extends Helpers {

    protected IOSDriver driver;

    @iOSXCUITFindBy(accessibility = "Calendar")
    private RemoteWebElement calendarHomePageContainer;

    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeButton[`name == \"Continue\"`]")
    private RemoteWebElement continueButton;

    @iOSXCUITFindBy(accessibility = "add-plus-button")
    private  RemoteWebElement addEventButton;

    @iOSXCUITFindBy(accessibility = "calendars-button")
    private RemoteWebElement calendars;

    public CalendarHomePage(IOSDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void swipeToDayInaWeek(String day,int swipes) {
        int counter = 0;
        while (counter < swipes) {
            WebElement element = driver.findElement(AppiumBy.iOSClassChain("**/XCUIElementTypeStaticText[`name == \"current-day\"`]"));
            String label = element.getAttribute("label");

            if (label.contains(day)) {
                return;
            }
            swipeByDirection(driver, Directions.LEFT);
            swipes++;
        }
        throw new NoSuchElementException("Could not find the day: " + day);
    }

    @Step("Calendar home page is loaded")
    public boolean calendarHomePageLoaded() {
        return new WebDriverWait(driver, GlobalVariables.globalTimeout).until(ExpectedConditions.visibilityOf(calendarHomePageContainer)).isDisplayed();
    }
    @Step("Adding event")
    public void clickAddButton(){
        addEventButton.click();
    }
    @Step("Pressing on Calendars Menu")
    public void clickCalendarsButton(){
        calendars.click();
    }

    @Step("Pressing on continue button")
    public void clickContinueButton() throws InterruptedException {
        continueButton.click();
        Thread.sleep(GlobalVariables.globalTimeout);
    }

}

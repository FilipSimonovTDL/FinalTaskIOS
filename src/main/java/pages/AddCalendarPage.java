package pages;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.qameta.allure.Step;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.GlobalVariables;
import util.Helpers;

public class AddCalendarPage extends Helpers {

    protected IOSDriver driver;


    @iOSXCUITFindBy(accessibility = "calendar-title-field")
    private RemoteWebElement calendarTitle;

    @iOSXCUITFindBy(accessibility = "calendar-current-selected-color")
    private RemoteWebElement calendarColor;

    @iOSXCUITFindBy(accessibility = "delete-calendar-button")
    private RemoteWebElement deleteButton;

    @iOSXCUITFindBy(iOSNsPredicate = "name == \"Delete Calendar\"")
    private RemoteWebElement deleteButtonFinal;

    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeButton[`name == \"done-button\"`][2]")
    private RemoteWebElement doneButton;


    public AddCalendarPage(IOSDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @Step("Add calendar  page is loaded")
    public boolean AddCalendarPageLoaded() {
        return new WebDriverWait(driver, GlobalVariables.globalTimeout).until(ExpectedConditions.visibilityOf(calendarTitle)).isDisplayed();
    }

    @Step("Pressing done button")
    public void clickDoneButton(){
        //doneButton.click(); the button itself isn't clickable not even on Appium inspector
        pressElementByCoordinates(driver,doneButton);
    }
    @Step("Setting calendar name")
    public void setTitle(String title) {
        calendarTitle.sendKeys(title);
    }

    @Step("Changing calendar color")
    public void clickCalendarColorButton(){
        calendarColor.click();
    }

    @Step("Deleting calendar")
    public void deleteCalendar(){
        deleteButton.click();
        deleteButtonFinal.click();
    }
}

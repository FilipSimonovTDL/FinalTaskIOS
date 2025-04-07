package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.qameta.allure.Step;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.GlobalVariables;


import static util.GlobalVariables.*;

public class CalendarColorPage {

    protected IOSDriver driver;

    @iOSXCUITFindBy(iOSNsPredicate = "name == 'calendar-current-selected-color' AND label == 'Yellow'")
    private WebElement color;


    public CalendarColorPage(IOSDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @Step("Calendar color page is loaded")
    public boolean calendarColorPageLoaded() {
        return new WebDriverWait(driver, GlobalVariables.globalTimeout).until(ExpectedConditions.visibilityOf(color)).isDisplayed();
    }

    @Step("Setting calender color")
    public void chooseCalendarColor(String color){
        String predicate = "name == 'calendar-current-selected-color' AND label == '" + color + "'";
        try {
            new WebDriverWait(driver, GlobalVariables.globalTimeout).until(ExpectedConditions.elementToBeClickable(
                    AppiumBy.iOSNsPredicateString(predicate)));
            WebElement element= driver.findElement(AppiumBy.iOSNsPredicateString(predicate));
            element.click();
            driver.navigate().back();
        } catch (NoSuchElementException | TimeoutException e) {
            throw new NoSuchElementException(
                    "Could not find or click calendar color " + color + " within " + globalTimeout + " seconds"
            );
        }
    }
}

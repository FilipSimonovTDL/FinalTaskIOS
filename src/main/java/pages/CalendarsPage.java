package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.qameta.allure.Step;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.GlobalVariables;

public class CalendarsPage {

    protected IOSDriver driver;

    @iOSXCUITFindBy(accessibility = "add-calendar-button")
    private RemoteWebElement addCalendarMenu;

    @iOSXCUITFindBy(accessibility = "add-calendar-menubutton")
    private RemoteWebElement addCalendarButton;

    public CalendarsPage(IOSDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @Step("Calendar home page is loaded")
    public boolean calendarsPageLoaded() {
        return new WebDriverWait(driver, GlobalVariables.globalTimeout).until(ExpectedConditions.visibilityOf(addCalendarMenu)).isDisplayed();
    }
    @Step("Validating if newly created calendar is selected")
    public boolean validateTheNewCalendar(String calendarName){
        String classChain = String.format("**/XCUIElementTypeCell[`name == \"calendarlist-cell:Default:%s\"`]/XCUIElementTypeOther[2]/XCUIElementTypeImage[1]", calendarName);
        try {
            new WebDriverWait(driver, GlobalVariables.globalTimeout).until(ExpectedConditions.visibilityOf(driver.findElement(AppiumBy.iOSClassChain(classChain))));
            WebElement element = driver.findElement(AppiumBy.iOSClassChain(classChain));
            String status= element.getAttribute("label");
            return status.equals("selected");
        } catch (NoSuchElementException e) {
            return false;
        }
    }
    @Step("Edit calendar")
    public void pressEditButtonOnCalendarByName(String calendarName){
        String classChain = String.format("**/XCUIElementTypeOther[`name == \"calendarlist-cell:Default:%s\"`]",calendarName);
        try {
            new WebDriverWait(driver, GlobalVariables.globalTimeout).until(ExpectedConditions.visibilityOf(driver.findElement(AppiumBy.iOSClassChain(classChain))));
            WebElement element = driver.findElement(AppiumBy.iOSClassChain(classChain));
            element.click();
        }catch (NoSuchElementException | TimeoutException e) {
            throw new NoSuchElementException(
                    "Could not find or click the Edit button for calendar");
        }
    }
    @Step("Opening add calendar menu")
    public void clickAddCalendarMenu(){
        addCalendarMenu.click();
    }

    @Step("Adding calendar")
    public void clickAddCalendarButton() {
        if (new WebDriverWait(driver, GlobalVariables.globalTimeout).until(ExpectedConditions.visibilityOf(addCalendarButton)).isDisplayed()) {
            addCalendarButton.click();
        } else throw new NoSuchElementException("Add Calendar Button not visible because the menu wasn't open");
    }

}

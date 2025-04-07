package pages;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.GlobalVariables;

public class NewEventPage {

    protected IOSDriver driver;

    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeStaticText[`name == \"New\"`]")
    private RemoteWebElement newEventPageContainer;

    @iOSXCUITFindBy(xpath = "(//XCUIElementTypePickerWheel)[1]")
    private RemoteWebElement firstWheelPicker;

    @iOSXCUITFindBy(xpath = "(//XCUIElementTypePickerWheel)[2]")
    private RemoteWebElement secondWheelPicker;

    @iOSXCUITFindBy(iOSNsPredicate = "name == 'DatePicker.Show' OR name == 'DatePicker.Hide'")
    private RemoteWebElement datepicker;

    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeCell[@name='start-date-picker-cell']/child::*//XCUIElementTypeButton)[1]")
    private RemoteWebElement startDatePickerButton;

    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeCell[@name='start-date-picker-cell']/child::*//XCUIElementTypeButton)[2]")
    private RemoteWebElement startTimePickerButton;

    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeCell[@name='end-date-picker-cell']/child::*//XCUIElementTypeButton)[1]")
    private RemoteWebElement endDatePickerButton;

    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeCell[@name='end-date-picker-cell']/child::*//XCUIElementTypeButton)[2]")
    private RemoteWebElement endTimePickerButton;

    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeCell[@name='travel-time-cell']//XCUIElementTypeButton)[1]")
    private RemoteWebElement travelTime;

    @iOSXCUITFindBy(accessibility = "title-field")
    private RemoteWebElement Title;

    @iOSXCUITFindBy(accessibility = "all-day-switch")
    private RemoteWebElement alldaySwitch;

    @iOSXCUITFindBy(accessibility = "add-button")
    private RemoteWebElement addButton;


    public NewEventPage(IOSDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @Step("New event page is loaded")
    public boolean newEventPageLoaded() {
        return new WebDriverWait(driver, GlobalVariables.globalTimeout).until(ExpectedConditions.visibilityOf(newEventPageContainer)).isDisplayed();
    }

    @Step("Checking All day switch function")
    public boolean isTimePickerDisplayed() {
        try {
            return startTimePickerButton.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }


    @Step("All day switch is toggled")
    public void toggleAllDaySwitch(){
        alldaySwitch.click();
    }

    @Step("Opening Travel time")
    public void openTravelTime(){
        travelTime.click();
    }

    @Step("Creating event")
    public void createEvent(){
        addButton.click();
    }

    @Step("{type} date")
    public void chooseDate(String type, String month, String year, String day,String hour, String minutes) {
        RemoteWebElement datePickerButton;
        RemoteWebElement timePickerButton;

        switch (type.toUpperCase()) {
            case "START":
                datePickerButton = startDatePickerButton;
                timePickerButton = startTimePickerButton;
                break;
            case "END":
                datePickerButton = endDatePickerButton;
                timePickerButton = endTimePickerButton;
                break;
            default:
                throw new IllegalArgumentException("Invalid type: " + type + ". Use 'START' or 'END'.");
        }
        datePickerButton.click();
        datepicker.click();
        new WebDriverWait(driver, GlobalVariables.globalTimeout).until(ExpectedConditions.visibilityOf(firstWheelPicker)).sendKeys(month);
        secondWheelPicker.sendKeys(year);
        datepicker.click();
        String xpath = "//XCUIElementTypeStaticText[@name="+day +"]";
        WebElement element= driver.findElement(By.xpath(xpath));
        if(element.isDisplayed()){
            element.click();
        }
        else{
            throw new NoSuchElementException("There isn't such a day in the selected Month");
        }
        new WebDriverWait(driver, GlobalVariables.globalTimeout).until(ExpectedConditions.elementToBeClickable(startTimePickerButton));
        timePickerButton.click();
        new WebDriverWait(driver, GlobalVariables.globalTimeout).until(ExpectedConditions.visibilityOf(firstWheelPicker)).sendKeys(hour);
        secondWheelPicker.sendKeys(minutes);
    }


    public void selectTravelTimeOption(String travelTimeValue) {
        String accessibilityId = "tavel-time-menu-option:" + travelTimeValue;// it isn't actually travel ...
        try {
            WebElement time = new WebDriverWait(driver, GlobalVariables.globalTimeout).until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId(accessibilityId)));
            time.click();
        } catch (Exception e) {
            throw new NoSuchElementException("Desired Travel time options isn't available");
        }
    }

    @Step("Setting title based on API response")
    public void setTitle(String title) {
        Title.sendKeys(title);
    }
}

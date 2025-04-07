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

public class MonthPage extends Helpers {

    protected IOSDriver driver;


    @iOSXCUITFindBy(accessibility = "Months")
    private RemoteWebElement monthsPage;

    private WebElement searchDate;

    public MonthPage(IOSDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }


    @Step("Months page is loaded")
    public boolean MonthsPageLoaded() {
        return new WebDriverWait(driver, GlobalVariables.globalTimeout).until(ExpectedConditions.visibilityOf(monthsPage)).isDisplayed();
    }
    @Step("Returning search date")
    public WebElement getSearchDate() {
        return searchDate;
    }

    @Step("Scrolling to date")
    public void scrollTillEventDate(String date,Directions directions,int swipeCount) {
        WebElement element = null;
        for (int i = 0; i < swipeCount; i++) {
            try {
                String accessibilityPredicate = "name CONTAINS '" + date + "'";
                element = driver.findElement(AppiumBy.iOSNsPredicateString(accessibilityPredicate));
                if (element.isDisplayed()) {
                    searchDate=element;
                    return;
                }
            } catch (NoSuchElementException e) {
                swipeByDirection(driver, directions);
            }
        }
        System.out.println("Couldn't find date within desired number of swipes ");
    }

    @Step("Checking if event is created")
    public boolean checkEventStatus(WebElement element){
        String status = element.getAttribute("value");
        //assert status != null;
        return !status.equals("No events");
    }

}

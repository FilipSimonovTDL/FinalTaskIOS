package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import util.DriverSetup;
import util.GlobalVariables;
import util.Helpers;

public class CalendarTest extends DriverSetup {
    @Description("Creating a whole day event")
    @Story("Creation of whole day event")
    @Test(testName = "Whole Day Event Test")
    public void wholeDayEventTest() throws InterruptedException {
        Assert.assertTrue(calendarHomePage.calendarHomePageLoaded(), "Calendar home page is not loaded");
        calendarHomePage.clickContinueButton();
        calendarHomePage.clickAddButton();
        Assert.assertTrue(newEventPage.newEventPageLoaded(), "New event page is not loaded");
        GlobalVariables.response = restAssuredUtility.getActivityValue("activity");
        newEventPage.setTitle(GlobalVariables.response);
        newEventPage.chooseDate("start","May","2025","22","10","30");
        newEventPage.chooseDate("end","May","2025","23","11","30");
        newEventPage.openTravelTime();
        newEventPage.selectTravelTimeOption("30 minutes");
        newEventPage.toggleAllDaySwitch();
        Assert.assertFalse(newEventPage.isTimePickerDisplayed());
        newEventPage.createEvent();
        driver.navigate().back();
        Assert.assertTrue(monthPage.MonthsPageLoaded(),"Months page is not loaded");
        monthPage.scrollTillEventDate("22 May", Helpers.Directions.UP,5);
        WebElement date = monthPage.getSearchDate();
        Assert.assertTrue(monthPage.checkEventStatus(date));
    }

    @Description("Creating new calendar")
    @Story("Creation of new calendar")
    @Test(testName = "Create a new calendar Test")
    public void createNewCalendarTest() throws InterruptedException {
        String Title= "New Calendar";
        Assert.assertTrue(calendarHomePage.calendarHomePageLoaded(), "Calendar home page is not loaded");
        calendarHomePage.clickContinueButton();
        calendarHomePage.clickCalendarsButton();
        Assert.assertTrue(calendarsPage.calendarsPageLoaded(),"Calendars page is not loaded");
        calendarsPage.clickAddCalendarMenu();
        calendarsPage.clickAddCalendarButton();
        Assert.assertTrue(addCalendarPage.AddCalendarPageLoaded(), "Add Calendar page is not loaded");
        addCalendarPage.setTitle(Title);
        addCalendarPage.clickCalendarColorButton();
        Assert.assertTrue(calendarColorPage.calendarColorPageLoaded(),"Select color page is not loaded");
        calendarColorPage.chooseCalendarColor("Blue");
        Assert.assertTrue(addCalendarPage.AddCalendarPageLoaded(), "Add Calendar page is not loaded");
        addCalendarPage.clickDoneButton();
        Assert.assertTrue(calendarsPage.calendarsPageLoaded(),"Calendars page is not loaded");
        Assert.assertTrue(calendarsPage.validateTheNewCalendar(Title));
        calendarsPage.pressEditButtonOnCalendarByName(Title);
        Assert.assertTrue(addCalendarPage.AddCalendarPageLoaded(), "Add Calendar page is not loaded");
        addCalendarPage.deleteCalendar();
        Assert.assertTrue(calendarsPage.calendarsPageLoaded(),"Calendars page is not loaded");
    }
}

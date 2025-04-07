package util;

import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.util.List;
import java.util.stream.IntStream;

import static java.time.Duration.*;
import static org.openqa.selenium.interactions.PointerInput.Kind.TOUCH;
import static org.openqa.selenium.interactions.PointerInput.MouseButton.LEFT;
import static org.openqa.selenium.interactions.PointerInput.Origin.viewport;

public class Helpers {

    public enum Directions {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    private final PointerInput FINGER = new PointerInput(TOUCH, "finger");


    public void swipeByDirection(IOSDriver driver, Directions directions) {
        int width = driver.manage().window().getSize().getWidth();
        int height = driver.manage().window().getSize().getHeight();
        int startY=height/2;
        int startX= width/2;
        int endY=startY;
        int endX=startX;

        switch (directions) {
            case UP -> endY = (int) (driver.manage().window().getSize().getHeight() * 0.2);
            case DOWN -> endY = (int) (driver.manage().window().getSize().getHeight() * 0.8);
            case LEFT -> { endX= (int) (driver.manage().window().getSize().getWidth() * 0.2); startX = (int) (width* 0.8);}
            case RIGHT -> { endX = (int) (driver.manage().window().getSize().getWidth() * 0.8); startX = (int)(width *0.2);}
            default -> throw new IllegalArgumentException("Invalid direction selected:" + directions);
        }

        Sequence swipe = new Sequence(FINGER, 0);
        swipe.addAction(FINGER.createPointerMove(ZERO, viewport(), startX, startY));
        swipe.addAction(FINGER.createPointerDown(LEFT.asArg()));
        swipe.addAction(FINGER.createPointerMove(ofSeconds(1), viewport(), endX, endY));
        swipe.addAction(FINGER.createPointerUp(LEFT.asArg()));
        driver.perform(List.of(swipe));
    }

    public void scrollTo(IOSDriver driver, WebElement element, Directions directions, int swipeCount) {
        IntStream.range(0, swipeCount).forEach(obj -> {
            if (!element.isDisplayed())
                swipeByDirection(driver, directions);
        });
    }

    public void pressElementByCoordinates(IOSDriver driver, WebElement element){
        Point location = getCenter(element);
        Sequence longPress = new Sequence(FINGER, 0);
        longPress.addAction(FINGER.createPointerMove(ZERO, viewport(), location.x, location.y));
        longPress.addAction(FINGER.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        longPress.addAction(FINGER.createPointerMove(ofMillis(200), viewport(), location.x, location.y));
        longPress.addAction(FINGER.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(List.of(longPress));
    }

    public void longPress(IOSDriver driver, WebElement element) {
        Point location = getCenter(element);
        Sequence longPress = new Sequence(FINGER, 0);
        longPress.addAction(FINGER.createPointerMove(ZERO, viewport(), location.x, location.y));
        longPress.addAction(FINGER.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        longPress.addAction(FINGER.createPointerMove(ofSeconds(1), viewport(), location.x, location.y));
        longPress.addAction(FINGER.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(List.of(longPress));
    }

    public Point getCenter(WebElement element) {
        Point location = element.getLocation();
        Dimension size = element.getSize();
        return new Point(location.x + size.getWidth() / 2, location.y + size.getHeight() / 2);
    }
}

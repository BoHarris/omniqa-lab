package mobile.pages.android;

import io.appium.java_client.AppiumDriver;
import mobile.pages.LoginScreen;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AndroidLoginPage implements LoginScreen{
    private final AppiumDriver<?> driver; 

    private static final Duration WAIT = Duration.ofSeconds(10)

    private static final By USERNAME = By.id("username");
    private static final By PASSWORD = By.id("password");
    private static final By LOGIN_BTN =  By.id("btn_login");
    private static final By FLASH_TEXT =By.id("flash");

    public AndroidLoginPage(AppiumDriver<?> driver){
        this.driver = driver;
    }

    @Override
    public LoginScreen enterUserName(String user){
        WebElement el = waitVisible(USERNAME);
        el.clear();
        el.sendKeys(user);
        return this;
    }

     @Override
    public LoginScreen enterPassword(String pass) {
        WebElement el = waitVisible(PASSWORD);
        el.clear();
        el.sendKeys(pass);
        return this;
    }

    @Override
    public void tapLogin(){
        new WebDriverWait(driver, WAIT)
        .until(ExpectedConditions.elementToBeClickable(LOGIN_BTN))
        .click();
    }

    @Override
    public String flashMessage(){
        return waitFor(FLASH_TEXT).getText();
    } 

    private WebElement waitFor(By locator){
        return new WebDriverWait(driver, Duration.ofSeconds(10))
        .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}

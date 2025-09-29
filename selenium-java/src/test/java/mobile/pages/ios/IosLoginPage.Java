package mobile.pages.ios;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import mobile.pages.LoginScreen;
import java.time.Duration;

public class IosLoginPage implements LoginScreen{
    private final AppiumDriver<?> driver

    private static final Duration WAIT = Duration.ofSeconds(10)

    private static final By USERNAME   = AppiumBy.accessibilityId("username");
    private static final By PASSWORD   = AppiumBy.accessibilityId("password");
    private static final By LOGIN_BTN = AppiumBy.iOSNsPredicateString("name == 'LOGIN'");
    private static final By FLASH_TEXT = AppiumBy.iOSClassChain("**/XCUIElementTypeStaticText[`name == 'flash'`]");

    

    public IosLoginPage(AppiumDriver driver){
        this.driver = driver;
    }

    @Override
    public LoginScreen enterUserName(String user){
        WebElement el = waitFor(USERNAME);
        el.clear();
        el.sendKeys(user);
        return this;
    }

     @Override
    public LoginScreen enterPassword(String pass) {
        WebElement el = waitFor(PASSWORD);
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

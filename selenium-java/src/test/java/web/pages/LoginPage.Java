package test.java.web.pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import io.qameta.allure.Step;

import utils.ConfigReader;

public class LoginPage extends BasePage{
    private static final String PATH = "/login";

    private final By username = By.id("username");
    private final By password = By.id("password");
    private final By login_btn = By.cssSelector("button[type='submit']");
    private final By flash = By.id("flash");

    public LoginPage(WebDriver driver){super(driver);}

    @Step("Open login page")
    public LoginPage open(){driver.navigate().to(ConfigReader.baseUrl() + PATH); return this;}

    @step("Type username: {user}")
    public LoginPage enterUsername(String user){type(username, user); return this;}

    @step("Type password")
    public LoginPage enterPassword(String pass){type(password, pass); return this;}

    @step("Submit login form")
    public LoginPage submit(){click(login_btn); return this;}

    public String getFlashMessage(){ return text(flash);}
}
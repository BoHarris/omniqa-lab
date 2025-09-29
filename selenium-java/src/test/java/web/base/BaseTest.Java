package base;

import io.qameta.allure.Attachment;
import io.qameta.allure.testng.AllureTestNg;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Properties;

import drivers.WebDriverFactory;
import utils.Allure.AllureEnv;
import utils.ConfigReader;
import utils.ScreenshotUtil;

@Listeners({AllureTestNg.class})
public abstract class BaseTest{
    protected final Logger log = LogManager.getLogger(BaseTest.class);
    private static final ThreadLocal<WebDriver> TL_Driver = new ThreadLocal<>();

        @BeforeSuite(alwaysRun = true)
        public void writeAllureEnviroment(){
            Properties extra = new Properties();
            extra.setProperty("Selenium", "4.24.0");
            extra.setProperty("TestNG", "7.10.2");
            AllureEnv.write(extra);
        }

        @Parameters({"browser", "headless"})
        @BeforeMethod(alwaysRun = true){
        public void setUp(
            @Optional String browser,
            @Optional String headless
        ){

        String b = (browser != null && !browser.isBlank()) 
        ? browser 
        : ConfigReader.headless();

        boolean isHeadless = (headless != null) 
        ? Boolean.parseBoolean(headless) 
        : ConfigReader.headless();

        WebDriver driver = WebDriverFactory.createDriver(b, isHeadless);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(
            ConfigReader.pageLoadTimeoutSec()));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(
            ConfigReader.implicitlyWait()));

        TL_Driver.set(driver);

        log.info("Opening Base URL: {}", ConfigReader.baseUrl());
        driver.get(ConfigReader.baseUrl());
    }

    
    
    @AfterMethod(alwaysRun = true)
    public void teardown(ITestResult result){
        if(result.getStatus() == ITestResult.FAILURE && getDriver() != null){
            log.error("Test failed: {}", result.getName());
            saveScreenshot();
            attachPageSource(new String(
                getDriver().getPageSource().getBytes(StandardCharsets.UTF_8), 
                StandardCharsets.UTF_8));
        }
        if(getDriver() != null){ 
            getDriver().quit(); 
            TL_Driver.remove();
        }
    }
    
    public WebDriver getDriver(){
        return TL_Driver.get();
    }

    @Attachment(value = "Page Source", type = "text/html")
    public String attachPageSource(String html){
        return html;
    }

    @Attachment(value = "Failure Screenshot", type = "image/png")
    public byte[] saveScreenshot(){
        return ScreenshotUtil.takePng(getDriver());
    }
}



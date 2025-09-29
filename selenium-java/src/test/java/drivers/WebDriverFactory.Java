package drivers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public final class WebDriverFactory{

    private WebDriverFactory(){}
    
    public static WebDriver createDriver(String browser, boolean headless){
            switch(browser.toLowerCase()){
                case "firefox" -> {
                    WebDriverManager.FirefoxDriver().setup();
                    FirefoxOptions ff = new FirefoxOptions();
                    ff.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                    if(headless) ff.addArguments("-headless");
                    return new FirefoxDriver(ff);
                }
                case "edge" ->{
                    WebDriverManager.EdgeDriver().setup();
                    EdgeOptions eo = new EdgeOptions();
                    eo.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                    if(headless) eo.addArguments("-headless=new");
                    return new EdgeDriver(eo);
                }
                default->{ //Chrome
                    WebDriverManager.ChromeDriver().setup();
                    ChromeOptions co = new ChromeOptions();
                    co.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                    if(headless) co.addArguments("-headless=new");
                    return new ChromeDriver(co);
                }

            }
        }
}
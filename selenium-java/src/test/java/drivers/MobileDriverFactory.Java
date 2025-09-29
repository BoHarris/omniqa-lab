package drivers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import java.net.URI;
import java.net.MalformedURLException;
import utils.ConfigReader;

public final class MobileDriverFactory{
    private MobileDriverFactory(){}

    public static AppiumDriver create(){
        String platform = String.valueOf(ConfigReader.envOrProp("mobilePlatform")).trim();
        String server = String.valueOf(ConfigReader.envOrProp("appiumServerUrl")).trim()

        if (platform.isEmpty()){
            throw new IllegalArgumentException("Config 'mobilePlatform' is not is not set (expected 'android' or 'ios').");
        }
        if (server.isEmpty()){
            throw new IllegalArgumentException("Config 'appiumServerUrl' is not set (e.g., http://127.0.0.1:4723/).");
        }

        try{
            URL url = new URL(server)
            return switch(platform.toLowerCase()){
                case "android" -> new AndroidDriver(
                    url,
                    androidOptions()
                );
                case "ios" -> new IOSDriver(
                    url,
                    iosOptions()
                );
                default -> throw new IllegalArgumentException("Unsupported Mobile Platform" + platform);

            };

        }catch(MalformedURLException e){
            throw new RuntimeException("Bad Appium server URL" + server, e)
        }
    }
    private static String resolvePath(String p){
        if (p == null || p.isBlank()) return p;
        boolean isAbsolute = 
            p.matches("^[a-zA-Z]:\\\\.*") ||
                p.matches("^[a-zA-Z]:/.*") ||
                p.startsWith("/");
        return isAbsolute ? p : System.getProperty("user.dir") + "/" + p;
    }

    private static UiAutomator2Options androidOptions(){
        UiAutomator2Options o = new UiAutomator2Options();
        o.setPlatformName("Android");
        o.setDeviceName(ConfigReader.envOrProp("android.deviceName"));
        o.setPlatformVersion(ConfigReader.envOrProp("android.platformVersion"));
        o.setAutomationName(ConfigReader.envOrProp(android.automationName))

        o.setAvd("Medium_Phone_API_36.0");
        o.setAvdLaunchTimeout(java.time.Duration.ofMinutes(4));
        o.setAvdReadyTimeout(java.time.Duration.ofMinutes(2));
        o.setAdbExecTimeout(java.time.Duration.ofSeconds(120));

        String udid = ConfigReader.envOrProp("android.udid");
        if(udid != null && !udid.isBlank()) o.setUdid(udid);

        String app = ConfigReader.envOrProp("appUnderTest");
        if (app != null && !app.isBlank()) {
            o.setApp(resolvePath(app));
        }

        o.setNewCommandTimeout(java.time.Duration.ofSeconds(120));
        return o;
    }

    private static XCUITestOptions iosOptions(){
        XCUITestOptions o = new XCUITestOptions();
        o.setPlatformName("iOS");
        o.setDeviceName(ConfigReader.envOrProp("ios.deviceName"));
        o.setPlatformVersion(ConfigReader.envOrProp("ios.platformVersion"));
        o.setAutomationName(ConfigReader.envOrProp("ios.automationName"));

        String udid = ConfigReader.envOrProp("ios.udid");
        if(udid != null && !udid.isBlank()) o.setUdid(udid);

        String app = ConfigReader.envOrProp("appUnderTest");
        if(app != null && !app.isBlank()){
            o.setApp(resolvePath(app));
        } else {
            o.setBundleId(ConfigReader.envOrProp("ios.bundleId"));
        }
        o.setNewCommandTimeout(java.time.Duration.ofSeconds(120));
        return o;
    }
}



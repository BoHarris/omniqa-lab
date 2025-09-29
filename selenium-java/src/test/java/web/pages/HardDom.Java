package pages;

import base.BasePage;
import io.qameta.allure.Step;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.ConfigReader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Properties;

public class HardDom extends BasePage{

    private static final String PATH = "challenging_dom";

    private final By header = By.cssSelector(".example h3");
    private final By firstBlueButton = By.cssSelector(".large-2.columns a.button:not(.alert):not(.success)");
    private final By actionHeader = By.cssSelector(".large-10.columns th:nth-of-type(7)");
    private final By sixthEditLink  = By.cssSelector("tbody tr:nth-of-type(6) [href='#edit']");
    private final By canvasLocator = By.cssSelector(".row canvas");

    public HardDom(WebDriver driver)  { super(driver);}

    @Step("Open Challenging DOM Page")
    public HardDom open(){driver.navigate().to(ConfigReader.baseUrl() +"/" + PATH); return this;}

    @Step("Assert header text equals: {expected}")
    public HardDom readHeaderText(String expected) {
        String actual = driver.findElement(header).getText().trim();
        if (!actual.equals(expected)) {
            throw new AssertionError("Header mismatch. Expected: " + expected + "but was: " + actual);
        }
        return this;
    }

    // Read canvas text
    public String readCanvasText() throws IOException, TesseractException, NullPointerException {
        WebElement canvas = driver.findElement(canvasLocator);

        // 1) Get PNG bytes from canvas
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String dataUrl = (String) js.executeScript(
                "return arguments[0].toDataURL('image/png');", canvas);

        if(dataUrl == null || !dataUrl.startsWith("data:image/png")){
            throw new IllegalStateException("Canvas toDataURL failed" + dataUrl);
        }

        String base64 = dataUrl.split(",")[1];
        byte[] pngBytes = Base64.getDecoder().decode(base64);
        BufferedImage img = ImageIO.read(new ByteArrayInputStream(pngBytes));

        // Grayscale handling
        BufferedImage gray = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        op.filter(img, gray);

        // OCR
        Tesseract t = new Tesseract();
        t.setLanguage("eng");
        t.setVariable("tessedit_char_whitelist", "0123456789Answer: ");
        t.setVariable("user_defined_dpi", "300");
        t.setVariable("tessedit_pageseg_mode", "6");

        String text = t.doOCR(gray).trim();

        // Simple check
        if(!text.contains("Answer")){
            throw new AssertionError("Canvas text not recognised: " + text);

    }
    return text;

}
}
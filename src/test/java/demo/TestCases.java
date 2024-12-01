package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.v123.domstorage.model.Item;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

//import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases {
    ChromeDriver driver;
    Wrappers wrapper;

    /*
     * Do not change the provided methods unless necessary, they will help in
     * automation and assessment
     */
    @BeforeTest
    public void startBrowser() {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

        driver = new ChromeDriver(options);
        wrapper = new Wrappers(driver);
        driver.manage().window().maximize();
    }

    /*
     * TODO: Write your tests here with testng @Test annotation.
     * Follow `testCase01` `testCase02`... format or what is provided in
     * instructions
     */
    @Test
    public void testCase01() throws InterruptedException {
        System.out.println("Start of Test case 01");
        driver.get("http://www.flipkart.com/");
        // wrapper.closeLoginPopUP();
        // Thread.sleep(3000);
        wrapper.search("Washing Machine");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(300));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Popularity']")));
        WebElement popularityText = driver.findElement(By.xpath("//div[text()='Popularity']"));
        popularityText.click();
        Wrappers.countofItems();
        System.out.println("End of Test case 01");
    }

    @Test
    public void testCase02() throws InterruptedException {
        System.out.println("Start of Test case 02");
        driver.get("http://www.flipkart.com/");
        Thread.sleep(3000);
        Wrappers.search("iPhone");

        Wrappers.discountTitles();
        System.out.println("End of Test case 02");

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testCase03() throws InterruptedException {
        System.out.println("Start of Test case 03");
        driver.get("http://www.flipkart.com/");
        Wrappers.closeLoginPopUP();

        Wrappers.search("Coffee Mug");

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        // click on 4 * and above//div[text()='4★ & above']/preceding-sibling::div
        WebElement fourstarCheckbox = driver.findElement(By.xpath("(//div[@class='XqNaEv'])[1]"));
        fourstarCheckbox.click();

        Wrappers.top5CoffeeMug();
        System.out.println("End of Test case 03");

    }

    @AfterTest
    public void endTest() {
        driver.close();
        driver.quit();

    }
}
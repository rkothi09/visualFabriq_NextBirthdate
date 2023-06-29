package NextBirthDayDatenew.NextBirthDayDatenew;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.reporters.Files;

public class NextBirthdayAPITest {
    private WebDriver driver;


    @BeforeClass
    public void setUp() {
        // Set the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "C:\\\\Users\\Lenovo\\Downloads\\chromedriver_win32\\chromedriver.exe");

        // Create an instance of ChromeDriver
        driver = new ChromeDriver();
    }

    @AfterClass
    public void tearDown() {
        // Close the browser
        driver.quit();
    }
    
    @AfterMethod
    public void afterMethod(ITestResult result) {
        // Take a screenshot if the test method failed
        if (result.getStatus() == ITestResult.FAILURE) {
            takeScreenshot(result.getName());
        }
    }

    private void takeScreenshot(String methodName) {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshot, new File(methodName + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   
    @Test(priority=0)
    public void testValidInputHour() {
        String dateOfBirth = "1990-10-30";
        String unit = "hour";
        testValidInput(dateOfBirth, unit);
    }

    @Test(priority=1)
    public void testValidInputDay() {
        String dateOfBirth = "1990-10-30";
        String unit = "day";
        String expectedMessage = "days left";
        testValidInput(dateOfBirth, unit);
    }

    @Test(priority=2)
    public void testValidInputWeek() {
        String dateOfBirth = "1990-10-30";
        String unit = "week";
        String expectedMessage = "weeks left";
        testValidInput(dateOfBirth, unit);
    }

    @Test(priority=3)
    public void testValidInputMonth() {
        String dateOfBirth = "1990-10-30";
        String unit = "month";
        String expectedMessage = "months left";
        testValidInput(dateOfBirth, unit);
    }

    private void testValidInput(String dateOfBirth, String unit) 
    {
        driver.get("https://lx8ssktxx9.execute-api.eu-west-1.amazonaws.com/Prod/next-birthday?dateofbirth="
                + dateOfBirth + "&unit=" + unit);
        WebElement messageElement = driver.findElement(By.tagName("body"));
        String message = messageElement.getText();
        // Add assertion to verify the response message
        String timeRemaining = NextBirthdayCalculator.getTimeUntilNextBirthday(dateOfBirth, unit);
        Assert.assertTrue(message.contains(timeRemaining));
    }

    @Test(priority=4)
    public void testMissingQueryParameters() {
        driver.get("https://lx8ssktxx9.execute-api.eu-west-1.amazonaws.com/Prod/next-birthday");
        WebElement messageElement = driver.findElement(By.tagName("body"));
        String message = messageElement.getText();
        // Add assertion to verify the response message
        Assert.assertTrue(message.contains("Missing query parameters"));
    }

    @Test(priority=5)
    public void testInvalidDateFormat() {
        String dateOfBirth = "1990/10/30";
        String unit = "hour";
        driver.get("https://lx8ssktxx9.execute-api.eu-west-1.amazonaws.com/Prod/next-birthday?dateofbirth="
                + dateOfBirth + "&unit=" + unit);
        WebElement messageElement = driver.findElement(By.tagName("body"));
        String message = messageElement.getText();
        // Add assertion to verify the response message
        Assert.assertTrue(message.contains("Please specify dateofbirth in valid format YYYY-MM-DD"));
    }

    @Test(priority=6)
    public void testInvalidUnitValue() {
        String dateOfBirth = "1990-10-30";
        String unit = "year";
        driver.get("https://lx8ssktxx9.execute-api.eu-west-1.amazonaws.com/Prod/next-birthday?dateofbirth="
                + dateOfBirth + "&unit=" + unit);
        WebElement messageElement = driver.findElement(By.tagName("body"));
        String message = messageElement.getText();
        // Add assertion to verify the response message
        Assert.assertTrue(message.contains("Invalid unit value"));
    }

    @Test(priority=7)
    public void testFutureDateOfBirth() {
        String dateOfBirth = "2030-01-01";
        String unit = "hour";
        driver.get("https://lx8ssktxx9.execute-api.eu-west-1.amazonaws.com/Prod/next-birthday?dateofbirth="
                + dateOfBirth + "&unit=" + unit);
        WebElement messageElement = driver.findElement(By.tagName("body"));
        String message = messageElement.getText();
        // Add assertion to verify the response message
        Assert.assertTrue(message.contains("Future Birthdate"));
    }

    @Test(priority=8)
    public void testTodayDateOfBirth() {
        String dateOfBirth = LocalDate.now().toString();
        String unit = "hour";
        driver.get("https://lx8ssktxx9.execute-api.eu-west-1.amazonaws.com/Prod/next-birthday?dateofbirth="
                + dateOfBirth + "&unit=" + unit);
        WebElement messageElement = driver.findElement(By.tagName("body"));
        String message = messageElement.getText();
        // Add assertion to verify the response message
        String timeRemaining = NextBirthdayCalculator.getTimeUntilNextBirthday(dateOfBirth, unit);
        Assert.assertTrue(message.contains(timeRemaining));
    }

    @Test(priority=9)
    public void testLeapYear() {
        String dateOfBirth = "2000-02-29";
        String unit = "hour";
        driver.get("https://lx8ssktxx9.execute-api.eu-west-1.amazonaws.com/Prod/next-birthday?dateofbirth="
                + dateOfBirth + "&unit=" + unit);
        WebElement messageElement = driver.findElement(By.tagName("body"));
        String message = messageElement.getText();
        // Add assertion to verify the response message
        Assert.assertTrue(message.contains("It is leap year"));
    }
}

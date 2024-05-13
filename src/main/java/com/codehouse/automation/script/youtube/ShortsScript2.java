package com.codehouse.automation.script.youtube;

import com.codehouse.constant.Constants;
import com.codehouse.util.Utils;
import com.codehouse.util.VideoInformation;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ShortsScript2 {
    public static void main(String[] args) throws InterruptedException, IOException {
        //Start chrome
        startChromeBrowser();

        // Save video file names from {@Link Constants.VIDEO_NAME_FOLDER_PATH} folder to {@Link Constants.VIDEO_NAME_FILE_PATH} file
        VideoInformation.saveVideoNameToFile(
                Constants.VIDEO_NAME_FOLDER_PATH, Constants.VIDEO_NAME_FILE_PATH, 10);

        // Run the actual selenium script
        runScript(Constants.VIDEO_NAME_FILE_PATH, Constants.CHROME_DRIVE_PATH, Constants.YOUTUBE_STUDIO_URL);
    }

    private static void startChromeBrowser() throws IOException, InterruptedException {
        String command = "\"C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe\" --remote-debugging-port=9267 --user-data-dir=\"C:\\Users\\Prakash\\Documents\\Personal-Work\"";
        Runtime.getRuntime().exec(command);
        Thread.sleep(2000);
    }

    private static void runScript(String videoFileNamePath, String chromeDriverPath, String pageUrl) throws IOException, InterruptedException {
        //Initializing Chrome driver
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-debugging-port=9267");
        options.addArguments("--user-data-dir=\"C:\\Users\\Prakash\\Documents\\Personal-Work\"");
        options.setExperimentalOption("debuggerAddress", "localhost:9267");
        ChromeDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        // Navigate to the page URL
        driver.get(pageUrl);

        // Reading video name from files as list
        List<String> videoNameList = Utils.readNameFromDisk(videoFileNamePath);

        for (String videoPath : videoNameList) {
            System.out.println(videoPath);
            try {
                //Click on upload button
                driver.findElement(By.id("upload-icon")).click();

                //Click on select file button
                driver.findElement(By.id("select-files-button")).click();
                Thread.sleep(2000); // Consider using explicit waits instead

                // Select the video using auto id
                ProcessBuilder builder = new ProcessBuilder(Constants.FILE_UPLOAD_AUTOIT_SCRIPT_PATH, videoPath);
                builder.start().waitFor();
                Thread.sleep(6000);

                // Set title of the video
                driver.findElement(By.id("textbox")).clear();
                driver.findElement(By.id("textbox")).sendKeys(
                        "Secret Bhabhi | Cute Bhabhi | Hot Bhabhi #shorts #trending #couple");

                // Set description of the video
                driver.findElement(By.id("textbox")).clear();
                driver.findElement(By.id("textbox")).sendKeys("""
                        Secret Bhabhi | Cute Bhabhi | Hot Bhabhi 
                        #shorts #trending #couple
                        """);

                // click on show more for adding tags on the video
                driver.findElement(By.xpath("//div[text()='Show more']")).click();
                Thread.sleep(500);
                // Add tags
                driver.findElement(By.id("text-input")).sendKeys("secret bhabhi, cute bhabhi, hot bhabhi, shorts, trending,");
                Thread.sleep(500);
                // Hindi language select
                driver.findElement(By.id("language-input")).click();
                Thread.sleep(500);
                driver.findElement(By.id("text-item-89")).click();

                // select location
                driver.findElement(By.id("location")).click();
                Thread.sleep(500); // Consider using explicit waits instead
                driver.findElement(By.cssSelector("input[aria-label='Video location']")).sendKeys("jaipur");

                // Click on next button
                driver.findElement(By.id("next-button")).click();
                driver.findElement(By.id("next-button")).click();
                driver.findElement(By.id("next-button")).click();
                Thread.sleep(500);

                // select date
                driver.findElement(By.id("second-container-expand-button")).click();
                Thread.sleep(500);
                driver.findElement(By.className("today")).click();

                // Select time
                WebElement timeWebElement = driver.findElement(By.id("time-of-day-container")).findElement(By.tagName("input"));
                timeWebElement.clear();
                timeWebElement.sendKeys("2:15 PM");

                //Random click
                driver.findElement(By.id("publish-from-private-non-sponsor")).click();

                //Schedule Button
                driver.findElement(By.id("done-button")).click();
            } catch (Exception e) {
                System.out.println(">>>>>" + videoPath + " -- ");
                e.printStackTrace();
            }
        }

        // Close the browser session
        driver.quit();
    }
}

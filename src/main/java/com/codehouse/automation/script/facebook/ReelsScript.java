package com.codehouse.automation.script.facebook;

import com.codehouse.constant.Constants;
import com.codehouse.util.Utils;
import com.codehouse.util.VideoInformation;
import org.checkerframework.checker.guieffect.qual.UI;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class ReelsScript {

    public static void main(String[] args) throws InterruptedException, IOException {
        // Save image file names from {@Link Constants.IMAGE_NAME_FOLDER_PATH} folder to {@Link Constants.IMAGE_NAME_FILE_PATH} file
        Utils.saveFileNamesToTextFile(
                Constants.REELS_NAME_FOLDER_PATH,
                Constants.REELS_NAME_FILE_PATH, "VIDEO", 20);

        // Run the actual selenium script
        runScript(Constants.SEEMA_BHABHI_URL);
    }

    private static void runScript(String pageUrl) throws IOException, InterruptedException {
        //Starting chrome on specific port
        String command = "\"C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe\" --remote-debugging-port=9267 --user-data-dir=\"C:\\Users\\Prakash\\Documents\\Personal-Work\"";
        Runtime.getRuntime().exec(command);
        Thread.sleep(2000);

        //Initializing Chrome driver
        System.setProperty("webdriver.chrome.driver", Constants.CHROME_DRIVE_PATH);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.setExperimentalOption("debuggerAddress", "localhost:9267");
        ChromeDriver driver = new ChromeDriver(options);
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        initChromeWithTabs(driver, pageUrl);

        // Reading image name from files as list
        List<String> videoNameList = Utils.readNameFromDisk(Constants.REELS_NAME_FILE_PATH);

        int currentTab = 0;
        for (int i = 0; i < videoNameList.size(); i++) {
            String s = videoNameList.get(i);
            if (currentTab == 3) currentTab = 0;
            System.out.println(s);
            try {
                Thread.sleep(1000);
                driver.switchTo().window(driver.getWindowHandles().toArray()[currentTab].toString());

                try {
                    // cross click button
                    driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[2]/div[1]/div[1]/span/div/i")).click();
                    Thread.sleep(7000);
                } catch (Exception e) {
                }

                // reel click button
                driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[3]/div/div/div[1]/div[1]/div[2]/div/div/div/div/div[4]/div[2]/div/div[2]/div[1]/div/div/div/div/div[2]/div[3]/div[1]")).click();
                Thread.sleep(4000);

                // reel select button
                driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[5]/div/div/div[3]/form/div/div/div[1]/div/div[3]/div[1]/div[2]/div/div/div[1]/div/div/div")).click();
                Thread.sleep(3000);

                //select reel video
                ProcessBuilder builder = new ProcessBuilder(Constants.FILE_UPLOAD_AUTOIT_SCRIPT_PATH, s);
                builder.start();
                Thread.sleep(6000);

                // Click next button
                try {
                    driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[5]/div/div/div[3]/form/div/div/div[1]/div/div[4]/div[2]/div/div/div")).click();
                    Thread.sleep(4000);
                } catch (Exception e) {
                }

                // Click next button
                driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[5]/div/div/div[3]/form/div/div/div[1]/div/div[4]/div[2]/div[2]/div[1]/div")).click();
                Thread.sleep(3000);

                // set description

                try {
                    driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[5]/div/div/div[3]/form/div/div/div[1]/div/div[3]/div[1]/div[2]/div/div/div/div/div[1]/div[1]/div[1]/div[1]")).sendKeys("""
                            .
                            .
                            .
                            #breastfeed #tandembreastfeeding #babysleep #reels
                            #reelsviral #reelsindia #love
                            #hindisong #foryou #love
                            #viral #fyp #reelsvideo
                            #freelsvideo #viralreels #viralreelsfb
                            #viral #viralvideo #fyp
                            #trend #trendingreels #song
                            #viralsongs #reelsvideo #instareel
                            #hotshot #reelsfb #bhabhilover
                            #dancevideo #sareelove #bhabhilover
                                                        
                            """);
                    Thread.sleep(3000);
                } catch (Exception e) {

                }
                Thread.sleep(8000);
                driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[5]/div/div/div[3]/form/div/div/div[1]/div/div[4]/div[2]/div[2]/div[1]/div/div[1]/div/span/span")).click();
                Thread.sleep(2000);

                currentTab++;
            } catch (Exception e) {
                System.err.println(">>>>> Error " + s + " >> " + e.getMessage());
                i--;
                Actions action = new Actions(driver);
                action.sendKeys(Keys.ESCAPE).perform();
                Thread.sleep(2000);
                driver.navigate().refresh();
                Thread.sleep(10000);
            }
        }
    }


    /**
     * Initialize the tabs
     * @param driver
     * @param url
     */
    private static void initChromeWithTabs(ChromeDriver driver, String url) {
        driver.navigate().to(url);
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(url);
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(url);
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(url);
    }
}
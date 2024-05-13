package com.codehouse.automation.script.facebook;

import com.codehouse.constant.Constants;
import com.codehouse.util.ImageInformation;
import com.codehouse.util.Utils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class PageScript {
    public static void main(String[] args) throws InterruptedException, IOException {
        // Save image file names from {@Link Constants.IMAGE_NAME_FOLDER_PATH} folder to {@Link Constants.IMAGE_NAME_FILE_PATH} file
        ImageInformation.saveImageNameToFile(
                Constants.IMAGE_FOLDER_PATH_SEEMA_BHABHI,
                Constants.IMAGE_NAME_FILE_PATH, 30);

        // Run the actual selenium script
        runScript(Constants.IMAGE_NAME_FILE_PATH,
                Constants.CHROME_DRIVE_PATH,
                Constants.SEEMA_BHABHI_URL);
    }

    private static void runScript(String imageFileNamePath, String chromeDriverPath, String pageUrl) throws IOException, InterruptedException {
        //Starting chrome on specific port
        String command = "\"C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe\" --remote-debugging-port=9267 --user-data-dir=\"C:\\Users\\Prakash\\Documents\\Personal-Work\"";
        Runtime.getRuntime().exec(command);
        Thread.sleep(2000);

        //Initializing Chrome driver
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.setExperimentalOption("debuggerAddress", "localhost:9267");
        ChromeDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        initChromeWithTabs(driver, pageUrl);

        // Reading image name from files as list
        List<String> imageNameList = Utils.readNameFromDisk(imageFileNamePath);

        int currentTab = 0;
        for (int i = 0; i < imageNameList.size(); i++) {
            String s = imageNameList.get(i);
            if (currentTab == 3) currentTab = 0;
            System.out.println(s);
            try {
                Thread.sleep(1000);
                driver.switchTo().window(driver.getWindowHandles().toArray()[currentTab].toString());

                Thread.sleep(1000);
                // photo select btn
                driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[3]/div/div/div[1]/div[1]/div[2]/div/div/div/div/div[4]/div[2]/div/div[2]/div[1]/div/div/div/div/div[2]/div[2]")).click();
                Thread.sleep(2000);

                // description
                driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[4]/div/div/div[1]/div/div[2]/div/div/div/form/div/div[1]/div/div/div/div[2]/div[1]/div[1]/div[1]/div/div/div[1]")).click();
                Thread.sleep(1000);
                driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[4]/div/div/div[1]/div/div[2]/div/div/div/form/div/div[1]/div/div/div/div[2]/div[1]/div[1]/div[1]/div/div/div[1]")).sendKeys("""
                        Full Hot Video: https://bit.ly/49pwVp8
                        .
                        .
                        #sareelove #cutegirls #sareelove #bhabhilover #reelsvideo #bhabhiloves
                        #BollywoodQueen1 #Bollywood Queen #BollywoodActress #bollywood actress #bollywoodqueen
                        #beautiful
                        #BestPhotographyChallenge
                        #queenchallenge
                        #bestfriends
                        #beautiful
                        #Beauty
                        #black. Ke
                        #beautychallenge1k""");

                driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[4]/div/div/div[1]/div/div[2]/div/div/div/form/div/div[1]/div/div/div/div[2]/div[1]/div[2]/div/div[1]/div/div/div/div[1]/div/div/div")).click();
                Thread.sleep(3000);

                ProcessBuilder builder = new ProcessBuilder("C:\\Users\\Prakash\\Documents\\Automation\\FB-Automation\\fileupload.exe", s);
                builder.start();
                Thread.sleep(6000);

                driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[4]/div/div/div[1]/div/div[2]/div/div/div/form/div/div[1]/div/div/div/div[3]/div[4]/div/div")).click();
                Thread.sleep(5000);

                try {
                    driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[4]/div/div[2]/div[1]/div/div[2]/div/div/div/div[3]/div[2]/div/div[1]/div[2]/div")).click();
                    Thread.sleep(2000);

                } catch (Exception e) {
                    System.err.println(">>>" + e.getMessage());
                }

                currentTab++;
            } catch (Exception e) {
                System.err.println(">>>>> Error " + s + " >> " + e.getMessage());
                i--;
                driver.navigate().refresh();
                try {
                    Alert alert = driver.switchTo().alert();
                    alert.accept();
                } catch (Exception ignored) {
                }
                Thread.sleep(10000);
            }
        }
    }

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

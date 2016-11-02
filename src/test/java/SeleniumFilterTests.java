import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.*;
import java.util.concurrent.TimeUnit;


public class SeleniumFilterTests {

   @Test
   public void SeleniumFilterImagesTest () throws InterruptedException {

       System.setProperty("webdriver.gecko.driver", "/home/me/Downloads/geckodriver");
       WebDriver driver = new FirefoxDriver();
       driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
       driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);

       driver.get("http://ellenwhite.org/library?f%5B0%5D=bundle%3Afiles");

       WebElement element = driver.findElement(By.id("edit-checkboxes-4-image"));
       element.click();

       driver.findElement(By.id("edit-filter-button--9")).click();

       Thread.sleep(10000);
       assertEquals("http://ellenwhite.org/library?f[0]=bundle%3Afiles&f[1]=sm_field_files_primary_media%3Aimage", driver.getCurrentUrl());

       driver.close();
       driver.quit();
   }

   @Test
    public void SeleniumFilterDocsTest () throws InterruptedException {

       System.setProperty("webdriver.chrome.driver", "/home/me/Downloads/chromedriver");
       WebDriver driver = new ChromeDriver();
       driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
       driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);

       driver.get("http://ellenwhite.org/library?f%5B0%5D=bundle%3Afiles");
       WebElement element = driver.findElement(By.id("edit-checkboxes-4-document"));
       element.click();

       driver.findElement(By.id("edit-filter-button--9")).click();

       Thread.sleep(10000);
       assertEquals("http://ellenwhite.org/library?f[0]=bundle%3Afiles&f[1]=sm_field_files_primary_media%3Adocument", driver.getCurrentUrl());


       driver.close();
       driver.quit();

   }


}

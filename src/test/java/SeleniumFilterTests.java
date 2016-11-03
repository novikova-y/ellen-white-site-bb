import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


public class SeleniumFilterTests {

    final String libraryURL = "http://ellenwhite.org/library?f%5B0%5D=bundle%3Afiles";
    private static WebDriver driver;

    @BeforeClass
    public static void initDriver() {

        System.setProperty("webdriver.chrome.driver", "/home/me/Downloads/chromedriver");
        //System.setProperty("webdriver.gecko.driver", "/home/me/Downloads/geckodriver");
        //driver = new FirefoxDriver();
        driver = new ChromeDriver();


        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);

    }


    @AfterClass
    public static void closeDriver() {
        driver.close();
        driver.quit();
    }


    @Test
    public void FilterDocumentsTest() throws InterruptedException {

        driver.get(libraryURL);

        WebElement element = driver.findElement(By.id("edit-checkboxes-4-document"));
        element.click();

        driver.findElement(By.id("edit-filter-button--9")).click();

        //load 100 elements  on page
        driver.findElement(By.id("edit-radios-2")).click();
        driver.findElement(By.id("edit-submit")).click();


        List<WebElement> libraryList = driver.findElements(By.className("collection-search-results")).get(0).findElements(By.tagName("li"));

        for (int i = 0; i < libraryList.size(); i++){
            //check if has
            Boolean isListLink = Objects.equals(libraryList.get(i).getCssValue("clear"),"none");

            if (isListLink){
                WebElement currentElement = libraryList.get(i).findElements(By.className("collection-page-search-result-snippet")).get(0).findElements(By.tagName("a")).get(0);

                System.out.println(currentElement.getAttribute("href"));
            }
        }

        //Thread.sleep(10000);
        //assertEquals("http://ellenwhite.org/library?f[0]=bundle%3Afiles&f[1]=sm_field_files_primary_media%3Adocument", driver.getCurrentUrl());
        //driver.findElement(By.linkText("1. Original Documentation About John White by the Justice of Pease (DF 701)")).click();

        //WebElement docVwr = driver.findElement(By.id("toolbar_documentViewer0"));
        //Assert.assertEquals(true, docVwr.isDisplayed());

    }

    @Test
    public void FilterImagesTest() throws InterruptedException {

        driver.get(libraryURL);

        WebElement element = driver.findElement(By.id("edit-checkboxes-4-image"));
        element.click();

        driver.findElement(By.id("edit-filter-button--9")).click();

        //load 100 elements  on page
        driver.findElement(By.id("edit-radios-2")).click();
        driver.findElement(By.id("edit-submit")).click();

        List<WebElement> libraryList = driver.findElements(By.className("collection-search-results")).get(0).findElements(By.tagName("li"));

        for (int i = 0; i < libraryList.size(); i++){
            //check if has
            Boolean isListLink = Objects.equals(libraryList.get(i).getCssValue("clear"),"none");

            if (isListLink){
                WebElement currentElement = libraryList.get(i).findElements(By.className("collection-page-search-result-snippet")).get(0).findElements(By.tagName("a")).get(0);

                System.out.println(currentElement.getAttribute("href"));
            }
        }


        //Thread.sleep(10000);
        //assertEquals("http://ellenwhite.org/library?f[0]=bundle%3Afiles&f[1]=sm_field_files_primary_media%3Aimage", driver.getCurrentUrl());
        //driver.findElement(By.linkText("1. David Laceys Mother- b. c. 1786")).click();

        //WebElement imgVwr = driver.findElement(By.cssSelector("button[title=\"Zoom in\"]"));
        //Assert.assertEquals(true, imgVwr.isDisplayed());
    }

    @Test
    public void FilterAudiosTest() throws InterruptedException {

        driver.get(libraryURL);

        WebElement element = driver.findElement(By.id("edit-checkboxes-4-audio"));
        element.click();

        driver.findElement(By.id("edit-filter-button--9")).click();

        //load 50 elements on page
        driver.findElement(By.id("edit-radios-1")).click();
        driver.findElement(By.id("edit-submit")).click();

        List<WebElement> libraryList = driver.findElements(By.className("collection-search-results")).get(0).findElements(By.tagName("li"));

        for (int i = 0; i < libraryList.size(); i++){
            //check if has
            Boolean isListLink = Objects.equals(libraryList.get(i).getCssValue("clear"),"none");

            if (isListLink){
                WebElement currentElement = libraryList.get(i).findElements(By.className("collection-page-search-result-snippet")).get(0).findElements(By.tagName("a")).get(0);

                System.out.println(currentElement.getAttribute("href"));
            }
        }


        //Thread.sleep(10000);
        //assertEquals("http://ellenwhite.org/library?f[0]=bundle%3Afiles&f[1]=sm_field_files_primary_media%3Aaudio", driver.getCurrentUrl());
        //driver.findElement(By.linkText("1. Forecast of the World's Destiny")).click();


        //WebElement adPlr = driver.findElement(By.className("jp-interface"));
        //Assert.assertEquals(true, adPlr.isDisplayed());

    }

    @Test
    public void FilterVideosTest() throws InterruptedException {

        driver.get(libraryURL);

        WebElement element = driver.findElement(By.id("edit-checkboxes-4-video"));
        element.click();

        driver.findElement(By.id("edit-filter-button--9")).click();

        //load 100 elements  on page
        driver.findElement(By.id("edit-radios-2")).click();
        driver.findElement(By.id("edit-submit")).click();


        List<WebElement> libraryList = driver.findElements(By.className("collection-search-results")).get(0).findElements(By.tagName("li"));

        for (int i = 0; i < libraryList.size(); i++){
            //check if has
            Boolean isListLink = Objects.equals(libraryList.get(i).getCssValue("clear"),"none");

            if (isListLink){
                WebElement currentElement = libraryList.get(i).findElements(By.className("collection-page-search-result-snippet")).get(0).findElements(By.tagName("a")).get(0);

                System.out.println(currentElement.getAttribute("href"));
            }
        }

        //Thread.sleep(10000);
        //assertEquals("http://ellenwhite.org/library?f[0]=bundle%3Afiles&f[1]=sm_field_files_primary_media%3Avideo", driver.getCurrentUrl());

        //driver.findElement(By.linkText("1. H. M. S. Richards talks about Ellen White")).click();
        //WebElement vdVwr = driver.findElement(By.id("youtube-field-player"));
        //Assert.assertEquals(true, vdVwr.isDisplayed());
    }

}
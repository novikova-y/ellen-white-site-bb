import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.*;

import java.util.*;
import java.util.concurrent.TimeUnit;


public class SeleniumFilterTests {

    final String libraryURL = "http://ellenwhite.org/library?f%5B0%5D=bundle%3Afiles";
    private static WebDriver driver;
    private static Map<WebElement,String> exceptionMap = new HashMap<WebElement, String>();

    private void openLinkInNewTab (WebElement element, String urlString){
        String selectLinkOpenInNewTab = Keys.chord(Keys.CONTROL, Keys.RETURN);
        element.sendKeys(selectLinkOpenInNewTab);
    }
    final String libraryDocsBy100 = "http://ellenwhite.org/library?f[0]=bundle%3Afiles&f[1]=sm_field_files_primary_media%3Adocument&rNum=100";

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
       driver.get(libraryDocsBy100);

        //find all elements of the list on page

        List<WebElement> libraryList = driver.findElements(By.className("collection-search-results")).get(0).findElements(By.tagName("li"));

        Map<WebElement, String> libraryMap = new HashMap<WebElement, String>();

        for (int i = 0; i < libraryList.size(); i++){
            //check if has
            Boolean isListLink = Objects.equals(libraryList.get(i).getCssValue("clear"),"none");

            if (isListLink){
                WebElement currentElement = libraryList.get(i).findElements(By.className("collection-page-search-result-snippet")).get(0).findElements(By.tagName("a")).get(0);
                String currentLink = currentElement.getAttribute("href");

                libraryMap.put(currentElement, currentLink);

//                inspectLibraryLink(currentElement, currentLink);
            }
        }

        for (Map.Entry<WebElement, String> entry : libraryMap.entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

//        Thread.sleep(5000);

        System.out.println(libraryMap.size());

        for (Map.Entry<WebElement, String> entry : libraryMap.entrySet()){
            System.out.println("! ENTRY !");

            inspectLibraryLink(entry.getKey(), entry.getValue());
        }

    }

    private void inspectLibraryLink(WebElement element, String urlString) throws InterruptedException{
        Thread.sleep(1000);

        System.out.println(urlString);

//        openLinkInNewTab(element, urlString);

        Thread.sleep(1000);

//        switchTabToRight();
        changeURL(urlString);

        Thread.sleep(5000);

        checkToolbarPresence (element, urlString);

        Thread.sleep(1000);

//        closeTab();
    }

    /*private void openLinkInNewTab (WebElement element, String urlString) throws InterruptedException{
        String selectLinkOpenInNewTab = Keys.chord(Keys.CONTROL, Keys.RETURN);
        element.sendKeys(selectLinkOpenInNewTab);
    }*/

    private void switchTabToRight() throws InterruptedException{
        String windowHandle = driver.getWindowHandle();
        ArrayList<String> tabs = new ArrayList (driver.getWindowHandles());

//        System.out.println(tabs);

        driver.switchTo().window(tabs.get(1));
    }

    private void changeURL(String url){
        driver.navigate().to(url);
    }

    private void checkToolbarPresence(WebElement element, String url) {
        if ( driver.findElements(By.id("toolbar_documentViewer0")).size() == 0){
            exceptionMap.put(element, url);
            //WebElement dynamicElement = (new WebDriverWait(driver, 5))
            //.until(ExpectedConditions.presenceOfElementLocated(By.id("toolbar_documentViewer0")));
        }
    }

    private void closeTab(){
        driver.findElements(By.tagName("Body")).get(0).sendKeys(Keys.CONTROL + "W");
    }

    @Test
    public void FilterImagesTest() throws InterruptedException {

        driver.get(libraryURL);

        WebElement element = driver.findElement(By.id("edit-checkboxes-4-image"));
        element.click();

        driver.findElement(By.id("edit-filter-button--9")).click();

        //load 100 elements on page
        driver.findElement(By.id("edit-radios-2")).click();
        driver.findElement(By.id("edit-submit")).click();

        //find all elements of the list on page
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

        //find all elements of the list on page
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

        //find all elements of the list on page
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
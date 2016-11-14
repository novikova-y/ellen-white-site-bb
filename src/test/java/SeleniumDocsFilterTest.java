import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class SeleniumDocsFilterTest {

    private static WebDriver driver;

    final String pageURL = "http://ellenwhite.org/issues-and-answers";

    final String libraryDocsBy100 = "http://ellenwhite.org/library?f[0]=bundle%3Afiles&f[1]=sm_field_files_primary_media%3Adocument&rNum=25";

    private static Map<WebElement, String> libraryMap = new HashMap<WebElement, String>();
    private static Map<WebElement,String> exceptionMap = new HashMap<WebElement, String>();
    private final String nextButtonClassName = "pager-next";

    PrintWriter writer = null;

    private void writeToFile(Map<WebElement, String> linksMap) throws Exception{
        try{
            PrintWriter writer = new PrintWriter("linksFromLibrary.txt", "UTF-8");

            writer.println("The first line");

            for (Map.Entry<WebElement, String> entry : libraryMap.entrySet()){
                writer.println(entry.getValue() + "\n");
            }

            writer.close();
        } catch (Exception e) {
            // do something
        }
    }

    @Test
    public void FilterDocumentsTest() throws InterruptedException, Exception{

        final String libraryURL = "http://ellenwhite.org/library?f[0]=bundle%3Afiles&f[1]=sm_field_files_primary_media%3Adocument&rNum=100";
        final String toolbarName = "toolbar_documentViewer0";

        driver.navigate().to(libraryURL);

        iteratePages();

        writeToFile(libraryMap); ///TEMP

        inspectLinks();

        checkExceptionMap();
    }

    private void iteratePages() throws InterruptedException{
        System.out.println("ITER!");
        System.out.println(libraryMap.size());

        inspectPage();

        Thread.sleep(1000);

        boolean hasNextButton = driver.findElements(By.className(nextButtonClassName)).size() >= 1;

        System.out.println(hasNextButton);

        Thread.sleep(1000);

        if (hasNextButton) {
            System.out.println("NEXT!");
            WebElement nextButton = driver.findElements(By.className(nextButtonClassName)).get(0);

            nextButton.click();

            Thread.sleep(15000); // links per page dependent

            iteratePages();
        }
    }



    private void inspectPage(){
        List<WebElement> libraryList = driver.findElements(By.className("collection-search-results")).get(0).findElements(By.tagName("li"));

        for (int i = 0; i < libraryList.size(); i++){
            Boolean isListLink = Objects.equals(libraryList.get(i).getCssValue("clear"),"none");

            if (isListLink){
                WebElement currentElement = libraryList.get(i).findElements(By.className("collection-page-search-result-snippet")).get(0).findElements(By.tagName("a")).get(0);
                String currentLink = currentElement.getAttribute("href");

                libraryMap.put(currentElement, currentLink);
            }
        }
    }

    private void inspectLinks() throws InterruptedException{
        System.out.println(libraryMap.size());

        for (Map.Entry<WebElement, String> entry : libraryMap.entrySet()){
            System.out.println("inspecting entry!");
            System.out.println(entry.getValue());

            inspectLibraryLink(entry.getKey(), entry.getValue());
        }
    }

    private void inspectLibraryLink(WebElement element, String urlString) throws InterruptedException{
        Thread.sleep(1000);

        changeURL(urlString);

        Thread.sleep(5000);

        checkToolbarPresence(element, urlString);

        Thread.sleep(1000);
    }

    private void checkExceptionMap(){
        if (exceptionMap.size() <= 0) {
            System.out.println("everything is fine!");
            return;
        }

        for (Map.Entry<WebElement, String> entry : exceptionMap.entrySet()){
            System.out.println("list of links with missing document viewer");
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        Assert.fail("some documents are missing, full list is provided before");
    }

    private void changeURL(String url){
        driver.navigate().to(url);
    }

    private void checkToolbarPresence(WebElement element, String url){
        if ( driver.findElements(By.id("toolbar_documentViewer0")).size() == 0){
            exceptionMap.put(element, url);
        }
    }

    private void closeTab(){
        driver.findElements(By.tagName("Body")).get(0).sendKeys(Keys.CONTROL + "W");
    }

    @BeforeClass
    public static void beforeClass(){

        //System.setProperty("webdriver.gecko.driver","/home/me/Downloads/geckodriver");
        //driver = new FirefoxDriver();
        System.setProperty("webdriver.chrome.driver", "/home/me/Downloads/chromedriver");
        driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
    }

    @AfterClass
    public static void afterClass(){
        driver.close();
        driver.quit();

    }
}
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;


import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;



public class SeleniumAudioFilterTest {

    private static WebDriver driver;
    final String pageURL = "http://ellenwhite.org/issues-and-answers";

    final String libraryDocsBy50 = "http://ellenwhite.org/library?f[0]=bundle%3Afiles&f[1]=sm_field_files_primary_media%3Aaudio&rNum=50";


    private static Map<WebElement, String> libraryMap = new HashMap<WebElement, String>();
    private static Map<WebElement,String> exceptionMap = new HashMap<WebElement, String>();
    private final String nextButtonClassName = "pager-next";


    PrintWriter writer = null;

    private void writeToFile(Map<WebElement, String> linksMap) throws Exception{ // Incapsulate in utils class
        try{
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            PrintWriter writer = new PrintWriter("linksFromAudioLibrary_" + timestamp.toString().substring(0, 16) + ".txt", "UTF-8");

            for (Map.Entry<WebElement, String> entry : linksMap.entrySet()){
                writer.println(entry.getValue() + "\n");
            }

            writer.close();
        } catch (Exception e) {
            // do something
        }
    }

    @Test
    public void FilterAudioTest() throws InterruptedException, Exception {
        final String libraryURL = "http://ellenwhite.org/library?f[0]=bundle%3Afiles&f[1]=sm_field_files_primary_media%3Aaudio&rNum=50";
        final String playerName = "jp-interface";

        driver.navigate().to(libraryURL);

        iteratePages();

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

    private void checkExceptionMap() throws  Exception{
        if (exceptionMap.size() <= 0) {
            System.out.println("everything is fine!");
            return;
        }

        for (Map.Entry<WebElement, String> entry : exceptionMap.entrySet()){
            System.out.println("list of links with missing audio player");
            System.out.println(entry.getValue());
        }

        writeToFile(exceptionMap);

        Assert.fail("some audios are missing, full list is provided before");
    }

    private void changeURL(String url){
        driver.navigate().to(url);
    }

    private void checkToolbarPresence(WebElement element, String url){
        if ( driver.findElements(By.className("jp-interface")).size() == 0){
            exceptionMap.put(element, url);
        }
    }

    @BeforeClass
    public static void beforeClass(){

        //System.setProperty("webdriver.gecko.driver","/home/warumweil/Downloads/geckodriver");
        //driver = new FirefoxDriver();
        System.setProperty("webdriver.chrome.driver", "/home/warumweil/Downloads/chromedriver");
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

package page;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TrivagoHomePage extends AbstractPage {
    private static final Logger LOGGER = LogManager.getRootLogger();

    private static final String HOMEPAGE_URL = "https://www.trivago.ca/";
    private static final String CARS_PAGE_URL = "https://www.trivago.ca/cars";
    private static final String FLIGHTS_PAGE_URL = "https://www.trivago.ca/flights";

    private By destinationInputLocator = By.xpath("//input[@id='querytext']");

    private By destinationExceptionLocator = By.xpath("//div[@class='dealform__error-message']");

    private By roomFormLocator = By.xpath("//button[contains(@class,'button-guests')]");

    private By roomFormDivLocator = By.xpath("//div[contains(@class,'guest-selector')]");

    private By bigGroupHintLocator = By.xpath("//a[@rel='nofollow noopener noreferrer']");
    
    private By numberOfAdultsInputLocator = By.xpath("//input[@id='adults-input']");

    private By roomFormApplyButtonLocator = By.xpath("//button[text()='Apply']");

    private By numberOfAdultsSpanLocator = By.xpath("//span[contains(text(),'Guests')]");

    private By searchButtonLocator = By.xpath("//span[text()='Search']");

    public TrivagoHomePage(WebDriver driver) {
        super(driver);
    }

    public TrivagoHomePage openHomePage() {
        driver.get(HOMEPAGE_URL);
        LOGGER.log(Level.INFO, "Home page is opened");
        return this;
    }

    public TrivagoCarsPage openCarsPage() {
        driver.get(CARS_PAGE_URL);
        LOGGER.log(Level.INFO, "Cars page is opened");
        return new TrivagoCarsPage(driver);
    }

    public TrivagoFlightsPage openFlightsPage() {
        driver.get(FLIGHTS_PAGE_URL);
        LOGGER.log(Level.INFO, "Flights page is opened");
        return new TrivagoFlightsPage(driver);
    }

    public TrivagoHomePage enterDestination(String destination) {
        findElementByLocatorAndClick(destinationInputLocator).sendKeys(destination);
        LOGGER.log(Level.INFO, "Destination [{}]  is entered", destination);
        return this;
    }

    public String getDestinationExceptionText() {
        LOGGER.log(Level.INFO, "Destination exception text is received");
        return findElementByLocatorAndGetText(destinationExceptionLocator);
    }

    public TrivagoHomePage openRoomForm() {
        findElementByLocatorAndClick(roomFormLocator);
        LOGGER.log(Level.INFO,"Room form is opened");
        return this;
    }

    public String getBigGroupHintText() {
        LOGGER.log(Level.INFO, "Big group hint text is received");
        return findElementByLocatorAndGetText(bigGroupHintLocator);
    }
    
    public TrivagoHomePage fillAdultsField(int numberOfAdults) {
        findElementByLocatorAndClick(numberOfAdultsInputLocator)
                .sendKeys(Keys.BACK_SPACE + String.valueOf(numberOfAdults));
        findElementByLocatorAndClick(roomFormDivLocator);
        LOGGER.log(Level.INFO, "Adults field is filled");
        return this;
    }

    public TrivagoHomePage clickRoomFormApplyButton() {
        findElementByLocatorAndClick(roomFormApplyButtonLocator);
        LOGGER.log(Level.INFO, "Apply button in the room form is clicked");
        return this;
    }

    public String getNumberOfAdults() {
        LOGGER.log(Level.INFO, "Number of adults is received");
        return findElementByLocatorAndGetText(numberOfAdultsSpanLocator).replace("Guests","")
                                                                        .trim();
    }

    public TrivagoStaysResultsPage searchHotels() {
        findElementByLocatorAndClick(searchButtonLocator);
        LOGGER.log(Level.INFO, "Looking for the stays");
        return new TrivagoStaysResultsPage(driver);
    }

    private WebElement defaultFindElementByLocator(By locator) {
        return new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions
                        .presenceOfElementLocated(locator));
    }

    public WebElement findElementByLocator(By locator) {
        try {
            return defaultFindElementByLocator(locator);
        } catch (StaleElementReferenceException e) {
            return defaultFindElementByLocator(locator);
        }
    }
    
    public WebElement findElementByLocatorAndClick(By locator) {
        try {
            WebElement element = defaultFindElementByLocator(locator);
            element.click();
            return element;
        } catch (StaleElementReferenceException e) {
            WebElement element = defaultFindElementByLocator(locator);
            element.click();
            return element;
        }
    }
    
    public String findElementByLocatorAndGetText(By locator) {
        try {
            return findElementByLocator(locator).getText();
        } catch (StaleElementReferenceException e) {
            return findElementByLocator(locator).getText();
        }
    }
}

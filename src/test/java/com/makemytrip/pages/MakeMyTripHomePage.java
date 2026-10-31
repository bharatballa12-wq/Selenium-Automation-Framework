package com.makemytrip.pages;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MakeMyTripHomePage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By loginPopupClose = By.cssSelector("[data-cy='closeModal']");
    private final By hotelsIcon = By.cssSelector("[data-cy='menu_Hotels'] a");
    private final By oneWayOption = By.xpath("//input[@value='oneWay'] | //label[@for='oneWayTrip'] | //div[@role='radio'][@aria-label='One Way']");
    private final By sourceField = By.id("fromCity");
    private final By sourceInput = By.cssSelector("input[placeholder='From']");
    private final By hyderabadOption = By.xpath("//*[@role='option'][.//span[normalize-space()='HYD']]");
    private final By destinationField = By.id("toCity");
    private final By destinationInput = By.cssSelector("input[placeholder='To']");
    private final By bengaluruOption = By.xpath("//*[@role='option'][.//span[normalize-space()='BLR']]");
    private final String departureDateSelector = ".DayPicker-Day[aria-label='%s']";
    private final By searchButton = By.cssSelector("[data-cy='submit'] button");

    public MakeMyTripHomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void open() {
        driver.get("https://www.makemytrip.com/flights/?cmp=SEM|M|DF|B|Brand|B_M_Makemytrip_Search_Exact|Brand_Top_5_Exact|Expanded|&ef_id=:G:s&msclkid=187766514fcd1598bdc60cb4d8fbb9b5");
        wait.until(ExpectedConditions.titleContains("MakeMyTrip"));
        dismissLoginPopupIfPresent();
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public void selectOneWay() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(oneWayOption)).click();
            System.out.println("✓ One Way trip option selected");
        } catch (TimeoutException e1) {
            try {
                System.out.println("Trying JavaScript fallback for One Way selection...");
                ((JavascriptExecutor) driver).executeScript(
                    "var labels = document.querySelectorAll('label');" +
                    "for(var label of labels) {" +
                    "  if(label.textContent.includes('One Way')) {" +
                    "    label.click();" +
                    "    return true;" +
                    "  }" +
                    "}" +
                    "var inputs = document.querySelectorAll('input[value=\"oneWay\"]');" +
                    "if(inputs.length > 0) { inputs[0].click(); return true; }" +
                    "return false;"
                );
                System.out.println("✓ One Way trip option selected (via JavaScript)");
            } catch (Exception e2) {
                throw e1;
            }
        }
    }

    public void enterSource() {
        wait.until(ExpectedConditions.elementToBeClickable(sourceField)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(sourceInput))
            .sendKeys("Hyderabad");
        wait.until(ExpectedConditions.elementToBeClickable(hyderabadOption)).click();
    }

    public void enterDestination() {
        wait.until(ExpectedConditions.elementToBeClickable(destinationField)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(destinationInput))
            .sendKeys("Bengaluru");
        wait.until(ExpectedConditions.elementToBeClickable(bengaluruOption)).click();
    }

    public void selectDepartureDate(LocalDate departureDate) {
        String date = departureDate.format(
            DateTimeFormatter.ofPattern("EEE MMM dd yyyy", Locale.ENGLISH));
        wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector(String.format(departureDateSelector, date)))).click();
    }

    public void verifyAndClickSearch() {
        verifySearchDetails();
        clickSearch();
    }

    public void clickSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }

    public boolean verifySearchDetails() {
        try {
            WebElement fromCityElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(sourceField));
            String fromCity = fromCityElement.getDomProperty("value");
            System.out.println("✓ From City verified: " + fromCity);

            WebElement toCityElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(destinationField));
            String toCity = toCityElement.getDomProperty("value");
            System.out.println("✓ To City verified: " + toCity);

            if (fromCity.isEmpty() || toCity.isEmpty()) {
                System.out.println("✗ Error: From City or To City is empty!");
                return false;
            }

            System.out.println("✓ All flight search details are correct: From=" + fromCity + 
                ", To=" + toCity);
            return true;
        } catch (TimeoutException e) {
            System.out.println("✗ Error: Could not verify search details - " + e.getMessage());
            return false;
        }
    }

    public void clickHotels() {
        wait.until(ExpectedConditions.elementToBeClickable(hotelsIcon)).click();
        wait.until(ExpectedConditions.urlContains("/hotels/"));
    }

    public String scrollToAirlinePartnersHeading() {
        By airlinePartnersHeading = By.xpath("//h2[normalize-space()='Experience Flying with our Airline Partners']");
        WebElement heading = wait.until(ExpectedConditions.presenceOfElementLocated(airlinePartnersHeading));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", heading);
        return wait.until(ExpectedConditions.visibilityOf(heading)).getText();
    }

    private void dismissLoginPopupIfPresent() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(loginPopupClose)).click();
        } catch (TimeoutException exception) {
            return;
        }
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loginPopupClose));
    }
}
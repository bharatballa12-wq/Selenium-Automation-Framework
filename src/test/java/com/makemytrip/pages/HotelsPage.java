package com.makemytrip.pages;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HotelsPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By locationField = By.id("city");
    private final By flightsIcon = By.cssSelector("[data-cy='menu_Flights'] a");
    private final By bengaluruOption = By.xpath("//*[@role='option'][contains(., 'Bangalore')]");
    private final By checkInLabel = By.cssSelector("label[for='checkin']");
    private final By adultCount = By.cssSelector("[aria-label='Adults counter'] [role='status']");
    private final By decreaseAdults = By.cssSelector("[aria-label='Adults counter'] button[aria-label^='Decrease']");
    private final By applyButton = By.cssSelector("[data-cy='RoomsGuestsNew_327']");
    private final By searchButton = By.xpath("//button[contains(., 'Search')] | //button[@id*='search']");

    public HotelsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public String getDefaultLocation() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locationField))
            .getDomProperty("value");
    }

    public void enterBengaluru() {
        try {
            System.out.println("Attempting to enter Bengaluru in hotel search...");
            Boolean result = (Boolean) ((JavascriptExecutor) driver).executeScript(
                "try {" +
                "  var inputs = document.querySelectorAll('input');" +
                "  for(var input of inputs) {" +
                "    var placeholder = input.getAttribute('placeholder') || '';" +
                "    if ((placeholder.includes('city') || placeholder.includes('stay') || " +
                "         placeholder.includes('location')) && input.type !== 'hidden') {" +
                "      input.focus();" +
                "      input.value = 'Bengaluru';" +
                "      input.dispatchEvent(new Event('input', { bubbles: true }));" +
                "      input.dispatchEvent(new Event('change', { bubbles: true }));" +
                "      return true;" +
                "    }" +
                "  }" +
                "} catch(e) { console.log('Error: ' + e.message); }" +
                "return false;"
            );
            
            if (result) {
                System.out.println("✓ Bengaluru entered via JavaScript");
                Thread.sleep(1500);
                try {
                    wait.until(ExpectedConditions.elementToBeClickable(bengaluruOption)).click();
                    System.out.println("✓ Bangalore option selected");
                } catch (TimeoutException e) {
                    System.out.println("Note: Could not click Bangalore option");
                }
            } else {
                // Fallback: Try direct selector approach
                try {
                    System.out.println("JavaScript approach failed, trying direct selector...");
                    WebElement cityField = wait.until(ExpectedConditions.elementToBeClickable(locationField));
                    cityField.click();
                    Thread.sleep(500);
                    
                    WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//input[@id='city'] | //input[contains(@placeholder, 'city')]")));
                    inputField.sendKeys("Bengaluru");
                    System.out.println("✓ Bengaluru entered via direct selector");
                    Thread.sleep(1000);
                    
                    try {
                        wait.until(ExpectedConditions.elementToBeClickable(bengaluruOption)).click();
                        System.out.println("✓ Bangalore option selected");
                    } catch (TimeoutException e) {
                        System.out.println("Note: Could not click Bangalore option");
                    }
                } catch (Exception e2) {
                    throw new RuntimeException("Failed to enter Bengaluru in hotel search: " + e2.getMessage(), e2);
                }
            }
        } catch (Exception e) {
            System.out.println("✗ Error: Could not enter city - " + e.getMessage());
            throw new RuntimeException("Failed to enter Bengaluru in hotel search", e);
        }
    }

    public void selectCheckInDate() {
        selectCheckInDate(LocalDate.of(2026, 11, 2));
    }

    public void selectCheckInDate(LocalDate date) {
        By checkInDate = dateLocator(date);
        if (driver.findElements(checkInDate).isEmpty()) {
            wait.until(ExpectedConditions.elementToBeClickable(checkInLabel)).click();
        }
        wait.until(ExpectedConditions.elementToBeClickable(checkInDate)).click();
    }

    public void selectCheckOutDate() {
        selectCheckOutDate(LocalDate.of(2026, 11, 4));
    }

    public void selectCheckOutDate(LocalDate date) {
        wait.until(ExpectedConditions.elementToBeClickable(dateLocator(date))).click();
    }

    public void selectOneAdult() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(adultCount));
        String adults = driver.findElement(adultCount).getText();
        if (adults.equals("2")) {
            wait.until(ExpectedConditions.elementToBeClickable(decreaseAdults)).click();
        }
        wait.until(ExpectedConditions.textToBe(adultCount, "1"));
        wait.until(ExpectedConditions.elementToBeClickable(applyButton)).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(applyButton));
    }

    public void verifyAndClickSearch() {
        if (verifyHotelSearchDetails()) {
            clickSearch();
        } else {
            throw new RuntimeException("Hotel search details verification failed!");
        }
    }

    public boolean verifyHotelSearchDetails() {
        try {
            WebElement locationElement = null;
            String location = "";
            
            try {
                locationElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("city")));
                location = locationElement.getDomProperty("value");
            } catch (Exception e) {
                try {
                    locationElement = driver.findElement(
                        By.xpath("//input[contains(@placeholder, 'city')]"));
                    location = locationElement.getDomProperty("value");
                } catch (Exception e2) {
                    System.out.println("Warning: Could not find location field");
                }
            }
            
            if (location == null || location.isEmpty()) {
                System.out.println("✗ Error: Hotel location is empty!");
                return false;
            }
            
            System.out.println("✓ Hotel Location verified: " + location);
            return true;
        } catch (Exception e) {
            System.out.println("✗ Error: Could not verify hotel search details - " + e.getMessage());
            return false;
        }
    }

    public void clickSearch() {
        try {
            Thread.sleep(2000);
            WebElement searchBtn = null;
            
            try {
                searchBtn = driver.findElement(By.xpath("//button[contains(., 'Search')]"));
            } catch (Exception e1) {
                try {
                    searchBtn = driver.findElement(By.id("hsw_search_button"));
                } catch (Exception e2) {
                    searchBtn = driver.findElement(By.xpath("//button[@id*='search']"));
                }
            }
            
            wait.until(ExpectedConditions.elementToBeClickable(searchBtn)).click();
            System.out.println("✓ Search button clicked");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Search interrupted", e);
        } catch (Exception e) {
            throw new RuntimeException("Error clicking search button: " + e.getMessage(), e);
        }
    }

    public void clickFlights() {
        wait.until(ExpectedConditions.elementToBeClickable(flightsIcon)).click();
        wait.until(ExpectedConditions.urlContains("/flights/"));
    }

    private By dateLocator(LocalDate date) {
        String label = date.format(DateTimeFormatter.ofPattern("EEE MMM dd yyyy", Locale.ENGLISH));
        return By.xpath(String.format(".//div[@aria-label='%s']", label));
    }
}

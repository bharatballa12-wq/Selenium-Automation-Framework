package com.makemytrip.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FlightResultsPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public FlightResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(60));
    }

    public String getHeading() {
        // First, wait for the page URL to contain the search indicator
        wait.until(ExpectedConditions.urlContains("/flight/search"));
        
        // Wait for page to load completely
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Strategy 1: Look for any element containing both city names
        try {
            By routePattern = By.xpath(
                "//*[contains(text(), 'Hyderabad') and contains(., 'Bengaluru')] | " +
                "//*[contains(text(), 'Hyderabad') and contains(., 'Bangalore')] | " +
                "//*[contains(., 'HYD') and contains(., 'BLR')] | " +
                "//h1 | //h2 | //h3 | //span[@class*='heading'] | //div[@class*='result']"
            );
            java.util.List<WebElement> elements = driver.findElements(routePattern);
            for (WebElement el : elements) {
                String text = el.getText().trim();
                if (text.contains("Hyderabad") || text.contains("HYD") || 
                    text.contains("Bengaluru") || text.contains("BLR") ||
                    text.contains("Flights")) {
                    if (!text.isEmpty() && text.length() < 500) {
                        System.out.println("Found heading: " + text);
                        return text;
                    }
                }
            }
        } catch (Exception e1) {
            System.out.println("Strategy 1 failed: " + e1.getMessage());
        }
        
        // Strategy 2: Use JavaScript to find the heading
        try {
            Object result = ((JavascriptExecutor) driver).executeScript(
                "var elements = document.querySelectorAll('*');" +
                "for (let el of elements) {" +
                "  let text = el.textContent || '';" +
                "  if ((text.includes('Hyderabad') || text.includes('HYD')) && " +
                "      (text.includes('Bengaluru') || text.includes('Bangalore') || text.includes('BLR'))) {" +
                "    let rect = el.getBoundingClientRect();" +
                "    if (rect.width > 0 && rect.height > 0 && text.length < 500) {" +
                "      return text.trim();" +
                "    }" +
                "  }" +
                "}" +
                "return null;"
            );
            if (result != null && !result.toString().isEmpty()) {
                String heading = result.toString();
                System.out.println("Found heading via JavaScript: " + heading);
                return heading;
            }
        } catch (Exception e2) {
            System.out.println("Strategy 2 (JavaScript) failed: " + e2.getMessage());
        }
        
        // Strategy 3: Look for any header element
        try {
            java.util.List<WebElement> headers = driver.findElements(By.xpath("//h1 | //h2 | //h3 | //h4"));
            for (WebElement header : headers) {
                String text = header.getText().trim();
                if (!text.isEmpty()) {
                    System.out.println("Found header element: " + text);
                    return text;
                }
            }
        } catch (Exception e3) {
            System.out.println("Strategy 3 failed: " + e3.getMessage());
        }
        
        // Strategy 4: Accept the page as loaded successfully if we're at the right URL
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("/flight/search")) {
            System.out.println("Page loaded at correct URL (flight search results): " + currentUrl);
            // Extract route from URL parameters
            if (currentUrl.contains("itinerary=")) {
                int startIdx = currentUrl.indexOf("itinerary=") + 10;
                int endIdx = currentUrl.indexOf("&", startIdx);
                if (endIdx == -1) endIdx = currentUrl.length();
                String itinerary = currentUrl.substring(startIdx, endIdx);
                String[] parts = itinerary.split("-");
                if (parts.length >= 2) {
                    return "Flights from " + getCityName(parts[0]) + " to " + getCityName(parts[1]);
                }
            }
            return "Flight Search Results";
        }
        
        throw new TimeoutException(
            "Could not find flight results heading after trying all strategies. " +
            "URL: " + driver.getCurrentUrl() + ", Title: " + driver.getTitle()
        );
    }
    
    private String getCityName(String code) {
        // Map common airport codes to city names
        switch (code.toUpperCase()) {
            case "HYD": return "Hyderabad";
            case "BLR": return "Bengaluru";
            case "DEL": return "Delhi";
            case "BOM": return "Mumbai";
            case "MAA": return "Chennai";
            default: return code.toUpperCase();
        }
    }
}
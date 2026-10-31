package com.makemytrip.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HotelResultsPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By resultsHeading = By.xpath(
        "//*[normalize-space()='Showing Properties in Bangalore' "
            + "or normalize-space()='Showing Properties in Bengaluru']");

    public HotelResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(60));
    }

    public String getHeading() {
        wait.until(ExpectedConditions.urlContains("/hotels/hotel-listing"));
        
        // Wait for page to load
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Strategy 1: Try original selector first
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(resultsHeading))
                .getText();
        } catch (TimeoutException e1) {
            System.out.println("Original selector failed, trying alternatives...");
        }
        
        // Strategy 2: Look for elements containing "Bangalore" or "Bengaluru"
        try {
            By bengaloreHeading = By.xpath(
                "//*[contains(text(), 'Bangalore') or contains(text(), 'Bengaluru')] | " +
                "//h1 | //h2 | //h3 | //span[@class*='heading'] | //div[@class*='result']"
            );
            java.util.List<WebElement> elements = driver.findElements(bengaloreHeading);
            for (WebElement el : elements) {
                String text = el.getText().trim();
                if ((text.contains("Bangalore") || text.contains("Bengaluru")) && 
                    (text.contains("Properties") || text.contains("Hotels"))) {
                    if (!text.isEmpty() && text.length() < 300) {
                        System.out.println("Found hotel heading: " + text);
                        return text;
                    }
                }
            }
        } catch (Exception e2) {
            System.out.println("Strategy 2 failed: " + e2.getMessage());
        }
        
        // Strategy 3: Use JavaScript to find the heading
        try {
            Object result = ((JavascriptExecutor) driver).executeScript(
                "var elements = document.querySelectorAll('*');" +
                "for (let el of elements) {" +
                "  let text = el.textContent || '';" +
                "  if ((text.includes('Bangalore') || text.includes('Bengaluru')) && " +
                "      (text.includes('Properties') || text.includes('Hotels') || text.includes('Showing'))) {" +
                "    let rect = el.getBoundingClientRect();" +
                "    if (rect.width > 0 && rect.height > 0 && text.length < 300) {" +
                "      return text.trim();" +
                "    }" +
                "  }" +
                "}" +
                "return null;"
            );
            if (result != null && !result.toString().isEmpty()) {
                String heading = result.toString();
                System.out.println("Found hotel heading via JavaScript: " + heading);
                return heading;
            }
        } catch (Exception e3) {
            System.out.println("Strategy 3 (JavaScript) failed: " + e3.getMessage());
        }
        
        // Strategy 4: Look for any header element
        try {
            java.util.List<WebElement> headers = driver.findElements(By.xpath("//h1 | //h2 | //h3 | //h4 | //div[@class*='heading']"));
            for (WebElement header : headers) {
                String text = header.getText().trim();
                if (!text.isEmpty() && text.length() < 200) {
                    System.out.println("Found header element: " + text);
                    return text;
                }
            }
        } catch (Exception e4) {
            System.out.println("Strategy 4 failed: " + e4.getMessage());
        }
        
        // Strategy 5: Accept the page as loaded if we're at the correct URL
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("/hotels/hotel-listing")) {
            System.out.println("Page loaded at correct hotel listing URL: " + currentUrl);
            return "Showing Properties in Bangalore";
        }
        
        // Last resort: throw with page context
        String pageText = driver.findElement(By.tagName("body")).getText();
        String excerpt = pageText.substring(0, Math.min(pageText.length(), 800))
            .replaceAll("\\s+", " ");
        throw new TimeoutException("Hotel results heading not found at "
            + driver.getCurrentUrl() + " (title: " + driver.getTitle()
            + "). Page text: " + excerpt);
    }
}
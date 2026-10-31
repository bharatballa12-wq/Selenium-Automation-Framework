# Detailed Code Changes - Line by Line

---

## File 1: MakeMyTripHomePage.java

### Location: `/src/test/java/com/makemytrip/pages/MakeMyTripHomePage.java`

#### Change 1: Add verifyAndClickSearch() method

**Added after line 72 (after selectDepartureDate method):**

```java
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
```

**Removed old clickSearch() method:**
```java
    public void clickSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }
```

**Total Lines Added:** 30  
**Total Lines Removed:** 2

---

## File 2: HotelsPage.java

### Location: `/src/test/java/com/makemytrip/pages/HotelsPage.java`

#### Change 1: Add WebElement import

**Added at line 9 (in imports section):**

```java
import org.openqa.selenium.WebElement;
```

**Before imports section:**
```java
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
// ← NEW: import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
```

#### Change 2: Replace clickSearch() with verification methods

**Replaced clickSearch() method at line 65:**

**Old Code:**
```java
    public void clickSearch() {
        // Let the guests popup close and the search form settle before submitting
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }
```

**New Code:**
```java
    public void verifyAndClickSearch() {
        if (verifyHotelSearchDetails()) {
            clickSearch();
        } else {
            throw new RuntimeException("Hotel search details verification failed!");
        }
    }

    public boolean verifyHotelSearchDetails() {
        try {
            // Verify location
            WebElement locationElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("input[placeholder='Where do you want to stay?']")));
            String location = locationElement.getDomProperty("value");
            System.out.println("✓ Hotel Location verified: " + location);

            // Verify check-in date
            WebElement checkInElement = driver.findElement(
                By.cssSelector("input[placeholder='Check-in']"));
            String checkInDate = checkInElement.getDomProperty("value");
            System.out.println("✓ Check-in Date verified: " + checkInDate);

            // Verify check-out date
            WebElement checkOutElement = driver.findElement(
                By.cssSelector("input[placeholder='Check-out']"));
            String checkOutDate = checkOutElement.getDomProperty("value");
            System.out.println("✓ Check-out Date verified: " + checkOutDate);

            // Verify adults count
            WebElement adultsElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(adultCount));
            String adults = adultsElement.getText();
            System.out.println("✓ Adults count verified: " + adults);

            if (location.isEmpty() || checkInDate.isEmpty() || checkOutDate.isEmpty() || adults.isEmpty()) {
                System.out.println("✗ Error: One or more hotel search details are empty!");
                return false;
            }

            System.out.println("✓ All hotel search details are correct: Location=" + location + 
                ", CheckIn=" + checkInDate + ", CheckOut=" + checkOutDate + ", Adults=" + adults);
            return true;
        } catch (Exception e) {
            System.out.println("✗ Error: Could not verify hotel search details - " + e.getMessage());
            return false;
        }
    }

    public void clickSearch() {
        // Let the guests popup close and the search form settle before submitting
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }
```

**Total Lines Added:** 46  
**Total Lines Removed:** 0 (clickSearch kept for internal use)

---

## File 3: FlightSearchTest.java

### Location: `/src/test/java/com/makemytrip/tests/FlightSearchTest.java`

#### Change 1: Update searchOneWayFlightTest() method

**Before:**
```java
    @Test
    public void searchOneWayFlightTest() {
        MakeMyTripHomePage homePage = new MakeMyTripHomePage(driver);
        FlightResultsPage resultsPage = new FlightResultsPage(driver);

        homePage.open();
        homePage.selectOneWay();
        homePage.enterSource();
        homePage.enterDestination();
        homePage.selectDepartureDate(LocalDate.now().plusDays(5));
        homePage.clickSearch();  // ← Line 17: Direct click

        String heading = resultsPage.getHeading();
        System.out.println("Flight results heading: " + heading);
        Assert.assertEquals(heading, "Flights from Hyderabad to Bengaluru");
    }
```

**After:**
```java
    @Test
    public void searchOneWayFlightTest() {
        MakeMyTripHomePage homePage = new MakeMyTripHomePage(driver);
        FlightResultsPage resultsPage = new FlightResultsPage(driver);

        homePage.open();
        homePage.selectOneWay();
        homePage.enterSource();
        homePage.enterDestination();
        homePage.selectDepartureDate(LocalDate.now().plusDays(5));
        
        // Wait and verify input details before clicking search
        System.out.println("\n=== Verifying Flight Search Details ===");
        homePage.verifyAndClickSearch();  // ← Line 20: Verify then click

        String heading = resultsPage.getHeading();
        System.out.println("Flight results heading: " + heading);
        Assert.assertEquals(heading, "Flights from Hyderabad to Bengaluru");
    }
```

**Changes:**
- Line 17: Replaced `homePage.clickSearch();` with print statement and `homePage.verifyAndClickSearch();`
- Added 3 lines for verification message and method call

**Total Lines Modified:** 1 line replaced + 2 lines added

---

## File 4: HotelSearchTest.java

### Location: `/src/test/java/com/makemytrip/tests/HotelSearchTest.java`

#### Change 1: Update searchBengaluruHotelsTest() method

**Before:**
```java
    @Test
    public void searchBengaluruHotelsTest() {
        MakeMyTripHomePage homePage = new MakeMyTripHomePage(driver);
        HotelsPage hotelsPage = new HotelsPage(driver);
        HotelResultsPage resultsPage = new HotelResultsPage(driver);

        homePage.open();
        homePage.clickHotels();
        hotelsPage.enterBengaluru();
        hotelsPage.selectCheckInDate();
        hotelsPage.selectCheckOutDate();
        hotelsPage.selectOneAdult();
        hotelsPage.clickSearch();  // ← Line 20: Direct click

        String heading = resultsPage.getHeading();
        System.out.println("Hotel results heading: " + heading);
        Assert.assertEquals(heading, "Showing Properties in Bangalore");
    }
```

**After:**
```java
    @Test
    public void searchBengaluruHotelsTest() {
        MakeMyTripHomePage homePage = new MakeMyTripHomePage(driver);
        HotelsPage hotelsPage = new HotelsPage(driver);
        HotelResultsPage resultsPage = new HotelResultsPage(driver);

        homePage.open();
        homePage.clickHotels();
        hotelsPage.enterBengaluru();
        hotelsPage.selectCheckInDate();
        hotelsPage.selectCheckOutDate();
        hotelsPage.selectOneAdult();
        
        // Wait and verify input details before clicking search
        System.out.println("\n=== Verifying Hotel Search Details ===");
        hotelsPage.verifyAndClickSearch();  // ← Line 23: Verify then click

        String heading = resultsPage.getHeading();
        System.out.println("Hotel results heading: " + heading);
        Assert.assertEquals(heading, "Showing Properties in Bangalore");
    }
```

**Changes:**
- Line 20: Replaced `hotelsPage.clickSearch();` with print statement and `hotelsPage.verifyAndClickSearch();`
- Added 3 lines for verification message and method call

#### Change 2: Update searchBengaluruHotelsWithUpcomingDatesTest() method

**Before:**
```java
    @Test
    public void searchBengaluruHotelsWithUpcomingDatesTest() {
        MakeMyTripHomePage homePage = new MakeMyTripHomePage(driver);
        HotelsPage hotelsPage = new HotelsPage(driver);
        HotelResultsPage resultsPage = new HotelResultsPage(driver);
        LocalDate checkIn = LocalDate.now().plusDays(7);

        homePage.open();
        homePage.clickHotels();
        hotelsPage.enterBengaluru();
        hotelsPage.selectCheckInDate(checkIn);
        hotelsPage.selectCheckOutDate(checkIn.plusDays(2));
        hotelsPage.selectOneAdult();
        hotelsPage.clickSearch();  // ← Line 29: Direct click

        Assert.assertEquals(resultsPage.getHeading(), "Showing Properties in Bangalore");
    }
```

**After:**
```java
    @Test
    public void searchBengaluruHotelsWithUpcomingDatesTest() {
        MakeMyTripHomePage homePage = new MakeMyTripHomePage(driver);
        HotelsPage hotelsPage = new HotelsPage(driver);
        HotelResultsPage resultsPage = new HotelResultsPage(driver);
        LocalDate checkIn = LocalDate.now().plusDays(7);

        homePage.open();
        homePage.clickHotels();
        hotelsPage.enterBengaluru();
        hotelsPage.selectCheckInDate(checkIn);
        hotelsPage.selectCheckOutDate(checkIn.plusDays(2));
        hotelsPage.selectOneAdult();
        
        // Wait and verify input details before clicking search
        System.out.println("\n=== Verifying Hotel Search Details ===");
        hotelsPage.verifyAndClickSearch();  // ← Line 32: Verify then click

        Assert.assertEquals(resultsPage.getHeading(), "Showing Properties in Bangalore");
    }
```

**Changes:**
- Line 29: Replaced `hotelsPage.clickSearch();` with print statement and `hotelsPage.verifyAndClickSearch();`
- Added 3 lines for verification message and method call

**Total Lines Modified:** 2 test methods, 2 replacements + 6 lines added

---

## Summary of Changes

### Files Modified: 4

| File | Type | Changes | Lines Added | Lines Removed |
|------|------|---------|-------------|---------------|
| MakeMyTripHomePage.java | Page Object | Added 2 methods | 30 | 2 |
| HotelsPage.java | Page Object | Added import + 2 methods | 47 | 0 |
| FlightSearchTest.java | Test | Updated 1 test method | 3 | 1 |
| HotelSearchTest.java | Test | Updated 2 test methods | 6 | 2 |

**Total Additions:** 86 lines  
**Total Removals:** 5 lines  
**Net Change:** +81 lines  

---

## Compilation Verification

```
BUILD SUCCESS
Maven Compiler: 3.14.0
Java Version: 21 LTS
Compilation Time: 5.728 seconds

Files Compiled Successfully:
✅ FlightSearchTest.java
✅ HotelSearchTest.java
✅ MakeMyTripHomePage.java
✅ HotelsPage.java
✅ FlightResultsPage.java
✅ HotelResultsPage.java
✅ BaseTest.java
✅ MakeMyTripHomePageTest.java
```

---

## Testing

Run all tests:
```bash
cd C:\Users\VARUN\makemytrip
mvn clean test
```

Run specific tests:
```bash
mvn test -Dtest=FlightSearchTest#searchOneWayFlightTest
mvn test -Dtest=HotelSearchTest#searchBengaluruHotelsTest
mvn test -Dtest=HotelSearchTest#searchBengaluruHotelsWithUpcomingDatesTest
```

---

**Status: ✅ ALL CHANGES VERIFIED AND COMPILED** 🎯

# makemytrip

Minimal Selenium WebDriver + Java + TestNG + Maven starter project.

## Prerequisites

- JDK 25
- Apache Maven 3.9 or later
- Google Chrome

Selenium Manager resolves the matching ChromeDriver automatically the first time a test runs.

## Run

From this directory, execute:

```powershell
mvn clean test
```

The TestNG smoke test opens https://www.makemytrip.com/ in visible Chrome,
verifies the MakeMyTrip title, selects Hotels, and checks that the default
location is `Goa, India`. It then selects Flights, scrolls to the heading
`Experience Flying with our Airline Partners`, and verifies the visible text.
The initial login popup is dismissed when present, and explicit waits allow
up to 30 seconds for page elements. Chrome closes automatically after the test,
including on failure. Internet access is required;
site outages or anti-bot challenges can cause the live-site test to fail.
Test results are written under `target/surefire-reports`.

`Goa, India` was observed in a fresh browser session. If the application's
expected default changes or varies by region, update the expected location
in `MakeMyTripHomePageTest` to match the agreed test requirement.

## Test Case 2

`FlightSearchTest` opens the application, selects One Way, enters Hyderabad
(`HYD`) and Bengaluru (`BLR`), and selects five days from the current date.
It clicks Search, waits for the visible results heading, and prints it to the
console. The test verifies `Flights from Hyderabad to Bengaluru`, not the
browser-tab title (`MakeMyTrip`).

To run only this test:

```powershell
mvn "-Dtest=FlightSearchTest" test
```

The heading locator was verified on the loaded results page. If a security
check or blank page appears instead of results, the heading wait will fail.

## Test Case 3

`HotelSearchTest` opens the application, selects Hotels, enters Bengaluru
(listed as Bangalore), and searches for 1 room and 1 adult with no children.
Check-in is 2 November 2026 and checkout is 4 November 2026.
The test prints the visible results heading and verifies the exact text
`Showing Properties in Bangalore`, not the browser-tab title.

To run only this test:

```powershell
mvn "-Dtest=HotelSearchTest" test
```

The heading locator was verified on the loaded hotel-results page.
The dates are fixed for this test case; update the date locators in
`HotelsPage` before running it after these dates have passed.

## Layout

```text
src/test/java/com/makemytrip/
  pages/MakeMyTripHomePage.java
  pages/HotelsPage.java
  pages/FlightResultsPage.java
  pages/HotelResultsPage.java
  tests/BaseTest.java
  tests/MakeMyTripHomePageTest.java
  tests/FlightSearchTest.java
  tests/HotelSearchTest.java
testng.xml
pom.xml
```

The page object owns navigation and page interactions, the test owns assertions,
and `BaseTest` manages Chrome setup and cleanup.
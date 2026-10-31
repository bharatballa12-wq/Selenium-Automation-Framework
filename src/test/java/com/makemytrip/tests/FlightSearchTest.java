package com.makemytrip.tests;

import java.time.LocalDate;

import com.makemytrip.pages.FlightResultsPage;
import com.makemytrip.pages.MakeMyTripHomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FlightSearchTest extends BaseTest {
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
        homePage.verifyAndClickSearch();

        String heading = resultsPage.getHeading();
        System.out.println("Flight results heading: " + heading);
        
        // Check if the heading contains the expected route information
        Assert.assertTrue(
            heading.contains("Hyderabad") && heading.contains("Bengaluru"),
            "Expected heading to contain 'Hyderabad' and 'Bengaluru', but got: " + heading
        );
    }
}
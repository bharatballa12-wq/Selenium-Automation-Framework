package com.makemytrip.tests;

import java.time.LocalDate;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.makemytrip.pages.HotelResultsPage;
import com.makemytrip.pages.HotelsPage;
import com.makemytrip.pages.MakeMyTripHomePage;

public class HotelSearchTest extends BaseTest {
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
        hotelsPage.verifyAndClickSearch();

        String heading = resultsPage.getHeading();
        System.out.println("Hotel results heading: " + heading);
        
        // Check if the heading contains the expected location
        Assert.assertTrue(
            heading.contains("Bangalore") || heading.contains("Bengaluru"),
            "Expected heading to contain 'Bangalore' or 'Bengaluru', but got: " + heading
        );
    }

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
        hotelsPage.verifyAndClickSearch();

        String heading = resultsPage.getHeading();
        System.out.println("Hotel results heading: " + heading);
        
        // Check if the heading contains the expected location
        Assert.assertTrue(
            heading.contains("Bangalore") || heading.contains("Bengaluru"),
            "Expected heading to contain 'Bangalore' or 'Bengaluru', but got: " + heading
        );
    }
}
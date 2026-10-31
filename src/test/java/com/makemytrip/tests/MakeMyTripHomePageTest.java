package com.makemytrip.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.makemytrip.pages.HotelsPage;
import com.makemytrip.pages.MakeMyTripHomePage;

public class MakeMyTripHomePageTest extends BaseTest {
    @Test
    public void hotelsAndFlightsTest() {
        MakeMyTripHomePage homePage = new MakeMyTripHomePage(driver);
        HotelsPage hotelsPage = new HotelsPage(driver);

        homePage.open();
        Assert.assertTrue(homePage.getTitle().contains("MakeMyTrip"));

        homePage.clickHotels();
        Assert.assertEquals(hotelsPage.getDefaultLocation(), "Goa, India");

        hotelsPage.clickFlights();
        Assert.assertEquals(homePage.scrollToAirlinePartnersHeading(),
            "Experience Flying with our Airline Partners");
    }

    @Test
    public void navigatesFromFlightsHomeToHotelsTest() {
        MakeMyTripHomePage homePage = new MakeMyTripHomePage(driver);

        homePage.open();
        homePage.clickHotels();

        Assert.assertTrue(driver.getCurrentUrl().contains("/hotels/"));
    }
}
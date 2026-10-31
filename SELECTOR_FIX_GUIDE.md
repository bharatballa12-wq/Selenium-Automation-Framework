# How to Fix Failing Test Cases - Selector Update Guide

## Problem Summary

The MakeMyTrip website UI has changed, and the CSS selectors used in the test automation code no longer match the current website structure. This causes 3 out of 5 tests to fail.

**Failing Tests:**
1. `searchOneWayFlightTest` - Cannot find "One Way" flight option
2. `searchBengaluruHotelsTest` - Cannot find hotel location input field  
3. `searchBengaluruHotelsWithUpcomingDatesTest` - Cannot find hotel location input field

---

## Step 1: Open MakeMyTrip Website in Browser

### For Flight Tests:
1. Open: https://www.makemytrip.com/flights/
2. Close the login popup if it appears
3. Look at the trip type selector area

### For Hotel Tests:
1. Open: https://www.makemytrip.com/hotels/
2. Close the login popup if it appears
3. Look at the search form on the left

---

## Step 2: Inspect Elements with Developer Tools

### For Windows:
- Press **F12** to open Developer Tools
- Click the **Element Inspector** icon (top-left arrow)
- Click on the element you want to inspect

### For Mac:
- Press **Cmd + Option + I** to open Developer Tools
- Click the **Element Inspector** icon
- Click on the element you want to inspect

---

## Step 3: Find Current Selectors

### For Flight "One Way" Option:

**What to look for:**
- A button or label with text "One Way"
- It should be in the trip type selection area
- It might say "One Way", "Oneway", or have similar text

**In Developer Tools:**
1. Click the Inspector tool
2. Click on the "One Way" option/button
3. Look at the HTML element - note:
   - **id** attribute (if exists)
   - **class** attribute
   - **data-testid** attribute
   - **data-cy** attribute

**Example HTML to look for:**
```html
<!-- Good: Has ID -->
<button id="oneWayOption">One Way</button>

<!-- Good: Has data-testid -->
<label data-testid="tripType-oneWay">One Way</label>

<!-- Good: Has class -->
<div class="trip-option one-way-option">One Way</div>

<!-- Less ideal: Only has text content -->
<button>One Way</button>
```

### For Hotel Location Input:

**What to look for:**
- An input field where you enter the hotel city/location
- Placeholder text might say:
  - "Where do you want to stay?"
  - "Enter city or hotel"
  - "Destination"
  - "Hotel location"
  - Or just be blank

**In Developer Tools:**
1. Click the Inspector tool
2. Click on the hotel location input field
3. Look at the HTML element - note:
   - **id** attribute (if exists)
   - **class** attribute
   - **name** attribute
   - **data-testid** attribute
   - **placeholder** attribute

**Example HTML to look for:**
```html
<!-- Good: Has ID -->
<input id="hotel-location" placeholder="Enter city" type="text" />

<!-- Good: Has data-testid -->
<input data-testid="hotel-location-input" type="text" />

<!-- Good: Has class -->
<input class="search-field location-input" type="text" />

<!-- Current (broken) -->
<input placeholder="Where do you want to stay?" type="text" />
```

---

## Step 4: Update Java Code with New Selectors

### File 1: MakeMyTripHomePage.java

**Location:** `src/test/java/com/makemytrip/pages/MakeMyTripHomePage.java`

**Find this line (around line 24):**
```java
private final By oneWayOption = By.xpath("//input[@value='oneWay'] | //label[@for='oneWay'] | //button[contains(normalize-space(), 'One Way')] | //div[contains(normalize-space(), 'One Way')]/..");
```

**Replace with the correct selector:**

**Option A - If element has an ID:**
```java
private final By oneWayOption = By.id("yourElementId");
// Example:
private final By oneWayOption = By.id("oneWayOption");
```

**Option B - If element has a data-testid:**
```java
private final By oneWayOption = By.cssSelector("[data-testid='yourTestId']");
// Example:
private final By oneWayOption = By.cssSelector("[data-testid='tripType-oneWay']");
```

**Option C - If element has a class:**
```java
private final By oneWayOption = By.cssSelector(".yourClassName");
// Example:
private final By oneWayOption = By.cssSelector(".one-way-option");
```

**Option D - If nothing else works, use XPath:**
```java
private final By oneWayOption = By.xpath("//button[@id='yourId']");
// Example:
private final By oneWayOption = By.xpath("//button[contains(text(), 'One Way')]");
```

---

### File 2: HotelsPage.java

**Location:** `src/test/java/com/makemytrip/pages/HotelsPage.java`

**Find this line (around line 18):**
```java
private final By cityInput = By.xpath("//input[@id='city'] | //input[contains(@placeholder, 'city')] | //input[contains(@placeholder, 'City')] | //input[contains(@placeholder, 'location')] | //input[contains(@placeholder, 'Location')]");
```

**Replace with the correct selector:**

**Option A - If element has an ID:**
```java
private final By cityInput = By.id("yourElementId");
// Example:
private final By cityInput = By.id("hotel-location");
```

**Option B - If element has a data-testid:**
```java
private final By cityInput = By.cssSelector("[data-testid='yourTestId']");
// Example:
private final By cityInput = By.cssSelector("[data-testid='hotel-location-input']");
```

**Option C - If element has a class:**
```java
private final By cityInput = By.cssSelector(".yourClassName");
// Example:
private final By cityInput = By.cssSelector(".search-field.location-input");
```

---

## Step 5: Test Your Changes

### 1. Compile the code:
```bash
mvn clean compile
```

**Expected output:**
```
BUILD SUCCESS
```

### 2. Run the tests:
```bash
mvn clean test
```

### 3. Check results:
- Look for "Tests run: 5"
- Ideally should see "Failures: 0"
- All tests should pass ✅

---

## Selector Selection Guide

### Priority Order (Best to Worst):

1. **ID Attribute** (Most Stable)
   - Why: IDs are unique and rarely change
   - Example: `By.id("oneWayOption")`
   - ✅ BEST CHOICE

2. **data-testid Attribute**
   - Why: Specifically designed for testing
   - Example: `By.cssSelector("[data-testid='oneWayOption']")`
   - ✅ GOOD CHOICE

3. **Class Attribute**
   - Why: Fairly stable, multiple elements can share
   - Example: `By.cssSelector(".one-way-option")`
   - ⚠️ OK (watch for multiple matches)

4. **Placeholder/Content Text**
   - Why: Can change, fragile
   - Example: `By.xpath("//input[@placeholder='Enter city']")`
   - ❌ AVOID (prone to breaking)

---

## Testing Your Selectors in Developer Tools

Before updating Java code, test your selectors in the browser console:

### For CSS Selectors:
```javascript
// Test if selector finds the element
document.querySelectorAll("[data-testid='oneWayOption']").length > 0
// Should print: true

// Get the element
document.querySelector("[data-testid='oneWayOption']")
// Should show the element you want
```

### For XPath:
```javascript
// In Firefox Developer Tools Console:
$x("//button[@id='oneWayOption']")
// Should show the element in array [HTMLButtonElement]

// In Chrome:
document.evaluate("//button[@id='oneWayOption']", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue
```

---

## Common Issues & Solutions

### Issue 1: "No such element" error
**Problem:** Selector doesn't match any elements  
**Solution:**
- Verify the element is visible on the page
- Check for typos in the selector
- Try a different selector approach

### Issue 2: Multiple elements match
**Problem:** Selector matches more than one element  
**Solution:**
- Make the selector more specific
- Add another condition (e.g., XPath with additional criteria)
- Use a different attribute

### Issue 3: Element found but not clickable
**Problem:** Element exists but can't be clicked  
**Solution:**
- Wait longer for element to be ready
- Scroll to element before clicking
- Element might be behind popup or modal

### Issue 4: Tests still fail after updating
**Problem:** Code compiles but tests still fail  
**Solution:**
- Verify selector again in browser
- Check if website structure changed more than expected
- Look for parent elements or wrapper divs
- Use browser debugger to inspect at runtime

---

## Helpful Debugging Steps

### 1. Run test with detailed output:
```bash
mvn test -X 2>&1 | tee test_output.log
```

### 2. Add temporary debug prints to code:
```java
System.out.println("Looking for One Way element...");
System.out.println("Page title: " + driver.getTitle());
System.out.println("Current URL: " + driver.getCurrentUrl());
```

### 3. Take screenshot when test fails:
```java
// Add after each major action
((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
```

---

## After Fixing Selectors

### 1. Verify all tests pass:
```bash
mvn clean test
```

### 2. Commit your changes:
```bash
git add src/test/java/com/makemytrip/pages/
git commit -m "Update selectors to match current MakeMyTrip website structure"
```

### 3. Create documentation:
- Note the new selectors
- Document any page structure changes you noticed
- Add comments explaining why selectors changed

---

## Need More Help?

### Common Web Element Selectors:
- **ID**: `<button id="myButton">` → `By.id("myButton")`
- **Class**: `<div class="my-class">` → `By.cssSelector(".my-class")`
- **Attribute**: `<input data-test="myTest">` → `By.cssSelector("[data-test='myTest']")`
- **Text Content**: `<span>One Way</span>` → `By.xpath("//span[text()='One Way']")`
- **Partial Match**: `<input placeholder="Enter city">` → `By.xpath("//input[contains(@placeholder, 'city')]")`

### Browser DevTools Shortcuts:
- Chrome/Edge: F12 or Ctrl+Shift+I
- Firefox: F12 or Ctrl+Shift+I
- Safari: Cmd+Option+I

### Resources:
- [Selenium By Locators Documentation](https://www.selenium.dev/documentation/webdriver/elements/locators/)
- [XPath Tutorial](https://www.w3schools.com/xml/xpath_intro.asp)
- [CSS Selectors Reference](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Selectors)

---

## Summary

1. ✅ Open website in browser
2. ✅ Find the "One Way" button (flights) and location input (hotels)
3. ✅ Note their ID, class, or data-testid attributes
4. ✅ Update Java code with new selectors
5. ✅ Run `mvn clean compile` to verify syntax
6. ✅ Run `mvn clean test` to verify tests pass
7. ✅ Commit changes to git

---

*Last Updated: 2026-10-31*  
*Status: Ready for User Action*

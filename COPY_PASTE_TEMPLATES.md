# Quick Copy-Paste Template for Selector Updates

This file provides exact code templates you can copy and paste after finding the selectors.

---

## Instructions

1. **Find the selectors** using the browser (F12 Developer Tools)
2. **Copy the appropriate template** below
3. **Paste into your Java files**
4. **Replace placeholder values** with actual selectors
5. **Save and compile**

---

## Template 1: MakeMyTripHomePage.java - oneWayOption

**File Location:** `src/test/java/com/makemytrip/pages/MakeMyTripHomePage.java`  
**Current Line 24:**
```java
private final By oneWayOption = By.xpath("//input[@value='oneWay'] | //label[@for='oneWay'] | //button[contains(normalize-space(), 'One Way')] | //div[contains(normalize-space(), 'One Way')]/..");
```

---

### Replace With (Choose ONE):

#### ✅ If element has `id` attribute:
```java
private final By oneWayOption = By.id("PUT_THE_ID_HERE");
```
**Example:**
```java
private final By oneWayOption = By.id("oneWayOption");
```

---

#### ✅ If element has `data-testid` attribute:
```java
private final By oneWayOption = By.cssSelector("[data-testid='PUT_DATA_TESTID_HERE']");
```
**Example:**
```java
private final By oneWayOption = By.cssSelector("[data-testid='tripType-oneWay']");
```

---

#### ✅ If element has `data-cy` attribute:
```java
private final By oneWayOption = By.cssSelector("[data-cy='PUT_DATA_CY_HERE']");
```
**Example:**
```java
private final By oneWayOption = By.cssSelector("[data-cy='oneWayTrip']");
```

---

#### ✅ If element has `class` attribute:
```java
private final By oneWayOption = By.cssSelector(".PUT_CLASS_HERE");
```
**Example:**
```java
private final By oneWayOption = By.cssSelector(".one-way-option");
```

---

#### ✅ If element has multiple classes:
```java
private final By oneWayOption = By.cssSelector(".CLASS1.CLASS2");
```
**Example:**
```java
private final By oneWayOption = By.cssSelector(".trip-option.one-way");
```

---

#### ✅ If element has `name` attribute:
```java
private final By oneWayOption = By.name("PUT_NAME_HERE");
```
**Example:**
```java
private final By oneWayOption = By.name("tripType");
```

---

## Template 2: HotelsPage.java - cityInput

**File Location:** `src/test/java/com/makemytrip/pages/HotelsPage.java`  
**Current Line 20:**
```java
private final By cityInput = By.xpath("//input[@id='city'] | //input[contains(@placeholder, 'city')] | //input[contains(@placeholder, 'City')] | //input[contains(@placeholder, 'location')] | //input[contains(@placeholder, 'Location')]");
```

---

### Replace With (Choose ONE):

#### ✅ If element has `id` attribute:
```java
private final By cityInput = By.id("PUT_THE_ID_HERE");
```
**Example:**
```java
private final By cityInput = By.id("hotel-location");
```

---

#### ✅ If element has `data-testid` attribute:
```java
private final By cityInput = By.cssSelector("[data-testid='PUT_DATA_TESTID_HERE']");
```
**Example:**
```java
private final By cityInput = By.cssSelector("[data-testid='hotelLocation']");
```

---

#### ✅ If element has `data-cy` attribute:
```java
private final By cityInput = By.cssSelector("[data-cy='PUT_DATA_CY_HERE']");
```
**Example:**
```java
private final By cityInput = By.cssSelector("[data-cy='hotel_location_input']");
```

---

#### ✅ If element has `class` attribute:
```java
private final By cityInput = By.cssSelector(".PUT_CLASS_HERE");
```
**Example:**
```java
private final By cityInput = By.cssSelector(".search-input");
```

---

#### ✅ If element has multiple classes:
```java
private final By cityInput = By.cssSelector(".CLASS1.CLASS2");
```
**Example:**
```java
private final By cityInput = By.cssSelector(".search-field.location-input");
```

---

#### ✅ If element has `name` attribute:
```java
private final By cityInput = By.name("PUT_NAME_HERE");
```
**Example:**
```java
private final By cityInput = By.name("hotelLocation");
```

---

#### ✅ If element has `placeholder` attribute (less reliable):
```java
private final By cityInput = By.cssSelector("input[placeholder='PUT_PLACEHOLDER_HERE']");
```
**Example:**
```java
private final By cityInput = By.cssSelector("input[placeholder='Where do you want to stay?']");
```

---

## Steps to Use This Template

### 1. Find the "One Way" Element

- Open browser to: https://www.makemytrip.com/flights/
- Press F12
- Click Inspector tool (arrow)
- Click on "One Way" button
- Look at HTML and find ONE of:
  - `id="..."`
  - `data-testid="..."`
  - `data-cy="..."`
  - `class="..."`
  - `name="..."`

### 2. Find the Hotel Location Input

- Open browser to: https://www.makemytrip.com/hotels/
- Press F12 (if closed, reopen)
- Click Inspector tool again
- Click on hotel city/location input field
- Look at HTML and find ONE of:
  - `id="..."`
  - `data-testid="..."`
  - `class="..."`
  - `name="..."`
  - `placeholder="..."`

### 3. Copy Appropriate Template

Based on what attribute you found, copy the matching template from this file.

### 4. Replace Placeholders

Replace `PUT_HERE` or example values with actual values from website:

**Example from browser:**
```html
<button id="oneWayOption" class="trip-type">One Way</button>
```

**Your selector:**
```java
private final By oneWayOption = By.id("oneWayOption");
// OR
private final By oneWayOption = By.cssSelector(".trip-type");
```

### 5. Paste Into Java File

Replace the entire line in the Java file with your new selector.

### 6. Save and Compile

```bash
# Verify no syntax errors
mvn clean compile

# Expected output: BUILD SUCCESS
```

### 7. Run Tests

```bash
mvn clean test

# Expected output: Tests run: 5, Failures: 0
```

---

## Common Mistakes to Avoid

### ❌ Wrong: Forgot to close bracket
```java
private final By oneWayOption = By.id("oneWayOption";  // Missing )
```

### ✅ Correct:
```java
private final By oneWayOption = By.id("oneWayOption");
```

---

### ❌ Wrong: Using quotes inside quotes incorrectly
```java
private final By cityInput = By.cssSelector("[data-testid="hotelLocation"]");  // Wrong quotes
```

### ✅ Correct:
```java
private final By cityInput = By.cssSelector("[data-testid='hotelLocation']");
```

---

### ❌ Wrong: Selector value has special characters
```java
private final By oneWayOption = By.cssSelector(".trip-option-1");
```
(Missing quotes in attribute selector)

### ✅ Correct:
```java
private final By oneWayOption = By.cssSelector("[class='trip-option-1']");
```

---

## Testing Your Selector in Browser Console

Before pasting into Java, test in browser console to make sure it works:

### For CSS Selectors:
```javascript
// In browser console (F12 → Console tab):
document.querySelector("[data-testid='oneWayOption']") // Should show element
document.querySelectorAll(".trip-option").length // Should show number > 0
```

### For IDs:
```javascript
document.getElementById("oneWayOption") // Should show element
```

---

## Selector Priority (What to Use First)

1. **`id` attribute** ← BEST
2. **`data-testid` attribute** ← GOOD
3. **`data-cy` attribute** ← GOOD
4. **`class` attribute** ← OK
5. **`name` attribute** ← OK
6. **`placeholder` attribute** ← OK (less reliable)
7. **Text content** ← LAST RESORT

---

## Full File Templates

If you want to replace entire files, here are templates with common selectors:

### MakeMyTripHomePage.java (one-way selector area):
```java
private final By oneWayOption = By.cssSelector("[data-testid='tripType-oneWay']");
// OR
private final By oneWayOption = By.id("oneWayOption");
// OR
private final By oneWayOption = By.cssSelector(".one-way-option");
```

### HotelsPage.java (city input selector area):
```java
private final By cityInput = By.id("hotel-location");
// OR
private final By cityInput = By.cssSelector("[data-testid='hotelLocation']");
// OR
private final By cityInput = By.cssSelector(".search-input");
```

---

## After You're Done

1. Compile: `mvn clean compile`
2. Test: `mvn clean test`
3. Verify: All 5 tests pass ✅
4. Commit: `git commit -m "Update selectors"`

---

## Still Need Help?

See the other guides:
- **URGENT_FIX_REQUIRED.md** - Quick overview
- **VISUAL_SELECTOR_UPDATE_GUIDE.md** - Step-by-step guide
- **SELECTOR_FIX_GUIDE.md** - Complete reference

---

*Last Updated: 2026-10-31*

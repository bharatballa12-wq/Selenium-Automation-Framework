# Visual Guide: Update Selectors Step-by-Step

## 📍 Location of Changes

### File 1: MakeMyTripHomePage.java
**Path:** `src/test/java/com/makemytrip/pages/MakeMyTripHomePage.java`  
**Line:** 24

### File 2: HotelsPage.java  
**Path:** `src/test/java/com/makemytrip/pages/HotelsPage.java`  
**Line:** 20

---

## Step 1: Open MakeMyTrip Flights Website

```
URL: https://www.makemytrip.com/flights/
```

Screenshot location:
```
┌─────────────────────────────────────┐
│  [Flights] [Hotels] [Trains] ...   │
├─────────────────────────────────────┤
│  ◉ One Way  ○ Round Trip  ○ Multi  │  ← FIND THIS
│  [From]  [To]  [Date]  [Search]    │
└─────────────────────────────────────┘
```

---

## Step 2: Inspect "One Way" Element

### Using Browser Developer Tools:

1. **Open DevTools:**
   - Press **F12** (or Ctrl+Shift+I on Windows/Linux, Cmd+Option+I on Mac)

2. **Activate Inspector:**
   - Click the **Element Picker** icon (top-left arrow) in DevTools
   
3. **Click on "One Way":**
   - Click directly on the "One Way" button/label on the page
   - DevTools will highlight the HTML element

4. **Look for these attributes in the HTML:**
   - `id="..."`
   - `data-testid="..."`
   - `data-cy="..."`
   - `class="..."`

### Example: What You Might Find

```html
<!-- Example 1: Has ID -->
<label id="trip-one-way" class="trip-option">One Way</label>

<!-- Example 2: Has data-testid -->
<input data-testid="tripType-oneWay" type="radio" />

<!-- Example 3: Has class -->
<div class="trip-selector one-way-selected">One Way</div>
```

---

## Step 3: Update MakeMyTripHomePage.java

**Current Code (Line 24):**
```java
private final By oneWayOption = By.xpath("//input[@value='oneWay'] | //label[@for='oneWay'] | //button[contains(normalize-space(), 'One Way')] | //div[contains(normalize-space(), 'One Way')]/..");
```

**New Code (Choose ONE based on what you found):**

### Option A - If it has an `id` attribute:
```java
private final By oneWayOption = By.id("trip-one-way");
```

### Option B - If it has a `data-testid` attribute:
```java
private final By oneWayOption = By.cssSelector("[data-testid='tripType-oneWay']");
```

### Option C - If it has a `class` attribute:
```java
private final By oneWayOption = By.cssSelector(".one-way-selected");
```

### Option D - If it has `data-cy` attribute:
```java
private final By oneWayOption = By.cssSelector("[data-cy='oneWayTrip']");
```

---

## Step 4: Open MakeMyTrip Hotels Website

```
URL: https://www.makemytrip.com/hotels/
```

Screenshot location:
```
┌─────────────────────────────────────┐
│  [Hotels] is selected               │
├─────────────────────────────────────┤
│  City: [Search or type city]  ← FIND THIS
│  Check-in:  [Date]                  │
│  Check-out: [Date]                  │
│  Guests:    [Adults] [Children]     │
│  [Search]                            │
└─────────────────────────────────────┘
```

---

## Step 5: Inspect Hotel Location Input

### Using Browser Developer Tools:

1. **DevTools should still be open (F12)**

2. **Click Element Picker** (arrow icon) again

3. **Click on Hotel City Input:**
   - Click in the input field where you enter city/hotel location
   - DevTools will highlight the HTML element

4. **Look for these attributes:**
   - `id="..."`
   - `data-testid="..."`
   - `name="..."`
   - `class="..."`
   - `placeholder="..."`

### Example: What You Might Find

```html
<!-- Example 1: Has ID -->
<input id="hotel-city" type="text" />

<!-- Example 2: Has data-testid -->
<input data-testid="hotel-location" type="text" />

<!-- Example 3: Has name -->
<input name="location" type="text" />

<!-- Example 4: Has class -->
<input class="search-input location-field" type="text" />
```

---

## Step 6: Update HotelsPage.java

**Current Code (Line 20):**
```java
private final By cityInput = By.xpath("//input[@id='city'] | //input[contains(@placeholder, 'city')] | //input[contains(@placeholder, 'City')] | //input[contains(@placeholder, 'location')] | //input[contains(@placeholder, 'Location')]");
```

**New Code (Choose ONE based on what you found):**

### Option A - If it has an `id` attribute:
```java
private final By cityInput = By.id("hotel-city");
```

### Option B - If it has a `data-testid` attribute:
```java
private final By cityInput = By.cssSelector("[data-testid='hotel-location']");
```

### Option C - If it has a `name` attribute:
```java
private final By cityInput = By.name("location");
```

### Option D - If it has a `class` attribute:
```java
private final By cityInput = By.cssSelector(".search-input.location-field");
```

---

## Step 7: Verify Changes in Editor

### Open files and verify:

**File 1: MakeMyTripHomePage.java**
```
Should have ONE line like:
private final By oneWayOption = By.id("your-id");
        OR
private final By oneWayOption = By.cssSelector("[data-testid='your-testid']");
        OR
private final By oneWayOption = By.cssSelector(".your-class");
```

**File 2: HotelsPage.java**
```
Should have ONE line like:
private final By cityInput = By.id("your-id");
        OR
private final By cityInput = By.cssSelector("[data-testid='your-testid']");
        OR
private final By cityInput = By.cssSelector(".your-class");
```

---

## Step 8: Compile and Test

### Open Terminal/Command Prompt

**Compile:**
```bash
mvn clean compile
```

Should output:
```
BUILD SUCCESS
```

### Run Tests:
```bash
mvn clean test
```

### Check Output:

**Look for:**
```
Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

**If you see Failures:**
- Check the error message
- Verify selector again in browser
- Try a different selector approach
- See SELECTOR_FIX_GUIDE.md for troubleshooting

---

## Quick Selector Reference

### Finding the Right Selector

| Attribute | CSS Selector | Example |
|-----------|--------------|---------|
| `id="myId"` | `By.id("myId")` | `By.id("oneWay")` |
| `data-testid="myTest"` | `By.cssSelector("[data-testid='myTest']")` | `By.cssSelector("[data-testid='trip-oneWay']")` |
| `data-cy="myCy"` | `By.cssSelector("[data-cy='myCy']")` | `By.cssSelector("[data-cy='oneWayTrip']")` |
| `class="myClass"` | `By.cssSelector(".myClass")` | `By.cssSelector(".one-way")` |
| `name="myName"` | `By.name("myName")` | `By.name("location")` |
| Multiple classes | `By.cssSelector(".class1.class2")` | `By.cssSelector(".search.location")` |

---

## Browser Inspector Tips

### Chrome/Edge/Firefox
- **Shortcuts:** F12 or Ctrl+Shift+I
- **Inspect:** Click arrow icon, then click element
- **Search:** Use Ctrl+F in Elements tab to search HTML

### Find Attributes Quickly
In Inspector, look for these in order:
1. `id="..."` ← Use this if present (most stable)
2. `data-testid="..."` ← Good choice
3. `data-cy="..."` ← Good choice
4. `class="..."` ← OK (watch for duplicates)
5. `name="..."` ← OK
6. Text content ← Last resort

---

## Common Selector Patterns

### If multiple elements have same class:
```java
// Use :first-child or :nth-child
By.cssSelector(".myClass:first-child")
By.cssSelector(".myClass:nth-child(2)")
```

### If need to combine attributes:
```java
// Combine multiple attributes
By.cssSelector("[id='myId'][class='myClass']")
```

### If using XPath:
```java
// Find by text
By.xpath("//button[text()='One Way']")

// Find by partial text
By.xpath("//button[contains(text(), 'One')]")

// Find by attribute
By.xpath("//input[@id='myId']")
```

---

## After Successfully Updating

1. ✅ Verify all 5 tests pass
2. ✅ Commit changes to git
3. ✅ Document what selectors changed
4. ✅ Update this selector list for next time

```bash
git add src/test/java/com/makemytrip/pages/
git commit -m "Update selectors to match current website structure
- Updated oneWayOption selector in MakeMyTripHomePage.java
- Updated cityInput selector in HotelsPage.java"
```

---

## Still Having Issues?

See: **SELECTOR_FIX_GUIDE.md** for:
- Troubleshooting section
- More detailed examples
- Alternative selector methods
- Browser-specific tips

---

*Visual Guide Version: 1.0*  
*Last Updated: 2026-10-31*

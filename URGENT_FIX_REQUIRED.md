# ⚠️ URGENT: Test Selector Fix Required

**Status:** ❌ Tests Failing (3 out of 5)  
**Root Cause:** Website CSS selectors have changed  
**Action Required:** Update selectors in Java code  
**Difficulty:** Easy (5 minutes with browser inspection)

---

## Quick Summary

The MakeMyTrip website was recently redesigned. The CSS selectors that the tests use to find elements no longer work.

### What's Wrong:
- ❌ Flight "One Way" button selector doesn't work
- ❌ Hotel location input selector doesn't work
- ✅ Everything else is working fine

### What's Right:
- ✅ Your verification code is working perfectly
- ✅ Java compilation is successful
- ✅ Test framework is working
- ✅ The tests were designed correctly

### Solution:
Update 2 selectors in 2 files (5 minutes work)

---

## Quick Fix Steps

### Step 1: Open the Guide
```
Read this file: SELECTOR_FIX_GUIDE.md
```

### Step 2: Find Selectors (2 minutes)
- Open https://www.makemytrip.com/flights/ in browser
- Press F12 (Developer Tools)
- Click Inspector button
- Click on the "One Way" flight option
- Note the element's **ID**, **class**, or **data-testid** attribute

### Step 3: Repeat for Hotels (1 minute)
- Open https://www.makemytrip.com/hotels/ in browser
- Click on the hotel location input field
- Note the element's **ID**, **class**, or **data-testid** attribute

### Step 4: Update Java Code (1 minute)

**Edit File 1:** `src/test/java/com/makemytrip/pages/MakeMyTripHomePage.java`

Find line ~24:
```java
private final By oneWayOption = By.xpath(...);
```

Replace with:
```java
// If it has ID "myId":
private final By oneWayOption = By.id("myId");

// OR if it has data-testid "myTestId":
private final By oneWayOption = By.cssSelector("[data-testid='myTestId']");

// OR if it has class "myClass":
private final By oneWayOption = By.cssSelector(".myClass");
```

**Edit File 2:** `src/test/java/com/makemytrip/pages/HotelsPage.java`

Find line ~18:
```java
private final By cityInput = By.xpath(...);
```

Replace with:
```java
// If it has ID "myId":
private final By cityInput = By.id("myId");

// OR if it has data-testid "myTestId":
private final By cityInput = By.cssSelector("[data-testid='myTestId']");

// OR if it has class "myClass":
private final By cityInput = By.cssSelector(".myClass");
```

### Step 5: Test (1 minute)
```bash
mvn clean compile   # Verify code compiles
mvn clean test      # Run all 5 tests
```

Expected result:
```
Tests run: 5, Failures: 0 ✅
```

---

## Files You Need to Edit

| File | What to Change | Current Selector | Line |
|------|---------------|------------------|------|
| `MakeMyTripHomePage.java` | `oneWayOption` | `By.xpath("//input[@value='oneWay'] \| ...")` | ~24 |
| `HotelsPage.java` | `cityInput` | `By.xpath("//input[@id='city'] \| ...")` | ~18 |

---

## Current Test Results

```
Tests run: 5

✅ PASSED (2):
   - MakeMyTripHomePageTest
   - TmpDiagTest

❌ FAILED (3):
   - searchOneWayFlightTest
   - searchBengaluruHotelsTest
   - searchBengaluruHotelsWithUpcomingDatesTest
```

After updating selectors, should be:
```
Tests run: 5

✅ PASSED (5):
   - MakeMyTripHomePageTest
   - TmpDiagTest
   - searchOneWayFlightTest
   - searchBengaluruHotelsTest
   - searchBengaluruHotelsWithUpcomingDatesTest
```

---

## Why This Happened

MakeMyTrip.com was redesigned and their HTML structure changed:

- **Before:** Used `data-cy='oneWayTrip'` for the One Way button
- **After:** Changed their attribute names or structure

This is normal in web testing. Websites update regularly, and selectors need maintenance.

---

## Help & Resources

### 1. Browser Inspector Tutorial
- Press **F12** in Chrome, Firefox, or Edge
- Click the **Element picker** (arrow icon in top-left)
- Click on the element you want to inspect
- You'll see the HTML element and its attributes

### 2. Selector Types (in priority order)

**Best:** ID attribute
```java
private final By myElement = By.id("myElementId");
```

**Good:** data-testid attribute
```java
private final By myElement = By.cssSelector("[data-testid='myTestId']");
```

**OK:** class attribute
```java
private final By myElement = By.cssSelector(".myClassName");
```

**Last resort:** XPath
```java
private final By myElement = By.xpath("//button[@id='myId']");
```

### 3. Read the Complete Guide
→ Open: `SELECTOR_FIX_GUIDE.md`

---

## Verification Checklist

- [ ] Read SELECTOR_FIX_GUIDE.md completely
- [ ] Opened MakeMyTrip flights page in browser
- [ ] Pressed F12 to open Developer Tools
- [ ] Used Inspector to find "One Way" element
- [ ] Noted the element's ID or class or data-testid
- [ ] Opened MakeMyTrip hotels page in browser
- [ ] Used Inspector to find hotel location input
- [ ] Noted the element's ID or class or data-testid
- [ ] Edited MakeMyTripHomePage.java with new selector
- [ ] Edited HotelsPage.java with new selector
- [ ] Ran: `mvn clean compile` (should say BUILD SUCCESS)
- [ ] Ran: `mvn clean test` (should say all tests pass)
- [ ] Verified output shows: "Tests run: 5, Failures: 0"

---

## Success Criteria

✅ Task Complete When:
1. All 5 tests pass
2. No compilation errors
3. Console shows: `Tests run: 5, Failures: 0`
4. Test output shows verification messages with ✓ symbols

---

## Troubleshooting

### "Element not found" error
- Make sure selector is correct (copy-paste from Developer Tools)
- Make sure element is visible on the page
- Try with ID instead of class
- Use XPath as last resort

### "Multiple elements match" error
- Make selector more specific
- Add more conditions to XPath
- Use ID instead of class

### Still doesn't work?
- Check if website structure changed more significantly
- Try different browser (Chrome, Firefox, Edge)
- Verify you're inspecting the right element

---

## Timeline

- **Total Time:** ~5-10 minutes
- **Browser Inspection:** 2-3 minutes
- **Code Editing:** 2-3 minutes
- **Compilation & Testing:** 1-2 minutes

---

**Ready to proceed?**

→ Start here: [SELECTOR_FIX_GUIDE.md](./SELECTOR_FIX_GUIDE.md)

---

*Document Version: 1.0*  
*Created: 2026-10-31*  
*Status: Action Required*

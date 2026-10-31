# CSS Selector Inspection Guide

Follow these steps to find and document the current CSS selectors on MakeMyTrip website.

## Step 1: Inspect Flight Search Elements

### Navigate to MakeMyTrip Flights
1. Open: https://www.makemytrip.com/flights
2. Wait for page to fully load

### Find Flight Type Selector (ONE WAY / ROUND TRIP)
1. Press `F12` to open Developer Tools
2. Click the **Inspector/Elements** tab
3. Click the **Pick Element** icon (cursor in box) at top-left of DevTools
4. Click on the "One Way" radio button or text on the MakeMyTrip page
5. The DevTools will highlight the HTML element

**What to capture:**
- Look for `id=` attribute (easiest to use)
- Look for `data-*` attributes (like `data-cy`, `data-testid`)
- Look for `class=` attribute
- Look for unique parent elements with IDs/data attributes

**Expected current structure might be:**
```
<input id="oneWayTrip" ... />
OR
<radio data-cy="oneWayTrip" ... />
OR
<span class="oneWayOption" ... />
```

**Current code is looking for:**
```xpath
//span[normalize-space()='Flights from Hyderabad to Bengaluru']
```

---

### Find Departure City Input (FROM)
1. In DevTools, use **Pick Element** again
2. Click on the departure city input field (shows "Hyderabad" or similar)
3. Inspect the element

**Current code is looking for:**
```xpath
//*[contains(@placeholder, 'From')]
//*[@placeholder='Where do you want to fly from?']
```

---

### Find Destination City Input (TO)
1. In DevTools, use **Pick Element** again
2. Click on the destination city input field
3. Inspect the element

**Current code is looking for:**
```xpath
//*[contains(@placeholder, 'To')]
//*[@placeholder='Where do you want to fly to?']
```

---

### Find Search Button
1. In DevTools, use **Pick Element** again
2. Click on the "Search" or "Find Flights" button
3. Inspect the element

**Current code is looking for:**
```xpath
//button[contains(normalize-space(), 'Search')]
//a[contains(normalize-space(), 'Search')]
```

---

## Step 2: Inspect Hotel Search Elements

### Navigate to MakeMyTrip Hotels
1. Open: https://www.makemytrip.com/hotels
2. Wait for page to fully load

### Find Hotel Location Input
1. Press `F12` to open Developer Tools
2. Click the **Pick Element** icon
3. Click on the hotel location/city input field
4. The DevTools will highlight the HTML

**Current code is looking for:**
```xpath
input[id='city']
input[placeholder='Where do you want to stay?']
//*[@placeholder='Where do you want to stay?']
```

---

### Find Check-in Date Input
1. In DevTools, use **Pick Element** again
2. Click on the check-in date field
3. Inspect the element

**Current code is looking for:**
```xpath
//input[@id='checkin']
//*[contains(@placeholder, 'Check-in')]
```

---

### Find Check-out Date Input
1. In DevTools, use **Pick Element** again
2. Click on the check-out date field
3. Inspect the element

**Current code is looking for:**
```xpath
//input[@id='checkout']
//*[contains(@placeholder, 'Check-out')]
```

---

### Find Guests Selection (if applicable)
1. In DevTools, use **Pick Element** again
2. Click on guests/rooms selector
3. Inspect the element

---

### Find Hotel Search Button
1. In DevTools, use **Pick Element** again
2. Click on "Search" button
3. Inspect the element

**Current code is looking for:**
```xpath
//button[contains(normalize-space(), 'Search')]
//a[contains(normalize-space(), 'Search')]
```

---

## Step 3: Document Your Findings

Once you've inspected each element, fill out the template below and share it with me.

```
==================================================
FLIGHT SEARCH SELECTORS
==================================================

One Way Selector:
ID: _______________
data-cy: _______________
class: _______________
Other: _______________
Recommended Selector: _______________

Departure City Input:
ID: _______________
placeholder: _______________
class: _______________
Other: _______________
Recommended Selector: _______________

Destination City Input:
ID: _______________
placeholder: _______________
class: _______________
Other: _______________
Recommended Selector: _______________

Search Button:
ID: _______________
text: _______________
class: _______________
Other: _______________
Recommended Selector: _______________

==================================================
HOTEL SEARCH SELECTORS
==================================================

Hotel Location Input:
ID: _______________
placeholder: _______________
class: _______________
Other: _______________
Recommended Selector: _______________

Check-in Date Input:
ID: _______________
placeholder: _______________
class: _______________
Other: _______________
Recommended Selector: _______________

Check-out Date Input:
ID: _______________
placeholder: _______________
class: _______________
Other: _______________
Recommended Selector: _______________

Hotel Search Button:
ID: _______________
text: _______________
class: _______________
Other: _______________
Recommended Selector: _______________
```

---

## Tips for Finding Selectors

### Best Selectors (in order of preference):
1. **ID attributes** - `id="someId"` → Use `By.id("someId")`
2. **data-* attributes** - `data-cy="element"` → Use `By.cssSelector("[data-cy='element']")`
3. **name attributes** - `name="field"` → Use `By.name("field")`
4. **Unique class combinations** - `class="btn btn-primary"` → Use `By.className("btn-primary")`
5. **XPath with unique parents** - Only if above don't work

### How to Format Selectors:
- **CSS Selector**: `input[placeholder='City']` or `[data-cy='oneWayTrip']`
- **XPath**: `//input[@placeholder='City']` or `//span[text()='Search']`
- **ID**: Just the ID value, will be converted to `By.id()`

### Common Attribute Types to Look For:
```
<input id="cityInput" />              → id="cityInput"
<input data-cy="flight-search" />     → data-cy="flight-search"
<input placeholder="Enter city" />    → placeholder="Enter city"
<button class="search-btn" />         → class="search-btn"
<span aria-label="Search" />          → aria-label="Search"
```

Once you provide these selectors, I'll update the Java code automatically!

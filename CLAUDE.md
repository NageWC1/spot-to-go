# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

---

## Project Overview

**App Name:** Spot To Go
**Type:** Android application (academic/learning project)
**Goal:** Allow users to discover nearby restaurants on a Google Map, tap a restaurant marker to view its details, and watch real-life preview videos (YouTube/TikTok) linked to that restaurant.

This is a **learning-focused academic project**, not a production app. Keep solutions simple, minimal, and demonstratable. Every technical decision should be justifiable in an academic report.

---

## Project Status

The Android app does not yet exist — the repository currently holds only planning and design documentation. The next step is to scaffold the Android project and implement features incrementally.

---

## Planned Tech Stack

| Layer | Choice | Reason |
|---|---|---|
| Language | Java (primary) or Kotlin | Project brief mentions Java; Kotlin is acceptable |
| Maps | Google Maps SDK for Android | Required by project description |
| Location | FusedLocationProviderClient (Google Play Services) | Best-practice, battery-efficient location API |
| Restaurant Data | Google Places API (Nearby Search) | No custom dataset needed — Places API returns real nearby restaurants with names, ratings, and coordinates |
| Video | YouTube Android Player API or WebView with YouTube/TikTok URL | Embed or redirect to videos linked per restaurant |
| Auth (optional) | Firebase Authentication | Simple login/registration if required |
| Storage (optional) | Firebase Firestore or local SQLite | Store video links mapped to Place IDs |

---

## The Dataset Question (Key Clarification)

**No custom dataset is needed.** The Google Places API (Nearby Search) returns live, real restaurant data based on the user's GPS coordinates. Each restaurant has a `place_id` which can be used as a stable key.

**Video linking strategy (simple approach):**
- Maintain a small hardcoded or Firestore-stored map of `place_id → YouTube/TikTok URL`
- For the academic demo, seed a few well-known local restaurants with real video URLs
- This avoids any ML/scraping complexity while still demonstrating the full feature

---

## The AI/Model Question (Key Clarification)

The "Agentic AI Search Bar" described in the prototype is a **UI concept**, not a strict requirement for the basic implementation.

**Recommended simple approach:**
- Implement the search bar as a filtered text search over Places API results (filter by name/type)
- In the dissertation, frame this as a foundation that *could* be extended with an LLM (e.g., Gemini API) for natural language queries
- Do not integrate a real LLM unless the project brief explicitly requires it — a keyword filter satisfies the academic requirement

---

## App Screen Architecture

```
LoginActivity / RegisterActivity
        ↓
MainActivity (Home + Search Bar)
        ↓
MapActivity (Google Map + Restaurant Markers)
        ↓
RestaurantDetailActivity (Info: name, rating, distance, buttons)
        ↓              ↓
VideoActivity     DirectionsActivity (Google Maps intent)
```

Each screen is a separate Activity. No complex navigation component is needed for this scope.

---

## Build Commands (once Android project is scaffolded)

```bash
# Build the project
./gradlew assembleDebug

# Run all tests
./gradlew test

# Run a single test class
./gradlew test --tests "com.example.spottogo.ExampleUnitTest"

# Install on connected device/emulator
./gradlew installDebug

# Clean build
./gradlew clean assembleDebug
```

API keys go in `local.properties` (never committed):
```
MAPS_API_KEY=your_key_here
PLACES_API_KEY=your_key_here
```

---

## TODO List

### Phase 1 — Project Setup
- [ ] Create Android project in Android Studio (package: `com.example.spottogo`, min SDK 24)
- [ ] Add dependencies: Google Maps SDK, Places SDK, FusedLocation, Glide (image loading)
- [ ] Configure `local.properties` for API keys; reference them in `AndroidManifest.xml` via `${MAPS_API_KEY}`
- [ ] Set up Git `.gitignore` for Android (exclude `local.properties`, `*.keystore`, `build/`)

### Phase 2 — Core Map Feature
- [ ] `MapActivity`: load Google Map centered on user's current GPS location
- [ ] Call Places API Nearby Search (type: restaurant, radius: 1500m) and place markers on the map
- [ ] Each marker stores its `place_id` as a tag for later lookup

### Phase 3 — Restaurant Detail
- [ ] `RestaurantDetailActivity`: display name, rating, distance, short description
- [ ] Fetch photo from Places API and display with Glide
- [ ] Add "Watch Video" button and "Get Directions" button

### Phase 4 — Video Integration
- [ ] Create a `video_links` map (Firestore collection or hardcoded `HashMap<String, String>`) mapping `place_id` to a YouTube URL
- [ ] `VideoActivity`: open video URL via Intent (YouTube app or browser) or embed via `WebView`

### Phase 5 — Directions
- [ ] Launch Google Maps navigation via an implicit Intent (`geo:` URI or `https://maps.google.com/...?daddr=`)

### Phase 6 — Search Bar
- [ ] `MainActivity`: search bar that filters Places API results by keyword
- [ ] Pass search query as `keyword` parameter in the Nearby Search API call

### Phase 7 — Auth (if required)
- [ ] `LoginActivity` / `RegisterActivity` using Firebase Auth (email/password)
- [ ] Gate `MapActivity` behind a logged-in check

### Phase 8 — Polish & Academic Report
- [ ] Add loading indicators (ProgressBar) for all async calls
- [ ] Handle permission denied for location gracefully
- [ ] Write dissertation proposal sections based on actual implementation

---

## Coding Style (from `resources/my Coding style.md`)

- **Design before coding** — understand the full structure before writing any class
- **Descriptive naming**: `fetchNearbyRestaurants()` not `getData()`; `isLocationPermissionGranted` not `flag`
- **One method = one responsibility** — split large methods; no "handleEverything" functions
- **Comments explain WHY**, not what — add a comment when the reasoning is non-obvious
- **Sections separated** by clear spacing and logical grouping: constants → fields → lifecycle → private helpers
- **Error handling is explicit** — never silently swallow exceptions; log and show user-facing feedback

---

## Writing Style (from `resources/my writing style.md`)

When writing report sections or documentation:
- Follow the pattern: **Objective → Constraint → Structure → Explanation → Outcome**
- Formal but readable; avoid generic AI phrasing
- Explain the **why** behind every decision, not just what was done
- 70% simple language, 30% precise technical vocabulary

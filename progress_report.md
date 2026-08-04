# Project Progress Report
## Spot To Go — Android Dissertation Project
**Date:** 4 August 2026 (originally 4 June 2026, updated each session — see dated entries below)
**Current Phase:** Android Development — core screens, navigation, Firebase Auth, and Gemini AI search complete; direction decided to move restaurant data (Places API), reviews, and video (YouTube Data API) to fully dynamic sourcing — paused pending resolution of Gemini API student access

---

## Overall Progress

| Phase | Status |
|---|---|
| Project Understanding | DONE |
| Proposal Planning | DONE |
| Proposal Writing | DONE |
| Proof Reading & Validation | DONE |
| Android Development | IN PROGRESS |

---

## What Has Been Completed

### 1. Project Understanding
- Read and analysed all three source documents:
  - `project discription.md` — core app idea and requirements
  - `proposal requirement brief.md` — academic deliverable structure and marking criteria
  - `prototype explanation.md` — UI/UX design and screen-by-screen breakdown
- Identified the two key challenges early: dataset selection and model necessity
- **Resolution:** No custom dataset needed — Google Places API provides live restaurant data. No ML model needed — keyword filtering satisfies the search requirement at this academic scope.

### 2. Project Planning (CLAUDE.md)
- Created `CLAUDE.md` as the master reference document for the project
- Documents: tech stack decisions, screen architecture, dataset approach, video linking strategy, build commands, and full TODO list (Phases 1–8)
- This file acts as the single source of truth for all future development decisions

### 3. Proposal Structure Planning
- Defined section-by-section word count allocation targeting exactly 3,000 words
- Confirmed format: **LaTeX** (local, using MiKTeX + VS Code + LaTeX Workshop)
- Resolved bibliography approach: 8 academic sources in `references.bib` using BibTeX/APA format

### 4. Proposal Document Written
- **File:** `proposal.tex`
- **References file:** `references.bib`
- Sections completed:

| # | Section | Target Words | Status |
|---|---|---|---|
| 1 | Background & Project Description | 350 | Written |
| 2 | Aim and Objectives | 280 | Written |
| 3 | Critical Review of Related Literature | 750 | Written |
| 4 | Methodologies and Methods | 750 | Written |
| 5 | Risks and Ethical Issues | 320 | Written |
| 6 | Work Plan + Gantt Chart | 250 | Written |
| 7 | Conclusion | 150 | Written |
| — | Headings & transitions | ~150 | Written |
| | **Total** | **~3,000** | |

- Gantt chart included as a figure using the `pgfgantt` LaTeX package
- 8 references cited throughout the document across all four literature areas
- `prototype.jpeg` added as a figure in Section 4.3 with a 55-word caption

### 5. Proposal Improvements
- Added Level 1 DFD (TikZ diagram) as Section 4.4
- Added Section 4.8 — Usability Testing (heuristic inspection + SUS user study)
- Improved Literature Review (§3.3 video persuasion reasoning; §3.4 Java vs Kotlin justification)
- Updated Gantt Chart to 17 weeks (April–August), WP7 added for Report & Presentation
- Auth clarification added (§4.3 demo-only note; §5.2 contradiction fixed)
- Word count reduced from 3,572 → ~2,996

### 6. Android Development — Started
- **Android Studio** installed (Quail 1)
- **Project scaffolded:** package `com.example.spottogo`, min SDK 24, Kotlin + Jetpack Compose
- **API keys configured:** Google Maps API key stored in `local.properties` (not committed)
- **Google Cloud Console:** Maps SDK for Android and Places API enabled

#### Files Created / Modified

| File | Description |
|---|---|
| `gradle/libs.versions.toml` | Added maps-compose, play-services-maps, play-services-location, accompanist-permissions, navigation-compose dependencies |
| `app/build.gradle.kts` | Added `manifestPlaceholders` for API key injection; added all new dependencies |
| `app/src/main/AndroidManifest.xml` | Added INTERNET, ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION permissions; Maps API key meta-data |
| `data/Restaurant.kt` | `Restaurant` data class + `RestaurantRepository` with 5 hardcoded seed restaurants (offsets from GPS location) |
| `ui/login/LoginScreen.kt` | Login screen — email/password fields, Login button, Guest button |
| `ui/map/MapScreen.kt` | Google Map with GPS centering, 5 restaurant markers, search bar overlay, location permission handling |
| `ui/detail/RestaurantDetailScreen.kt` | Detail screen — cuisine, address, rating, distance, Watch Video button, Get Directions button |
| `MainActivity.kt` | Navigation host wiring Login → Map → Detail with back stack management |

#### Features Working
- Login screen UI
- Google Map loads and centers on user's GPS location
- 5 restaurant markers placed relative to user's location
- Search bar filters markers by name or cuisine type
- Tap marker → navigate to Restaurant Detail screen
- Restaurant Detail shows: name, cuisine type, address, star rating, distance
- "Watch Video" button opens YouTube URL in browser/YouTube app
- "Get Directions" button launches Google Maps navigation to restaurant

---

## Android Project Location

> **Note:** The Android project is saved locally on the development machine.
> Path: `<LOCAL_ANDROID_PROJECT_PATH>`
> The planning and documentation files live separately at `<LOCAL_DOCS_PATH>`.
> API key is in `local.properties` — never committed to version control.

---

## Files Created (All Sessions)

| File | Description |
|---|---|
| `CLAUDE.md` | Project master reference — architecture, plan, TODO list |
| `proposal.tex` | Full dissertation proposal in LaTeX |
| `references.bib` | BibTeX file with 8 academic sources |
| `progress_report.md` | This file |

---

## What Remains

| Phase | Task | Status |
|---|---|---|
| Phase 1 | Scaffold Android project | DONE |
| Phase 2 | Google Maps + GPS location | DONE |
| Phase 3 | Restaurant markers (seeded data) | DONE |
| Phase 4 | Restaurant detail + video + directions | DONE |
| Phase 5 | Gemini API — Agentic AI Search Bar | DONE — natural language query → structured filters, tested on device |
| Phase 6 | Live Places API integration — now scoped as full dynamic pipeline (Gemini → Places search → real details/reviews → YouTube Data API video) | **PAUSED** — direction agreed and documented above, blocked on resolving Gemini API student access before implementation starts |
| Phase 7 | Firebase Auth (login/register) | DONE — tested on physical device, map gated behind login |
| Phase 8 | UI polish, loading indicators, error handling | IN PROGRESS — 11 screens implemented incl. bottom nav, password visibility, loading spinners |
| Phase 9 | Final report writing | IN PROGRESS — draft submitted for supervisor feedback |

---

## Session — 3 June 2026

### Code Review & Build Fix
- Reviewed all implementation files: `MainActivity.kt`, `Restaurant.kt`, `LoginScreen.kt`, `MapScreen.kt`, `RestaurantDetailScreen.kt`, `AndroidManifest.xml`, `app/build.gradle.kts`, `libs.versions.toml`
- All Kotlin source files confirmed correct — logic, navigation, and UI implementation are sound
- **Bug fixed:** `gradle/libs.versions.toml` was missing the `kotlin-android` plugin entry (`org.jetbrains.kotlin.android`). Added it — this was preventing Gradle sync from fully resolving the Kotlin Android compiler
- Gradle sync completed successfully after fix

### Emulator / Run Environment
- Run button was disabled due to Gradle sync issue (now resolved) and emulator not starting
- **Root cause identified:** CPU virtualization (`VT-x` / `AMD-V`) is **disabled in BIOS** — confirmed via PowerShell (`HyperVRequirementVirtualizationFirmwareEnabled = False`)
- Hyper-V and Windows Hypervisor Platform were already off — BIOS is the only remaining blocker
- **Fix (pending):** Enter BIOS → enable Intel VT-x or AMD-V → save → restart
- After restart: re-run AEHD silent installer from `Sdk\extras\google\Android_Emulator_Hypervisor_Driver\silent_install.bat` as Administrator if needed
- AVD already created in Android Studio — emulator should launch once virtualization is enabled

---

## Session — 3 June 2026 (Part 2)

### Material Icons Dependency Fix
- **Problem:** `RestaurantDetailScreen.kt` had unresolved import errors for `Icons`, `Icons.AutoMirrored.Filled.ArrowBack`, `Icons.Default.LocationOn`, `Icons.Default.Star`
- **Root cause:** `material-icons-extended` was never declared as a dependency — only `material3` was present
- **Fix applied:**
  - `gradle/libs.versions.toml` — added entry: `androidx-compose-material-icons-extended = { group = "androidx.compose.material", name = "material-icons-extended" }` (no version needed; managed by Compose BOM)
  - `app/build.gradle.kts` — added: `implementation(libs.androidx.compose.material.icons.extended)`
- Gradle sync run after fix — all icon imports resolved

### App Confirmed Running on Emulator
- Virtualization (VT-x) was successfully enabled in BIOS (from previous session fix)
- App launched and all three screens verified visually:
  - Login screen — renders correctly with Email, Password, Login button, Continue as Guest
  - Map screen — Google Map loads, 5 markers visible, search bar functional
  - Restaurant Detail screen — cuisine, address, rating, distance, Watch Video and Get Directions buttons present
- Navigation flow Login → Map → Detail (and back) confirmed working

### UI Review & Planned Improvements
- Current UI is functional but not final — the following improvements are planned:
  - **Restaurant detail page** needs more information and better layout
  - **Bottom navigation bar** to be added with four tabs: Home, Map (current search page), Privacy, Contact Us
  - Home and utility tabs (Privacy, Contact Us) are lower priority — map, video, and directions functional verification comes first
- Decision: focus next session on verifying video preview and directions actually open correctly before any UI polish work

### Progress Presentation Created
- **File:** `project progress files/Spot_To_Go_Progress.pptx`
- 10-slide PowerPoint with speaker notes covering:
  - Slide 1: Title
  - Slide 2: Project overview and tech stack
  - Slide 3: Google Cloud Console — API setup steps
  - Slide 4: Android Studio setup — dependencies, Gradle, plugin bug fix
  - Slide 5: Virtual device setup — VT-x BIOS problem and fix (red/green comparison boxes)
  - Slide 6: Project file structure (with Android Studio screenshot embedded)
  - Slide 7: Login screen (with screenshot)
  - Slide 8: Map & Search screen (with screenshot)
  - Slide 9: Restaurant Detail screen (with screenshot)
  - Slide 10: Phase roadmap — 4 done, 5 remaining
- All 4 app screenshots embedded in relevant slides

---

## Session — 4 June 2026

### Agentic AI Search Bar — Decision & Planning

- **Gap identified:** The prototype document (`prototype explanation.md`) explicitly defines the search bar as an **Agentic AI powered feature** — a key innovation of the app. The current keyword filter implementation does not satisfy this requirement.
- **Decision:** Integrate the **Google Gemini API** into the search bar to add genuine natural language understanding.
- **How it will work:**
  1. User types a natural language query — e.g. `"Best cheap food near me"` or `"Quiet café for studying"`
  2. Query is sent to Gemini API with a structured system prompt
  3. Gemini returns extracted intent as JSON: `{ cuisine_type, price_range, vibe }`
  4. App uses these attributes to filter and rank Places API results on the map
- **Why this is agentic:** The AI understands the user's *goal*, not just exact keywords — then acts on it by selecting relevant results. This matches the definition of agentic behaviour described in the prototype.
- **Implementation scope:** Gemini API call is a single async function in `MapScreen.kt`; JSON response drives the existing filter logic. No new screen needed.

### Progress Presentation Updated
- **File:** `project progress files/Spot_To_Go_Progress.pptx`
- Slide 8 (Map & Search) updated:
  - Search bar bullet relabelled: *"Search bar — Agentic AI powered (Key Innovation)"*
  - Added 3 new bullet points: current state (keyword filter), planned Gemini API integration, and natural language examples
  - Speaker note fully rewritten to cover current keyword filter behaviour AND the Gemini AI upgrade plan with talking points for the presentation

---

## Session — 20 July 2026

### Git Repository Sync
- Local branch was one commit behind `origin/main`; three pending local changes (`.idea/vcs.xml`, presentation deck, `google-services.json`) were confirmed byte-identical to what the remote commit (`1ee4271` — screenshot swap fix + Firebase config) already contained
- Stashed the duplicate local changes, fast-forwarded to `origin/main`, verified the stash held nothing new, then dropped it
- Repository confirmed fully in sync with no risk of losing work

### UI Implementation Audit
- **Trigger:** concern that the "new" build screens and login logic were missing from the Android app, since running the app appeared to show an older version
- Cross-checked all 11 screens under `ui/` against the latest design screenshots in `final report/images` (splash, login, register, home, map, restaurant detail, video, TikTok, directions, contact, privacy)
- **Finding:** every screen was already implemented and matched its screenshot; the login gate (`AuthRepository.isLoggedIn` check in `MainActivity.kt`) was also confirmed wired correctly
- **Actual root cause of the "old version" confusion (identified after further testing):** the developer's phone was in dark mode while the client's device is in light mode — `SpotToGoTheme` switches automatically via `isSystemInDarkTheme()`, so the two looked different but both were the current build. A stale installed APK on the emulator was a secondary contributor, resolved by a full `clean installDebug`.

### Build Environment Fix
- Terminal builds failed with `JAVA_HOME is not set`; resolved by pointing `JAVA_HOME` at Android Studio's bundled JDK (`C:\Program Files\Android\Android Studio\jbr`) rather than installing a separate JDK
- Set permanently via user environment variable so future terminal builds work without reconfiguration
- Confirmed `.\gradlew clean installDebug` succeeds and installs onto the `Medium_Phone` emulator

### Deprecation Warning Cleanup
- Fixed 3 `compileDebugKotlin` deprecation warnings by switching to `Icons.AutoMirrored.Filled` variants:
  - `ExitToApp` in `ui/home/HomeScreen.kt` and `ui/map/MapScreen.kt`
  - `DirectionsWalk` in `ui/directions/DirectionsScreen.kt`
- Rebuilt to confirm a clean compiler output with no warnings
- Committed and pushed (`4412088`)

### Client Handoff Preparation
- Built a standalone debug APK (`app/build/outputs/apk/debug/app-debug.apk`, ~20 MB) — the recommended way to share the app, since it removes every environment difference (SDK path, JDK version, gradle cache) between machines
- Also prepared a clean copy of the source project (`SpotToGo_source_for_client.zip`) for cases where the client wants to open the project in their own Android Studio:
  - Excluded `.gradle`, `build/`, `.kotlin`, `.cxx`, `captures`, `.externalNativeBuild`, and IDE cache/user-state files
  - Kept `MAPS_API_KEY` in `local.properties` (required to build) but removed the hardcoded `sdk.dir` path, since Android Studio auto-fills this with the correct path on whichever machine opens the project

---

## Session — 4 August 2026

### Gemini API — Agentic AI Search Bar Implemented

- **Goal:** Replace the plain keyword filter with the Gemini-powered natural language search described in the prototype (see Session — 4 June 2026 planning notes)
- **`Restaurant.kt`:** added `priceRange` ("budget" / "mid-range" / "premium") and `vibeTags` (e.g. `"romantic"`, `"quiet"`, `"family-friendly"`) to each seed restaurant, giving the AI attributes to filter against beyond name/cuisine
- **`GeminiSearchService.kt` (new file):** sends the user's raw query to the Gemini API (`gemini-2.0-flash`, `generateContent` endpoint) with a prompt instructing it to return only a JSON object `{ cuisine, priceRange, vibe }`; parses the response into a `SearchIntent` data class; wrapped in `Result` (same pattern as `AuthRepository`) so failures are explicit rather than silently swallowed
- **`MapScreen.kt`:** search box now debounces input by 600ms before calling Gemini (avoids firing a request per keystroke); while the AI call is in flight, or if it fails, the screen instantly falls back to the existing substring keyword filter over name/cuisine — the feature degrades gracefully rather than blocking search; added a small `CircularProgressIndicator` in the search field's trailing icon while a query is being interpreted
- **Build wiring:** `GEMINI_API_KEY` added to `local.properties` (gitignored, never committed) and exposed to code via `buildConfigField` in `app/build.gradle.kts`, mirroring how `MAPS_API_KEY` is already injected — required enabling `buildFeatures.buildConfig = true`
- **No new dependencies added:** the HTTP call uses `java.net.HttpURLConnection` and JSON parsing uses `org.json` (both built into the Android SDK), keeping the dependency footprint minimal per the project's "simple, minimal, demonstrable" guidance

### Verified on Device

- Built and installed on the `Medium_Phone` emulator (`gradlew installDebug`)
- Typed `"quiet romantic place"` into the map search bar: markers filtered from 5 down to 2 (Bella Italia, Sushi World) — both are the only seed restaurants tagged `romantic` and `quiet`. Neither word appears in any restaurant's name or cuisine, which confirms the result came from Gemini's structured intent rather than the keyword-fallback path
- Checked `logcat` during the run — no crashes, no request errors
- Cleared the search box and confirmed all 5 markers return, verifying the reset/empty-query path

### Follow-up Verification — Marker → Detail → Video/Directions Flow

- **Goal:** confirm that tapping a marker from an AI-filtered search result still leads correctly into the existing Detail → Video / Directions flow (this predates the Gemini work but had not been re-tested since the search changes)
- Tapped a marker on the map → correctly opened `RestaurantDetailScreen` with the right restaurant's data
- Tapped **Watch Video Preview** → correctly navigated to `VideoScreen` and launched YouTube with the stored video ID
- Tapped **Get Directions** → correctly opened the in-app `DirectionsScreen` (origin/destination card, travel-mode chips, ETA, embedded mini-map)
- Tapped **START** on the Directions screen → confirmed via `adb shell dumpsys activity activities` that the foreground activity switched to `com.google.android.apps.maps/com.google.android.maps.MapsActivity`, i.e. the real Google Maps app opened with turn-by-turn driving directions between the two coordinates
- **Conclusion:** the full navigation chain (AI search → marker → detail → video/directions) works end-to-end; no regressions from the Gemini search changes

### Two Issues Found During Verification

1. **Gemini API key hitting quota errors.** Testing the same query multiple times in a row eventually returned an empty result set instead of a filtered one. Direct `curl` testing of the key against the Gemini endpoint confirmed a `429 RESOURCE_EXHAUSTED` response with `limit: 0` on the free tier for this key's Google Cloud project. This is a project/billing configuration issue, not an app bug — the app's fallback-to-keyword-search behaviour worked exactly as designed when the AI call failed, it just isn't a satisfying demo when it happens. **Action needed:** either enable quota/billing on the existing key's project, or generate a fresh key from a project that has free-tier quota available.
2. **Seed video URLs are placeholders.** "Watch Video Preview" correctly opens YouTube with the stored `videoUrl`, but the hardcoded video IDs in `RestaurantRepository` (e.g. `Oo6HXisGLoM` for The Spice Garden) are not real/available videos, so YouTube reports "This video is unavailable." The launch mechanism itself works correctly — this is a content/data issue only. **Action needed:** replace the 5 placeholder video IDs with real YouTube video links before the next demo.

### Placeholder Video IDs Replaced With Real Videos

- **Clarified scope first:** the 5 restaurants are fictional seed data (Places API integration is Phase 6, not yet built), so no real video of e.g. "The Spice Garden" specifically can exist — any video is necessarily a generic, cuisine-matched review. Confirmed with the project owner that the fix should stay within the existing hardcoded `place_id -> video URL` architecture (per the original CLAUDE.md Phase 4 plan) rather than building a dynamic YouTube-search feature, since the seed restaurants aren't real businesses regardless of how the video is sourced
- Searched for real, cuisine-matched restaurant/food review videos and verified each candidate is live and embeddable via YouTube's oEmbed endpoint (`GET /oembed?url=...&format=json`) before using it — avoids repeating the same "unavailable" mistake with a different fabricated ID
- Updated all 5 seed restaurants in `Restaurant.kt` with real, verified video IDs:
  - The Spice Garden (Indian) → "I Review The World's Best Indian Restaurant" by Gary Eats
  - Noodle House (Chinese) → "Chinese Street Food Tour in Guilin, China | ENTER NOODLE HEAVEN" by The Food Ranger
  - Bella Italia (Italian) → "This Italian Restaurant Has the BEST Sauce! | Bella Italia Ristorante Review" by the altem life — a direct name match
  - Burger Republic (American) → "I Review AMERICA'S BEST BURGER" by Gary Eats
  - Sushi World (Japanese) → "A Day In The Life Of A Sushi Master" by Tasty
- Added a `videoAuthor` field to `Restaurant` and updated `VideoScreen.kt` to display the real channel name, since the screen previously hardcoded "by Foodie Explorer" for every restaurant regardless of the actual video — that would have been actively misleading now that real videos play
- **Verified on device:** rebuilt, reinstalled, tapped through The Spice Garden → Watch Video Preview → Watch on YouTube — the real YouTube app opened and began playing a pre-roll ad before the actual video, confirming the video is live (a broken/unavailable video would show an error instantly rather than loading an ad)

### Direction Decided — Move to Fully Dynamic Restaurant Data (Planning Only, Not Yet Built)

- **Decision:** the project's direction going forward is to replace the 5 hardcoded seed restaurants with real restaurant data fetched dynamically at search time, rather than continuing to expand the static demo dataset. Concretely:
  - **Discovery:** Gemini parses the typed query into structured filters (cuisine, price, keyword/vibe terms), and those filters drive a real **Google Places Text/Nearby Search** call — this replaces `RestaurantRepository` as the source of restaurant results
  - **Details:** location, address, rating, and price all come directly from the Places API response for whichever restaurant is tapped — no hardcoded data
  - **Reviews:** Google **Place Details** returns up to 5 real user reviews per place — this would be new content the app doesn't currently show at all
  - **Directions:** already works with any real lat/lng, so this needs no changes once restaurants are real
  - **YouTube video:** can become genuinely dynamic via the **YouTube Data API** (`search.list`), searching `"<restaurant name> review"` and using a real top result, instead of a hand-picked hardcoded link
- **Known constraints flagged during discussion (not yet resolved with the project owner):**
  - **"Vibe" (quiet, romantic, etc.) has no structured field in the Places API.** The realistic option is folding vibe words into the Places text query itself and relying on Google's own fuzzy ranking — there's no guaranteed structured filter like the current hardcoded `vibeTags` gives us. Alternative is dropping vibe filtering entirely for real restaurants. **Not yet decided.**
  - **TikTok cannot be sourced dynamically through any Google API.** TikTok isn't a Google product and its public API doesn't support open search by business name without a restricted business-partner agreement — there is no "get it through Google" path for TikTok. It would need to stay a manual/static link, or be dropped from the dynamic vision entirely. **Not yet decided.**
- **Status: paused before implementation.** Work on this direction is on hold until the Gemini API access situation is resolved (see below) — no code has been changed for this yet, this section only records the agreed direction so the next session can pick up from here.

### Paused — Waiting on Gemini API Student Access

- The existing Gemini key is hitting `429 RESOURCE_EXHAUSTED` with `limit: 0` on the free-tier daily quota (see issue logged above) — this looks like a project/billing configuration problem rather than a temporary throttle
- Project owner is a student and is pursuing student-tier API access, expecting it may resolve the quota problem
- **Expectation set during discussion:** there is no literal "unlimited" Gemini API tier, including paid tiers — higher tiers raise rate limits significantly but don't remove them. A properly-provisioned project (student or otherwise) should show a real non-zero quota number rather than `limit: 0`; if the *same* key/project is reused, waiting alone likely won't fix it — the Cloud Console quotas page would need checking directly
- All Gemini-related work (including the dynamic-search direction above) is paused until this is sorted out

---

## Key Decisions Made (For Reference)

| Decision | Choice | Reason |
|---|---|---|
| Language | Kotlin | Java not available in latest Android Studio template; Kotlin is acceptable per proposal |
| UI Framework | Jetpack Compose | Default in Android Studio Quail 1; modern, concise |
| IDE | Android Studio Quail 1 | Standard for Android development |
| Document format | LaTeX | Free, clean PDF output, proper citation handling |
| LaTeX environment | Local — MiKTeX + VS Code | No cost, works offline, no account needed |
| Restaurant data | Hardcoded seed data (5 restaurants) | Reliable for demo; Places API is the next step |
| Video linking | Static place_id → YouTube URL map | Simple, demonstrable, avoids API restrictions |
| Search bar — Phase 1 | Keyword filter over seed data | Working foundation; demonstrable without API dependency |
| Search bar — Phase 2 | Gemini API natural language understanding | Required by prototype spec; genuine agentic AI behaviour |
| Gemini networking | `HttpURLConnection` + `org.json` (no new dependency) | Both built into the Android SDK; keeps dependency footprint minimal for an academic-scope app |
| Gemini failure handling | Fall back to keyword filter on error/timeout | Search must never dead-end just because the AI call failed or the network is slow |
| Bibliography style | APA (apalike) | Standard for academic proposals |

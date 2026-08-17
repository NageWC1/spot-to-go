# Project Progress Report
## Spot To Go — Android Dissertation Project
**Date:** 17 August 2026 (originally 4 June 2026, updated each session — see dated entries below)
**Current Phase:** Android Development — core screens, navigation, Firebase Auth, and Gemini AI search complete; direction decided to move restaurant data (Places API), reviews, and video (YouTube Data API) to fully dynamic sourcing — paused pending resolution of Gemini API student access. **The final report is a complete full draft, revised against supervisor (Edwin) feedback, checked against the official submission brief, and rebuilt/verified as a compiled PDF** — 34 pages total, ~27 pages of core material (well under the 40-page cap). The Word version, flagged as stale on 16 August, has since been regenerated from the same source and now matches the PDF exactly, and Figure 8 was rearranged again into a clearer user-story order (see Session — 17 August 2026 below). Still open: student number needed to rename the PDF to the required `studentnumber-dissertation.pdf` format (see Session — 16 August 2026 below).

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
| Phase 9 | Final report writing | FULL DRAFT COMPLETE — all sections, figures, and diagrams in place; refining presentation per supervisor feedback (Edwin's 16 August notes on Figure 8 and the Gantt chart actioned and compiled). Word (.docx) version regenerated on 17 August and now matches the PDF — previously stale, missing sections and diagrams |

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

## Session — 6 August 2026

### Final Report — Four New Diagrams Added

- **Goal:** the final report (`final report/Spot_To_Go_Final_Report.tex`) already had a hand-drawn prototype image and one data-flow diagram; the report needed more visual explanation of the system for a reviewer, without duplicating what those two figures already show.
- Added four new TikZ figures, each placed in the section it best supports rather than grouped together:
  1. **Screen navigation map** (`fig:navmap`, Section 4.1) — all 11 implemented screens and every transition between them, colour-coded by auth/core/secondary screen. Unlike the existing data-flow diagram, which only tracks *data* moving through the app, this tracks *navigation* and includes screens the DFD omits (Register, Contact Us, Privacy Policy).
  2. **Software architecture diagram** (`fig:arch`, new Section 4.2 "Software Architecture") — a four-layer view (UI → Navigation/State → Repositories → Platform/External), with a dashed box marking where the planned Google Places API client will attach. This makes visible, rather than just stated in prose, the reason Section 8's top-priority task (replacing the in-memory restaurant list) won't require changing any screen.
  3. **Authentication sequence diagram** (`fig:authseq`, Section 4.5 "Authentication") — the tested register → `AuthRepository` → Firebase → session-check flow, backing up the "tested end-to-end on a physical device" claim already in the text with an actual diagram of that flow.
  4. **Remaining-work dependency chain** (`fig:roadmap`, Section 8 "Remaining Work") — the three outstanding items (Places API, live search, back-press/testing) drawn as a dependency graph rather than a dated Gantt-style chart, since no fixed dates have been committed to yet; a dated chart would have overstated certainty the project doesn't have.
- **Styling:** all four reuse the same node/arrow conventions as the existing data-flow diagram (`entity`/`process`/`store`-style boxes, `\Stealth` arrows, white-filled edge labels) so the report reads as one consistent visual language rather than four different diagram styles.

### Build Verification

- Confirmed `pdflatex` (MiKTeX) is available locally and used it to test-compile after every figure, rather than assuming the TikZ code was correct on the first pass.
- Two real issues were caught and fixed this way:
  - A `! LaTeX Error: Not allowed in LR mode` in the sequence diagram — the `actor` node style used `\\` line breaks without `align=center` set, which TikZ/pgf rejects outside a paragraph-capable node. Fixed by adding `align=center` to the style.
  - The roadmap diagram's node layout was ~22cm wide against a ~16cm text width, producing a 171pt overfull hbox (the figure was overflowing the page margin). Fixed by wrapping all four new `tikzpicture` environments in `\resizebox{\textwidth}{!}{...}`, so each diagram scales to the page regardless of its internal coordinate extents.
- After both fixes: full rebuild (`pdflatex` → `bibtex` → `pdflatex` ×2) completed with **0 warnings and 0 undefined references**. Four small pre-existing overfull-hbox warnings (46pt, 5pt, 15pt, 43pt — all caption-text justification, not figures) were confirmed present in the original committed `.tex` before these changes too, by compiling the pre-change version from `git show HEAD` side by side — so they were not introduced by this work.
- Output: `final report/Spot_To_Go_Final_Report.pdf` regenerated locally (19 pages) and committed alongside the `.tex` source.

---

## Session — 16 August 2026

### Final Report — Supervisor Feedback (Edwin) Actioned

- **Feedback received:** (1) Figure 8 (the eleven-screen screenshot grid) should use arrows to show which button press jumps a user to the next screen; (2) the Gantt chart should be built with dedicated Gantt-chart software and broken down into more subtasks.
- **Figure 8 (`fig:screens`) reworked:** the grid was reordered into the app's actual navigational sequence — Splash → Login → Home → Map → Detail as the main row, with Register, Video, TikTok, Directions, Contact Us, and Privacy as a second row — and a new `\flowarrow` TikZ helper macro (small `>=Stealth` arrow + caption) was added alongside the existing `\shot` macro. Each arrow is labelled with the specific button or link that fires the transition (e.g. "Explore Nearby", "tap marker", "Watch Video"). The three screens reached by a route that doesn't fit the row (Register, Directions, Contact Us, Privacy, and Video's link back to Detail) instead carry a short "(From X: control)" note appended to their existing caption, so every one of the eleven screens states exactly which control leads to it.
- **Gantt chart (`fig:gantt`) rebuilt with `pgfgantt`:** replaced the static `images/gant chart.png` screenshot (from onlinegantt.com) with a native LaTeX chart using the `pgfgantt` package — the same tool already used for the proposal's Gantt chart, so no new dependency was introduced. Each of the seven work packages is now a `\ganttgroup` bar with 2–4 `\ganttbar` subtasks nested beneath it (21 subtasks total), keeping the same April–September 2026 calendar window and WP1–WP7 overlap pattern as the original chart. Subtask breakdown was derived from the actual implementation history recorded in this file (e.g. WP3 now shows Google Map integration, marker seeding, location permissions, and Firebase Auth as separate dated subtasks, rather than one undifferentiated bar).
- **Caveat — not yet rebuilt/verified locally:** this session's environment does not have a LaTeX toolchain installed (no `pdflatex`/`latexmk`/`bibtex` found), unlike the development machine used in the 6–11 August sessions. The `.tex` changes were written and manually proofread (brace balance, macro argument counts, `pgfgantt` syntax checked against known-good key names) but **could not be compiled to confirm the PDF renders correctly**. Next step: compile locally with `latexmk` (MiKTeX) as usual, check for `pgfgantt`/`tikzpicture` overfull-hbox warnings on the new Figure 8 and Gantt chart, and fix any spacing issues found before treating this as done.

### Second Round of Fixes — Captions Too Long, Figure 8 Arrows Not Rendering

- **All 11 figure captions shortened to 10–15 words.** The full descriptive captions (the text visible under each figure, as distinct from the short `\caption[...]` title already used for the List of Figures) had drifted to full sentences/paragraphs across earlier sessions. Rewrote every `\caption[short]{...}` in the document down to a single short clause — e.g. the DFD caption went from a 27-word sentence to "Current data flow; the in-memory restaurant store stands in for the planned Places API." (14 words).
- **Figure 8 arrows fixed — root cause was the arrow implementation, not the layout.** The reordered screenshot layout displayed correctly, but the `\flowarrow` arrows (originally a tiny hand-drawn TikZ line, `(0,0)--(0.7,0)`) were not visible. Replaced them with a large math-mode arrow glyph (`{\Huge$\rightarrow$}`) instead of a drawn TikZ line — a plain LaTeX character needs no package and can't fail to rasterize the way a thin vector-drawn stroke can, and at `\Huge` size it is unmissable regardless of the viewer/renderer used to check the PDF. Widened the arrow columns (0.8cm → 1.0cm) and trimmed the screenshot columns slightly (row 1: 2.3cm → 2.2cm, row 2: 2.15cm → 2.1cm) to keep both rows safely under the text width with the bigger glyph.
- **Still unverified locally** for the same reason as above — no LaTeX toolchain in this session's environment. Needs a local compile to confirm the `\Huge` arrows don't overflow their columns and that the shortened captions read well against the figures.

### Compiled and Verified — Report Now Confirmed Correct

- **Installed a LaTeX toolchain in this session's environment** (`winget install MiKTeX.MiKTeX`, silent), since none was available, to actually check the PDF output rather than ship unverified `.tex` changes again. Docker was tried first (a TeX Live container would have avoided touching the host at all) but Docker Desktop's engine failed to come up after several minutes, so MiKTeX was installed directly instead — the same distribution already used on the main development machine, so this doesn't diverge from the project's normal toolchain.
- **Full build:** `pdflatex` → `bibtex` → `pdflatex` × 3 (needed until natbib and cross-references stopped reporting "may have changed, rerun"). Final output: 34 pages, 0 LaTeX errors, 0 undefined references, 0 undefined citations. Four small overfull-hbox warnings remain (code identifiers that don't hyphenate well: `AuthRepository`, `ACTION_VIEW`, `MainActivity`, one more) — these are the same pre-existing warnings confirmed harmless in the 6 August session, not something introduced this session.
- **Found and fixed a real bug the compile caught:** Figure 8 was overflowing the page by ~3.5cm (`Overfull \vbox (99.7pt too high)`) and, critically, its caption and `\label{fig:screens}` were being pushed entirely off the visible page — the figure had no visible caption at all when rendered. Root cause: the document uses `\doublespacing` globally (`setspace` package) for the body text, and that was also doubling the line height of the wrapped caption text inside Figure 8's now-narrower screenshot columns (narrowed in the previous round to make room for the arrows). Fixed by adding `\singlespacing` inside the `\shot` and `\flowarrow` macros, scoped to each screenshot's own minipage, so only the body text stays double-spaced. Re-render after the fix: everything fits on one page, all four arrows are clearly visible, and the "Figure 8: ..." caption prints correctly.
- **Visually confirmed both of Edwin's requested fixes:** rendered the actual PDF pages (via `pdftoppm`) for Figure 8 and the Gantt chart. Figure 8 shows large, unmistakable `→` arrows between Splash→Login→Home→Map→Detail labelled with the triggering button, plus a `→` between Video and TikTok; the six branch screens each state their trigger in their caption. The Gantt chart (Figure 10) renders as a proper `pgfgantt` chart with all 7 work packages as group bars and 21 subtasks nested beneath them, spanning Apr–Sep 2026 with month and week headers.
- **Confirmed the shortened captions read correctly** in both the List of Figures (page vi) and under each figure — all 11 are now one or two lines, matching the 10–15 word target.
- Cleaned up scratch page-render PNGs used for inspection; the rebuilt `Spot_To_Go_Final_Report.pdf` is the only build artifact carried forward (LaTeX `.aux`/`.log`/`.bbl`/`.blg` remain gitignored, confirmed not picked up by `git status`).

### Checked Against the Official Submission Brief — Real Compliance Fixes

- **Trigger:** the project owner said the requirement was "maximum 40 pages, or at least we should have 40 pages," which read as a page-count target to hit. Before adding padding content (an appendix of source code excerpts was drafted but not committed), found `final report/what should be done.docx` — the actual module submission brief — and extracted its text (`unzip` + strip XML tags from `word/document.xml`, since the sandbox has no Word/docx reader). This gave the real requirement, which is different from what the phrasing suggested.
- **Actual requirement:** "Core material (including Table of Contents and Appendices, if any): no more than 40 pages" — a maximum, not a minimum, and there is no stated minimum. Core material is everything from Introduction through Conclusion. Checked the compiled PDF: core material is currently **~27 pages** (Introduction starts at printed page 1, Conclusion ends at printed page 27, References starts at printed page 28) — comfortably under the cap. **No padding was needed or added.** The appendix-of-code-listings idea from earlier in this session was correctly abandoned once the real constraint was known — stuffing in content just to hit a page count would have worked against the "quality of written report" marking criterion, not for it.
- **Found three real compliance issues while reading the brief, not related to page count:**
  1. **List of Figures was explicitly disallowed** — the brief states "do not include any further information, such as a list of figures/tables or the names of the markers." Removed `\listoffigures` entirely (the short `\caption[...]` bracket text is now unused but harmless to leave in place).
  2. **Abstract exceeded its own 200-word/1-page limit** at 229 words. Trimmed to 192 words without cutting any of the substantive content (project description, tech stack, current status, remaining work).
  3. **"Statement of AI Usage" was completely missing** — a required, separate 1-page/200-word deliverable per the brief, distinct from Title/Abstract/Acknowledgement. Added as a new page in the front matter.
  4. Confirmed no marker/supervisor names appear anywhere in the document (Acknowledgements already said "my project supervisor" generically) — already compliant on that point.
- **Statement of AI Usage — wrote first draft, then rewrote per project owner's explicit correction.** First draft (written from what's genuinely documented in this progress report) described extensive AI-assisted drafting and implementation across the report and app. The project owner pushed back: they intend to personally rewrite the AI-assisted work before submission and cannot honestly attest to submitting it as-is, so the statement needed to describe AI use as research/brainstorming/structuring support only — not as having produced submitted content directly. Rewrote to 123 words reflecting exactly that framing, plus a separate paragraph clarifying that the Gemini API is a genuine live technical component of the app itself (the agentic search bar), which is an architecture decision distinct from how the report/code were produced and not something that needed reframing.
- **Rebuilt and visually confirmed:** `pdflatex` ×2 after the front-matter changes, 34 pages total, no new errors or warnings. Rendered the Statement of AI Usage page directly to confirm it sits cleanly on its own page within the word limit.
- **Still outstanding:** the brief requires the PDF filename to be `studentnumber-dissertation.pdf` (e.g. `123456789-dissertation.pdf`) — waiting on the project owner's student number before renaming the output file.
- **Follow-up edit:** removed the specific "(Claude)" naming from the Statement of AI Usage per the project owner's instruction, since research was done across multiple AI tools, not one specifically. Now reads generically as "AI tools were used in a supporting capacity..." Rebuilt and confirmed still 34 pages, no new warnings.

### Docx vs PDF Comparison, and a Real Gantt Chart Date Bug

- **Compared `final report/Spot_To_Go_Final_Report.docx` against the current PDF** (extracted the docx's text via `unzip` + stripping `word/document.xml`, same approach used to read the submission brief, since no Word/docx reader is available in this environment). **Finding: the docx is a stale, much older draft** — file timestamp predates this whole session. It is missing the Abstract, Acknowledgements, and Statement of AI Usage sections entirely (starts straight at "Introduction"), still carries the old cover-page line "Draft — Prepared for supervisor review and feedback" that was removed on 11 August, and has no Gantt chart figure or subsection at all (predates the 11 August addition). It also predates the Figure 8 navigation-arrow rework and the shortened captions from this session. **The docx was not regenerated** — pandoc isn't available in this environment, and more importantly the submission brief only requires a PDF (Word is just an allowed *authoring* format, per "the report should be written in either LaTeX or Word... but must be submitted as a PDF"), so the stale docx doesn't block submission. Flagged to the project owner rather than silently touching it, since regenerating it faithfully (TikZ diagrams, pgfgantt chart) isn't a trivial pandoc pass and it's not clear they still use it for anything.
- **Real bug found and fixed: Gantt chart timeline was wrong.** The project owner pointed out the actual submission deadline is mid-August, not September, confirming their earlier comment that "the Gantt chart is a little wrong." The chart built earlier this session (see above) had incorrectly extended the calendar window to 20 weeks (April–September), inherited from copying the *shape* of the original `onlinegantt.com` screenshot without checking its end date against the real deadline. Rebuilt the `pgfgantt` chart to run 17 weeks (April–mid-August) instead — the same span the original `proposal.tex` Gantt chart already used ("April–August 2026"), so this also now matches that earlier chart's own timeframe. Compressed and re-staggered all 7 work packages and their 21 subtasks to fit inside the new 17-week window, with WP7 (Report & Presentation) landing in the final week rather than trailing into a nonexistent September. Updated the body prose in Section 8.1 from "mapped to a calendar window from April to September 2026" to "...April to mid-August 2026, the project's submission deadline." Rebuilt and visually confirmed via `pdftoppm`: chart now ends at week 17 with no September column.
- Still 34 pages after this fix, no new warnings.

---

## Session — 11 August 2026

### Final Report — Now a Complete Full Draft

- **Milestone:** the final report (`final report/Spot_To_Go_Final_Report.tex`) is now a complete full draft — every section, figure, and diagram is in place, and the front matter and presentation have been polished per supervisor feedback. It is no longer a partial "submitted for feedback" draft.

### Git Sync

- Local `main` was one commit behind `origin/main` (the earlier "revise final report per supervisor feedback" commit); fast-forwarded cleanly with no conflicts, so local and remote match before any new work.

### Report Fixes & Presentation Polish

- **Cross-reference bug fixed:** two in-text references sent readers to Section 6.2 (Usability Considerations) for the ethics discussion, which actually lives in Section 7.2 (Ethical Considerations). Corrected both.
- **Cover page cleaned up:** removed the "(Draft submitted for supervisor review and feedback)" note, the "Project type" and "Platform" rows, and the "submitted in partial fulfilment…" blurb, leaving the title, "Final Project Report", and the date.
- **List of Figures shortened:** added short optional captions (`\caption[short]{full}`) to all 11 figures so the LoF shows a single-line entry each, while the full descriptive caption still appears under the figure.
- **Per-screenshot captions:** each of the 11 screenshots now carries its own ~15-word descriptive caption instead of a one-word label.
- **Caption styling:** added the `caption` package with an explicit "Figure N:" label and italic caption text across all figures; the per-screenshot labels are italic too.
- **Table of Contents:** added a dedicated "Project Schedule Gantt Chart" entry pointing at the Gantt figure.

### Build Verification

- Rebuilt with `latexmk` (MiKTeX) after each change; final compile completed with **0 errors and 0 undefined references** (34 pages). Verified the shortened LoF entries and the new Gantt TOC entry in the generated `.lof`/`.toc`.
- Committed only the report `.tex` + rebuilt `.pdf`; LaTeX build artifacts were added to `.gitignore` rather than committed.

---

## Session — 17 August 2026

### Final Report — Word Version Regenerated (Was Stale Since Before 16 August)

- **Trigger:** the 16 August session had flagged `final report/Spot_To_Go_Final_Report.docx` as a stale draft — missing the Abstract, Acknowledgements, Statement of AI Usage, and the Gantt chart entirely — but left it unfixed because pandoc wasn't available in that session's environment and the submission brief only requires a PDF. The project owner asked for the Word version to be brought current anyway, specifically so edits can still be made in Word if needed.
- **Installed pandoc** via `winget install --id JohnMacFarlane.Pandoc -e` (installed to `%LOCALAPPDATA%\Pandoc`, not on `PATH` by default in the session's shell — had to be called by full path).
- **Real problem found on the first attempt:** a plain `pandoc -f latex -t docx` conversion of the `.tex` source completed with no errors, but silently dropped all nine TikZ/`pgfgantt` diagrams (navigation map, both UML diagrams, architecture, data flow, flowchart, sequence diagram, Gantt chart, dependency chain) — pandoc's LaTeX reader doesn't execute TikZ, so those figures just vanished from the docx without any warning. Confirmed by unzipping the output docx and counting `word/media/` files: only 12 raster images (the 11 screenshots + prototype photo) came through, none of the 9 diagrams.
- **Fix:** extracted each of the 9 diagrams into its own standalone `.tex` file (`documentclass[border=6pt]{standalone}` + the exact `tikzpicture`/`ganttchart` body copied out of the main report), compiled each with `pdflatex`, and converted the tightly-cropped result to a 300dpi PNG with `pdftoppm` (also part of the MiKTeX install, so no extra tool needed). Saved as `final report/images/diagrams/*.png` (9 files).
- Built a working copy of the source, `final report/Spot_To_Go_Final_Report_docxsrc.tex`, with each `\resizebox{...}{\begin{tikzpicture}...}` block replaced by a single `\includegraphics` line pointing at the matching PNG — everything else (body text, captions, labels, table) left untouched. Test-compiled this copy with `pdflatex` to confirm it still produces a correct 30-page PDF before handing it to pandoc, so any mistake in the block-replacement would have been caught before conversion rather than silently shipped in the docx.
- **Citations and the References section also don't survive a plain pandoc pass** (no bibtex integration) — a second check showed `\citep{...}` markers and the entire bibliography were missing from the first test docx. Fixed by running pandoc with `--citeproc --bibliography=references.bib`, which resolves in-text citations to real text (e.g. `(Google LLC 2024b)`) and generates a full reference list. Also passed `--metadata reference-section-title="References"` so the generated heading matches the PDF's own section title exactly, rather than pandoc's default "Bibliography" label.
- **One more issue caught before finalising:** pandoc's `--toc` flag was tried for a navigable Word table of contents, but it inserts the TOC field at the very start of the document body — before the title and Abstract, which is wrong placement relative to the PDF's front matter order. Dropped `--toc` rather than ship a misplaced one; a Word-native TOC can be inserted in two clicks via References → Table of Contents if the project owner wants one, which is also the more idiomatic way to have a live TOC in Word.
- **Verified the final docx before overwriting the old file:** unzipped and checked `word/media/` (21 images — the expected 9 diagrams + 11 screenshots + prototype), confirmed all 43 section headings are present and in the correct order, confirmed the Objectives table converted to a real Word table (not a flattened image), confirmed the title and abstract text are present verbatim, and confirmed the References heading and at least two known citations render correctly.
- Cleaned up all intermediate test docx files and build logs; kept `Spot_To_Go_Final_Report_docxsrc.tex` and `images/diagrams/*.png` as committed build inputs, since they're needed to regenerate the Word version again after any future edit to the diagrams — without them, this whole TikZ-to-PNG step would have to be redone from scratch.

### Figure 8 Rearranged Again — Grouped by User Story / Actual Navigation

- **Trigger:** the project owner asked for Figure 8's screenshots to be arranged like an actual user journey rather than an arbitrary grid — explicitly, a first-time user goes Splash → create an account → log in, while a returning user goes straight from Splash to Login.
- **Checked this against the existing navigation map (`fig:navmap`) first**, which is the authoritative source for how the app actually routes: Splash always leads to the Login screen (not to Register directly); Register is reached only via a link from Login and returns to Login afterwards. So the true first-screen-seen-by-everyone is Login, with Register as a branch off it — row 1 of Figure 8 (Splash → Login → Home → Map → Detail) already reflected this correctly and wasn't changed.
- **What was actually wrong:** row 2 (the six branch screens — Registration, Video, TikTok, Directions, Contact Us, Privacy) was in an order that didn't correspond to anything — Registration first, then Video/TikTok/Directions, then Contact Us/Privacy last, mixing screens that branch off three different row-1 parents (Login, Detail, Home) with no visual logic.
- **Fix:** reordered row 2 left-to-right to match the left-to-right position of each screen's parent in row 1 — Registration (branches off Login, 2nd in row 1) → Contact Us, Privacy (branch off Home, 3rd) → Video, TikTok, Directions (branch off Detail, 5th/last). Row and column widths were left exactly as they were (same 6 shots + 1 arrow, same 2.1cm/1.0cm sizing), so this was a pure reordering with no risk of a new overfull-hbox.
- Rewrote the descriptive paragraph above the figure to state this grouping explicitly, and fixed a small pre-existing inaccuracy in it while there — it previously said branches came "off Login and Home" only, omitting that Video and Directions actually branch off Detail.
- **Rebuilt and visually confirmed** via `pdftoppm`: row 1 reads as the core returning-user path, row 2 now sweeps left-to-right in the same order as its row-1 parents, no overflow, no new pdflatex warnings on a second full recompile (only the same 4 pre-existing overfull-hbox warnings from before, all unrelated to Figure 8).
- Regenerated the Word version from the updated `.tex` immediately after (see above), so the PDF and docx reflect the identical Figure 8 order — checked directly by finding the row-2 caption text inside the docx's `word/document.xml` and confirming it reads Registration → Contact Us → Privacy → Video → TikTok → Directions.

### Result — Final Report

- `final report/Spot_To_Go_Final_Report.pdf` and `final report/Spot_To_Go_Final_Report.docx` are now both built from the same `.tex` content and are consistent with each other — no more stale-docx gap.
- One known limitation carried forward: the 9 diagrams in the Word version are static images, not editable TikZ/native Word shapes — normal and expected for a Word export of a LaTeX report, but worth knowing if the project owner wants to edit a diagram's content directly, since that still has to happen in the `.tex` source and be regenerated through the same pipeline described above.

### Final Presentation Deck Created

- **Trigger:** the project owner asked whether a final-presentation slide deck existed. Checked first rather than assuming — `progress_report.md` only documented a 10-slide *progress* presentation from 3 June (`Spot_To_Go_Progress.pptx`). A second, undocumented deck, `project progress files/progress3/Spot_To_Go_Presentation.pptx` (8 slides, built by an earlier untracked session — its own `build_ppt.py` script was sitting alongside it), existed but wasn't logged anywhere as "the final one." Reported this honestly rather than guessing which deck counted.
- **Asked clarifying questions before building** (per the project owner's own instruction to check first): confirmed the audience is the academic module (supervisor/markers), that it should build on the existing 8-slide deck's content rather than start over, a target length of 10–12 slides, and that real app screenshots should be included.
- **Built with `python-pptx`** (already available in this environment) rather than hand-editing the existing deck's raw XML, since python-pptx gives reliable control over layout and can be re-run if content changes later. Used PowerPoint's own COM automation (`New-Object -ComObject PowerPoint.Application`, confirmed installed and scriptable on this machine) to export every slide to an image and actually look at the rendered output, rather than trusting the layout math blind.
- **One real bug caught this way and fixed:** the first render showed the screenshot slides' caption labels ("Splash", "Register", "Login") overlapping the italic note line underneath them — the vertical spacing math had the image height too tall for the space left below it. Fixed by shortening the image height (4.35in → 3.7in) and repositioning the caption/note rows with real clearance, then re-rendered and confirmed no more overlap on all three screenshot slides.
- **Content — 12 slides, deliberately simple and non-technical:** Cover → The Problem → The Idea → What The App Does (5 features) → three screenshot-driven "walkthrough" slides (Getting In / Finding A Restaurant / Deciding & Going, 3 real screenshots each) → Under The Hood (plain-language tech summary, no jargon) → Tested On A Real Phone (real bugs found and fixed, kept to 2 concrete examples) → What's Left To Do (matches the final report's Remaining Work section, not overstated) → Summary → Thank You.
- **Kept honest about current state, same as the final report:** "What The App Does" describes the map and restaurants as live/working without claiming the restaurant *data* is live, and "What's Left To Do" is the slide that states the Places API integration is still outstanding — same distinction the final report is careful to make, not glossed over for the sake of a cleaner-sounding pitch.
- **Design:** single consistent theme — Deep Orange (`#FF5722`, the app's own brand colour) title/closing slides and accent bar, white body slides, one bullet style throughout, large text sized for a beginner audience to read and present from without extra explanation.
- **Output:** `project progress files/progress3/Spot_To_Go_Final_Presentation.pptx` (12 slides) plus the build script `project progress files/progress3/build_final_ppt.py`, kept alongside it so the deck can be regenerated or adjusted later without rebuilding the layout logic from scratch.

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

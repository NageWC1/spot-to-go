# Project Progress Report
## Spot To Go — Android Dissertation Project
**Date:** 4 June 2026
**Current Phase:** Android Development — Phase 1–4 Complete, Gemini AI Search Next

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
| Phase 5 | **Gemini API — Agentic AI Search Bar** | **UP NEXT** |
| Phase 6 | Live Places API integration | NOT STARTED |
| Phase 7 | Firebase Auth (login/register) | NOT STARTED |
| Phase 8 | UI polish, loading indicators, error handling | NOT STARTED |
| Phase 9 | Final report writing | NOT STARTED |

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
| Bibliography style | APA (apalike) | Standard for academic proposals |

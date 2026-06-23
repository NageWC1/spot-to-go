"""
Generates Spot_To_Go_Progress_Report.pptx using the UEA template.
Keeps the exact theme/colours/fonts; only replaces text and adds screenshots.

To regenerate the PPT after edits, run:
    python build_ppt.py
"""
import sys, os
sys.stdout.reconfigure(encoding='utf-8')

from pptx import Presentation
from pptx.util import Inches, Pt
from pptx.oxml.ns import qn
from lxml import etree

# ── Paths ─────────────────────────────────────────────────────────────────────
BASE   = os.path.dirname(os.path.abspath(__file__))
TMPL   = os.path.join(BASE, 'Progress report_UEA_template.pptx')
UI     = os.path.join(BASE, 'ui')
OUTPUT = os.path.join(BASE, 'Spot_To_Go_Progress_Report.pptx')

prs = Presentation(TMPL)

# ── Delete duplicate example slides 8-13 (keep only slides 1-7) ──────────────
NS_R = 'http://schemas.openxmlformats.org/officeDocument/2006/relationships'
id_lst = prs.slides._sldIdLst
for _ in range(len(prs.slides) - 7):          # remove from the end
    elem = id_lst[-1]
    r_id = elem.get(f'{{{NS_R}}}id')
    id_lst.remove(elem)
    try:
        prs.slides.part._rels.pop(r_id, None)
    except Exception:
        pass

# ── Helpers ───────────────────────────────────────────────────────────────────
def clear(placeholder):
    """Wipe all runs/breaks from every paragraph, leave one empty paragraph."""
    tc = placeholder.text_frame._txBody
    paras = tc.findall(qn('a:p'))
    for p in paras[1:]:
        tc.remove(p)
    first = paras[0]
    for child in list(first):
        first.remove(child)

def para(tf, text, level=0, bold=False, pt=None, first_para=False):
    """Append (or reuse the first) paragraph in text-frame tf."""
    if first_para:
        p = tf.paragraphs[0]
        p.text = text
        p.level = level
    else:
        p = tf.add_paragraph()
        p.text = text
        p.level = level
    if p.runs:
        run = p.runs[0]
        run.font.bold = bold
        if pt:
            run.font.size = Pt(pt)
    return p

def notes(slide, text):
    slide.notes_slide.notes_text_frame.text = text

def linebreak_rect(shape, line1, line2):
    """Put two lines (with a soft line-break) into a Rectangle's text frame."""
    tf = shape.text_frame
    clear(shape)
    p_elem = tf.paragraphs[0]._p
    for r in p_elem.findall(qn('a:r')):
        p_elem.remove(r)
    r1 = etree.SubElement(p_elem, qn('a:r'))
    etree.SubElement(r1, qn('a:t')).text = line1
    etree.SubElement(p_elem, qn('a:br'))
    r2 = etree.SubElement(p_elem, qn('a:r'))
    etree.SubElement(r2, qn('a:t')).text = line2

def pic(slide, fname, left, top, w, h):
    slide.shapes.add_picture(os.path.join(UI, fname),
                             Inches(left), Inches(top),
                             Inches(w), Inches(h))

# ── SLIDE 1 — Title ───────────────────────────────────────────────────────────
s = prs.slides[0]
for sh in s.shapes:
    if sh.name == 'Rectangle 2':
        linebreak_rect(sh, 'Spot To Go  —  Android App',
                           'Progress Report  |  23 June 2026')
    if sh.name == 'Rectangle 3':
        linebreak_rect(sh, 'Nagenthiran Nagarajah',
                           'University of East Anglia  |  23-Jun-2026')

notes(s,
    "Welcome. My name is Nagenthiran and this is my weekly progress report for "
    "Spot To Go — an Android app that lets users discover nearby restaurants on "
    "a Google Map, view details, and watch YouTube or TikTok preview videos. "
    "This week I completed the full UI: all 11 screens from our hand-drawn "
    "paper prototype are now implemented and connected with a live navigation graph.")

# ── SLIDE 2 — Plan of Last Meeting ────────────────────────────────────────────
s = prs.slides[1]
for sh in s.shapes:
    if sh.name == 'Title 1':
        sh.text_frame.paragraphs[0].text = 'Plan Of The Last Meeting'
    if sh.name == 'Content Placeholder 2':
        tf = sh.text_frame
        clear(sh)
        rows = [
            ("Goals agreed at the previous meeting:", 0, True,  13),
            ("Build all 11 UI screens from the hand-drawn paper prototype", 1, False, 12),
            ("Wire every screen together with a complete navigation graph", 1, False, 12),
            ("Apply a custom Deep Orange colour theme and branded launcher icon", 1, False, 12),
            ("Ensure consistent background and font styling across all screens", 1, False, 12),
            ("Commit all code and push to GitHub (NageWC1/spot-to-go)", 1, False, 12),
            ("", 0, False, 10),
            ("Planned for the next phase (discussed at last meeting):", 0, True,  13),
            ("Firebase Authentication — real registration and login", 1, False, 12),
            ("Google Places Nearby Search API — replace seed data with live restaurants", 1, False, 12),
            ("Live search bar connected to the Places API keyword parameter", 1, False, 12),
        ]
        for i, (text, lvl, bold, pt) in enumerate(rows):
            para(tf, text, lvl, bold, pt, first_para=(i == 0))

notes(s,
    "At our last meeting we agreed on five concrete goals for this week. "
    "The main goal was implementing all 11 screens visible in the paper prototype. "
    "We also agreed to apply a branded colour theme and icon so the app looks "
    "polished on a real device. "
    "Looking ahead, we had already discussed the next phase: Firebase for auth "
    "and Google Places for live restaurant data. Those form the basis of next week's tasks.")

# ── SLIDE 3 — Progress Overview ───────────────────────────────────────────────
s = prs.slides[2]
for sh in s.shapes:
    if sh.name == 'Title 1':
        sh.text_frame.paragraphs[0].text = 'Progress Overview For The Last Week'
    if sh.name == 'Content Placeholder 2':
        tf = sh.text_frame
        clear(sh)
        done = [
            "Splash Screen — 2-second auto-navigate, loading indicator",
            "Home Page — search bar, Explore Nearby button, 5-tab bottom navigation",
            "Login Page — email/password, Forgot Password, Guest mode, Register link",
            "Registration Page — full validation, Privacy Policy checkbox, error messages",
            "Map Screen — GPS centring, 5 seed restaurant markers, live search filter",
            "Restaurant Detail — name, rating, distance, cuisine type, action buttons",
            "Video Screen — YouTube placeholder player, Watch / TikTok dual buttons",
            "TikTok Link Page — branded dark preview, like/comment/share, OPEN IN TIKTOK",
            "Directions Screen — Car / Bus / Walk mode chips, live map, START button",
            "Contact Us Page — validated form, Snackbar success confirmation",
            "Privacy Policy — scrollable 5-section document",
            "Deep Orange theme (#FF5722) + gradient launcher icon  →  GitHub commit c87e960",
        ]
        for i, text in enumerate(done):
            para(tf, f'✓  {text}', 0, False, 12, first_para=(i == 0))

notes(s,
    "All goals from the last meeting were completed. "
    "The tick list covers every screen from the paper prototype. "
    "Point out the last item — the orange theme and icon are now on the real device, "
    "and everything is pushed to GitHub. "
    "The nav graph flows: Splash → Home → Login / Register → Map → Detail → Video → TikTok "
    "and Map → Detail → Directions. Contact Us and Privacy Policy are reachable from the "
    "Home bottom navigation bar.")

# ── SLIDE 4 — All 11 Screenshots ──────────────────────────────────────────────
s = prs.slides[3]
for sh in s.shapes:
    if sh.name == 'Title 1':
        sh.text_frame.paragraphs[0].text = 'Progress For The Last Week — UI Screens'
    if sh.name == 'Content Placeholder 2':
        tf = sh.text_frame
        clear(sh)
        p = tf.paragraphs[0]
        p.text = 'All 11 screens built in Jetpack Compose, matching the hand-drawn prototype.'
        if p.runs:
            p.runs[0].font.italic = True
            p.runs[0].font.size   = Pt(11)

# Image grid: row 1 (6 images), row 2 (5 images)
ROW1 = ['splash screen.jpeg', 'home page.jpeg', 'login page.jpeg',
        'registration page.jpeg', 'map page.jpeg', 'hotel review page.jpeg']
ROW2 = ['watch video page.jpeg', 'tiktok launch page.jpeg',
        'direction screen.jpeg', 'contact us page.jpeg', 'privacy page.jpeg']

W, H, GAP = 1.0, 2.05, 0.10
TOP1 = 2.2
TOP2 = TOP1 + H + 0.22

def row_left(n):
    return 0.5 + (9.0 - (n * W + (n - 1) * GAP)) / 2

for i, f in enumerate(ROW1):
    pic(s, f, row_left(len(ROW1)) + i * (W + GAP), TOP1, W, H)
for i, f in enumerate(ROW2):
    pic(s, f, row_left(len(ROW2)) + i * (W + GAP), TOP2, W, H)

notes(s,
    "Walk through the two rows of screenshots. "
    "Row 1 (left to right): Splash Screen with the orange gradient and location pin, "
    "Home Page with the search bar and Explore Nearby button, "
    "Login Page, Registration Page with the checkbox and validation, "
    "Map Screen showing live Google Map with restaurant markers, "
    "and the Restaurant Detail page. "
    "Row 2: Video Screen with the YouTube thumbnail and TikTok button, "
    "TikTok Link Page with the branded dark UI, "
    "Directions Screen with the transport mode chips and live map, "
    "Contact Us form, and the Privacy Policy. "
    "Every screen uses Material 3 with our Deep Orange theme. "
    "Navigation is handled entirely by Jetpack Compose Navigation — no Activities, "
    "just composable screens connected in a single NavHost.")

# ── SLIDE 5 — Built-in Backends ───────────────────────────────────────────────
s = prs.slides[4]
for sh in s.shapes:
    if sh.name == 'Title 1':
        sh.text_frame.paragraphs[0].text = 'Progress For The Last Week — Built-In Backends'
    if sh.name == 'Content Placeholder 2':
        tf = sh.text_frame
        clear(sh)
        rows = [
            ("The app already has 6 live backend-like systems — no server needed yet:", 0, True, 13),
            ("1. GPS  (FusedLocationProviderClient)", 1, True,  12),
            ("Real device coordinates centre the map and position all seed restaurants around the user.", 2, False, 10),
            ("2. Google Maps SDK  —  live map tiles, markers, camera animation", 1, True,  12),
            ("Fully live Google Map. Tap a marker → restaurant name and rating appear instantly.", 2, False, 10),
            ("3. RestaurantRepository  —  in-memory data layer (temporary database)", 1, True,  12),
            ("5 seed restaurants with name, cuisine, rating, distance, YouTube URL. Replaced by Places API next week.", 2, False, 10),
            ("4. Jetpack Navigation  —  back-stack and route management", 1, True,  12),
            ("All 11 screen routes managed centrally. Back button, pop-up stack, and deep links all work.", 2, False, 10),
            ("5. Android Intent System  —  external app launcher", 1, True,  12),
            ("Opens YouTube for videos, TikTok for restaurant search, Google Maps for turn-by-turn navigation.", 2, False, 10),
            ("6. Compose State  (remember / mutableStateOf)", 1, True,  12),
            ("Search queries update markers in real-time. Selected restaurant survives Map → Detail → Video.", 2, False, 10),
        ]
        for i, (text, lvl, bold, pt) in enumerate(rows):
            para(tf, text, lvl, bold, pt, first_para=(i == 0))

notes(s,
    "This slide addresses a common question: we haven't connected Firebase yet — "
    "so is there any real backend? The answer is yes, six systems are already live. "
    "\n"
    "GPS: the FusedLocationProviderClient returns the device's real coordinates. "
    "The map truly centres on where you are. "
    "\n"
    "Google Maps SDK: the map tiles are live from Google's servers. "
    "Markers, camera animation, and the my-location button are all real SDK features. "
    "\n"
    "RestaurantRepository acts as a temporary in-memory database. "
    "It will be replaced by the Places API next week. "
    "\n"
    "Jetpack Navigation manages all 11 routes — the back stack is real, "
    "pressing Back from Detail returns to Map correctly. "
    "\n"
    "The Intent system connects to real external apps: tapping Watch Video "
    "opens the actual YouTube video; GET DIRECTIONS opens Google Maps navigation. "
    "\n"
    "Finally, Compose state keeps the search query and selected restaurant "
    "alive across recompositions — the map search filter works live today.")

# ── SLIDE 6 — Actions for Next Week (Milestones) ─────────────────────────────
s = prs.slides[5]
for sh in s.shapes:
    if sh.name == 'Title 1':
        sh.text_frame.paragraphs[0].text = 'Actions for the Next Week'
    if sh.name == 'Content Placeholder 2':
        tf = sh.text_frame
        clear(sh)
        rows = [
            ("Milestones", 0, True,  14),
            ("Dissertation Proposal draft — submit to supervisor by 30 June 2026", 1, False, 12),
            ("Progress Report — continue weekly reporting cycle", 1, False, 12),
            ("Backend Phase — Firebase Auth + Places API target: 07 July 2026", 1, False, 12),
            ("", 0, False, 10),
            ("Tasks for Next Week", 0, True,  14),
            ("Task 1  —  Firebase Authentication (Registration & Login backend)", 1, False, 12),
            ("Task 2  —  Google Places Nearby Search API (real restaurant data)", 1, False, 12),
            ("Task 3  —  Live Search connected to Places API keyword parameter", 1, False, 12),
        ]
        for i, (text, lvl, bold, pt) in enumerate(rows):
            para(tf, text, lvl, bold, pt, first_para=(i == 0))

notes(s,
    "Three milestones are active this coming week. "
    "The dissertation proposal draft is due to the supervisor by 30 June. "
    "Weekly progress reporting continues. "
    "The backend implementation phase is targeted for completion by 07 July. "
    "\n"
    "The three development tasks are: Firebase Authentication, "
    "Google Places Nearby Search API integration, and live search. "
    "These three together will move the app from a UI prototype with seed data "
    "to a fully functional, data-driven application.")

# ── SLIDE 7 — Actions for Next Week (Detail) ─────────────────────────────────
s = prs.slides[6]
for sh in s.shapes:
    if sh.name == 'Title 1':
        sh.text_frame.paragraphs[0].text = 'Actions for the Next Week — Task Detail'
    if sh.name == 'Content Placeholder 2':
        tf = sh.text_frame
        clear(sh)
        rows = [
            ("Task 1 — Firebase Authentication", 0, True,  13),
            ("Add google-services.json + Firebase Auth dependency to build.gradle", 1, False, 11),
            ("Register: call createUserWithEmailAndPassword() on CREATE ACCOUNT tap", 1, False, 11),
            ("Login: call signInWithEmailAndPassword(), show inline errors on failure", 1, False, 11),
            ("Gate MapScreen — unauthenticated users redirected back to LoginScreen", 1, False, 11),
            ("Task 2 — Google Places Nearby Search API", 0, True,  13),
            ("Add Places SDK to libs.versions.toml and app/build.gradle.kts", 1, False, 11),
            ("Create PlacesRepository calling Nearby Search (type: restaurant, radius: 1500 m)", 1, False, 11),
            ("Replace RestaurantRepository seed data with live API results on MapScreen", 1, False, 11),
            ("Task 3 — Live Search Functionality", 0, True,  13),
            ("Pass MapScreen search bar text as keyword param in Places API call", 1, False, 11),
            ("Refresh markers in real-time as the user types (debounce 400 ms)", 1, False, 11),
            ("Show CircularProgressIndicator while API request is in progress", 1, False, 11),
        ]
        for i, (text, lvl, bold, pt) in enumerate(rows):
            para(tf, text, lvl, bold, pt, first_para=(i == 0))

notes(s,
    "Here is the step-by-step breakdown for next week. "
    "\n"
    "Task 1 — Firebase Auth: "
    "We add google-services.json to the app module and the Firebase Auth dependency. "
    "The Register screen calls createUserWithEmailAndPassword. "
    "The Login screen calls signInWithEmailAndPassword. "
    "Both show error messages if credentials are wrong or if there is a network issue. "
    "An auth state listener will gate the Map screen — if not logged in, "
    "the user is sent back to Login. "
    "\n"
    "Task 2 — Places API: "
    "We add the Places SDK dependency, create a new PlacesRepository class "
    "that hits the Nearby Search endpoint with the user's GPS coordinates, "
    "filtering for restaurants within 1500 metres. "
    "This replaces the current five hardcoded seed restaurants. "
    "\n"
    "Task 3 — Live Search: "
    "The search bar text will be sent as the keyword parameter in the Places call. "
    "We will add a 400ms debounce so the API is not called on every keystroke. "
    "A CircularProgressIndicator will appear while the request is loading.")

# ── Save ──────────────────────────────────────────────────────────────────────
prs.save(OUTPUT)
print(f'Done — {len(prs.slides)} slides saved to:')
print(OUTPUT)

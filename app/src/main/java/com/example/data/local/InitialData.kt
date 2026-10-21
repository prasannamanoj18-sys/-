package com.example.data.local

import com.example.data.model.ErrorCodeItem
import com.example.data.model.MaintenanceTask
import com.example.data.model.TechnicalArticle
import com.example.data.model.TechnicianProfile

object InitialData {

    fun getDefaultArticles(): List<TechnicalArticle> = listOf(
        TechnicalArticle(
            id = 1,
            category = "TIMING",
            machineType = "General",
            difficulty = "Intermediate",
            titleSinhala = "රොටරි හුක් ටයිමිං (Rotary Hook Timing)",
            titleEnglish = "Rotary Hook Timing Adjustment",
            titleTamil = "ரோட்டரி ஹூக் டைமிங் சீரமைப்பு",
            summarySinhala = "Juki DDL-8700/9000 සහ කාර්මික මැෂින් සඳහා නිවැරදි රොටරි හුක් ටයිමිං සහ ක්ලියරන්ස් සැකසීම.",
            summaryEnglish = "Precision rotary hook timing and needle clearance setup for Juki DDL series and industrial machines.",
            summaryTamil = "ஜூக்கி டிடிஎல் தொடர் மற்றும் தொழில்துறை இயந்திரங்களுக்கான துல்லியமான ரோட்டரி ஹூக் டைமிங் மற்றும் இடைவெளி.",
            contentSinhala = """1. ඉඳිකටුව පහළම මට්ටමට (BDC - Bottom Dead Center) ගෙන එන්න.
2. හෑන්ඩ් වීල් එක කරකවා ඉඳිකටුව 1.8mm සිට 2.0mm දක්වා ඉහළට එසවෙන පරිදි සකසන්න.
3. රොටරි හුක් එකේ තුඩ (Hook point) ඉඳිකටුවේ ස්කාෆ් (Scarf) ප්‍රදේශය වෙත ගෙන එන්න.
4. හුක් තුඩ ඉඳිකටු ඇසට (Needle eye) ඉහළින් 1.0mm - 1.5mm උසකින් පිහිටිය යුතුය.
5. ඉඳිකටුව සහ හුක් තුඩ අතර හිඩැස (Clearance) 0.05mm - 0.1mm (කඩදාසි කොළයක ඝනකම) විය යුතුය. ඉඳිකටුව හුක් එකේ ගෑවී නොතිබිය යුතුය.
6. හුක් ගියර් ඉස්කුරුප්පු (Hook screws) තුන තදින් සිර කරන්න.""",
            contentEnglish = """1. Rotate the handwheel towards you until the needle bar reaches its Bottom Dead Center (BDC).
2. Continue rotating handwheel until the needle bar rises by exactly 1.8mm to 2.0mm.
3. Align the rotary hook point to the center of the needle scarf.
4. Ensure the hook tip is positioned 1.0mm to 1.5mm above the upper edge of the needle eye.
5. Set the clearance between the hook point and needle scarf to 0.05mm - 0.1mm (thickness of a sheet of paper). The needle must not be deflected.
6. Tighten the 3 hook set screws securely in sequence.""",
            contentTamil = """1. ஊசி பட்டை மிகக் கீழ் நிலைக்கு (BDC) வரும் வரை கை சக்கரத்தை சுழற்றுங்கள்.
2. கை சக்கரத்தை தொடர்ந்து சுழற்றி ஊசி பட்டையை 1.8 மிமீ முதல் 2.0 மிமீ வரை மேலே உயர்த்தவும்.
3. ரோட்டரி ஹூக்கின் முனையை ஊசியின் ஸ்கார்ஃப் பகுதிக்கு நேராக சீரமைக்கவும்.
4. ஹூக் முனை ஊசி துளைக்கு மேலே 1.0 மிமீ முதல் 1.5 மிமீ உயரத்தில் இருப்பதை உறுதிப்படுத்தவும்.
5. ஹூக் முனைக்கும் ஊசிக்கும் இடையிலான இடைவெளியை (Clearance) 0.05 மிமீ - 0.1 மிமீ ஆக அமைக்கவும். ஊசி ஹூக்கில் படக்கூடாது.
6. மூன்று ஹூக் திருகுகளையும் வரிசையாக உறுதியாக இறுக்குங்கள்.""",
            keySpecsOrTiming = "Needle Rise: 1.8mm - 2.0mm | Clearance: 0.05mm - 0.10mm | Height above eye: 1.0mm - 1.5mm",
            isBookmarked = true,
            viewCount = 142
        ),
        TechnicalArticle(
            id = 2,
            category = "TROUBLESHOOTING",
            machineType = "General",
            difficulty = "Basic",
            titleSinhala = "මැහුම් නූල් කැඩීම (Thread Breakage) - හේතු සහ කාර්මික විසඳුම්",
            titleEnglish = "Upper Thread Breakage - Root Causes and Technician Solutions",
            titleTamil = "மேல் நூல் அறுபடுதல் - காரணங்கள் மற்றும் தொழில்நுட்ப தீர்வுகள்",
            summarySinhala = "නූල් කැඩීමට බලපාන ප්‍රධාන හේතු 6ක් සහ ඒවා ක්ෂණිකව නිරාකරණය කරගන්නා ආකාරය.",
            summaryEnglish = "The 6 most common root causes of needle thread breakage and factory line troubleshooting steps.",
            summaryTamil = "நூல் அறுபடுவதற்கான முக்கிய காரணங்கள் மற்றும் விரைவான தொழிற்சாலை தீர்வுகள்.",
            contentSinhala = """1. ඉඳිකටු ඇස හෝ තුඩ මොට වී හෝ සීරී තිබීම: නව ප්‍රමිතියෙන් යුත් ඉඳිකටුවක් (Organ / Schmetz) මාරු කරන්න.
2. රොටරි හුක් තුඩේ සීරීම් (Burrs on hook point): ඉතා සියුම් වැලි කඩදාසියකින් (#1200 emery paper) හුක් තුඩ මනාව ඔප දමන්න.
3. ඉහළ නූල් ආතතිය (Upper tension) අධික වීම: නූල් ආතති නට් එක බුරුල් කර නිවැරදි සමබරතාවය තහවුරු කරන්න.
4. බොබින් කේස් හිඩැස සහ නූල් මාර්ගය (Thread path): නූල් මාර්ගයේ ඇති ගයිඩ් (guides) සීරී ඇත්දැයි පරීක්ෂා කරන්න.
5. අධික වේගය නිසා ඉඳිකටුව රත් වීම (Needle heating): පොලියෙස්ටර් නූල් දියවීම වැළැක්වීමට සිලිකන් ඔයිල් කුෂන් භාවිතා කරන්න.
6. ඉඳිකටුව වැරදි දිශාවට සවි කර තිබීම: දිගු ඇළිය (Long groove) ක්‍රියාකරුගේ වම් පසට සිටින සේ සවි කරන්න.""",
            contentEnglish = """1. Needle eye damage or blunt needle: Replace with genuine industrial needle (DBx1 or DPx5) sized correctly for thread.
2. Burr or scratch on hook point: Polish the rotary hook tip with ultra-fine #1200 emery tape or diamond paste.
3. Excessive needle thread tension: Loosen the primary tension nut to achieve balanced interlocking stitch.
4. Grooves on thread guides: Inspect thread guides, take-up lever, and tension discs for grooving cuts and replace if worn.
5. Needle heat melting synthetic thread: Apply needle cooler or silicone thread lubrication reservoir for high-speed runs.
6. Incorrect needle insertion: Verify the long groove faces left (towards operator) and the scarf faces the rotary hook.""",
            contentTamil = """1. ஊசி முனை அல்லது துளை சேதமடைதல்: புதிய தரமான ஊசியை (DBx1 / DPx5) மாற்றவும்.
2. ரோட்டரி ஹூக் முனையில் கீறல்கள்: ஹூக் முனையை மெல்லிய மணர்த்தாளால் (#1200) மெருகூட்டவும்.
3. அதிக மேல் நூல் பதற்றம் (Tension): நூல் பதற்ற திருகினை தளர்த்தி சரியான சமநிலையை உறுதி செய்யவும்.
4. நூல் வழிகாட்டிகளில் பள்ளங்கள்: நூல் செல்லும் பாதையில் உள்ள வழிகாட்டிகளை ஆய்வு செய்து தேய்ந்திருந்தால் மாற்றவும்.
5. ஊசி வெப்பமடைதல்: அதிவேக தையலில் நூல் உருகாமல் தடுக்க சிலிகான் எண்ணெயைப் பயன்படுத்தவும்.
6. ஊசி தவறான திசையில் பொருத்தப்படுதல்: ஊசியின் நீண்ட பள்ளம் (Long groove) இடதுபுறம் இருப்பதை உறுதிப்படுத்தவும்.""",
            keySpecsOrTiming = "Tension: 30g - 45g (Needle thread) | Needle Groove: Faces Operator Left",
            isBookmarked = false,
            viewCount = 98
        ),
        TechnicalArticle(
            id = 3,
            category = "TIMING",
            machineType = "Overlock / Serger",
            difficulty = "Advanced",
            titleSinhala = "ඕවර්ලොක් (Overlock) ලූපර් ටයිමිං සහ ක්ලියරන්ස් සැකසීම",
            titleEnglish = "Overlock Lower & Upper Looper Timing & Clearance Setup",
            titleTamil = "ஓவர்லாக் லூப்பர் டைமிங் மற்றும் இடைவெளி சீரமைப்பு",
            summarySinhala = "Pegasus M900 සහ Siruba 747 ඕවර්ලොක් මැෂින් සඳහා Upper සහ Lower ලූපර් ටයිමිං.",
            summaryEnglish = "Complete looper timing synchronization and clearances for 3, 4, and 5-thread overlock sergers.",
            summaryTamil = "3, 4 மற்றும் 5-நூல் ஓவர்லாக் இயந்திரங்களுக்கான முழுமையான லூப்பர் டைமிங் மற்றும் இடைவெளி.",
            contentSinhala = """1. Lower Looper Timing: ඉඳිකටුව BDC මට්ටමේ සිට 2.5mm - 3.0mm ඉහළට එසවෙන විට Lower Looper තුඩ ඉඳිකටු ඇසට ඉහළින් 1.5mm උසින් ඉඳිකටු ස්කාෆ් එක පසු කළ යුතුය.
2. Looper to Needle Clearance: ලූපර් තුඩ සහ ඉඳිකටුව අතර හිඩැස 0.05mm විය යුතුය (ඉඳිකටු ආරක්ෂකය - needle guard නිවැරදිව තබාගන්න).
3. Upper Looper Timing: Lower Looper එක දකුණේ සිට වමට ගමන් කරන විට, Upper Looper එකේ ඇස පිටුපසින් Lower looper තුඩ 0.1mm හිඩැසක් සහිතව හරස් විය යුතුය.
4. ඉඳිකටු උස (Needle Height): ඉඳිකටු තහඩුවේ (needle plate) සිට ඉඳිකටු තුඩ දක්වා සම්මත උස 9.5mm සිට 10.0mm දක්වා තබාගන්න.""",
            contentEnglish = """1. Lower Looper Timing: As the needle bar rises 2.5mm to 3.0mm from BDC, the lower looper point must cross the needle centerline 1.5mm above the needle eye.
2. Looper to Needle Clearance: Adjust lower looper front clearance to 0.05mm. The movable rear needle guard must prevent needle pushback.
3. Upper and Lower Looper Crossing: As loopers cross on the left stroke, maintain 0.1mm clearance between upper looper back and lower looper tip.
4. Needle Height: From top of needle plate to needle point at top dead center (TDC) should be between 9.5mm and 10.0mm per manufacturer spec.""",
            contentTamil = """1. கீழ் லூப்பர் டைமிங்: ஊசி BDC இலிருந்து 2.5 மிமீ முதல் 3.0 மிமீ வரை உயரும் போது, கீழ் லூப்பர் முனை ஊசி துளைக்கு 1.5 மிமீ மேலே ஊசியைக் கடக்க வேண்டும்.
2. லூப்பர் மற்றும் ஊசி இடைவெளி: லூப்பர் முனைக்கும் ஊசிக்கும் இடையிலான இடைவெளியை 0.05 மிமீ ஆக அமைக்கவும்.
3. மேல் மற்றும் கீழ் லூப்பர் கடத்தல்: லூப்பர்கள் இடதுபுறத்தில் கடக்கும்போது, மேல் லூப்பரின் பின்புறத்திற்கும் கீழ் லூப்பர் முனைக்கும் இடையே 0.1 மிமீ இடைவெளி இருக்க வேண்டும்.
4. ஊசி உயரம்: ஊசி தட்டிலிருந்து ஊசி முனை வரை மேல் நிலையில் (TDC) 9.5 மிமீ முதல் 10.0 மிமீ வரை இருக்க வேண்டும்.""",
            keySpecsOrTiming = "Needle Height: 9.5mm - 10.0mm | Looper Clearance: 0.05mm | Crossing Clearance: 0.10mm",
            isBookmarked = true,
            viewCount = 118
        ),
        TechnicalArticle(
            id = 4,
            category = "TROUBLESHOOTING",
            machineType = "General",
            difficulty = "Intermediate",
            titleSinhala = "මැහුම් මඟහැරීම (Skipped Stitches) නිවැරදි කිරීම",
            titleEnglish = "Skipped Stitches Diagnostic and Mechanical Adjustment",
            titleTamil = "தையல் தவறுதல் (Skipped Stitches) கண்டறிதல் மற்றும் சரிசெய்தல்",
            summarySinhala = "නූල් පුඩුව (Loop formation) නිසි ලෙස නොසෑදීම නිසා සිදුවන මැහුම් මඟහැරීම් නිවැරදි කිරීම.",
            summaryEnglish = "Troubleshooting skipped stitches caused by poor loop formation, fabric flagging, or hook clearance.",
            summaryTamil = "நூல் வளையம் சரியாக உருவாகாததால் ஏற்படும் தையல் தவறுகளை சரிசெய்வதற்கான முறைகள்.",
            contentSinhala = """1. ඉඳිකටුව නැමී ඇත්දැයි පරීක්ෂා කිරීම: පැතලි වීදුරුවක් මත ඉඳිකටුව පෙරළා බලා නැමී ඇත්නම් අලුත් එකක් යොදන්න.
2. ඉඳිකටු උස (Needle Bar Height): ඉඳිකටුව පහළම මට්ටමේදී (BDC) ඉඳිකටු ඇස රොටරි හුක් එකේ මට්ටමට සාපේක්ෂව නිවැරදි ලකුණේ (timing mark) ඇත්දැයි බලන්න.
3. රොටරි හුක් හිඩැස (Hook Clearance): හිඩැස 0.15mm ට වඩා වැඩි වූ විට නූල් ලූපය අල්ලා නොගනී. එය 0.05mm දක්වා අඩු කරන්න.
4. පීඩන පාදමේ පීඩනය (Presser Foot Pressure): රෙදි උඩට එසවීම (Fabric Flagging) වැළැක්වීමට ප්‍රමාණවත් පීඩනයක් තබාගන්න.
5. නූල් ගුණාත්මකභාවය: නූල් වල ඇදෙනසුළු බව හෝ දුර්වලතා පරීක්ෂා කරන්න.""",
            contentEnglish = """1. Check for bent or deflected needle: Roll the needle on a flat glass surface to detect warps, replace immediately if bent.
2. Needle bar height check: Verify needle bar timing gauge line aligns with the lower edge of the needle bar bushing at BDC.
3. Rotary hook clearance too wide: If clearance exceeds 0.15mm, the hook misses the thread loop. Re-gap to 0.05mm - 0.1mm.
4. Fabric Flagging: Elastic fabrics bouncing with the needle prevent loop opening. Increase presser foot pressure spring and fit a smaller hole needle plate.
5. Check needle scarf orientation: The scarf must face directly towards the rotary hook tip.""",
            contentTamil = """1. ஊசி வளைந்துள்ளதா என சோதிக்கவும்: ஊசியை தட்டையான கண்ணாடி பரப்பில் உருட்டிப் பார்த்து வளைந்திருந்தால் புதிய ஊசியை மாற்றவும்.
2. ஊசி பார் உயரம்: BDC நிலையில் ஊசி பார் டைமிங் கோடு சரியான நிலையில் உள்ளதா என சரிபார்க்கவும்.
3. ரோட்டரி ஹூக் இடைவெளி: இடைவெளி 0.15 மிமீக்கு மேல் இருந்தால் நூல் வளையத்தைப் பிடிக்காது. அதை 0.05 மிமீ ஆக குறைக்கவும்.
4. துணி ஆடுதல் (Fabric Flagging): மீள் துணிகள் ஆடுவதைத் தடுக்க பிரஸ்ஸர் ஃபுட் அழுத்தத்தை அதிகரிக்கவும்.
5. ஊசி ஸ்கார்ஃப் திசை: ஸ்கார்ஃப் பகுதி ரோட்டரி ஹூக் முனையை நோக்கி இருப்பதை உறுதிப்படுத்தவும்.""",
            keySpecsOrTiming = "Hook Clearance: <= 0.08mm | Presser Foot Downforce: 4.5kg - 6.0kg for woven",
            isBookmarked = false,
            viewCount = 76
        ),
        TechnicalArticle(
            id = 5,
            category = "TIMING",
            machineType = "Flatlock / Interlock",
            difficulty = "Advanced",
            titleSinhala = "ෆ්ලැට්ලොක් (Flatlock / Interlock) ස්ප්‍රෙඩර් (Spreader) ටයිමිං",
            titleEnglish = "Flatlock / Interlock Top Cover Thread Spreader Timing",
            titleTamil = "பிளாட்லாக் இயந்திர டாப் கவர் ஸ்ப்ரெடர் டைமிங்",
            summarySinhala = "Yamato VC සහ Pegasus W500 මැෂින් සඳහා Top Cover නූල් ස්ප්‍රෙඩර් උස සහ ගමන් මාර්ගය.",
            summaryEnglish = "Top cover thread spreader stroke, height, and needle clearance adjustment for industrial coverstitch machines.",
            summaryTamil = "தொழில்துறை கவர்ஸ்டிட்ச் இயந்திரங்களுக்கான டாப் கவர் நூல் ஸ்ப்ரெடர் உயரம் மற்றும் சீரமைப்பு.",
            contentSinhala = """1. Spreader Height: ඉඳිකටු තහඩුවේ සිට ස්ප්‍රෙඩරයේ යටි පැත්ත දක්වා උස 8.0mm සිට 8.5mm දක්වා සකසන්න.
2. Spreader Travel: ස්ප්‍රෙඩරය වම් කෙළවරට යන විට, වම් ඉඳිකටුව සහ ස්ප්‍රෙඩර් තුඩ අතර හිඩැස 4.5mm - 5.0mm විය යුතුය.
3. Spreader Clearance: ස්ප්‍රෙඩරය දකුණට ගමන් කර නූල රැගෙන එන විට, ඉඳිකටු වලට නොගෑවී 0.5mm හිඩැසකින් ගමන් කළ යුතුය.
4. Front & Rear Needle Guards: ඉදිරිපස සහ පසුපස ඉඳිකටු ආරක්ෂක 0.05mm හිඩැසක් තබා ඉඳිකටු විස්ථාපනය වැළැක්විය යුතුය.""",
            contentEnglish = """1. Spreader Height: Adjust vertical distance from needle plate top surface to bottom of spreader to 8.0mm - 8.5mm.
2. Spreader Leftmost Stroke: When spreader is at leftmost position, distance from left needle center to spreader tip must be 4.5mm - 5.0mm.
3. Spreader Needle Clearance: When spreader swings rightward carrying top thread, maintain 0.5mm clearance from needle fronts.
4. Front and Rear Needle Guards: Set clearance of stationary needle guard to 0.05mm to prevent needle flexing into the looper path.""",
            contentTamil = """1. ஸ்ப்ரெடர் உயரம்: ஊசி தட்டிலிருந்து ஸ்ப்ரெடரின் கீழ் பகுதி வரை உயரம் 8.0 மிமீ முதல் 8.5 மிமீ வரை அமைக்கவும்.
2. ஸ்ப்ரெடர் இடதுபுற பயணம்: ஸ்ப்ரெடர் இடது எல்லைக்கு செல்லும் போது, இடது ஊசிக்கும் ஸ்ப்ரெடர் முனைக்கும் இடையே 4.5 மிமீ - 5.0 மிமீ இடைவெளி இருக்க வேண்டும்.
3. ஸ்ப்ரெடர் ஊசி இடைவெளி: ஸ்ப்ரெடர் வலதுபுறம் நகரும் போது ஊசிகளில் படாமல் 0.5 மிமீ இடைவெளியில் செல்ல வேண்டும்.
4. ஊசி காவலர்கள்: ஊசி வளையாமல் தடுக்க ஊசி காவலர்களை 0.05 மிமீ இடைவெளியில் அமைக்கவும்.""",
            keySpecsOrTiming = "Spreader Height: 8.0mm - 8.5mm | Left Stroke Distance: 4.5mm - 5.0mm",
            isBookmarked = false,
            viewCount = 63
        ),
        TechnicalArticle(
            id = 6,
            category = "MAINTENANCE",
            machineType = "All Machines",
            difficulty = "Basic",
            titleSinhala = "කාර්මික මහන මැෂින් තෙල් පොම්ප (Oil Pump) සහ ලිහිසි පද්ධතිය පරීක්ෂාව",
            titleEnglish = "Industrial Sewing Machine Lubrication & Oil Pump System Check",
            titleTamil = "தொழில்துறை தையல் இயந்திர எண்ணெய் பம்ப் மற்றும் உயவு அமைப்பு சோதனை",
            summarySinhala = "රොටරි හුක් සහ ඉහළ ෂාෆ්ට් ලිහිසි තෙල් ගලායාම තහවුරු කිරීම සහ ඔයිල් ෆිල්ටර පිරිසිදු කිරීම.",
            summaryEnglish = "Inspection of forced lubrication pumps, oil flow sight glass, and filter cleaning to protect mechanical components.",
            summaryTamil = "இயந்திர பாகங்களைப் பாதுகாக்க எண்ணெய் ஓட்டம் மற்றும் வடிகட்டி சுத்தம் செய்தல்.",
            contentSinhala = """1. Sight Glass පරීක්ෂාව: මැෂිම ධාවනය වන විට මැෂින් හිසේ ඉහළ ඇති තෙල් කවුළුවේ (Oil sight glass) තෙල් විසිරෙන්නේදැයි බලන්න.
2. තෙල් මට්ටම (Oil Pan Level): ඔයිල් පෑන් එකේ HIGH සහ LOW සලකුණු අතර නිවැරදි සුදු තෙල් (White mineral oil #10) පිරවිය යුතුය.
3. රොටරි හුක් ලිහිසි නියාමනය: රොටරි හුක් එක අසල ඇති තෙල් පාලන ඉස්කුරුප්පුව කරකවා කඩදාසි කැබැල්ලක් අල්ලා තෙල් විසිරීමේ රේඛාව තත්පර 5ක් තුළ 1mm - 2mm පරාසයක තහවුරු කරන්න.
4. Oil Filter පිරිසිදු කිරීම: ඔයිල් පෑන් එකේ ඇති ලෝහ පෙරහන (Filter mesh) මත රැඳී ඇති නූල් දූවිලි (Lint) මසකට වරක් පිරිසිදු කරන්න.""",
            contentEnglish = """1. Oil Sight Glass Inspection: While machine is running at full speed, check that oil pulsates continuously through the top glass dome.
2. Oil Pan Level: Ensure clear white mineral oil (Viscosity Grade ISO VG 10) sits between the HIGH and LOW index markers.
3. Rotary Hook Flow Metering: Adjust the oil volume regulation screw at hook base. Test with white paper held near hook for 5 seconds; oil mist line must be 1.0mm - 2.0mm wide.
4. Sump Filter Mesh Maintenance: Remove bottom oil pan pump filter mesh monthly to rinse away accumulated cotton lint and metal particles.""",
            contentTamil = """1. ஆயில் கண்ணாடி பார்வை: இயந்திரம் இயங்கும் போது மேல் கண்ணாடியில் எண்ணெய் பீறிட்டு பாய்கிறதா என சரிபார்க்கவும்.
2. எண்ணெய் அளவு: ஆயில் பானில் HIGH மற்றும் LOW குறிகளுக்கு இடையே வெள்ளை கனிம எண்ணெயை பராமரிக்கவும்.
3. ரோட்டரி ஹூக் எண்ணெய் ஒழுங்குமுறை: ஹூக் அருகே உள்ள திருகினை சரிசெய்து, 5 வினாடிகளில் வெள்ளை தாளில் 1.0 மிமீ - 2.0 மிமீ எண்ணெய் கோடு விழுவதை உறுதிப்படுத்தவும்.
4. ஆயில் பில்டர் சுத்தம்: ஆயில் பானில் உள்ள வடிகட்டியில் படிந்துள்ள பஞ்சு மற்றும் தூசிகளை மாதந்தோறும் சுத்தம் செய்யவும்.""",
            keySpecsOrTiming = "Oil Spec: ISO VG 10 White Oil | Hook Oil Paper Test: 1mm - 2mm mist line in 5s",
            isBookmarked = true,
            viewCount = 105
        ),
        TechnicalArticle(
            id = 7,
            category = "MANUALS",
            machineType = "All Machines",
            difficulty = "Basic",
            titleSinhala = "දිග මනින මිනුම් ඒකක සහ මිනුම් උපකරණ (Measuring Units & Tools)",
            titleEnglish = "Length Measuring Units & Measuring Tools (Tape & Ruler Markings)",
            titleTamil = "நீளத்தை அளவிடும் அலகுகள் மற்றும் அளவீட்டு கருவிகள் (Measuring Tools)",
            summarySinhala = "ඇඟලුම් කර්මාන්තයේ අඟල් මිනුම් (1/16\", 1/8\", 1/4\", 1/2\") කියවීම, මිනුම් පටි සහ උපකරණ භාවිතය.",
            summaryEnglish = "Garment industry tape & ruler markings (1/16\", 1/8\", 1/4\", 1/2\") and precision measurement tools.",
            summaryTamil = "ஆடைத் துறையில் அங்குல அளவீடுகள் (1/16, 1/8, 1/4, 1/2) மற்றும் அளவீட்டு கருவிகள்.",
            contentSinhala = """1. 1/16" (අඟලෙන් 1/16 - 1.59 mm): මිනුම් පටියේ හෝ රූලක ඇති කෙටිම ඉරයි. Edge stitching, 1/16" W/S (Wire stitch) සහ Topstitch මැහුම් සඳහා භාවිතා වේ.
2. 1/8" (අටෙන් එක - 3.18 mm): අඟලේ 2/16 ට සමාන වේ. සම්මත Topstitch සහ කොලර්/කෆ් දාර මැහුම් සඳහා බහුලව යොදා ගැනේ.
3. 3/16" (අටෙන් තුනෙන් බාගයක් - 4.76 mm): අඟලේ 3/16 ට සමාන වේ.
4. 1/4" (කාල් අඟල - 6.35 mm): අඟලේ 4/16 ට සමාන වේ. මහන මැෂිමේ පීඩන පාදමේ (Presser foot) සම්මත පළල සහ ප්‍රධාන මැහුම් වාටිය (Seam allowance) වේ.
5. 3/8" (අටෙන් තුන - 9.53 mm): අඟලේ 6/16 ට සමාන වේ. ඕවර්ලොක් (Overlock 4-thread / 5-thread) ආරක්ෂිත මැහුම් වාටිය වේ.
6. 1/2" (බාග අඟල - 12.70 mm): අඟලේ 8/16 ට සමාන වේ. කමිස සහ කලිසම් යටි වාටි (Bottom hem allowance) සඳහා ප්‍රධාන වශයෙන් යොදා ගැනේ.
7. 3/4" (මුක්කාල - 19.05 mm): අඟලේ 12/16 ට සමාන වේ. ඉණ පටි (Waistband) සහ පුළුල් වාටි සඳහා යොදා ගැනේ.
8. 1" (සම්පූර්ණ අඟල - 25.40 mm): අඟලක් කොටස් 16කට බෙදා ඇත. 1" රූලක් මඟින් SPI (Stitches Per Inch - අඟලකට මැහුම් ගණන) නිවැරදිව මැනිය හැක.
9. මිනුම් උපකරණ: මිනුම් පටිය (Tailor Tape), පැහැදිලි ඇක්‍රිලික් රූල (Clear Ruler), මැහුම් ගේජය (Seam Gauge), පිලර් ගේජය (Feeler Gauge - 0.05mm), වර්නියර් කැලිපරය (Vernier Caliper).""",
            contentEnglish = """1. 1/16" (1.59 mm): Shortest graduation line on an industrial tape. Used for precision edge stitching, W/S guides, and narrow margin topstitching.
2. 1/8" (3.18 mm): Equal to 2/16". Common for standard apparel collar/cuff edge stitching.
3. 3/16" (4.76 mm): Equal to 3/16". Used in twin-needle topstitch gauges.
4. 1/4" (6.35 mm): Equal to 4/16". Standard presser foot edge guide and universal garment seam allowance.
5. 3/8" (9.53 mm): Equal to 6/16". Standard 4-thread / 5-thread overlock seam trimming and joining allowance.
6. 1/2" (12.70 mm): Equal to 8/16" (Half Inch - longest center tick). Standard bottom hem fold allowance and zipper tape margin.
7. 3/4" (19.05 mm): Equal to 12/16". Standard waistband width and fold hem finishes.
8. 1" (25.40 mm): Full 1-inch span divided into 16 increments. Essential for verifying Stitches Per Inch (SPI).
9. Key Tools: Flexible fiberglass measuring tape, clear acrylic grid ruler, metal seam sliding gauge, steel feeler gauge (for 0.05mm - 0.1mm hook timing), and digital vernier calipers.""",
            contentTamil = """1. 1/16" (1.59 மிமீ): தொழில்துறை அளவீட்டு நாடாவில் மிகக் குறுகிய கோடு. விளிம்பு தையல் மற்றும் நுணுக்கமான தையல்களுக்குப் பயன்படுகிறது.
2. 1/8" (3.18 மிமீ): 2/16"க்கு சமமானது. காலர் மற்றும் கஃப் விளிம்பு தையலுக்கு பரவலாகப் பயன்படுகிறது.
3. 1/4" (6.35 மிமீ): கால் அங்குலம் (4/16"). பிரஸ்ஸர் ஃபுட் அகலம் மற்றும் நிலையான தையல் இடைவெளி (Seam allowance).
4. 3/8" (9.53 மிமீ): எட்டில் மூன்று (6/16"). ஓவர்லாக் தையல் இடைவெளி.
5. 1/2" (12.70 மிமீ): அரை அங்குலம் (8/16"). கீழ் மடிப்பு (Hem allowance) மற்றும் ஜிப்பர் விளிம்பிற்குப் பயன்படுகிறது.
6. 3/4" (19.05 மிமீ): முக்கால் அங்குலம் (12/16"). இடுப்பு பட்டை (Waistband) அகலம்.
7. 1" (25.40 மிமீ): முழு அங்குலம். ஒரு அங்குலத்தில் 16 பிரிவுகள் உள்ளன. தையல் அடர்த்தியை (SPI) அளவிடப் பயன்படுகிறது.
8. அளவீட்டு கருவிகள்: அளவீட்டு நாடா (Tape), அக்ரிலிக் ரூலர், சீம் கேஜ், ஃபீலர் கேஜ் (0.05 மிமீ), வெர்னியர் காலிபர்.""",
            keySpecsOrTiming = "1\" = 25.4mm | 1/16\" = 1.59mm | 1/8\" = 3.18mm | 1/4\" = 6.35mm | 1/2\" = 12.7mm",
            isBookmarked = true,
            viewCount = 188
        ),
        TechnicalArticle(
            id = 8,
            category = "MANUALS",
            machineType = "General",
            difficulty = "Intermediate",
            titleSinhala = "Juki DDL-8700 / DDL-9000 සේවා සහ නඩත්තු තාක්ෂණික මැනුවල් (Service Manual)",
            titleEnglish = "Juki DDL-8700 / DDL-9000 Direct-Drive Service & Technical Manual",
            titleTamil = "ஜூக்கி DDL-8700 / DDL-9000 சேவை மற்றும் தொழில்நுட்ப கையேடு",
            summarySinhala = "Juki DDL මාලාවේ කාර්මික මහන මැෂින් සඳහා සම්පූර්ණ නඩත්තු මාර්ගෝපදේශය, කොටස් සටහන සහ පිරිවිතර.",
            summaryEnglish = "Official technician service manual & timing specifications for Juki DDL-8700, DDL-9000, and SC-920 motor systems.",
            summaryTamil = "ஜூக்கி DDL-8700 மற்றும் DDL-9000 இயந்திரங்களுக்கான முழுமையான சேவை கையேடு மற்றும் குறிப்புகள்.",
            contentSinhala = """1. මැෂින් මොඩලය (Model): Juki DDL-8700 / DDL-9000 Direct-Drive High-Speed Single Needle.
2. උපරිම වේගය (Max Speed): 5,000 SPM (Stitches Per Minute).
3. ඉඳිකටු පද්ධතිය (Needle System): DBx1 (16x231, 287WH) #9 සිට #18 දක්වා.
4. ඉඳිකටු බාර් ස්ට්‍රෝක් (Needle Bar Stroke): 30.7 mm.
5. පීඩන පාදම එසවීම (Presser Foot Lift): අතින් 5.5 mm, දණහිසෙන් (Knee lifter) 13.0 mm.
6. ලිහිසි තෙල් (Lubrication): Fully automatic lubrication pump with ISO VG 10 white oil.
7. හුක් වර්ගය (Hook): Full-rotary auto-lubricating hook with oil volume regulator screw.""",
            contentEnglish = """1. Machine Model: Juki DDL-8700 / DDL-9000 High-Speed Direct-Drive Lockstitch.
2. Max Sewing Speed: 5,000 SPM (Stitches Per Minute).
3. Standard Needle System: DBx1 (Organ / Schmetz) size #9 to #18 (standard #14 for medium fabric).
4. Needle Bar Stroke: 30.7 mm (BDC to TDC).
5. Presser Foot Lift: 5.5 mm (by hand lever), 13.0 mm (by automatic knee lifter).
6. Lubrication: Fully automatic centrifugal oil pump with filter mesh (ISO VG 10 mineral oil).
7. Rotary Hook: Automatic lubrication rotary hook; adjust metering screw to achieve 1.5mm mist band in 5s.""",
            contentTamil = """1. இயந்திர மாதிரி: ஜூக்கி DDL-8700 / DDL-9000 அதிவேக தையல் இயந்திரம்.
2. அதிகபட்ச வேகம்: நிமிடத்திற்கு 5,000 தையல்கள் (5000 SPM).
3. ஊசி அமைப்பு: DBx1 அளவு #9 முதல் #18 வரை.
4. பிரஸ்ஸர் ஃபுட் உயரம்: கையால் 5.5 மிமீ, முழங்கால் நெம்புகோல் மூலம் 13.0 மிமீ.
5. உயவு அமைப்பு: முழுமையான தானியங்கி எண்ணெய் பம்ப் அமைப்பு (ISO VG 10).
6. ரோட்டரி ஹூக்: தானியங்கி எண்ணெய் விநியோகத்துடன் கூடிய ரோட்டரி ஹூக்.""",
            keySpecsOrTiming = "Model: Juki DDL-8700 / DDL-9000 | Speed: 5000 SPM | Needle: DBx1 #14 | Stroke: 30.7mm | Oil: ISO VG 10",
            isBookmarked = true,
            viewCount = 210
        ),
        TechnicalArticle(
            id = 9,
            category = "VIDEOS",
            machineType = "General",
            difficulty = "Intermediate",
            titleSinhala = "පුහුණු වීඩියෝව: රොටරි හුක් ටයිමිං පියවරෙන් පියවර සැකසීම (Video Masterclass)",
            titleEnglish = "Training Video: Step-by-Step Rotary Hook Timing & Needle Guard Calibration Video",
            titleTamil = "பயிற்சி வீடியோ: ரோட்டரி ஹூக் டைமிங் அமைக்கும் வீடியோ பயிற்சி",
            summarySinhala = "Juki, Brother සහ Jack මැෂින් සඳහා රොටරි හුක් ටයිමිං, ඉඳිකටු හිඩැස සහ ආරක්ෂක සැකසුම් වීඩියෝ පුහුණුව.",
            summaryEnglish = "Comprehensive step-by-step 1080p video workshop covering rotary hook alignment, needle scarf clearance, and screw torque.",
            summaryTamil = "ரோட்டரி ஹூக் சீரமைப்பு மற்றும் இடைவெளி அமைப்பதற்கான முழுமையான வீடியோ பயிற்சி.",
            contentSinhala = """1. [වීඩියෝ කාලය 00:00 - 02:30]: ඉඳිකටු තහඩුව, බොබින් කේස් සහ ෆීඩ් ඩෝග් ගලවා ඉවත් කිරීම.
2. [වීඩියෝ කාලය 02:31 - 05:45]: ඉඳිකටුව BDC මට්ටමේ සිට 1.8mm සිට 2.0mm දක්වා ඉහළට එසවීම සහ timing mark පෙළගැස්වීම.
3. [වීඩියෝ කාලය 05:46 - 08:50]: හුක් තුඩ ඉඳිකටු ඇසට ඉහළින් 1.0mm - 1.5mm මට්ටමට ගෙන ඒම සහ 0.05mm clearance එක ෆීලර් ගේජයෙන් මැනීම.
4. [වීඩියෝ කාලය 08:51 - 12:00]: හුක් ඉස්කුරුප්පු තද කිරීම, අතින් කරකවා පරීක්ෂා කිරීම සහ ටෙස්ට් මැහුමක් දැමීම.""",
            contentEnglish = """1. [Video 00:00 - 02:30]: Safe disassembly of needle plate, feed dog, and bobbin case assembly.
2. [Video 02:31 - 05:45]: Setting needle bar to BDC and measuring the 1.8mm - 2.0mm upward ascent with dial gauge.
3. [Video 05:46 - 08:50]: Hook point alignment into needle scarf with 0.05mm - 0.1mm clearance (feeler gauge verification).
4. [Video 08:51 - 12:00]: Sequential screw torque, needle deflection check, and high-speed test sew-out.""",
            contentTamil = """1. [வீடியோ 00:00 - 02:30]: ஊசி தட்டு மற்றும் பாபின் கேஸை கழற்றுதல்.
2. [வீடியோ 02:31 - 05:45]: ஊசி பட்டையை BDC இலிருந்து 1.8 மிமீ - 2.0 மிமீ வரை உயர்த்துதல்.
3. [வீடியோ 05:46 - 08:50]: ஹூக் முனையை ஊசி ஸ்கார்ஃப் பகுதிக்கு சீரமைத்து 0.05 மிமீ இடைவெளியை உறுதி செய்தல்.
4. [வீடியோ 08:51 - 12:00]: திருகுகளை இறுக்கி சோதனை தையல் போடுதல்.""",
            keySpecsOrTiming = "Video Length: 12 Min | Format: HD 1080p | Models: Juki DDL-8700, Jack A4, Brother S-7200 | Hook Gap: 0.05mm",
            isBookmarked = true,
            viewCount = 245
        ),
        TechnicalArticle(
            id = 10,
            category = "MANUALS",
            machineType = "Overlock / Serger",
            difficulty = "Advanced",
            titleSinhala = "Pegasus M900 / Siruba 747K ඕවර්ලොක් කාර්මික සේවා මැනුවල් (Service Manual)",
            titleEnglish = "Pegasus M900 / Siruba 747K High-Speed Overlock Technical Service Manual",
            titleTamil = "பெகாசஸ் M900 / சிருபா 747K ஓவர்லாக் தொழில்நுட்ப சேவை கையேடு",
            summarySinhala = "3-thread, 4-thread සහ 5-thread ඕවර්ලොක් මැෂින් සඳහා ලූපර් ගේජ්, පිහි කැපුම් සහ සේවා පිරිවිතර.",
            summaryEnglish = "Factory service manual for Pegasus M900 series and Siruba 747K overlock machines with knife clearances and feed ratio.",
            summaryTamil = "பெகாசஸ் M900 மற்றும் சிருபா 747K ஓவர்லாக் இயந்திரங்களுக்கான தொழிற்சாலை சேவை கையேடு.",
            contentSinhala = """1. මැෂින් මොඩලය: Pegasus M900 Series / Siruba 747K / Jack C4 High-Speed Overlock / Safety Stitch.
2. උපරිම වේගය: 7,000 SPM (Differential feed ratio: 1:0.7 to 1:2.0).
3. ඉඳිකටු වර්ගය: DCx27 (B27) #9 සිට #14 දක්වා.
4. ඉඳිකටු උස: ඉඳිකටු තහඩුවේ සිට ඉඳිකටු තුඩ දක්වා උස TDC හිදී 9.5 mm - 10.0 mm.
5. පහළ ලූපර් (Lower looper) කෝණය සහ දුර: ඉඳිකටු මධ්‍යයේ සිට ලූපර් තුඩට දුර 3.5 mm - 4.0 mm.
6. ඉහළ ලූපර් (Upper looper) උස: ඉඳිකටු තහඩුවේ සිට ඉහළ ලූපර් තුඩට දුර 10.5 mm - 11.5 mm.
7. කැපුම් පිහි (Knives): ඉහළ කාබයිඩ් පිහිය (Upper movable knife) පහළ ස්ථාවර පිහිය (Lower stationary knife) 0.5 mm - 1.0 mm overlap විය යුතුය.""",
            contentEnglish = """1. Model: Pegasus M900 Series / Siruba 747K / Jack C4 Overlock Serger.
2. Max Speed: 7,000 SPM | Differential Feed Ratio: 1:0.7 - 1:2.0.
3. Needle System: DCx27 (Organ / Schmetz) #11 or #14.
4. Needle Height at TDC: 9.5 mm to 10.0 mm from needle plate surface.
5. Lower Looper Distance: 3.5 mm to 4.0 mm right of needle center at leftmost stroke.
6. Upper Looper Height: 10.5 mm to 11.5 mm above needle plate at leftmost stroke.
7. Trimming Knives: Overlap upper and lower knives by 0.5 mm to 1.0 mm with sharp shearing spring tension.""",
            contentTamil = """1. இயந்திர மாதிரி: பெகாசஸ் M900 / சிருபா 747K அதிவேக ஓவர்லாக்.
2. அதிகபட்ச வேகம்: 7,000 SPM.
3. ஊசி அமைப்பு: DCx27 #11 / #14.
4. ஊசி உயரம்: ஊசி தட்டிலிருந்து மேல் நிலையில் (TDC) 9.5 மிமீ - 10.0 மிமீ.
5. கீழ் லூப்பர் இடைவெளி: ஊசி மையத்திலிருந்து 3.5 மிமீ - 4.0 மிமீ.
6. கத்திகள் சீரமைப்பு: மேல் மற்றும் கீழ் கத்திகள் 0.5 மிமீ - 1.0 மிமீ இணைய வேண்டும்.""",
            keySpecsOrTiming = "Model: Pegasus M900 / Siruba 747K | Speed: 7000 SPM | Needle: DCx27 #11 | Needle Height: 9.8mm | Knife Overlap: 0.8mm",
            isBookmarked = false,
            viewCount = 175
        ),
        TechnicalArticle(
            id = 11,
            category = "VIDEOS",
            machineType = "Overlock / Serger",
            difficulty = "Advanced",
            titleSinhala = "පුහුණු වීඩියෝව: ඕවර්ලොක් ලූපර් ටයිමිං සහ නූල් ගැළපීම (Looper Timing Video)",
            titleEnglish = "Training Video: Overlock Upper & Lower Looper Synchronization & Clearance Video",
            titleTamil = "பயிற்சி வீடியோ: ஓவர்லாக் லூப்பர் டைமிங் மற்றும் இடைவெளி வீடியோ",
            summarySinhala = "ඕවර්ලොක් මැෂිමක Upper සහ Lower loopers නිවැරදිව එකිනෙක හරස් වන ආකාරය සහ ඉඳිකටු හිඩැස පෙන්වන වීඩියෝව.",
            summaryEnglish = "15-minute high-definition video guide demonstrating overlock looper crossing clearance, needle guards, and stitch balance.",
            summaryTamil = "ஓவர்லாக் லூப்பர் சீரமைப்பு மற்றும் தையல் சமநிலையை விளக்கும் 15 நிமிட வீடியோ வழிகாட்டி.",
            contentSinhala = """1. [වීඩියෝ 00:00 - 03:20]: Lower looper එක ඉඳිකටුව පිටුපසින් ගමන් කරන නිවැරදි ගමන් මඟ සහ 0.05mm clearance සැකසීම.
2. [වීඩියෝ 03:21 - 07:15]: Upper looper එක Lower looper එක පිටුපසින් හරස් වන විට 0.10mm හිඩැස තබාගැනීම.
3. [වීඩියෝ 07:16 - 11:30]: ඉඳිකටු ආරක්ෂකය (Needle guard - front & rear) සකසා ඉඳිකටුව විස්ථාපනය වීම වැළැක්වීම.
4. [වීඩියෝ 11:31 - 15:00]: නූල් ආතතිය (4-thread tension) සමබර කර පරිපූර්ණ ඕවර්ලොක් මැහුමක් ලබා ගැනීම.""",
            contentEnglish = """1. [Video 00:00 - 03:20]: Lower looper passing needle scarf with 0.05mm gap and 2.8mm stroke calibration.
2. [Video 03:21 - 07:15]: Looper-to-looper crossing synchronization; maintaining 0.10mm back clearance.
3. [Video 07:16 - 11:30]: Front and rear movable needle guard positioning to prevent needle deflect under fabric load.
4. [Video 11:31 - 15:00]: Multi-thread tension balancing for flat, stretch-resistant 4-thread overedge seams.""",
            contentTamil = """1. [வீடியோ 00:00 - 03:20]: கீழ் லூப்பர் ஊசியைக் கடக்கும் முறை மற்றும் 0.05 மிமீ இடைவெளி அமைத்தல்.
2. [வீடியோ 03:21 - 07:15]: மேல் மற்றும் கீழ் லூப்பர்கள் கடக்கும்போது 0.10 மிமீ இடைவெளியை பராமரித்தல்.
3. [வீடியோ 07:16 - 11:30]: ஊசி காவலர்களை சரியாக சீரமைத்தல்.
4. [வீடியோ 11:31 - 15:00]: 4-நூல் பதற்றத்தை சமநிலைப்படுத்துதல்.""",
            keySpecsOrTiming = "Video Length: 15 Min | Models: Pegasus M900, Siruba 747, Jack C4 | Crossing Gap: 0.10mm | Looper Gap: 0.05mm",
            isBookmarked = false,
            viewCount = 192
        ),
        TechnicalArticle(
            id = 12,
            category = "MANUALS",
            machineType = "Flatlock / Interlock",
            difficulty = "Advanced",
            titleSinhala = "Yamato VC2700 / Pegasus W500 ෆ්ලැට්ලොක් තාක්ෂණික මැනුවල් (Interlock Manual)",
            titleEnglish = "Yamato VC2700 / Pegasus W500 Cylinder-Bed Interlock Service Manual",
            titleTamil = "யமடோ VC2700 / பெகாசஸ் W500 சிலிண்டர்-பெட் இன்டர்லாக் சேவை கையேடு",
            summarySinhala = "ෆ්ලැට්ලොක් (Interlock / Coverstitch) මැෂින් සඳහා Top cover spreader, ලූපර් සහ ඉඳිකටු මැනුවල් උපදෙස්.",
            summaryEnglish = "Complete factory technical specifications, looper stroke timing, and spreader height for Yamato VC and Pegasus W500.",
            summaryTamil = "யமடோ VC மற்றும் பெகாசஸ் W500 பிளாட்லாக் இயந்திரங்களுக்கான முழுமையான தொழிற்சாலை குறிப்புகள்.",
            contentSinhala = """1. මැෂින් මොඩලය: Yamato VC2700 / Pegasus W500 / W600 High-Speed Cylinder Bed Interlock.
2. ඉඳිකටු පද්ධතිය: UY128GAS #10 සිට #14 දක්වා (3-needle 5.6mm / 6.4mm gauge).
3. උපරිම වේගය: 6,000 SPM.
4. Top Spreader උස: ඉඳිකටු තහඩුවේ සිට ස්ප්‍රෙඩර් යටි පැත්තට 8.0 mm - 8.5 mm.
5. Looper Timing: ඉඳිකටුව පහළම මට්ටමේ සිට 3.0 mm ඉහළට යන විට ලූපර් තුඩ වම් ඉඳිකටු ඇසට ඉහළින් 1.2 mm පිහිටිය යුතුය.""",
            contentEnglish = """1. Model: Yamato VC2700 / Pegasus W500 / W600 Interlock Coverstitch.
2. Needle System: UY128GAS (Organ / Schmetz) size #10 - #14 (standard 3-needle 5.6mm or 6.4mm gauge).
3. Max Speed: 6,000 SPM with micro-differential feed.
4. Top Spreader Height: 8.0 mm to 8.5 mm from needle plate upper surface.
5. Lower Looper Timing: Ascending 3.0 mm from BDC, looper tip crosses left needle 1.2 mm above eye.""",
            contentTamil = """1. மாதிரி: யமடோ VC2700 / பெகாசஸ் W500 சிலிண்டர் பெட் இன்டர்லாக்.
2. ஊசி அமைப்பு: UY128GAS #10 - #14.
3. வேகம்: 6,000 SPM.
4. டாப் ஸ்ப்ரெடர் உயரம்: ஊசி தட்டிலிருந்து 8.0 மிமீ - 8.5 மிமீ.
5. கீழ் லூப்பர் டைமிங்: BDC இலிருந்து 3.0 மிமீ உயரும் போது ஊசிக்கு 1.2 மிமீ மேலே இருக்க வேண்டும்.""",
            keySpecsOrTiming = "Model: Yamato VC2700 / Pegasus W500 | Needle: UY128GAS #11 | Gauge: 5.6mm / 6.4mm | Spreader Height: 8.2mm",
            isBookmarked = false,
            viewCount = 138
        ),
        TechnicalArticle(
            id = 13,
            category = "VIDEOS",
            machineType = "Flatlock / Interlock",
            difficulty = "Advanced",
            titleSinhala = "පුහුණු වීඩියෝව: ෆ්ලැට්ලොක් ස්ප්‍රෙඩර් සහ ලූපර් සැකසීම (Flatlock Video Tutorial)",
            titleEnglish = "Training Video: Flatlock Top Cover Spreader & Looper Synchronization Video",
            titleTamil = "பயிற்சி வீடியோ: பிளாட்லாக் ஸ்ப்ரெடர் மற்றும் லூப்பர் வீடியோ பயிற்சி",
            summarySinhala = "Yamato සහ Pegasus ෆ්ලැට්ලොක් මැෂින්වල Top cover spreader එක නූල් ඇදගන්නා ආකාරය පියවරෙන් පියවර පෙන්වන වීඩියෝව.",
            summaryEnglish = "18-minute video tutorial detailing 3-needle coverstitch looper loop taking, spreader thread drop, and skipped stitch fixes.",
            summaryTamil = "பிளாட்லாக் இயந்திரங்களில் டாப் கவர் ஸ்ப்ரெடர் மற்றும் லூப்பர் அமைப்பதற்கான 18 நிமிட வீடியோ.",
            contentSinhala = """1. [වීඩියෝ 00:00 - 04:30]: 3-needle gauge එක සහ ඉඳිකටු තුනම එකම උසින් තහවුරු කිරීම.
2. [වීඩියෝ 04:31 - 09:15]: Spreader travel - ස්ප්‍රෙඩරය වම්පසට ගමන් කරන දුර (4.5mm - 5.0mm) සැකසීම.
3. [වීඩියෝ 09:16 - 13:40]: Top cover thread guide එක හරහා නූල ස්ප්‍රෙඩරයට හසුවන කෝණය නිවැරදි කිරීම.
4. [වීඩියෝ 13:41 - 18:00]: සිහින් knit රෙදි සඳහා differential feed එක සකසා රැළි වැටීම (puckering) වැළැක්වීම.""",
            contentEnglish = """1. [Video 00:00 - 04:30]: 3-needle clamp alignment and depth verification at TDC.
2. [Video 04:31 - 09:15]: Spreader stroke distance adjustment: maintaining 4.5mm - 5.0mm stroke to the left of the leftmost needle.
3. [Video 09:16 - 13:40]: Spreader guide wire timing to catch and lay top thread across needles smoothly.
4. [Video 13:41 - 18:00]: Micro-differential feed adjustment to prevent seam puckering on light synthetic knits.""",
            contentTamil = """1. [வீடியோ 00:00 - 04:30]: 3-ஊசி கிளாம்ப் மற்றும் ஊசி உயரங்களை சரிபார்த்தல்.
2. [வீடியோ 04:31 - 09:15]: ஸ்ப்ரெடர் இடதுபுற பயணம் (4.5 மிமீ - 5.0 மிமீ) அமைத்தல்.
3. [வீடியோ 09:16 - 13:40]: டாப் கவர் நூல் வழிகாட்டியை சீரமைத்தல்.
4. [வீடியோ 13:41 - 18:00]: தையல் சுருக்கத்தைத் தடுக்க டிஃபெரன்ஷியல் ஃபீட் அமைத்தல்.""",
            keySpecsOrTiming = "Video Length: 18 Min | Models: Yamato VC2700, Pegasus W500 | Stroke: 4.8mm | Needle Gap: 0.5mm",
            isBookmarked = false,
            viewCount = 154
        ),
        TechnicalArticle(
            id = 14,
            category = "MANUALS",
            machineType = "Bartack",
            difficulty = "Advanced",
            titleSinhala = "Juki LK-1900 / Jack JK-T1900 ඉලෙක්ට්‍රොනික් බාර්ටැක් සේවා මැනුවල් (Bartack Manual)",
            titleEnglish = "Juki LK-1900 / Jack JK-T1900 Computer-Controlled Bartack Service Manual",
            titleTamil = "ஜூக்கி LK-1900 / ஜேக் JK-T1900 கணினிமயமாக்கப்பட்ட பார்டேக் சேவை கையேடு",
            summarySinhala = "ඉලෙක්ට්‍රොනික් බාර්ටැක් මැෂින් සඳහා ෂටල් හුක් ටයිමිං, කැපුම් පද්ධතිය සහ වැඩ කලම්ප පිරිවිතර.",
            summaryEnglish = "Technical manual for electronic bartack machines with oscillating shuttle hook timing, thread trimmer, and clamp height.",
            summaryTamil = "மின்னணு பார்டேக் இயந்திரங்களுக்கான ஷட்டில் ஹூக் டைமிங் மற்றும் நூல் டிரிம்மர் கையேடு.",
            contentSinhala = """1. මැෂින් මොඩලය: Juki LK-1900 Series / Jack JK-T1900 Computer Bartacker.
2. උපරිම වේගය: 3,200 SPM.
3. ඉඳිකටු වර්ගය: DPx17 (135x17) #14 සිට #21 දක්වා.
4. වැඩ කලම්ප උස (Work Clamp Lift): 13 mm (pneumatic/solenoid lift 17 mm).
5. Shuttle Hook Timing: ඉඳිකටුව BDC සිට 2.2 mm - 2.5 mm ඉහළට යන විට Shuttle hook තුඩ ඉඳිකටු ඇසට 1.2 mm ඉහළින් හමුවිය යුතුය.
6. නූල් කපනය (Trimmer): Moving knife සහ Counter knife 0.3 mm overlap වන සේ සකසන්න.""",
            contentEnglish = """1. Model: Juki LK-1900 Series / Jack JK-T1900 Computer Bartack Machine.
2. Max Sewing Speed: 3,200 SPM.
3. Needle System: DPx17 (#14 to #21 depending on heavy denim/medium apparel).
4. Work Clamp Height: 13.0 mm standard lift (up to 17.0 mm pneumatic).
5. Semi-Rotary Shuttle Hook Timing: Ascending 2.2 mm - 2.5 mm from BDC, shuttle driver tip crosses 1.2 mm above needle eye.
6. Auto Trimmer: Moving knife engagement with fixed counter knife set to 0.3 mm overlap with spring preload.""",
            contentTamil = """1. மாதிரி: ஜூக்கி LK-1900 / ஜேக் JK-T1900 கம்ப்யூட்டர் பார்டேக் இயந்திரம்.
2. வேகம்: 3,200 SPM.
3. ஊசி அமைப்பு: DPx17 #14 - #21.
4. கிளாம்பின் உயரம்: 13.0 மிமீ.
5. ஷட்டில் ஹூக் டைமிங்: BDC இலிருந்து 2.2 மிமீ - 2.5 மிமீ உயரும் போது ஊசிக்கு 1.2 மிமீ மேலே இருக்க வேண்டும்.""",
            keySpecsOrTiming = "Model: Juki LK-1900 / Jack JK-T1900 | Speed: 3200 SPM | Needle: DPx17 #16 | Shuttle Rise: 2.2mm - 2.5mm",
            isBookmarked = false,
            viewCount = 140
        ),
        TechnicalArticle(
            id = 15,
            category = "MANUALS",
            machineType = "Buttonhole",
            difficulty = "Advanced",
            titleSinhala = "Juki LBH-1790 ඉලෙක්ට්‍රොනික් බොත්තම් කාස සේවා මැනුවල් (Buttonholing Manual)",
            titleEnglish = "Juki LBH-1790 High-Speed Computer-Controlled Buttonholing Service Manual",
            titleTamil = "ஜூக்கி LBH-1790 அதிவேக கணினிமயமாக்கப்பட்ட பட்டன்ஹோல் சேவை கையேடு",
            summarySinhala = "ඉලෙක්ට්‍රොනික් බොත්තම් කාස මැෂින් සඳහා හුක් ටයිමිං, පිහි කැපුම් ගැඹුර සහ ඉලෙක්ට්‍රොනික සෙන්සර් පිරිවිතර.",
            summaryEnglish = "Comprehensive service manual covering hook timing, knife drop timing, fabric clamp pressure, and electronic parameters for LBH-1790.",
            summaryTamil = "ஜூக்கி LBH-1790 பட்டன்ஹோல் இயந்திரங்களுக்கான முழுமையான சேவை கையேடு.",
            contentSinhala = """1. මැෂින් මොඩලය: Juki LBH-1790 Series / Jack JK-T1790 High-Speed Buttonholing Machine.
2. උපරිම වේගය: 4,200 SPM.
3. ඉඳිකටු වර්ගය: DPx5 (134) #11 සිට #14 දක්වා.
4. කාස කැපුම් පිහිය (Cloth Cutting Knife): Multi-cutting knife drop solenoid calibration; පිහිය ඉඳිකටු තහඩුවේ ඇළියට (cutting slot) 1.5 mm ගැඹුරට ඇතුළු විය යුතුය.
5. Rotary Hook Timing: ඉඳිකටුව BDC සිට 2.0 mm ඉහළට යන විට Hook point එක ඉඳිකටු ඇසට ඉහළින් 1.0 mm - 1.4 mm උසින් තිබිය යුතුය.
6. සෙන්සර් පරීක්ෂාව: Knife drop safety sensor සහ Needle bar safety switch නිවැරදිව ප්‍රතිචාර දක්වන බව පාලන පැනලයෙන් පරීක්ෂා කරන්න.""",
            contentEnglish = """1. Model: Juki LBH-1790 Series / Jack JK-T1790 Electronic Buttonholer.
2. Max Speed: 4,200 SPM.
3. Needle System: DPx5 (#11 to #14).
4. Cloth Cutting Knife Mechanism: Direct solenoid drop; knife penetration depth must be 1.5 mm into throat plate slot.
5. Rotary Hook Timing: At 2.0 mm needle bar rise from BDC, hook tip aligns 1.0 mm - 1.4 mm above needle eye.
6. Safety Sensors: Verify knife sensor home position and presser foot lifting limit switches on the main control unit.""",
            contentTamil = """1. மாதிரி: ஜூக்கி LBH-1790 / ஜேக் JK-T1790 பட்டன்ஹோல் இயந்திரம்.
2. வேகம்: 4,200 SPM.
3. ஊசி அமைப்பு: DPx5 #11 - #14.
4. துணி வெட்டும் கத்தி: கத்தி ஊசி தட்டின் பள்ளத்திற்குள் 1.5 மிமீ ஆழத்திற்கு இறங்க வேண்டும்.
5. ரோட்டரி ஹூக் டைமிங்: BDC இலிருந்து 2.0 மிமீ உயரும் போது ஊசிக்கு 1.0 மிமீ - 1.4 மிமீ மேலே சீரமைக்கவும்.""",
            keySpecsOrTiming = "Model: Juki LBH-1790 / Jack JK-T1790 | Speed: 4200 SPM | Needle: DPx5 #11 | Knife Depth: 1.5mm | Hook Rise: 2.0mm",
            isBookmarked = false,
            viewCount = 162
        ),
        TechnicalArticle(
            id = 16,
            category = "VIDEOS",
            machineType = "Buttonhole",
            difficulty = "Intermediate",
            titleSinhala = "පුහුණු වීඩියෝව: කාස සහ බාර්ටැක් කැපුම් පිහි (Knife) සහ සෙන්සර් මාරු කිරීම (Video)",
            titleEnglish = "Training Video: Buttonhole & Bartack Cutter Replacement & Safety Sensor Alignment Video",
            titleTamil = "பயிற்சி வீடியோ: பட்டன்ஹோல் மற்றும் பார்டேக் கத்தி மாற்றுதல் வீடியோ",
            summarySinhala = "බොත්තම් කාස සහ බාර්ටැක් මැෂින්වල කැපුම් පිහිය අලුතින් යෙදීම සහ පිහි සෙන්සර් ක්‍රමාංකනය පෙන්වන වීඩියෝව.",
            summaryEnglish = "14-minute technician training video demonstrating safe knife change, cutting block dressing, and optical sensor calibration.",
            summaryTamil = "பட்டன்ஹோல் கத்தி மாற்றுதல் மற்றும் ஆப்டிகல் சென்சார் சீரமைப்புக்கான 14 நிமிட வீடியோ வழிகாட்டி.",
            contentSinhala = """1. [වීඩියෝ 00:00 - 03:00]: මැෂිම OFF කර විදුලියෙන් විසන්ධි කිරීම සහ වැඩ කලම්පය ඉවත් කිරීම.
2. [වීඩියෝ 03:01 - 06:45]: පරණ මොට වූ පිහිය ගලවා නව කැපුම් පිහිය (Carbide knife blade) නියමිත කෝණයට සවි කිරීම.
3. [වීඩියෝ 06:46 - 10:15]: ඇලුමිනියම් කටර් බ්ලොක් (Cutter anvil) එකේ මතුපිට සීරීම් පරීක්ෂා කර මට්ටම් කිරීම.
4. [වීඩියෝ 10:16 - 14:00]: Knife drop sensor පරීක්ෂා කර, ටෙස්ට් කැපුමක් සිදුකර නූල් හෝ රෙදි ඉතිරි නොවී කැපෙන බව තහවුරු කිරීම.""",
            contentEnglish = """1. [Video 00:00 - 03:00]: Power safety shutdown and work clamp disassembly.
2. [Video 03:01 - 06:45]: Knife holder bolt removal and genuine replacement carbide blade installation at 90-degree alignment.
3. [Video 06:46 - 10:15]: Cutter anvil pad inspection and surface leveling to guarantee clean cut through heavy fabrics.
4. [Video 10:16 - 14:00]: Solenoid stroke limit adjustment, sensor verification, and automated test cut execution.""",
            contentTamil = """1. [வீடியோ 00:00 - 03:00]: மின்சாரத்தை அணைத்து கிளாம்பை கழற்றுதல்.
2. [வீடியோ 03:01 - 06:45]: பழைய கத்தியை கழற்றி புதிய கார்பைடு கத்தியை பொருத்துதல்.
3. [வீடியோ 06:46 - 10:15]: கட்டிங் அன்வில் மேற்பரப்பை சமன் செய்தல்.
4. [வீடியோ 10:16 - 14:00]: சென்சார் சரிபார்த்து சோதனை வெட்டு நிகழ்த்துதல்.""",
            keySpecsOrTiming = "Video Length: 14 Min | Models: Juki LBH-1790, LK-1900 | Anvil Gap: Flush | Blade: Genuine Carbide",
            isBookmarked = false,
            viewCount = 180
        )
    )

    fun getDefaultErrorCodes(): List<ErrorCodeItem> = listOf(
        ErrorCodeItem(
            id = 1,
            code = "E-01",
            system = "Juki Direct Drive (SC-920 / DDL-9000)",
            titleSinhala = "මෝටර් ලොක් වීම / අධික බර (Motor Lock / Overload)",
            titleEnglish = "Motor Lock / Mechanical Overload Stall",
            titleTamil = "மோட்டார் பூட்டு / அதிக சுமை (Motor Lock)",
            causeSinhala = "නූල් රොටරි හුක් එකේ එතීම, මැෂින් ෂාෆ්ට් තද වීම හෝ මෝටරයේ අධික යාන්ත්‍රික බරක් ඇතිවීම.",
            causeEnglish = "Thread caught tightly in rotary hook race, seized machine shaft, or heavy mechanical binding.",
            causeTamil = "ரோட்டரி ஹூக்கில் நூல் சிக்குதல், இயந்திர தண்டு இறுக்கமாதல் அல்லது அதிக இயந்திர சுமை.",
            solutionSinhala = """1. මැෂිම විදුලියෙන් විසන්ධි කරන්න.
2. හෑන්ඩ් වීල් එක අතින් කරකවා තද වී ඇත්දැයි බලන්න.
3. රොටරි හුක් එකේ එතී ඇති නූල් කොටස් සම්පූර්ණයෙන්ම ඉවත් කරන්න.
4. හුක් සහ ෂාෆ්ට් බුෂ් වලට තෙල් ස්වල්පයක් දමා නිදහසේ කැරකෙන බව තහවුරු කර නැවත ක්‍රියාත්මක කරන්න.""",
            solutionEnglish = """1. Turn OFF main power immediately.
2. Manually rotate handwheel to verify if machine head is mechanically jammed.
3. Remove bobbin case and hook retainer to extract jammed thread fragments.
4. Lubricate hook race and main shafts, test free rotation, and restart.""",
            solutionTamil = """1. மின்சாரத்தை உடனடியாக அணைக்கவும்.
2. கை சக்கரத்தை சுழற்றி இயந்திரம் இறுக்கமாக உள்ளதா என பார்க்கவும்.
3. ரோட்டரி ஹூக்கில் சிக்கியுள்ள நூல்களை அகற்றவும்.
4. எண்ணெய் இட்டு சுதந்திரமாக சுழல்வதை உறுதி செய்து மீண்டும் இயக்கவும்."""
        ),
        ErrorCodeItem(
            id = 2,
            code = "E-02",
            system = "Juki / Brother Servo Box",
            titleSinhala = "එන්කෝඩර් සෙන්සර් දෝෂය (Encoder Signal Error)",
            titleEnglish = "Encoder Signal / Synchronizer Failure",
            titleTamil = "என்கோடர் சிக்னல் பிழை (Encoder Error)",
            causeSinhala = "මෝටර් එන්කෝඩර් කේබලය බුරුල් වීම, සම්බන්ධකයේ දූවිලි බැඳීම හෝ සෙන්සර් පුවරුව අක්‍රිය වීම.",
            causeEnglish = "Motor encoder feedback signal lost, loose connector plug, optical wheel soiled by lint, or PCB failure.",
            causeTamil = "மோட்டார் என்கோடர் கேபிள் தளர்வாக இருத்தல், தூசு படிதல் அல்லது சென்சார் செயலிழப்பு.",
            solutionSinhala = """1. පාලන පෙට්ටියේ (Control box) මෝටර් සංඥා කේබල් ප්ලග් එක පරීක්ෂා කර තද කරන්න.
2. මෝටර් පියන ගලවා එන්කෝඩර් තැටියේ රැඳී ඇති දූවිලි ප්‍රවේශමෙන් පිසදමන්න.
3. දෝෂය පවතී නම් එන්කෝඩර් කේබලය හෝ සෙන්සර් එක අලුතින් යොදන්න.""",
            solutionEnglish = """1. Inspect and reseat the multi-pin encoder signal cable at the control box.
2. Open motor rear cap and clean optical encoder disc with clean dry compressed air.
3. Check wire harness continuity; replace encoder module if pulse signal remains absent.""",
            solutionTamil = """1. கட்டுப்பாட்டு பெட்டியில் என்கோடர் கேபிளை சரிபார்த்து இறுக்கவும்.
2. மோட்டார் மூடியை திறந்து என்கோடர் வட்டில் உள்ள தூசிகளை சுத்தம் செய்யவும்.
3. பிழை தொடர்ந்தால் என்கோடர் சென்சாரை மாற்றவும்."""
        ),
        ErrorCodeItem(
            id = 3,
            code = "E-03",
            system = "Jack A4 / C4 / Siruba",
            titleSinhala = "ඉඳිකටු ස්ථානගත කිරීමේ දෝෂය (Needle Positioning Error)",
            titleEnglish = "Needle Stop Synchronizer Positioning Error",
            titleTamil = "ஊசி நிலை சென்சார் பிழை (Needle Position Error)",
            causeSinhala = "ඉඳිකටුව නියමිත UP හෝ DOWN ස්ථානයේ නතර වීමට නොහැකි වීම, සින්ක්‍රොනයිසර් සෙන්සර් පිහිටීම වෙනස් වීම.",
            causeEnglish = "Machine failed to detect Up/Down needle stop position; synchronizer magnet out of alignment.",
            causeTamil = "ஊசி UP அல்லது DOWN நிலையில் நிற்கத் தவறுதல், சிங்க்ரோனைசர் காந்தம் விலகுதல்.",
            solutionSinhala = """1. හෑන්ඩ් වීල් එකේ සවිකර ඇති සින්ක්‍රොනයිසර් (Synchronizer) ඉස්කුරුප්පුව ලිහිල් වී ඇත්දැයි බලන්න.
2. ඉඳිකටු ඉහළ ස්ථානය (Up position) හෑන්ඩ් වීල් ලකුණට අනුකූලව නැවත සකසා ඉස්කුරුප්පුව තද කරන්න.
3. පාලක පැනලයේ Parameter P-02 හරහා නැවතුම් ස්ථානය යළි ක්‍රමාංකනය (Calibrate) කරන්න.""",
            solutionEnglish = """1. Inspect the synchronizer mounted inside the handwheel hub for loose clamping bolts.
2. Align needle bar timing mark to Up-position index and tighten synchronizer clamp.
3. Enter technician parameter mode (P-02) to recalibrate electronic stop angles.""",
            solutionTamil = """1. கை சக்கரத்தில் உள்ள சிங்க்ரோனைசர் திருகு தளர்ந்துள்ளதா என பார்க்கவும்.
2. ஊசி மேல் நிலையை (Up position) சரியாக சீரமைத்து திருகினை இறுக்கவும்.
3. அளவுருக்கள் (Parameters) மூலம் ஊசி நிலையை மீண்டும் அளவீடு செய்யவும்."""
        ),
        ErrorCodeItem(
            id = 4,
            code = "E-07",
            system = "Generic Industrial Servo / Juki / Jack",
            titleSinhala = "අධි වෝල්ටීයතා ආරක්ෂණ දෝෂය (Over-Voltage Protection)",
            titleEnglish = "DC Bus Over-Voltage Protection Triggered",
            titleTamil = "அதிக மின்னழுத்த பாதுகாப்பு பிழை (Over Voltage)",
            causeSinhala = "කර්මාන්ත ශාලාවේ ප්‍රධාන විදුලි සැපයුමේ වෝල්ටීයතාව 250V ට වඩා වැඩි වීම හෝ බ්‍රේකින් රෙසිස්ටරය (Brake resistor) බිඳ වැටීම.",
            causeEnglish = "AC main supply voltage surged above safe threshold (>250VAC) or regenerative braking circuit open.",
            causeTamil = "மின்சார விநியோகத்தில் மின்னழுத்தம் 250Vக்கு மேல் அதிகரித்தல் அல்லது பிரேக் ரெசிஸ்டர் பழுது.",
            solutionSinhala = """1. බහුමාපකයක් (Multimeter) මඟින් ප්‍රධාන විදුලි වෝල්ටීයතාවය මැන බලන්න.
2. වෝල්ටීයතාව 220V - 230V අතර පරාසයට පත්වන තෙක් මැෂිම ක්‍රියාත්මක නොකරන්න (වෝල්ටීයතා ස්ථායීකාරකයක් - Stabilizer යොදන්න).
3. පාලන පෙට්ටියේ බ්‍රේක් රෙසිස්ටර් ප්‍රතිරෝධය (40 - 50 Ohms) පරීක්ෂා කරන්න.""",
            solutionEnglish = """1. Measure incoming AC line voltage using a true-RMS digital multimeter.
2. If plant voltage exceeds 240VAC, wait for line stabilizer regulation before operating.
3. Inspect internal dynamic braking resistor (nominal 47Ω); replace if open-circuit.""",
            solutionTamil = """1. மல்டிமீட்டர் மூலம் பிரதான மின்சார மின்னழுத்தத்தை அளவிடவும்.
2. மின்னழுத்தம் 220V-230V நிலைக்கு வரும் வரை காத்திருக்கவும்.
3. கட்டுப்பாட்டு பெட்டியின் பிரேக் ரெசிஸ்டரை சரிபார்க்கவும்."""
        ),
        ErrorCodeItem(
            id = 5,
            code = "E-08",
            system = "Juki DDL-9000 / Brother S-7200",
            titleSinhala = "හිස එසවීමේ ආරක්ෂක ස්විචය (Safety Tilt Switch Open)",
            titleEnglish = "Machine Head Tilt Safety Switch Tripped",
            titleTamil = "தலை சாய்க்கும் பாதுகாப்பு சுவிட்ச் பிழை (Tilt Switch)",
            causeSinhala = "මැෂින් හිස පිටුපසට නවා තිබීම හෝ තෙල් පෑන් එකේ ආරක්ෂිත මයික්‍රොස්විචය ක්‍රියාත්මක වී නොතිබීම.",
            causeEnglish = "Machine head tilted backwards for maintenance, or safety microswitch arm disconnected/misaligned.",
            causeTamil = "இயந்திர தலை பின்னால் சாய்ந்திருத்தல் அல்லது பாதுகாப்பு சுவிட்ச் செயலிழப்பு.",
            solutionSinhala = """1. මැෂින් හිස මේසය මත නිවැරදිව තිරස්ව පහත් කරන්න.
2. මැෂින් පාමුල ඇති ආරක්ෂිත ස්විචය සහ චුම්බකය (Magnet switch) නියමිත ස්ථානයේ පිහිටා ඇත්දැයි බලන්න.
3. ස්විච් කේබලය විසන්ධි වී ඇත්නම් නැවත සම්බන්ධ කරන්න.""",
            solutionEnglish = """1. Lower the machine head fully onto the table rubber cushions.
2. Inspect the safety interlock microswitch and magnetic target under the bed.
3. Ensure switch plunger engages firmly; reseat sensor cable connector.""",
            solutionTamil = """1. இயந்திர தலையை மேசையில் சரியாக கீழே இறக்கவும்.
2. கீழே உள்ள பாதுகாப்பு சுவிட்ச் மற்றும் காந்தம் சரியான இடத்தில் உள்ளதா என பார்க்கவும்.
3. சுவிட்ச் கேபிளை சரியாக இணைக்கவும்."""
        ),
        ErrorCodeItem(
            id = 6,
            code = "E-14",
            system = "Juki / Typical Auto-Trimmer Models",
            titleSinhala = "ස්වයංක්‍රීය නූල් කැපුම් දෝෂය (Auto Thread Trimmer Fault)",
            titleEnglish = "Automatic Thread Trimmer Solenoid / Knife Stall",
            titleTamil = "தானியங்கி நூல் வெட்டி பிழை (Thread Trimmer Fault)",
            causeSinhala = "නූල් කැපුම් පිහිය (Trimmer knife) සිරවීම, සොලෙනොයිඩ් (Solenoid) එකට ප්‍රමාණවත් ධාරාවක් නොලැබීම.",
            causeEnglish = "Moving trimmer knife jammed by thread bunching, cam follower stuck, or solenoid drive pulse failure.",
            causeTamil = "நூல் வெட்டும் கத்தி சிக்குதல் அல்லது சோலனாய்டு இயங்காதிருத்தல்.",
            solutionSinhala = """1. ඉඳිකටු තහඩුව ගලවා නූල් කැපුම් පිහි තලය පිරිසිදු කරන්න.
2. නූල් කැපුම් කැම (Trimmer cam) ටයිමිං නිවැරදි දැයි පරීක්ෂා කරන්න.
3. සොලෙනොයිඩ් සම්බන්ධකය පරීක්ෂා කර පැනලයෙන් ට්‍රිමර් ටෙස්ට් ක්‍රියාත්මක කරන්න.""",
            solutionEnglish = """1. Remove throat plate and clear compacted thread lint around moving and fixed knives.
2. Inspect trimmer cam engagement timing; ensure knife stroke returns fully.
3. Test solenoid resistance (~4Ω to 8Ω) and run diagnostic test from technician panel.""",
            solutionTamil = """1. ஊசி தட்டை கழற்றி நூல் வெட்டும் கத்தியை சுத்தம் செய்யவும்.
2. டிரிம்மர் கேம் (Trimmer cam) டைமிங்கை சரிபார்க்கவும்.
3. சோலனாய்டு இணைப்பை சோதித்து இயக்கவும்."""
        )
    )

    fun getDefaultMaintenanceTasks(): List<MaintenanceTask> = listOf(
        MaintenanceTask(
            id = 1,
            frequency = "DAILY",
            taskSinhala = "රොටරි හුක් සහ ඉඳිකටු තහඩුව යට ඇති නූල් දූවිලි (Lint) බ්ලෝවර් මඟින් පිඹ පිරිසිදු කිරීම.",
            taskEnglish = "Blow out lint and thread dust from rotary hook race and underneath throat plate.",
            taskTamil = "ரோட்டரி ஹூக் மற்றும் ஊசி தட்டின் அடியில் உள்ள பஞ்சு தூசிகளை சுத்தம் செய்தல்.",
            isCompleted = true,
            lastCompletedDate = "Today"
        ),
        MaintenanceTask(
            id = 2,
            frequency = "DAILY",
            taskSinhala = "ඔයිල් පෑන් හි තෙල් මට්ටම HIGH සහ LOW සලකුණු අතර පවතී දැයි පරීක්ෂා කිරීම.",
            taskEnglish = "Check oil pan level is safely between HIGH and LOW level markings.",
            taskTamil = "ஆயில் பானில் எண்ணெய் அளவு HIGH மற்றும் LOW இடையே உள்ளதா என சரிபார்த்தல்.",
            isCompleted = true,
            lastCompletedDate = "Today"
        ),
        MaintenanceTask(
            id = 3,
            frequency = "DAILY",
            taskSinhala = "ඉඳිකටු තුඩේ සීරීම් හෝ නැමීම් ඇත්දැයි අතින් ස්පර්ශ කර බැලීම.",
            taskEnglish = "Inspect needle tip for burrs, blunts, or deflection by fingernail test.",
            taskTamil = "ஊசி முனையில் கீறல்கள் அல்லது வளைவுகள் உள்ளதா என சோதித்தல்.",
            isCompleted = false,
            lastCompletedDate = ""
        ),
        MaintenanceTask(
            id = 4,
            frequency = "WEEKLY",
            taskSinhala = "ඔයිල් පොම්පයේ තෙල් පෙරහන (Oil filter mesh) ගලවා භූමිතෙල් හෝ ක්ලීනර් මඟින් සේදීම.",
            taskEnglish = "Clean oil pump sump filter wire mesh to prevent pump starvation.",
            taskTamil = "எண்ணெய் பம்ப் வடிகட்டியை கழற்றி மண்ணெண்ணெய் கொண்டு கழுவுதல்.",
            isCompleted = false,
            lastCompletedDate = ""
        ),
        MaintenanceTask(
            id = 5,
            frequency = "WEEKLY",
            taskSinhala = "රෙදි අදින දත් (Feed dog) උස සහ ඉස්කුරුප්පු තද බව පරීක්ෂා කිරීම.",
            taskEnglish = "Inspect feed dog teeth height (0.8mm - 1.0mm above plate) and tight screw seating.",
            taskTamil = "துணி இழுக்கும் பற்களின் உயரம் (0.8-1.0 மிமீ) மற்றும் திருகுகளை சரிபார்த்தல்.",
            isCompleted = true,
            lastCompletedDate = "Oct 05"
        ),
        MaintenanceTask(
            id = 6,
            frequency = "WEEKLY",
            taskSinhala = "රොටරි හුක් ක්ලියරන්ස් (0.05mm - 0.1mm) පිලර් ගේජ් මඟින් මැන තහවුරු කිරීම.",
            taskEnglish = "Measure rotary hook to needle clearance with 0.05mm feeler gauge.",
            taskTamil = "ரோட்டரி ஹூக் இடைவெளியை (0.05 மிமீ) ஃபீலர் கேஜ் மூலம் அளவிடுதல்.",
            isCompleted = false,
            lastCompletedDate = ""
        ),
        MaintenanceTask(
            id = 7,
            frequency = "MONTHLY",
            taskSinhala = "ගියර් සහ කැම් (Gears & mechanical cams) සඳහා විශේෂිත සුදු ග්‍රීස් යෙදීම.",
            taskEnglish = "Apply dedicated industrial white lithium grease to enclosed bevel gears and cams.",
            taskTamil = "கியர்கள் மற்றும் கேம்களுக்கு தொழில்துறை கிரீஸ் இடுதல்.",
            isCompleted = false,
            lastCompletedDate = ""
        ),
        MaintenanceTask(
            id = 8,
            frequency = "MONTHLY",
            taskSinhala = "මෝටර් බෙල්ට් ආතතිය (V-belt deflection 10mm - 15mm) සහ ගෙවීයාම පරීක්ෂා කිරීම.",
            taskEnglish = "Inspect V-belt tension (10mm - 15mm flex under thumb) and pulley alignment.",
            taskTamil = "மோட்டார் பெல்ட் இறுக்கம் (10-15 மிமீ) மற்றும் சீரமைப்பை சரிபார்த்தல்.",
            isCompleted = false,
            lastCompletedDate = ""
        )
    )
}

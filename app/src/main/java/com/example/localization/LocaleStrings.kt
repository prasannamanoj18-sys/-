package com.example.localization

object LocaleStrings {

    fun get(key: String, language: AppLanguage): String {
        val map = stringsMap[key] ?: return key
        return map[language] ?: map[AppLanguage.SINHALA] ?: map[AppLanguage.ENGLISH] ?: key
    }

    // Common UI keys
    const val APP_NAME = "app_name"
    const val APP_SUBTITLE = "app_subtitle"
    const val LANGUAGE_SELECTOR_TITLE = "language_selector_title"
    const val NAV_HOME = "nav_home"
    const val NAV_GUIDES = "nav_guides"
    const val NAV_TROUBLESHOOT = "nav_troubleshoot"
    const val NAV_ERROR_CODES = "nav_error_codes"
    const val NAV_MAINTENANCE = "nav_maintenance"
    const val NAV_CALCULATORS = "nav_calculators"
    const val NAV_MEMBERSHIP = "nav_membership"
    const val NAV_ADMIN = "nav_admin"
    const val NAV_SETTINGS = "nav_settings"

    // Home screen
    const val HOME_WELCOME = "home_welcome"
    const val HOME_BANNER_SUBTITLE = "home_banner_subtitle"
    const val HOME_SEARCH_HINT = "home_search_hint"
    const val HOME_QUICK_ACTIONS = "home_quick_actions"
    const val HOME_MACHINES_TITLE = "home_machines_title"
    const val HOME_FEATURED_TIP = "home_featured_tip"
    const val HOME_STATUS_BOOKMARKS = "home_status_bookmarks"
    const val HOME_STATUS_MAINTENANCE = "home_status_maintenance"
    const val HOME_STATUS_TECH_LEVEL = "home_status_tech_level"

    // Action Cards
    const val CARD_TROUBLESHOOT_TITLE = "card_troubleshoot_title"
    const val CARD_TROUBLESHOOT_DESC = "card_troubleshoot_desc"
    const val CARD_MANUALS_TITLE = "card_manuals_title"
    const val CARD_MANUALS_DESC = "card_manuals_desc"
    const val CARD_ERRORS_TITLE = "card_errors_title"
    const val CARD_ERRORS_DESC = "card_errors_desc"
    const val CARD_MAINTENANCE_TITLE = "card_maintenance_title"
    const val CARD_MAINTENANCE_DESC = "card_maintenance_desc"
    const val CARD_CALCULATORS_TITLE = "card_calculators_title"
    const val CARD_CALCULATORS_DESC = "card_calculators_desc"
    const val CARD_MEMBERSHIP_TITLE = "card_membership_title"
    const val CARD_MEMBERSHIP_DESC = "card_membership_desc"

    // Categories
    const val CAT_ALL = "cat_all"
    const val CAT_TROUBLESHOOTING = "cat_troubleshooting"
    const val CAT_TIMING = "cat_timing"
    const val CAT_MAINTENANCE = "cat_maintenance"
    const val CAT_MANUALS = "cat_manuals"
    const val CAT_VIDEOS = "cat_videos"
    const val SEARCH_FILTER_HINT = "search_filter_hint"
    const val SEARCH_MANUALS_AND_VIDEOS = "search_manuals_and_videos"
    const val SEARCH_RESULTS_COUNT = "search_results_count"
    const val SEARCH_NO_RESULTS = "search_no_results"
    const val BADGE_MANUAL = "badge_manual"
    const val BADGE_VIDEO = "badge_video"
    const val BADGE_TIMING = "badge_timing"

    // Machine Types
    const val MACHINE_ALL = "machine_all"
    const val MACHINE_LOCKSTITCH = "machine_lockstitch"
    const val MACHINE_OVERLOCK = "machine_overlock"
    const val MACHINE_FLATLOCK = "machine_flatlock"
    const val MACHINE_BUTTONHOLE = "machine_buttonhole"
    const val MACHINE_BARTACK = "machine_bartack"

    // Content Interface
    const val CONTENT_SPEC_TITLE = "content_spec_title"
    const val CONTENT_STEPS_TITLE = "content_steps_title"
    const val CONTENT_VIEW_LANGUAGE = "content_view_language"
    const val CONTENT_ORIGINAL_TRANSLATIONS = "content_original_translations"
    const val CONTENT_DIFFICULTY = "content_difficulty"
    const val CONTENT_MACHINE_MODEL = "content_machine_model"
    const val CONTENT_BOOKMARK_SAVED = "content_bookmark_saved"
    const val CONTENT_BOOKMARK_REMOVED = "content_bookmark_removed"
    const val CONTENT_NO_ITEMS = "content_no_items"

    // Buttons
    const val BTN_SEARCH = "btn_search"
    const val BTN_CLEAR = "btn_clear"
    const val BTN_SAVE = "btn_save"
    const val BTN_DELETE = "btn_delete"
    const val BTN_EDIT = "btn_edit"
    const val BTN_ADD_NEW = "btn_add_new"
    const val BTN_CLOSE = "btn_close"
    const val BTN_BACK = "btn_back"
    const val BTN_RESET = "btn_reset"
    const val BTN_CALCULATE = "btn_calculate"
    const val BTN_CONFIRM = "btn_confirm"
    const val BTN_CANCEL = "btn_cancel"
    const val BTN_VIEW_ALL = "btn_view_all"
    const val BTN_MARK_DONE = "btn_mark_done"
    const val BTN_SWITCH_LANGUAGE = "btn_switch_language"
    const val BTN_QR_SCAN = "btn_qr_scan"

    // QR Scanner
    const val QR_SCANNER_TITLE = "qr_scanner_title"
    const val QR_INSTRUCTION = "qr_instruction"
    const val QR_RESULT_TITLE = "qr_result_title"
    const val QR_RESULT_URL_TYPE = "qr_result_url_type"
    const val QR_RESULT_TEXT_TYPE = "qr_result_text_type"
    const val QR_OPEN_LINK = "qr_open_link"
    const val QR_CONFIRM_OPEN = "qr_confirm_open"
    const val QR_SEARCH_MACHINE = "qr_search_machine"
    const val QR_PERMISSION_TITLE = "qr_permission_title"
    const val QR_PERMISSION_DESC = "qr_permission_desc"
    const val QR_PERMISSION_GRANT = "qr_permission_grant"
    const val QR_MANUAL_ENTRY_TITLE = "qr_manual_entry_title"
    const val QR_MANUAL_ENTRY_HINT = "qr_manual_entry_hint"
    const val QR_MANUAL_ENTRY_SUBMIT = "qr_manual_entry_submit"
    const val QR_CAMERA_ERROR = "qr_camera_error"
    const val QR_SCAN_AGAIN = "qr_scan_again"
    const val QR_INVALID_LINK = "qr_invalid_link"
    const val QR_COPY_RESULT = "qr_copy_result"
    const val QR_COPIED = "qr_copied"
    const val QR_TORCH_ON = "qr_torch_on"
    const val QR_TORCH_OFF = "qr_torch_off"
    const val QR_TORCH_DESC = "qr_torch_desc"
    const val QR_PICK_IMAGE = "qr_pick_image"
    const val QR_PICK_IMAGE_SUB = "qr_pick_image_sub"
    const val QR_FLIP_CAMERA = "qr_flip_camera"

    // Google Drive Files
    const val DRIVE_FILES_TITLE = "drive_files_title"
    const val DRIVE_FILES_SUBTITLE = "drive_files_subtitle"
    const val DRIVE_FETCH_BUTTON = "drive_fetch_button"
    const val DRIVE_FETCHING = "drive_fetching"
    const val DRIVE_FETCH_SUCCESS = "drive_fetch_success"
    const val DRIVE_FETCH_ERROR = "drive_fetch_error"
    const val DRIVE_SEARCH_HINT = "drive_search_hint"
    const val DRIVE_NO_FILES = "drive_no_files"
    const val DRIVE_OPEN_FILE = "drive_open_file"
    const val DRIVE_DOWNLOAD_FILE = "drive_download_file"
    const val DRIVE_ADD_FILE = "drive_add_file"
    const val DRIVE_FILE_NAME = "drive_file_name"
    const val DRIVE_FILE_LINK = "drive_file_link"
    const val DRIVE_FOLDER_ID_CONFIG = "drive_folder_id_config"
    const val DRIVE_TAB_ALL = "drive_tab_all"
    const val DRIVE_TAB_PDF = "drive_tab_pdf"
    const val DRIVE_TAB_VIDEO = "drive_tab_video"
    const val DRIVE_TAB_IMAGE = "drive_tab_image"
    const val DRIVE_TOTAL_FILES = "drive_total_files"

    // 5S Methodology (5S ක්‍රියාවලිය)
    const val FIVES_TITLE = "fives_title"
    const val FIVES_SUBTITLE = "fives_subtitle"
    const val FIVES_TAB_PILLARS = "fives_tab_pillars"
    const val FIVES_TAB_CHECKLIST = "fives_tab_checklist"
    const val FIVES_TAB_REDTAG = "fives_tab_redtag"
    const val FIVES_TAB_AUDIT = "fives_tab_audit"
    const val FIVES_RESET_CHECKLIST = "fives_reset_checklist"
    const val FIVES_PROGRESS = "fives_progress"
    const val FIVES_COMPLETED = "fives_completed"
    const val FIVES_ADD_REDTAG = "fives_add_redtag"
    const val FIVES_AUDIT_CALC = "fives_audit_calc"
    const val FIVES_SCORE_EXCELLENT = "fives_score_excellent"
    const val FIVES_SCORE_GOOD = "fives_score_good"
    const val FIVES_SCORE_IMPROVE = "fives_score_improve"

    // Error Codes
    const val ERRORS_HEADER_TITLE = "errors_header_title"
    const val ERRORS_SEARCH_HINT = "errors_search_hint"
    const val ERRORS_SYSTEM_LABEL = "errors_system_label"
    const val ERRORS_CAUSE_TITLE = "errors_cause_title"
    const val ERRORS_SOLUTION_TITLE = "errors_solution_title"

    // Maintenance
    const val PM_TITLE = "pm_title"
    const val PM_DAILY = "pm_daily"
    const val PM_WEEKLY = "pm_weekly"
    const val PM_MONTHLY = "pm_monthly"
    const val PM_COMPLETED_PROGRESS = "pm_completed_progress"
    const val PM_RESET_CHECKLIST = "pm_reset_checklist"

    // Calculators
    const val CALC_TITLE = "calc_title"
    const val CALC_NEEDLE_TAB = "calc_needle_tab"
    const val CALC_THREAD_TAB = "calc_thread_tab"
    const val CALC_STITCH_TAB = "calc_stitch_tab"
    const val CALC_SPEED_TAB = "calc_speed_tab"
    const val CALC_SELECT_METRIC = "calc_select_metric"
    const val CALC_RESULT_SINGER = "calc_result_singer"
    const val CALC_RESULT_SYSTEM = "calc_result_system"
    const val CALC_RECOMMENDED_FABRIC = "calc_recommended_fabric"

    // Membership & Account
    const val MEMBER_CARD_TITLE = "member_card_title"
    const val MEMBER_BADGE_LEVEL = "member_badge_level"
    const val MEMBER_FACTORY_NAME = "member_factory_name"
    const val MEMBER_EXP_YEARS = "member_exp_years"
    const val MEMBER_CERTIFICATION_PROGRESS = "member_certification_progress"
    const val MEMBER_QUIZ_TITLE = "member_quiz_title"
    const val MEMBER_QUIZ_SUBTITLE = "member_quiz_subtitle"
    const val MEMBER_QUIZ_START = "member_quiz_start"
    const val MEMBER_QUIZ_SCORE = "member_quiz_score"
    const val LEVEL_APPRENTICE = "level_apprentice"
    const val LEVEL_CERTIFIED = "level_certified"
    const val LEVEL_MASTER = "level_master"

    // User Account & Profile
    const val ACCOUNT_TITLE = "account_title"
    const val ACCOUNT_NAME_LABEL = "account_name_label"
    const val ACCOUNT_ROLE_LABEL = "account_role_label"
    const val ACCOUNT_FACTORY_LABEL = "account_factory_label"
    const val ACCOUNT_UPDATE_SUCCESS = "account_update_success"

    // Admin Dashboard
    const val ADMIN_TITLE = "admin_title"
    const val ADMIN_SUBTITLE = "admin_subtitle"
    const val ADMIN_ADD_ARTICLE = "admin_add_article"
    const val ADMIN_ADD_ERROR = "admin_add_error"
    const val ADMIN_TOTAL_ARTICLES = "admin_total_articles"
    const val ADMIN_TOTAL_ERRORS = "admin_total_errors"
    const val ADMIN_RESTORE_DEFAULTS = "admin_restore_defaults"
    const val ADMIN_LANGUAGE_SECTION_SINHALA = "admin_lang_section_sinhala"
    const val ADMIN_LANGUAGE_SECTION_ENGLISH = "admin_lang_section_english"
    const val ADMIN_LANGUAGE_SECTION_TAMIL = "admin_lang_section_tamil"
    const val ADMIN_FIELD_TITLE = "admin_field_title"
    const val ADMIN_FIELD_SUMMARY = "admin_field_summary"
    const val ADMIN_FIELD_STEPS = "admin_field_steps"
    const val ADMIN_FIELD_CATEGORY = "admin_field_category"
    const val ADMIN_FIELD_MACHINE = "admin_field_machine"
    const val ADMIN_FIELD_SPECS = "admin_field_specs"
    const val ADMIN_FIELD_DIFFICULTY = "admin_field_difficulty"
    const val ADMIN_SAVE_SUCCESS = "admin_save_success"
    const val ADMIN_DELETE_SUCCESS = "admin_delete_success"
    const val ADMIN_RESTORE_CONFIRM = "admin_restore_confirm"

    // Settings
    const val SETTINGS_TITLE = "settings_title"
    const val SETTINGS_LANGUAGE_TITLE = "settings_language_title"
    const val SETTINGS_LANGUAGE_DESC = "settings_language_desc"
    const val SETTINGS_THEME_TITLE = "settings_theme_title"
    const val SETTINGS_THEME_DARK = "settings_theme_dark"
    const val SETTINGS_THEME_LIGHT = "settings_theme_light"
    const val SETTINGS_ABOUT_TITLE = "settings_about_title"
    const val SETTINGS_ABOUT_DESC = "settings_about_desc"
    const val SETTINGS_VERSION = "settings_version"
    const val SETTINGS_SHARE_TITLE = "settings_share_title"
    const val SETTINGS_SHARE_DESC = "settings_share_desc"
    const val BTN_SHARE_APP = "btn_share_app"
    const val BTN_COPY_LINK = "btn_copy_link"
    const val MSG_LINK_COPIED = "msg_link_copied"

    // Error & Notification messages
    const val MSG_ERROR_REQUIRED_FIELDS = "msg_error_required_fields"
    const val MSG_SUCCESS_SAVED = "msg_success_saved"
    const val MSG_SUCCESS_LANGUAGE_CHANGED = "msg_success_language_changed"
    const val MSG_CONFIRM_DELETE = "msg_confirm_delete"

    private val stringsMap: Map<String, Map<AppLanguage, String>> = mapOf(
        APP_NAME to mapOf(
            AppLanguage.SINHALA to "කාර්මික දැනුම",
            AppLanguage.ENGLISH to "Sewing Machine Technical Knowledge",
            AppLanguage.TAMIL to "தொழில்நுட்ப அறிவு"
        ),
        APP_SUBTITLE to mapOf(
            AppLanguage.SINHALA to "කාර්මික මහන මැෂින් තාක්ෂණික අත්වැල සහ පුහුණුව",
            AppLanguage.ENGLISH to "Industrial Sewing Machine Technical Knowledge & Training",
            AppLanguage.TAMIL to "தையல் இயந்திர தொழில்நுட்ப அறிவு மற்றும் பயிற்சி"
        ),
        LANGUAGE_SELECTOR_TITLE to mapOf(
            AppLanguage.SINHALA to "භාෂාව / Language / மொழி",
            AppLanguage.ENGLISH to "Language / භාෂාව / மொழி",
            AppLanguage.TAMIL to "மொழி / Language / භාෂාව"
        ),
        NAV_HOME to mapOf(
            AppLanguage.SINHALA to "මුල් පිටුව",
            AppLanguage.ENGLISH to "Home",
            AppLanguage.TAMIL to "முகப்பு"
        ),
        NAV_GUIDES to mapOf(
            AppLanguage.SINHALA to "තාක්ෂණික දැනුම",
            AppLanguage.ENGLISH to "Technical Guides",
            AppLanguage.TAMIL to "தொழில்நுட்ப வழிகாட்டி"
        ),
        NAV_TROUBLESHOOT to mapOf(
            AppLanguage.SINHALA to "දෝෂ විසඳුම්",
            AppLanguage.ENGLISH to "Troubleshooting",
            AppLanguage.TAMIL to "பழுது நீக்குதல்"
        ),
        NAV_ERROR_CODES to mapOf(
            AppLanguage.SINHALA to "දෝෂ කේත",
            AppLanguage.ENGLISH to "Error Codes",
            AppLanguage.TAMIL to "பிழைக் குறியீடுகள்"
        ),
        NAV_MAINTENANCE to mapOf(
            AppLanguage.SINHALA to "නඩත්තු",
            AppLanguage.ENGLISH to "Maintenance",
            AppLanguage.TAMIL to "பராமரிப்பு"
        ),
        NAV_CALCULATORS to mapOf(
            AppLanguage.SINHALA to "ගණක මෙවලම්",
            AppLanguage.ENGLISH to "Calculators",
            AppLanguage.TAMIL to "கருவிகள்"
        ),
        NAV_MEMBERSHIP to mapOf(
            AppLanguage.SINHALA to "සාමාජිකත්වය",
            AppLanguage.ENGLISH to "Membership",
            AppLanguage.TAMIL to "உறுப்பினர் நிலை"
        ),
        NAV_ADMIN to mapOf(
            AppLanguage.SINHALA to "පරිපාලක",
            AppLanguage.ENGLISH to "Admin",
            AppLanguage.TAMIL to "நிர்வாகம்"
        ),
        NAV_SETTINGS to mapOf(
            AppLanguage.SINHALA to "සැකසුම්",
            AppLanguage.ENGLISH to "Settings",
            AppLanguage.TAMIL to "அமைப்புகள்"
        ),

        // Home
        HOME_WELCOME to mapOf(
            AppLanguage.SINHALA to "ආයුබෝවන් කාර්මික ශිල්පී",
            AppLanguage.ENGLISH to "Welcome Technician",
            AppLanguage.TAMIL to "வணக்கம் தொழில்நுட்ப வல்லுநர்"
        ),
        HOME_BANNER_SUBTITLE to mapOf(
            AppLanguage.SINHALA to "ඇඟලුම් කර්මාන්තශාලා මහන මැෂින් නඩත්තු සහ ටයිමිං ප්‍රවීණත්වය",
            AppLanguage.ENGLISH to "Garment Factory Sewing Machine Maintenance & Timing Mastery",
            AppLanguage.TAMIL to "ஆடை தொழிற்சாலை தையல் இயந்திர பராமரிப்பு மற்றும் நேர சீரமைப்பு"
        ),
        HOME_SEARCH_HINT to mapOf(
            AppLanguage.SINHALA to "දෝෂ, මැෂින් හෝ ටයිමිං සොයන්න...",
            AppLanguage.ENGLISH to "Search faults, machines or timing...",
            AppLanguage.TAMIL to "பிழைகள், இயந்திரங்கள் அல்லது நேரம் தேடுக..."
        ),
        HOME_QUICK_ACTIONS to mapOf(
            AppLanguage.SINHALA to "ප්‍රධාන තාක්ෂණික අංශ",
            AppLanguage.ENGLISH to "Main Technical Sections",
            AppLanguage.TAMIL to "முக்கிய தொழில்நுட்ப பிரிவுகள்"
        ),
        HOME_MACHINES_TITLE to mapOf(
            AppLanguage.SINHALA to "මැෂින් කාණ්ඩය තෝරන්න",
            AppLanguage.ENGLISH to "Select Machine Type",
            AppLanguage.TAMIL to "இயந்திர வகையைத் தேர்ந்தெடுக்கவும்"
        ),
        HOME_FEATURED_TIP to mapOf(
            AppLanguage.SINHALA to "දවසේ කාර්මික ඉඟිය: රොටරි හුක් ටයිමිං හිදී ඉඳිකටු ඇසට ඉහළින් 0.05mm - 0.1mm හිඩැසක් තබාගන්න.",
            AppLanguage.ENGLISH to "Technician Tip of the Day: Maintain 0.05mm - 0.1mm hook point to needle scarf clearance to prevent needle deflection.",
            AppLanguage.TAMIL to "தொழில்நுட்ப குறிப்பு: ஊசி வளைவதைத் தடுக்க ரோட்டரி ஹூக் முனைக்கும் ஊசிக்கும் இடையே 0.05 மிமீ இடைவெளியை பராமரிக்கவும்."
        ),
        HOME_STATUS_BOOKMARKS to mapOf(
            AppLanguage.SINHALA to "සුරැකි මාර්ගෝපදේශ",
            AppLanguage.ENGLISH to "Bookmarked Guides",
            AppLanguage.TAMIL to "சேமித்த வழிகாட்டிகள்"
        ),
        HOME_STATUS_MAINTENANCE to mapOf(
            AppLanguage.SINHALA to "සම්පූර්ණ කළ නඩත්තු",
            AppLanguage.ENGLISH to "Completed PMs",
            AppLanguage.TAMIL to "முடிந்த பராமரிப்புகள்"
        ),
        HOME_STATUS_TECH_LEVEL to mapOf(
            AppLanguage.SINHALA to "ශිල්පී ශ්‍රේණිය",
            AppLanguage.ENGLISH to "Technician Level",
            AppLanguage.TAMIL to "தொழில்நுட்ப நிலை"
        ),

        // Action cards
        CARD_TROUBLESHOOT_TITLE to mapOf(
            AppLanguage.SINHALA to "දෝෂ සහ විසඳුම්",
            AppLanguage.ENGLISH to "Troubleshooting & Fixes",
            AppLanguage.TAMIL to "பழுது நீக்குதல் மற்றும் தீர்வுகள்"
        ),
        CARD_TROUBLESHOOT_DESC to mapOf(
            AppLanguage.SINHALA to "නූල් කැඩීම, මැහුම් පැනීම, ඉඳිකටු කැඩීම ඇතුළු ප්‍රධාන දෝෂ",
            AppLanguage.ENGLISH to "Thread breakage, skipped stitches, needle break & tension fixes",
            AppLanguage.TAMIL to "நூல் அறுபடுதல், தையல் தவறுதல், ஊசி உடைதல் போன்ற பிழைகள்"
        ),
        CARD_MANUALS_TITLE to mapOf(
            AppLanguage.SINHALA to "ටයිමිං සහ සැකසුම්",
            AppLanguage.ENGLISH to "Timing & Adjustments",
            AppLanguage.TAMIL to "நேரம் மற்றும் சீரமைப்பு"
        ),
        CARD_MANUALS_DESC to mapOf(
            AppLanguage.SINHALA to "හුක් ටයිමිං, ලූපර් සැකසුම්, ඉඳිකටු බාර් උස සහ පීඩන පාදම",
            AppLanguage.ENGLISH to "Rotary hook timing, looper settings, needle bar height & feed dog",
            AppLanguage.TAMIL to "ஹூக் டைமிங், லூப்பர் சீரமைப்பு, ஊசி பார் உயரம் மற்றும் அழுத்தம்"
        ),
        CARD_ERRORS_TITLE to mapOf(
            AppLanguage.SINHALA to "ඉලෙක්ට්‍රොනික දෝෂ කේත",
            AppLanguage.ENGLISH to "Electronic Error Codes",
            AppLanguage.TAMIL to "மின்னணு பிழைக் குறியீடுகள்"
        ),
        CARD_ERRORS_DESC to mapOf(
            AppLanguage.SINHALA to "Juki, Brother, Jack, Siruba මෝටර් පෙට්ටි දෝෂ කේත සහ පිළියම්",
            AppLanguage.ENGLISH to "Direct drive motor error codes (E-01 to E-14) & step-by-step repairs",
            AppLanguage.TAMIL to "டைரக்ட் டிரைவ் மோட்டார் பிழைக் குறியீடுகள் மற்றும் பழுதுநீக்கும் முறைகள்"
        ),
        CARD_MAINTENANCE_TITLE to mapOf(
            AppLanguage.SINHALA to "නඩත්තු චෙක්ලිස්ට්",
            AppLanguage.ENGLISH to "Preventive Maintenance",
            AppLanguage.TAMIL to "தடுப்பு பராமரிப்பு பட்டியல்"
        ),
        CARD_MAINTENANCE_DESC to mapOf(
            AppLanguage.SINHALA to "දෛනික, සතිපතා සහ මාසික තෙල් දැමීම සහ පිරිසිදු කිරීම",
            AppLanguage.ENGLISH to "Daily, weekly and monthly oiling, pump checks & cleaning routines",
            AppLanguage.TAMIL to "தினசரி, வாராந்திர மற்றும் மாதாந்திர எண்ணெய் இடுதல் மற்றும் சுத்தம்"
        ),
        CARD_CALCULATORS_TITLE to mapOf(
            AppLanguage.SINHALA to "කාර්මික ගණක මෙවලම්",
            AppLanguage.ENGLISH to "Technician Calculators",
            AppLanguage.TAMIL to "தொழில்நுட்ப கருவிகள்"
        ),
        CARD_CALCULATORS_DESC to mapOf(
            AppLanguage.SINHALA to "ඉඳිකටු ප්‍රමාණ පරිවර්තනය (Metric/Singer), නූල් ගැලපීම, SPI",
            AppLanguage.ENGLISH to "Needle size chart (Nm/Singer), thread matching & RPM speed math",
            AppLanguage.TAMIL to "ஊசி அளவு அட்டவணை, நூல் பொருத்தம் மற்றும் வேகக் கணக்கீடு"
        ),
        CARD_MEMBERSHIP_TITLE to mapOf(
            AppLanguage.SINHALA to "සාමාජිකත්වය සහ සහතික",
            AppLanguage.ENGLISH to "Membership & Training",
            AppLanguage.TAMIL to "உறுப்பினர் மற்றும் பயிற்சி"
        ),
        CARD_MEMBERSHIP_DESC to mapOf(
            AppLanguage.SINHALA to "කාර්මික ශිල්පී ඩිජිටල් හැඳුනුම්පත සහ තාක්ෂණික පරීක්ෂණ",
            AppLanguage.ENGLISH to "Digital technician badge, skill progression and proficiency quiz",
            AppLanguage.TAMIL to "டிஜிட்டல் அடையாள அட்டை மற்றும் தொழில்நுட்ப தேர்வு வினாடி வினா"
        ),

        // Categories
        CAT_ALL to mapOf(
            AppLanguage.SINHALA to "සියල්ල",
            AppLanguage.ENGLISH to "All",
            AppLanguage.TAMIL to "அனைத்தும்"
        ),
        CAT_TROUBLESHOOTING to mapOf(
            AppLanguage.SINHALA to "දෝෂ හඳුනාගැනීම",
            AppLanguage.ENGLISH to "Troubleshooting",
            AppLanguage.TAMIL to "பழுது நீக்குதல்"
        ),
        CAT_TIMING to mapOf(
            AppLanguage.SINHALA to "ටයිමිං සැකසුම්",
            AppLanguage.ENGLISH to "Timing Adjustments",
            AppLanguage.TAMIL to "நேர சீரமைப்பு"
        ),
        CAT_MAINTENANCE to mapOf(
            AppLanguage.SINHALA to "නඩත්තු උපදෙස්",
            AppLanguage.ENGLISH to "Maintenance",
            AppLanguage.TAMIL to "பராமரிப்பு"
        ),
        CAT_MANUALS to mapOf(
            AppLanguage.SINHALA to "මැෂින් මාර්ගෝපදේශ (Manuals)",
            AppLanguage.ENGLISH to "Machine Manuals",
            AppLanguage.TAMIL to "இயந்திர வழிகாட்டிகள்"
        ),
        CAT_VIDEOS to mapOf(
            AppLanguage.SINHALA to "පුහුණු වීඩියෝ (Training Videos)",
            AppLanguage.ENGLISH to "Training Videos",
            AppLanguage.TAMIL to "பயிற்சி வீடியோக்கள்"
        ),
        SEARCH_FILTER_HINT to mapOf(
            AppLanguage.SINHALA to "මැෂින් මොඩලය (උදා: DDL-8700, M900) හෝ මැනුවල්/වීඩියෝ සොයන්න...",
            AppLanguage.ENGLISH to "Search machine model (e.g. DDL-8700, M900) or manual/video keyword...",
            AppLanguage.TAMIL to "மாதிரி (DDL-8700, M900) அல்லது கையேடு/வீடியோ தேடுக..."
        ),
        SEARCH_MANUALS_AND_VIDEOS to mapOf(
            AppLanguage.SINHALA to "මැනුවල් සහ පුහුණු වීඩියෝ සොයන්න",
            AppLanguage.ENGLISH to "Find Technical Manuals & Training Videos",
            AppLanguage.TAMIL to "தொழில்நுட்ப கையேடுகள் & வீடியோக்களைக் கண்டறியவும்"
        ),
        SEARCH_RESULTS_COUNT to mapOf(
            AppLanguage.SINHALA to "මැනුවල් / වීඩියෝ හමුවිය",
            AppLanguage.ENGLISH to "manuals & guides found",
            AppLanguage.TAMIL to "கையேடுகள் / வழிகாட்டிகள் கண்டறியப்பட்டன"
        ),
        SEARCH_NO_RESULTS to mapOf(
            AppLanguage.SINHALA to "සොයන ලද මැෂින් මොඩලයට හෝ මූල පදයට අදාළ මැනුවල් හෝ වීඩියෝ හමු නොවීය.",
            AppLanguage.ENGLISH to "No technical manuals or training videos found matching your search.",
            AppLanguage.TAMIL to "உங்கள் தேடலுடன் பொருந்தக்கூடிய கையேடுகள் அல்லது வீடியோக்கள் எதுவும் கிடைக்கவில்லை."
        ),
        BADGE_MANUAL to mapOf(
            AppLanguage.SINHALA to "මැනුවල්",
            AppLanguage.ENGLISH to "Manual",
            AppLanguage.TAMIL to "கையேடு"
        ),
        BADGE_VIDEO to mapOf(
            AppLanguage.SINHALA to "වීඩියෝ",
            AppLanguage.ENGLISH to "Video",
            AppLanguage.TAMIL to "வீடியோ"
        ),
        BADGE_TIMING to mapOf(
            AppLanguage.SINHALA to "ටයිමිං",
            AppLanguage.ENGLISH to "Timing",
            AppLanguage.TAMIL to "டைமிங்"
        ),

        // Machine Types
        MACHINE_ALL to mapOf(
            AppLanguage.SINHALA to "සියලු මැෂින්",
            AppLanguage.ENGLISH to "All Machines",
            AppLanguage.TAMIL to "அனைத்து இயந்திரங்கள்"
        ),
        MACHINE_LOCKSTITCH to mapOf(
            AppLanguage.SINHALA to "මහන මැෂින් (General)",
            AppLanguage.ENGLISH to "Standard Machine",
            AppLanguage.TAMIL to "நிலையான இயந்திரம்"
        ),
        MACHINE_OVERLOCK to mapOf(
            AppLanguage.SINHALA to "ඕවර්ලොක් (Overlock)",
            AppLanguage.ENGLISH to "Overlock / Serger",
            AppLanguage.TAMIL to "ஓவர்லாக் (Overlock)"
        ),
        MACHINE_FLATLOCK to mapOf(
            AppLanguage.SINHALA to "ෆ්ලැට්ලොක් (Flatlock)",
            AppLanguage.ENGLISH to "Flatlock / Interlock",
            AppLanguage.TAMIL to "பிளாட்லாக் (Flatlock)"
        ),
        MACHINE_BUTTONHOLE to mapOf(
            AppLanguage.SINHALA to "බොත්තම් කාස (Buttonhole)",
            AppLanguage.ENGLISH to "Buttonhole Machine",
            AppLanguage.TAMIL to "பட்டன்ஹோல் (Buttonhole)"
        ),
        MACHINE_BARTACK to mapOf(
            AppLanguage.SINHALA to "බාටැක් (Bartack)",
            AppLanguage.ENGLISH to "Bartack Machine",
            AppLanguage.TAMIL to "பார்டேக் (Bartack)"
        ),

        // Content
        CONTENT_SPEC_TITLE to mapOf(
            AppLanguage.SINHALA to "ප්‍රධාන කාර්මික පිරිවිතර සහ හිඩැස් (Clearance)",
            AppLanguage.ENGLISH to "Key Technical Specifications & Clearances",
            AppLanguage.TAMIL to "முக்கிய தொழில்நுட்ப விவரக்குறிப்புகள் மற்றும் இடைவெளி"
        ),
        CONTENT_STEPS_TITLE to mapOf(
            AppLanguage.SINHALA to "පියවරෙන් පියවර විසඳුම් සහ සැකසුම් මාර්ගෝපදේශය",
            AppLanguage.ENGLISH to "Step-by-Step Technical Repair & Adjustment Guide",
            AppLanguage.TAMIL to "படிப்படியான தொழில்நுட்ப பழுது மற்றும் சீரமைப்பு வழிகாட்டி"
        ),
        CONTENT_VIEW_LANGUAGE to mapOf(
            AppLanguage.SINHALA to "අන්තර්ගතය බලන භාෂාව",
            AppLanguage.ENGLISH to "Content View Language",
            AppLanguage.TAMIL to "உள்ளடக்கத்தை பார்க்கும் மொழி"
        ),
        CONTENT_ORIGINAL_TRANSLATIONS to mapOf(
            AppLanguage.SINHALA to "භාෂා පරිවර්තන සංසන්දනය",
            AppLanguage.ENGLISH to "Compare Multi-Language Translations",
            AppLanguage.TAMIL to "மொழிபெயர்ப்பு ஒப்பீடு"
        ),
        CONTENT_DIFFICULTY to mapOf(
            AppLanguage.SINHALA to "දුෂ්කරතා මට්ටම",
            AppLanguage.ENGLISH to "Skill Level",
            AppLanguage.TAMIL to "திறன் நிலை"
        ),
        CONTENT_MACHINE_MODEL to mapOf(
            AppLanguage.SINHALA to "මැෂින් මාදිලිය",
            AppLanguage.ENGLISH to "Machine Model",
            AppLanguage.TAMIL to "இயந்திர மாதிரி"
        ),
        CONTENT_BOOKMARK_SAVED to mapOf(
            AppLanguage.SINHALA to "මාර්ගෝපදේශය සුරැකි ලැයිස්තුවට එක් කරන ලදී",
            AppLanguage.ENGLISH to "Guide added to bookmarks",
            AppLanguage.TAMIL to "வழிகாட்டி சேமிக்கப்பட்டது"
        ),
        CONTENT_BOOKMARK_REMOVED to mapOf(
            AppLanguage.SINHALA to "සුරැකි ලැයිස්තුවෙන් ඉවත් කරන ලදී",
            AppLanguage.ENGLISH to "Removed from bookmarks",
            AppLanguage.TAMIL to "சேமிப்பிலிருந்து நீக்கப்பட்டது"
        ),
        CONTENT_NO_ITEMS to mapOf(
            AppLanguage.SINHALA to "අදාළ අයිතම හමු නොවීය",
            AppLanguage.ENGLISH to "No matching guides found",
            AppLanguage.TAMIL to "வழிகாட்டிகள் எதுவும் கிடைக்கவில்லை"
        ),

        // Buttons
        BTN_SEARCH to mapOf(
            AppLanguage.SINHALA to "සොයන්න",
            AppLanguage.ENGLISH to "Search",
            AppLanguage.TAMIL to "தேடுக"
        ),
        BTN_CLEAR to mapOf(
            AppLanguage.SINHALA to "ඉවත් කරන්න",
            AppLanguage.ENGLISH to "Clear",
            AppLanguage.TAMIL to "அழி"
        ),
        BTN_SAVE to mapOf(
            AppLanguage.SINHALA to "සුරකින්න",
            AppLanguage.ENGLISH to "Save",
            AppLanguage.TAMIL to "சேமி"
        ),
        BTN_DELETE to mapOf(
            AppLanguage.SINHALA to "මකා දමන්න",
            AppLanguage.ENGLISH to "Delete",
            AppLanguage.TAMIL to "நீக்கு"
        ),
        BTN_EDIT to mapOf(
            AppLanguage.SINHALA to "සංස්කරණය",
            AppLanguage.ENGLISH to "Edit",
            AppLanguage.TAMIL to "திருத்து"
        ),
        BTN_ADD_NEW to mapOf(
            AppLanguage.SINHALA to "නව දැනුම එක් කරන්න",
            AppLanguage.ENGLISH to "Add New Guide",
            AppLanguage.TAMIL to "புதிய வழிகாட்டியைச் சேர்"
        ),
        BTN_CLOSE to mapOf(
            AppLanguage.SINHALA to "වසන්න",
            AppLanguage.ENGLISH to "Close",
            AppLanguage.TAMIL to "மூடு"
        ),
        BTN_BACK to mapOf(
            AppLanguage.SINHALA to "ආපසු",
            AppLanguage.ENGLISH to "Back",
            AppLanguage.TAMIL to "பின்செல்"
        ),
        BTN_RESET to mapOf(
            AppLanguage.SINHALA to "නැවත සකසන්න",
            AppLanguage.ENGLISH to "Reset",
            AppLanguage.TAMIL to "மீட்டமை"
        ),
        BTN_CALCULATE to mapOf(
            AppLanguage.SINHALA to "ගණනය කරන්න",
            AppLanguage.ENGLISH to "Calculate",
            AppLanguage.TAMIL to "கணக்கிடு"
        ),
        BTN_CONFIRM to mapOf(
            AppLanguage.SINHALA to "තහවුරු කරන්න",
            AppLanguage.ENGLISH to "Confirm",
            AppLanguage.TAMIL to "உறுதி செய்"
        ),
        BTN_CANCEL to mapOf(
            AppLanguage.SINHALA to "අවලංගු කරන්න",
            AppLanguage.ENGLISH to "Cancel",
            AppLanguage.TAMIL to "ரத்து செய்"
        ),
        BTN_VIEW_ALL to mapOf(
            AppLanguage.SINHALA to "සියල්ල බලන්න",
            AppLanguage.ENGLISH to "View All",
            AppLanguage.TAMIL to "அனைத்தையும் பார்"
        ),
        BTN_MARK_DONE to mapOf(
            AppLanguage.SINHALA to "සම්පූර්ණයි",
            AppLanguage.ENGLISH to "Mark Done",
            AppLanguage.TAMIL to "முடிந்தது"
        ),
        BTN_SWITCH_LANGUAGE to mapOf(
            AppLanguage.SINHALA to "භාෂාව වෙනස් කරන්න",
            AppLanguage.ENGLISH to "Change Language",
            AppLanguage.TAMIL to "மொழியை மாற்று"
        ),

        // Error Codes screen
        ERRORS_HEADER_TITLE to mapOf(
            AppLanguage.SINHALA to "ඉලෙක්ට්‍රොනික මෝටර් පෙට්ටි දෝෂ කේත",
            AppLanguage.ENGLISH to "Electronic Control Box Error Codes",
            AppLanguage.TAMIL to "மின்னணு கட்டுப்பாட்டு பெட்டி பிழைக் குறியீடுகள்"
        ),
        ERRORS_SEARCH_HINT to mapOf(
            AppLanguage.SINHALA to "දෝෂ කේතය (උදා: E-01, E-07) සොයන්න...",
            AppLanguage.ENGLISH to "Search error code (e.g. E-01, E-07)...",
            AppLanguage.TAMIL to "பிழைக் குறியீட்டைத் தேடுக (எ.கா: E-01, E-07)..."
        ),
        ERRORS_SYSTEM_LABEL to mapOf(
            AppLanguage.SINHALA to "පද්ධතිය / නිෂ්පාදකයා",
            AppLanguage.ENGLISH to "System / Manufacturer",
            AppLanguage.TAMIL to "அமைப்பு / உற்பத்தியாளர்"
        ),
        ERRORS_CAUSE_TITLE to mapOf(
            AppLanguage.SINHALA to "දෝෂයට හේතුව",
            AppLanguage.ENGLISH to "Probable Cause",
            AppLanguage.TAMIL to "சாத்தியமான காரணம்"
        ),
        ERRORS_SOLUTION_TITLE to mapOf(
            AppLanguage.SINHALA to "කාර්මික විසඳුම සහ පියවර",
            AppLanguage.ENGLISH to "Technician Solution & Action",
            AppLanguage.TAMIL to "தொழில்நுட்ப தீர்வு மற்றும் செயல்முறை"
        ),

        // Maintenance
        PM_TITLE to mapOf(
            AppLanguage.SINHALA to "කාර්මික නඩත්තු චෙක්ලිස්ට්",
            AppLanguage.ENGLISH to "Preventive Maintenance Checklist",
            AppLanguage.TAMIL to "தடுப்பு பராமரிப்பு சரிபார்ப்பு பட்டியல்"
        ),
        PM_DAILY to mapOf(
            AppLanguage.SINHALA to "දෛනික (Daily)",
            AppLanguage.ENGLISH to "Daily Routine",
            AppLanguage.TAMIL to "தினசரி வழக்கம்"
        ),
        PM_WEEKLY to mapOf(
            AppLanguage.SINHALA to "සතිපතා (Weekly)",
            AppLanguage.ENGLISH to "Weekly Routine",
            AppLanguage.TAMIL to "வாராந்திர வழக்கம்"
        ),
        PM_MONTHLY to mapOf(
            AppLanguage.SINHALA to "මාසික (Monthly)",
            AppLanguage.ENGLISH to "Monthly Routine",
            AppLanguage.TAMIL to "மாதாந்திர வழக்கம்"
        ),
        PM_COMPLETED_PROGRESS to mapOf(
            AppLanguage.SINHALA to "නඩත්තු ප්‍රගතිය",
            AppLanguage.ENGLISH to "Maintenance Progress",
            AppLanguage.TAMIL to "பராமரிப்பு முன்னேற்றம்"
        ),
        PM_RESET_CHECKLIST to mapOf(
            AppLanguage.SINHALA to "චෙක්ලිස්ට් යළි පිහිටුවන්න",
            AppLanguage.ENGLISH to "Reset Checklist",
            AppLanguage.TAMIL to "பட்டியலை மீட்டமை"
        ),

        // Calculators
        CALC_TITLE to mapOf(
            AppLanguage.SINHALA to "කාර්මික ගණක මෙවලම්",
            AppLanguage.ENGLISH to "Technician Calculators",
            AppLanguage.TAMIL to "தொழில்நுட்ப கருவிகள்"
        ),
        CALC_NEEDLE_TAB to mapOf(
            AppLanguage.SINHALA to "ඉඳිකටු ප්‍රමාණ (Needle)",
            AppLanguage.ENGLISH to "Needle Size Chart",
            AppLanguage.TAMIL to "ஊசி அளவு அட்டவணை"
        ),
        CALC_THREAD_TAB to mapOf(
            AppLanguage.SINHALA to "නූල් සහ ඉඳිකටු (Thread)",
            AppLanguage.ENGLISH to "Thread & Needle Match",
            AppLanguage.TAMIL to "நூல் மற்றும் ஊசி பொருத்தம்"
        ),
        CALC_STITCH_TAB to mapOf(
            AppLanguage.SINHALA to "මැහුම් ඝනත්වය (SPI/mm)",
            AppLanguage.ENGLISH to "Stitch Density (SPI)",
            AppLanguage.TAMIL to "தையல் அடர்த்தி (SPI)"
        ),
        CALC_SPEED_TAB to mapOf(
            AppLanguage.SINHALA to "වේගය / RPM (Speed)",
            AppLanguage.ENGLISH to "Motor Pulley & RPM",
            AppLanguage.TAMIL to "மோட்டார் வேகம் (RPM)"
        ),
        CALC_SELECT_METRIC to mapOf(
            AppLanguage.SINHALA to "මෙට්‍රික් ඉඳිකටු ප්‍රමාණය තෝරන්න (Nm)",
            AppLanguage.ENGLISH to "Select Metric Needle Size (Nm)",
            AppLanguage.TAMIL to "மெட்ரிக் ஊசி அளவைத் தேர்ந்தெடுக்கவும் (Nm)"
        ),
        CALC_RESULT_SINGER to mapOf(
            AppLanguage.SINHALA to "සින්ගර් / ඇමරිකානු ප්‍රමාණය",
            AppLanguage.ENGLISH to "Singer / US Size Equivalent",
            AppLanguage.TAMIL to "சிங்கர் / அமெரிக்க அளவு சமமான"
        ),
        CALC_RESULT_SYSTEM to mapOf(
            AppLanguage.SINHALA to "ඉඳිකටු පද්ධති (Needle System)",
            AppLanguage.ENGLISH to "Needle Systems Used",
            AppLanguage.TAMIL to "பயன்படுத்தப்படும் ஊசி அமைப்புகள்"
        ),
        CALC_RECOMMENDED_FABRIC to mapOf(
            AppLanguage.SINHALA to "නිර්දේශිත රෙදි වර්ගය",
            AppLanguage.ENGLISH to "Recommended Fabric Application",
            AppLanguage.TAMIL to "பரிந்துரைக்கப்பட்ட துணி பயன்பாடு"
        ),

        // Membership
        MEMBER_CARD_TITLE to mapOf(
            AppLanguage.SINHALA to "කාර්මික ශිල්පී හැඳුනුම්පත",
            AppLanguage.ENGLISH to "Technician Digital Credential",
            AppLanguage.TAMIL to "தொழில்நுட்ப வல்லுநர் அடையாள அட்டை"
        ),
        MEMBER_BADGE_LEVEL to mapOf(
            AppLanguage.SINHALA to "සාමාජික ශ්‍රේණිය",
            AppLanguage.ENGLISH to "Membership Grade",
            AppLanguage.TAMIL to "உறுப்பினர் தரம்"
        ),
        MEMBER_FACTORY_NAME to mapOf(
            AppLanguage.SINHALA to "කර්මාන්ත ශාලාව / වැඩපළ",
            AppLanguage.ENGLISH to "Factory / Workshop",
            AppLanguage.TAMIL to "தொழிற்சாலை / பட்டறை"
        ),
        MEMBER_EXP_YEARS to mapOf(
            AppLanguage.SINHALA to "කාර්මික පළපුරුද්ද",
            AppLanguage.ENGLISH to "Years of Experience",
            AppLanguage.TAMIL to "அனுபவ ஆண்டுகள்"
        ),
        MEMBER_CERTIFICATION_PROGRESS to mapOf(
            AppLanguage.SINHALA to "සහතිකපත් සුදුසුකම් ප්‍රගතිය",
            AppLanguage.ENGLISH to "Certification Readiness",
            AppLanguage.TAMIL to "சான்றிதழ் முன்னேற்றம்"
        ),
        MEMBER_QUIZ_TITLE to mapOf(
            AppLanguage.SINHALA to "කාර්මික දැනුම පරීක්ෂණය",
            AppLanguage.ENGLISH to "Technician Competency Quiz",
            AppLanguage.TAMIL to "தொழில்நுட்ப திறன் வினாடி வினா"
        ),
        MEMBER_QUIZ_SUBTITLE to mapOf(
            AppLanguage.SINHALA to "ඔබගේ ටයිමිං සහ නඩත්තු ප්‍රවීණත්වය පරීක්ෂා කර ලකුණු ලබාගන්න",
            AppLanguage.ENGLISH to "Test your machine timing and troubleshooting knowledge",
            AppLanguage.TAMIL to "உங்கள் இயந்திர நேரம் மற்றும் பழுதுநீக்கும் அறிவை சோதிக்கவும்"
        ),
        MEMBER_QUIZ_START to mapOf(
            AppLanguage.SINHALA to "පරීක්ෂණය ආරම්භ කරන්න",
            AppLanguage.ENGLISH to "Start Quiz",
            AppLanguage.TAMIL to "வினாடி வினாவைத் தொடங்கு"
        ),
        MEMBER_QUIZ_SCORE to mapOf(
            AppLanguage.SINHALA to "ලකුණු",
            AppLanguage.ENGLISH to "Score",
            AppLanguage.TAMIL to "மதிப்பெண்"
        ),
        LEVEL_APPRENTICE to mapOf(
            AppLanguage.SINHALA to "ආධුනික කාර්මික ශිල්පී",
            AppLanguage.ENGLISH to "Junior Apprentice Technician",
            AppLanguage.TAMIL to "இளநிலை தொழில்நுட்ப வல்லுநர்"
        ),
        LEVEL_CERTIFIED to mapOf(
            AppLanguage.SINHALA to "සහතිකලත් කාර්මික ශිල්පී",
            AppLanguage.ENGLISH to "Certified Sewing Technician",
            AppLanguage.TAMIL to "சான்றளிக்கப்பட்ட தொழில்நுட்ப வல்லுநர்"
        ),
        LEVEL_MASTER to mapOf(
            AppLanguage.SINHALA to "ප්‍රධාන ඇඟලුම් ඉංජිනේරු",
            AppLanguage.ENGLISH to "Master Plant Sewing Engineer",
            AppLanguage.TAMIL to "தலைமை ஆடை தையல் பொறியாளர்"
        ),

        // Account
        ACCOUNT_TITLE to mapOf(
            AppLanguage.SINHALA to "පරිශීලක ගිණුම",
            AppLanguage.ENGLISH to "User Account & Profile",
            AppLanguage.TAMIL to "பயனர் கணக்கு மற்றும் சுயவிவரம்"
        ),
        ACCOUNT_NAME_LABEL to mapOf(
            AppLanguage.SINHALA to "නම",
            AppLanguage.ENGLISH to "Full Name",
            AppLanguage.TAMIL to "முழு பெயர்"
        ),
        ACCOUNT_ROLE_LABEL to mapOf(
            AppLanguage.SINHALA to "තනතුර",
            AppLanguage.ENGLISH to "Technician Role",
            AppLanguage.TAMIL to "பங்கு"
        ),
        ACCOUNT_FACTORY_LABEL to mapOf(
            AppLanguage.SINHALA to "සේවා ස්ථානය (කර්මාන්ත ශාලාව)",
            AppLanguage.ENGLISH to "Workplace (Plant/Factory)",
            AppLanguage.TAMIL to "பணியிடம் (தொழிற்சாலை)"
        ),
        ACCOUNT_UPDATE_SUCCESS to mapOf(
            AppLanguage.SINHALA to "ගිණුමේ තොරතුරු යාවත්කාලීන කරන ලදී",
            AppLanguage.ENGLISH to "Profile updated successfully",
            AppLanguage.TAMIL to "சுயவிவரம் வெற்றிகரமாக புதுப்பிக்கப்பட்டது"
        ),

        // Admin Dashboard
        ADMIN_TITLE to mapOf(
            AppLanguage.SINHALA to "පරිපාලක පුවරුව",
            AppLanguage.ENGLISH to "Admin Dashboard",
            AppLanguage.TAMIL to "நிர்வாக பலகை"
        ),
        ADMIN_SUBTITLE to mapOf(
            AppLanguage.SINHALA to "සිංහල, ඉංග්‍රීසි සහ දෙමළ ත්‍රිභාෂා අන්තර්ගත කළමනාකරණය",
            AppLanguage.ENGLISH to "Tri-lingual Technical Content & Error Code Management",
            AppLanguage.TAMIL to "மும்மொழி தொழில்நுட்ப உள்ளடக்கம் மற்றும் பிழைக் குறியீடு மேலாண்மை"
        ),
        ADMIN_ADD_ARTICLE to mapOf(
            AppLanguage.SINHALA to "නව මාර්ගෝපදේශයක් ඇතුළත් කරන්න",
            AppLanguage.ENGLISH to "Create New Technical Guide",
            AppLanguage.TAMIL to "புதிய தொழில்நுட்ப வழிகாட்டியை உருவாக்கவும்"
        ),
        ADMIN_ADD_ERROR to mapOf(
            AppLanguage.SINHALA to "නව දෝෂ කේතයක් ඇතුළත් කරන්න",
            AppLanguage.ENGLISH to "Add New Error Code",
            AppLanguage.TAMIL to "புதிய பிழைக் குறியீட்டைச் சேர்க்கவும்"
        ),
        ADMIN_TOTAL_ARTICLES to mapOf(
            AppLanguage.SINHALA to "මුළු මාර්ගෝපදේශ",
            AppLanguage.ENGLISH to "Total Guides",
            AppLanguage.TAMIL to "மொத்த வழிகாட்டிகள்"
        ),
        ADMIN_TOTAL_ERRORS to mapOf(
            AppLanguage.SINHALA to "මුළු දෝෂ කේත",
            AppLanguage.ENGLISH to "Total Error Codes",
            AppLanguage.TAMIL to "மொத்த பிழைக் குறியீடுகள்"
        ),
        ADMIN_RESTORE_DEFAULTS to mapOf(
            AppLanguage.SINHALA to "මූලික දැනුම නැවත පිහිටුවන්න",
            AppLanguage.ENGLISH to "Restore Factory Knowledge Base",
            AppLanguage.TAMIL to "தொழிற்சாலை அறிவுத் தளத்தை மீட்டமைக்கவும்"
        ),
        ADMIN_LANGUAGE_SECTION_SINHALA to mapOf(
            AppLanguage.SINHALA to "සිංහල භාෂා ක්ෂේත්‍ර (Sinhala Fields)",
            AppLanguage.ENGLISH to "Sinhala Language Fields",
            AppLanguage.TAMIL to "சிங்கள மொழி புலங்கள்"
        ),
        ADMIN_LANGUAGE_SECTION_ENGLISH to mapOf(
            AppLanguage.SINHALA to "ඉංග්‍රීසි භාෂා ක්ෂේත්‍ර (English Fields)",
            AppLanguage.ENGLISH to "English Language Fields",
            AppLanguage.TAMIL to "ஆங்கில மொழி புலங்கள்"
        ),
        ADMIN_LANGUAGE_SECTION_TAMIL to mapOf(
            AppLanguage.SINHALA to "දෙමළ භාෂා ක්ෂේත්‍ර (Tamil Fields)",
            AppLanguage.ENGLISH to "Tamil Language Fields",
            AppLanguage.TAMIL to "தமிழ் மொழி புலங்கள்"
        ),
        ADMIN_FIELD_TITLE to mapOf(
            AppLanguage.SINHALA to "මාතෘකාව (Title)",
            AppLanguage.ENGLISH to "Title",
            AppLanguage.TAMIL to "தலைப்பு (Title)"
        ),
        ADMIN_FIELD_SUMMARY to mapOf(
            AppLanguage.SINHALA to "සාරාංශය (Summary)",
            AppLanguage.ENGLISH to "Summary",
            AppLanguage.TAMIL to "சுருக்கம் (Summary)"
        ),
        ADMIN_FIELD_STEPS to mapOf(
            AppLanguage.SINHALA to "විසඳුම් පියවර සහ උපදෙස් (Steps & Content)",
            AppLanguage.ENGLISH to "Steps & Detailed Content",
            AppLanguage.TAMIL to "படிகள் மற்றும் உள்ளடக்கம் (Steps)"
        ),
        ADMIN_FIELD_CATEGORY to mapOf(
            AppLanguage.SINHALA to "කාණ්ඩය",
            AppLanguage.ENGLISH to "Category",
            AppLanguage.TAMIL to "பிரிவு"
        ),
        ADMIN_FIELD_MACHINE to mapOf(
            AppLanguage.SINHALA to "මැෂින් වර්ගය",
            AppLanguage.ENGLISH to "Machine Type",
            AppLanguage.TAMIL to "இயந்திர வகை"
        ),
        ADMIN_FIELD_SPECS to mapOf(
            AppLanguage.SINHALA to "ටයිමිං පිරිවිතර / Clearance (Specs)",
            AppLanguage.ENGLISH to "Timing Specs / Clearance",
            AppLanguage.TAMIL to "நேர விவரக்குறிப்புகள் / இடைவெளி"
        ),
        ADMIN_FIELD_DIFFICULTY to mapOf(
            AppLanguage.SINHALA to "දුෂ්කරතාව",
            AppLanguage.ENGLISH to "Difficulty",
            AppLanguage.TAMIL to "சிரமம்"
        ),
        ADMIN_SAVE_SUCCESS to mapOf(
            AppLanguage.SINHALA to "අන්තර්ගතය සාර්ථකව සුරකින ලදී",
            AppLanguage.ENGLISH to "Content saved successfully",
            AppLanguage.TAMIL to "உள்ளடக்கம் வெற்றிகரமாக சேமிக்கப்பட்டது"
        ),
        ADMIN_DELETE_SUCCESS to mapOf(
            AppLanguage.SINHALA to "අයිතමය සාර්ථකව මකා දමන ලදී",
            AppLanguage.ENGLISH to "Item deleted successfully",
            AppLanguage.TAMIL to "உருப்படி வெற்றிகரமாக நீக்கப்பட்டது"
        ),
        ADMIN_RESTORE_CONFIRM to mapOf(
            AppLanguage.SINHALA to "සියලුම ප්‍රමිතිගත කාර්මික දත්ත නැවත පිහිටුවන ලදී",
            AppLanguage.ENGLISH to "Factory technical database restored",
            AppLanguage.TAMIL to "தொழிற்சாலை தொழில்நுட்ப தரவுத்தளம் மீட்டமைக்கப்பட்டது"
        ),

        // Settings
        SETTINGS_TITLE to mapOf(
            AppLanguage.SINHALA to "සැකසුම්",
            AppLanguage.ENGLISH to "Settings",
            AppLanguage.TAMIL to "அமைப்புகள்"
        ),
        SETTINGS_LANGUAGE_TITLE to mapOf(
            AppLanguage.SINHALA to "භාෂාව / Language / மொழி",
            AppLanguage.ENGLISH to "භාෂාව / Language / மொழி",
            AppLanguage.TAMIL to "භාෂාව / Language / மொழி"
        ),
        SETTINGS_LANGUAGE_DESC to mapOf(
            AppLanguage.SINHALA to "යෙදුමේ ප්‍රධාන භාෂාව තෝරන්න (සිංහල, English, தமிழ்)",
            AppLanguage.ENGLISH to "Select app language (Sinhala, English, Tamil)",
            AppLanguage.TAMIL to "பயன்பாட்டு மொழியைத் தேர்ந்தெடுக்கவும் (சிங்களம், ஆங்கிலம், தமிழ்)"
        ),
        SETTINGS_THEME_TITLE to mapOf(
            AppLanguage.SINHALA to "වර්ණ තේමාව (Theme)",
            AppLanguage.ENGLISH to "Display Theme",
            AppLanguage.TAMIL to "காட்சி தீம்"
        ),
        SETTINGS_THEME_DARK to mapOf(
            AppLanguage.SINHALA to "කාර්මික අඳුරු තේමාව (Dark Navy)",
            AppLanguage.ENGLISH to "Industrial Dark Navy",
            AppLanguage.TAMIL to "இருண்ட நேவி தீம்"
        ),
        SETTINGS_THEME_LIGHT to mapOf(
            AppLanguage.SINHALA to "පැහැදිලි සුදු තේමාව (Clean Light)",
            AppLanguage.ENGLISH to "Clean Light",
            AppLanguage.TAMIL to "வெளிச்ச தீம்"
        ),
        SETTINGS_ABOUT_TITLE to mapOf(
            AppLanguage.SINHALA to "කාර්මික දැනුම පිළිබඳව",
            AppLanguage.ENGLISH to "About Sewing Tech Knowledge",
            AppLanguage.TAMIL to "தையல் தொழில்நுட்ப அறிவு பற்றி"
        ),
        SETTINGS_ABOUT_DESC to mapOf(
            AppLanguage.SINHALA to "කාර්මික මහන මැෂින් ශිල්පීන්, සුපර්වයිසර්වරුන් සහ ඇඟලුම් ඉංජිනේරුවන් සඳහා නිර්මාණය කරන ලද සම්පූර්ණ ත්‍රිභාෂා තාක්ෂණික දැනුම් පද්ධතියකි.",
            AppLanguage.ENGLISH to "Professional tri-lingual technical handbook and training platform designed for industrial sewing machine mechanics, factory technicians, and apparel line engineers.",
            AppLanguage.TAMIL to "ஆடை தொழிற்சாலை தையல் இயந்திர தொழில்நுட்ப வல்லுநர்கள் மற்றும் பொறியாளர்களுக்காக வடிவமைக்கப்பட்ட மும்மொழி தொழில்நுட்ப வழிகாட்டி."
        ),
        SETTINGS_VERSION to mapOf(
            AppLanguage.SINHALA to "අනුවාදය 1.0 (ශ්‍රී ලංකා සහ ගෝලීය ඇඟලුම් කර්මාන්තය)",
            AppLanguage.ENGLISH to "Version 1.0 (Garment Industry Standard)",
            AppLanguage.TAMIL to "பதிப்பு 1.0 (ஆடை தொழில் தரம்)"
        ),
        SETTINGS_SHARE_TITLE to mapOf(
            AppLanguage.SINHALA to "යෙදුමේ සබැඳිය බෙදාගන්න (Share App Link)",
            AppLanguage.ENGLISH to "Share App Link",
            AppLanguage.TAMIL to "செயலி இணைப்பைப் பகிரவும்"
        ),
        SETTINGS_SHARE_DESC to mapOf(
            AppLanguage.SINHALA to "මෙම App එක වෙනත් අයෙකුට යවා ඔවුන්ගේ දුරකථනයට කෙලින්ම Install කර ගැනීමට හෝ භාවිතා කිරීමට මෙම Link එක යවන්න.",
            AppLanguage.ENGLISH to "Share this link with others to open or install this app directly on their mobile device or browser.",
            AppLanguage.TAMIL to "இந்த செயலியை மற்றவர்களுடன் பகிர்ந்து அவர்களின் மொபைலில் நிறுவ அல்லது பயன்படுத்த இந்த இணைப்பை அனுப்பவும்."
        ),
        BTN_SHARE_APP to mapOf(
            AppLanguage.SINHALA to "යෙදුම Share කරන්න (WhatsApp / SMS)",
            AppLanguage.ENGLISH to "Share via Apps",
            AppLanguage.TAMIL to "பகிர்"
        ),
        BTN_COPY_LINK to mapOf(
            AppLanguage.SINHALA to "ලින්ක් එක Copy කරන්න",
            AppLanguage.ENGLISH to "Copy Link",
            AppLanguage.TAMIL to "இணைப்பை நகலெடு"
        ),
        MSG_LINK_COPIED to mapOf(
            AppLanguage.SINHALA to "ලින්ක් එක සාර්ථකව පිටපත් විය! (Link Copied)",
            AppLanguage.ENGLISH to "Link copied to clipboard!",
            AppLanguage.TAMIL to "இணைப்பு நகலெடுக்கப்பட்டது!"
        ),

        // Notifications & Errors
        MSG_ERROR_REQUIRED_FIELDS to mapOf(
            AppLanguage.SINHALA to "කරුණාකර අවශ්‍ය සියලු ක්ෂේත්‍ර පුරවන්න",
            AppLanguage.ENGLISH to "Please fill in all required fields",
            AppLanguage.TAMIL to "தேவையான அனைத்து புலங்களையும் நிரப்பவும்"
        ),
        MSG_SUCCESS_SAVED to mapOf(
            AppLanguage.SINHALA to "සාර්ථකව සුරකින ලදී",
            AppLanguage.ENGLISH to "Saved successfully",
            AppLanguage.TAMIL to "வெற்றிகரமாக சேமிக்கப்பட்டது"
        ),
        MSG_SUCCESS_LANGUAGE_CHANGED to mapOf(
            AppLanguage.SINHALA to "භාෂාව සිංහලට මාරු විය",
            AppLanguage.ENGLISH to "Language changed to English",
            AppLanguage.TAMIL to "மொழி தமிழுக்கு மாற்றப்பட்டது"
        ),
        MSG_CONFIRM_DELETE to mapOf(
            AppLanguage.SINHALA to "මෙම අයිතමය මකා දැමීමට ඔබට විශ්වාසද?",
            AppLanguage.ENGLISH to "Are you sure you want to delete this item?",
            AppLanguage.TAMIL to "இந்த உருப்படியை நிச்சயமாக நீக்க விரும்புகிறீர்களா?"
        ),

        // QR Scanner
        BTN_QR_SCAN to mapOf(
            AppLanguage.SINHALA to "QR කේතය Scan කරන්න",
            AppLanguage.ENGLISH to "Scan QR Code",
            AppLanguage.TAMIL to "QR குறியீட்டை ஸ்கேன் செய்க"
        ),
        QR_SCANNER_TITLE to mapOf(
            AppLanguage.SINHALA to "QR කේත ස්කෑනරය",
            AppLanguage.ENGLISH to "QR Code Scanner",
            AppLanguage.TAMIL to "QR குறியீடு ஸ்கேனர்"
        ),
        QR_INSTRUCTION to mapOf(
            AppLanguage.SINHALA to "මැෂින් අත්පොත්, මාර්ගෝපදේශ හෝ ලින්ක් ස්කෑන් කිරීමට කැමරාව QR කේතය වෙත යොමු කරන්න",
            AppLanguage.ENGLISH to "Point your camera at a QR code on sewing machine or manual",
            AppLanguage.TAMIL to "இயந்திர கையேடு அல்லது QR குறியீட்டை நோக்கி கேமராவை வைக்கவும்"
        ),
        QR_RESULT_TITLE to mapOf(
            AppLanguage.SINHALA to "ස්කෑන් කරන ලද QR ප්‍රතිඵලය",
            AppLanguage.ENGLISH to "Scanned QR Result",
            AppLanguage.TAMIL to "ஸ்கேன் செய்யப்பட்ட QR முடிவு"
        ),
        QR_RESULT_URL_TYPE to mapOf(
            AppLanguage.SINHALA to "වෙබ් ලින්ක් (Web Link / URL)",
            AppLanguage.ENGLISH to "Web Link / URL",
            AppLanguage.TAMIL to "வலை இணைப்பு / URL"
        ),
        QR_RESULT_TEXT_TYPE to mapOf(
            AppLanguage.SINHALA to "කාර්මික පෙළ / කේතය (Text / Model Code)",
            AppLanguage.ENGLISH to "Technical Text / Code",
            AppLanguage.TAMIL to "தொழில்நுட்ப உரை / குறியீடு"
        ),
        QR_OPEN_LINK to mapOf(
            AppLanguage.SINHALA to "ලින්ක් එක විවෘත කරන්න (තහවුරු කරන්න)",
            AppLanguage.ENGLISH to "Confirm & Open Link",
            AppLanguage.TAMIL to "உறுதிசெய்து இணைப்பைத் திறக்கவும்"
        ),
        QR_CONFIRM_OPEN to mapOf(
            AppLanguage.SINHALA to "මෙම ආරක්ෂිත ලින්ක් එක විවෘත කිරීමට ඔබට අවශ්‍ය බව තහවුරු කරන්න:",
            AppLanguage.ENGLISH to "Please confirm you want to open this verified link:",
            AppLanguage.TAMIL to "இந்த இணைப்பைத் திறக்க விரும்புகிறீர்களா என்பதை உறுதிப்படுத்தவும்:"
        ),
        QR_SEARCH_MACHINE to mapOf(
            AppLanguage.SINHALA to "මෙම මැෂින් වර්ගය සෙවුම් කරන්න",
            AppLanguage.ENGLISH to "Search this Machine / Keyword",
            AppLanguage.TAMIL to "இந்த இயந்திர மாதிரியைத் தேடுக"
        ),
        QR_PERMISSION_TITLE to mapOf(
            AppLanguage.SINHALA to "කැමරා අවසරය අවශ්‍යයි",
            AppLanguage.ENGLISH to "Camera Permission Required",
            AppLanguage.TAMIL to "கேமரா அனுமதி தேவை"
        ),
        QR_PERMISSION_DESC to mapOf(
            AppLanguage.SINHALA to "මැෂින් QR කේත සහ ලේඛන ස්කෑන් කිරීම සඳහා කැමරාව භාවිතා කිරීමට කරුණාකර අවසර ලබා දෙන්න.",
            AppLanguage.ENGLISH to "Camera permission is required to scan machine QR codes and manuals directly inside the app.",
            AppLanguage.TAMIL to "QR குறியீடுகளை ஸ்கேன் செய்ய கேமரா அனுமதி தேவை."
        ),
        QR_PERMISSION_GRANT to mapOf(
            AppLanguage.SINHALA to "කැමරා අවසරය ලබා දෙන්න",
            AppLanguage.ENGLISH to "Grant Camera Permission",
            AppLanguage.TAMIL to "கேமரா அனுமதி வழங்கவும்"
        ),
        QR_MANUAL_ENTRY_TITLE to mapOf(
            AppLanguage.SINHALA to "කේතය හෝ ලින්ක් එක අතින් ඇතුළත් කරන්න",
            AppLanguage.ENGLISH to "Enter Link or Code Manually",
            AppLanguage.TAMIL to "கைமுறையாக உள்ளிடவும்"
        ),
        QR_MANUAL_ENTRY_HINT to mapOf(
            AppLanguage.SINHALA to "URL, ලින්ක් හෝ මැෂින් කේතය මෙහි ටයිප් කරන්න (උදා: https://... හෝ DDL-8700)",
            AppLanguage.ENGLISH to "Type URL or machine model (e.g. https://... or DDL-8700)",
            AppLanguage.TAMIL to "இணைப்பு அல்லது குறியீட்டை உள்ளிடவும்"
        ),
        QR_MANUAL_ENTRY_SUBMIT to mapOf(
            AppLanguage.SINHALA to "ඇතුළත් කර පරීක්ෂා කරන්න",
            AppLanguage.ENGLISH to "Submit & Process",
            AppLanguage.TAMIL to "உள்ளீட்டைச் சரிபார்க்கவும்"
        ),
        QR_CAMERA_ERROR to mapOf(
            AppLanguage.SINHALA to "කැමරාව ආරම්භ කිරීමට නොහැකි විය. කරුණාකර පහත අතින් ඇතුළත් කිරීමේ විකල්පය භාවිතා කරන්න.",
            AppLanguage.ENGLISH to "Camera could not be started. Please use manual entry option below.",
            AppLanguage.TAMIL to "கேமராவைத் தொடங்க முடியவில்லை. கீழே உள்ள கைமுறை உள்ளீட்டைப் பயன்படுத்தவும்."
        ),
        QR_SCAN_AGAIN to mapOf(
            AppLanguage.SINHALA to "නැවත Scan කරන්න",
            AppLanguage.ENGLISH to "Scan Again",
            AppLanguage.TAMIL to "மீண்டும் ஸ்கேன் செய்க"
        ),
        QR_INVALID_LINK to mapOf(
            AppLanguage.SINHALA to "වලංගු නොවන හෝ අනාරක්ෂිත ලින්ක් එකකි",
            AppLanguage.ENGLISH to "Invalid or unsupported link format",
            AppLanguage.TAMIL to "செல்லுபடியாகாத இணைப்பு"
        ),
        QR_COPY_RESULT to mapOf(
            AppLanguage.SINHALA to "පිටපත් කරන්න",
            AppLanguage.ENGLISH to "Copy Result",
            AppLanguage.TAMIL to "நகலெடு"
        ),
        QR_COPIED to mapOf(
            AppLanguage.SINHALA to "පිටපත් කරන ලදී!",
            AppLanguage.ENGLISH to "Copied to clipboard!",
            AppLanguage.TAMIL to "நகலெடுக்கப்பட்டது!"
        ),
        QR_TORCH_ON to mapOf(
            AppLanguage.SINHALA to "ටෝච් එක ක්‍රියාත්මකයි (Torch ON)",
            AppLanguage.ENGLISH to "Torch is ON",
            AppLanguage.TAMIL to "டார்ச் ஆன்"
        ),
        QR_TORCH_OFF to mapOf(
            AppLanguage.SINHALA to "ටෝච් එක දල්වන්න (Turn Torch ON)",
            AppLanguage.ENGLISH to "Turn Torch ON",
            AppLanguage.TAMIL to "டார்ச் ஆன் செய்க"
        ),
        QR_TORCH_DESC to mapOf(
            AppLanguage.SINHALA to "අඳුරු මැෂින් ඇතුළත හෝ අඩු ආලෝකයේදී ටෝච් එක භාවිතා කරන්න",
            AppLanguage.ENGLISH to "Use torch to illuminate dark machine interiors or low light QR codes",
            AppLanguage.TAMIL to "குறைந்த வெளிச்சத்தில் டார்ச்சை பயன்படுத்தவும்"
        ),
        QR_PICK_IMAGE to mapOf(
            AppLanguage.SINHALA to "පින්තූරයකින් Scan කරන්න (Gallery)",
            AppLanguage.ENGLISH to "Scan from Photo / Gallery",
            AppLanguage.TAMIL to "படத்திலிருந்து ஸ்கேன் செய்க"
        ),
        QR_PICK_IMAGE_SUB to mapOf(
            AppLanguage.SINHALA to "ගැලරියේ ඇති QR කේත සහිත පින්තූරයක් තෝරන්න",
            AppLanguage.ENGLISH to "Select an image containing a QR code from gallery",
            AppLanguage.TAMIL to "கேலரியில் இருந்து படத்தைத் தேர்ந்தெடுக்கவும்"
        ),
        QR_FLIP_CAMERA to mapOf(
            AppLanguage.SINHALA to "කැමරාව මාරු කරන්න",
            AppLanguage.ENGLISH to "Switch Camera (Front/Back)",
            AppLanguage.TAMIL to "கேமராவை மாற்றவும்"
        ),

        // Google Drive Translations
        DRIVE_FILES_TITLE to mapOf(
            AppLanguage.SINHALA to "Google Drive ලේඛන සහ අත්පොත්",
            AppLanguage.ENGLISH to "Google Drive Documents & Manuals",
            AppLanguage.TAMIL to "Google Drive ஆவணங்கள்"
        ),
        DRIVE_FILES_SUBTITLE to mapOf(
            AppLanguage.SINHALA to "Google Drive එකෙන් Fetch කරගත් තාක්ෂණික අත්පොත්, PDF සහ වීඩියෝ",
            AppLanguage.ENGLISH to "Technical manuals, PDFs and videos fetched from Google Drive",
            AppLanguage.TAMIL to "Google Drive இலிருந்து பெறப்பட்ட கையேடுகள் மற்றும் வீடியோக்கள்"
        ),
        DRIVE_FETCH_BUTTON to mapOf(
            AppLanguage.SINHALA to "Google Drive එකෙන් Fetch කරන්න",
            AppLanguage.ENGLISH to "Fetch from Google Drive",
            AppLanguage.TAMIL to "Drive இலிருந்து பெறுக"
        ),
        DRIVE_FETCHING to mapOf(
            AppLanguage.SINHALA to "Google Drive වෙතින් ගොනු ලැයිස්තුව Fetch වෙමින් පවතී...",
            AppLanguage.ENGLISH to "Fetching file list from Google Drive...",
            AppLanguage.TAMIL to "கோப்பு பட்டியல் பெறப்படுகிறது..."
        ),
        DRIVE_FETCH_SUCCESS to mapOf(
            AppLanguage.SINHALA to "Google Drive ගොනු සාර්ථකව Fetch කර යාවත්කාලීන කරන ලදී!",
            AppLanguage.ENGLISH to "Google Drive files fetched and updated successfully!",
            AppLanguage.TAMIL to "கோப்புகள் வெற்றிகரமாக புதுப்பிக்கப்பட்டன!"
        ),
        DRIVE_FETCH_ERROR to mapOf(
            AppLanguage.SINHALA to "Drive ගොනු Fetch කිරීම අසාර්ථක විය. පවතින සුරකින ලද ගොනු පෙන්වයි.",
            AppLanguage.ENGLISH to "Could not fetch new files from Drive. Displaying cached files.",
            AppLanguage.TAMIL to "Drive இலிருந்து பெற முடியவில்லை. சேமிக்கப்பட்ட கோப்புகள் காட்டப்படுகின்றன."
        ),
        DRIVE_SEARCH_HINT to mapOf(
            AppLanguage.SINHALA to "Drive ගොනු හෝ මැෂින් මාදිලිය සෙවුම් කරන්න...",
            AppLanguage.ENGLISH to "Search Drive files or machine model...",
            AppLanguage.TAMIL to "கோப்புகள் அல்லது மாதிரியைத் தேடுக..."
        ),
        DRIVE_NO_FILES to mapOf(
            AppLanguage.SINHALA to "කිසිදු Google Drive ගොනුවක් හමු නොවීය. කරුණාකර 'Fetch' කරන්න හෝ නව ගොනුවක් එක් කරන්න.",
            AppLanguage.ENGLISH to "No Google Drive files found. Please tap 'Fetch' or add a file.",
            AppLanguage.TAMIL to "கோப்புகள் எதுவும் கிடைக்கவில்லை. 'Fetch' செய்க."
        ),
        DRIVE_OPEN_FILE to mapOf(
            AppLanguage.SINHALA to "Drive මගින් බලන්න",
            AppLanguage.ENGLISH to "View in Drive",
            AppLanguage.TAMIL to "Drive இல் காண்க"
        ),
        DRIVE_DOWNLOAD_FILE to mapOf(
            AppLanguage.SINHALA to "බාගන්න (Download)",
            AppLanguage.ENGLISH to "Download File",
            AppLanguage.TAMIL to "பதிவிறக்குக"
        ),
        DRIVE_ADD_FILE to mapOf(
            AppLanguage.SINHALA to "අප්ලෝඩ් කල ගොනුවක් එක් කරන්න",
            AppLanguage.ENGLISH to "Add Uploaded Drive File",
            AppLanguage.TAMIL to "புதிய கோப்பை சேர்க்கவும்"
        ),
        DRIVE_FILE_NAME to mapOf(
            AppLanguage.SINHALA to "ගොනුවේ නම (File Name)",
            AppLanguage.ENGLISH to "File Name",
            AppLanguage.TAMIL to "கோப்பின் பெயர்"
        ),
        DRIVE_FILE_LINK to mapOf(
            AppLanguage.SINHALA to "Google Drive Link හෝ File ID",
            AppLanguage.ENGLISH to "Google Drive Link or File ID",
            AppLanguage.TAMIL to "Google Drive இணைப்பு அல்லது ID"
        ),
        DRIVE_FOLDER_ID_CONFIG to mapOf(
            AppLanguage.SINHALA to "Google Drive Folder සැකසුම්",
            AppLanguage.ENGLISH to "Google Drive Folder Config",
            AppLanguage.TAMIL to "Drive கோப்புறை அமைப்புகள்"
        ),
        DRIVE_TAB_ALL to mapOf(
            AppLanguage.SINHALA to "සියල්ල (All)",
            AppLanguage.ENGLISH to "All Files",
            AppLanguage.TAMIL to "அனைத்தும்"
        ),
        DRIVE_TAB_PDF to mapOf(
            AppLanguage.SINHALA to "PDF අත්පොත්",
            AppLanguage.ENGLISH to "PDF Manuals",
            AppLanguage.TAMIL to "PDF கையேடுகள்"
        ),
        DRIVE_TAB_VIDEO to mapOf(
            AppLanguage.SINHALA to "වීඩියෝ (Videos)",
            AppLanguage.ENGLISH to "Videos",
            AppLanguage.TAMIL to "வீடியோக்கள்"
        ),
        DRIVE_TAB_IMAGE to mapOf(
            AppLanguage.SINHALA to "පින්තූර / ඩයග්‍රෑම්",
            AppLanguage.ENGLISH to "Diagrams & Images",
            AppLanguage.TAMIL to "படங்கள் / வரைபடங்கள்"
        ),
        DRIVE_TOTAL_FILES to mapOf(
            AppLanguage.SINHALA to "සම්පූර්ණ Drive ගොනු",
            AppLanguage.ENGLISH to "Total Drive Files",
            AppLanguage.TAMIL to "மொத்த கோப்புகள்"
        ),
        FIVES_TITLE to mapOf(
            AppLanguage.SINHALA to "5S ක්‍රියාවලිය (5S Process)",
            AppLanguage.ENGLISH to "5S Workplace Methodology",
            AppLanguage.TAMIL to "5S செயல்முறை (5S Process)"
        ),
        FIVES_SUBTITLE to mapOf(
            AppLanguage.SINHALA to "ඇඟලුම් ක්ෂේත්‍රයේ කාර්යක්ෂමතාව සහ පිළිවෙල සඳහා ජපන් 5S ක්‍රමවේදය",
            AppLanguage.ENGLISH to "Japanese 5S Lean Foundation for Apparel & Sewing Machine Maintenance",
            AppLanguage.TAMIL to "ஆடை உற்பத்தி மற்றும் இயந்திர பராமரிப்புக்கான 5S முறை"
        ),
        FIVES_TAB_PILLARS to mapOf(
            AppLanguage.SINHALA to "5S මුලධර්ම (Pillars)",
            AppLanguage.ENGLISH to "5S Pillars",
            AppLanguage.TAMIL to "5S தூண்கள் (Pillars)"
        ),
        FIVES_TAB_CHECKLIST to mapOf(
            AppLanguage.SINHALA to "පිරික්සුම් ලැයිස්තුව (Checklist)",
            AppLanguage.ENGLISH to "Daily Checklist",
            AppLanguage.TAMIL to "சரிபார்ப்பு பட்டியல்"
        ),
        FIVES_TAB_REDTAG to mapOf(
            AppLanguage.SINHALA to "රතු ටැග් සටහන (Red Tag)",
            AppLanguage.ENGLISH to "Red Tag Register",
            AppLanguage.TAMIL to "சிவப்பு குறிச்சொல் (Red Tag)"
        ),
        FIVES_TAB_AUDIT to mapOf(
            AppLanguage.SINHALA to "විගණන ලකුණු (Audit Score)",
            AppLanguage.ENGLISH to "5S Audit Score",
            AppLanguage.TAMIL to "தணிக்கை மதிப்பெண் (Audit)"
        ),
        FIVES_RESET_CHECKLIST to mapOf(
            AppLanguage.SINHALA to "නැවත මුල සිට පටන් ගන්න",
            AppLanguage.ENGLISH to "Reset Checklist",
            AppLanguage.TAMIL to "மீட்டமைக்க"
        ),
        FIVES_PROGRESS to mapOf(
            AppLanguage.SINHALA to "ප්‍රගතිය",
            AppLanguage.ENGLISH to "5S Progress",
            AppLanguage.TAMIL to "முன்னேற்றம்"
        ),
        FIVES_COMPLETED to mapOf(
            AppLanguage.SINHALA to "සම්පූර්ණයි",
            AppLanguage.ENGLISH to "Completed",
            AppLanguage.TAMIL to "முடிந்தது"
        ),
        FIVES_ADD_REDTAG to mapOf(
            AppLanguage.SINHALA to "නව රතු ටැගයක් එක් කරන්න",
            AppLanguage.ENGLISH to "Add New Red Tag",
            AppLanguage.TAMIL to "புதிய சிவப்பு குறிச்சொல்லைச் சேர்க்கவும்"
        ),
        FIVES_AUDIT_CALC to mapOf(
            AppLanguage.SINHALA to "ලකුණු ගණනය කරන්න",
            AppLanguage.ENGLISH to "Calculate 5S Score",
            AppLanguage.TAMIL to "மதிப்பெண்ணைக் கணக்கிடுங்கள்"
        ),
        FIVES_SCORE_EXCELLENT to mapOf(
            AppLanguage.SINHALA to "විශිෂ්ට 5S තත්වයක්! (Excellent)",
            AppLanguage.ENGLISH to "Excellent 5S Performance! (Grade A)",
            AppLanguage.TAMIL to "சிறந்த 5S நிலை!"
        ),
        FIVES_SCORE_GOOD to mapOf(
            AppLanguage.SINHALA to "යහපත් තත්වයක් (Good)",
            AppLanguage.ENGLISH to "Good 5S Standard (Grade B)",
            AppLanguage.TAMIL to "நல்ல நிலை"
        ),
        FIVES_SCORE_IMPROVE to mapOf(
            AppLanguage.SINHALA to "වැඩිදියුණු විය යුතුයි (Needs Action)",
            AppLanguage.ENGLISH to "Needs Improvement (Grade C)",
            AppLanguage.TAMIL to "மேம்படுத்தப்பட வேண்டும்"
        )
    )
}

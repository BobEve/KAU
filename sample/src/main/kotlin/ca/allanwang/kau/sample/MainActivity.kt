package ca.allanwang.kau.sample

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import ca.allanwang.kau.about.kauLaunchAbout
import ca.allanwang.kau.email.sendEmail
import ca.allanwang.kau.kpref.activity.CoreAttributeContract
import ca.allanwang.kau.kpref.activity.KPrefActivity
import ca.allanwang.kau.kpref.activity.KPrefAdapterBuilder
import ca.allanwang.kau.mediapicker.kauLaunchMediaPicker
import ca.allanwang.kau.mediapicker.kauOnMediaPickerResult
import ca.allanwang.kau.searchview.SearchItem
import ca.allanwang.kau.searchview.SearchView
import ca.allanwang.kau.searchview.bindSearchView
import ca.allanwang.kau.swipe.SWIPE_EDGE_LEFT
import ca.allanwang.kau.ui.views.RippleCanvas
import ca.allanwang.kau.utils.*
import ca.allanwang.kau.xml.showChangelog
import com.mikepenz.google_material_typeface_library.GoogleMaterial


class MainActivity : KPrefActivity() {

    var searchView: SearchView? = null

    companion object {

        //some of the most common english words for show
        val wordBank: List<String> by lazy {
            listOf("the", "name", "of", "very", "to", "through",
                    "and", "just", "a", "form", "in", "much", "is", "great", "it", "think", "you", "say",
                    "that", "help", "he", "low", "was", "line", "for", "before", "on", "turn", "are", "cause",
                    "with", "same", "as", "mean", "I", "differ", "his", "move", "they", "right", "be", "boy",
                    "at", "old", "one", "too", "have", "does", "this", "tell", "from", "sentence", "or", "set",
                    "had", "three", "by", "want", "hot", "air", "but", "well", "some", "also", "what", "play",
                    "there", "small", "we", "end", "can", "put", "out", "home", "other", "read", "were", "hand",
                    "all", "port", "your", "large", "when", "spell", "up", "add", "use", "even", "word", "land",
                    "how", "here", "said", "must", "an", "big", "each", "high", "she", "such", "which", "follow",
                    "do", "act", "their", "why", "time", "ask", "if", "men", "will", "change", "way", "went",
                    "about", "light", "many", "kind", "then", "off", "them", "need", "would", "house", "write",
                    "picture", "like", "try", "so", "us", "these", "again", "her", "animal", "long", "point",
                    "make", "mother", "thing", "world", "see", "near", "him", "build", "two", "self", "has",
                    "earth", "look", "father", "more", "head", "day", "stand", "could", "own", "go", "page",
                    "come", "should", "did", "country", "my", "found", "sound", "answer", "no", "school", "most",
                    "grow", "number", "study", "who", "still", "over", "learn", "know", "plant", "water", "cover",
                    "than", "food", "call", "sun", "first", "four", "people", "thought", "may", "let", "down", "keep",
                    "side", "eye", "been", "never", "now", "last", "find", "door", "any", "between", "new", "city",
                    "work", "tree", "part", "cross", "take", "since", "get", "hard", "place", "start", "made",
                    "might", "live", "story", "where", "saw", "after", "far", "back", "sea", "little", "draw",
                    "only", "left", "round", "late", "man", "run", "year", "don't", "came", "while", "show",
                    "press", "every", "close", "good", "night", "me", "real", "give", "life", "our", "few", "under",
                    "stopRankWordRankWord", "open", "ten", "seem", "simple", "together", "several", "next",
                    "vowel", "white", "toward", "children", "war", "begin", "lay", "got", "against", "walk", "pattern",
                    "example", "slow", "ease", "center", "paper", "love", "often", "person", "always", "money",
                    "music", "serve", "those", "appear", "both", "road", "mark", "map", "book", "science", "letter",
                    "rule", "until", "govern", "mile", "pull", "river", "cold", "car", "notice", "feet", "voice",
                    "care", "fall", "second", "power", "group", "town", "carry", "fine", "took", "certain", "rain",
                    "fly", "eat", "unit", "room", "lead", "friend", "cry", "began", "dark", "idea", "machine",
                    "fish", "note", "mountain", "wait", "north", "plan", "once", "figure", "base", "star", "hear",
                    "box", "horse", "noun", "cut", "field", "sure", "rest", "watch", "correct", "color", "able",
                    "face", "pound", "wood", "done", "main", "beauty", "enough", "drive", "plain", "stood", "girl",
                    "contain", "usual", "front", "young", "teach", "ready", "week", "above", "final", "ever", "gave",
                    "red", "green", "list", "oh", "though", "quick", "feel", "develop", "talk", "sleep", "bird",
                    "warm", "soon", "free", "body", "minute", "dog", "strong", "family", "special", "direct", "mind",
                    "pose", "behind", "leave", "clear", "song", "tail", "measure", "produce", "state", "fact", "product",
                    "street", "black", "inch", "short", "lot", "numeral", "nothing", "class", "course", "wind", "stay",
                    "question", "wheel", "happen", "full", "complete", "force", "ship", "blue", "area", "object", "half",
                    "decide", "rock", "surface", "order", "deep", "fire", "moon", "south", "island", "problem", "foot",
                    "piece", "yet", "told", "busy", "knew", "test", "pass", "record", "farm", "boat", "top", "common",
                    "whole", "gold", "king", "possible", "size", "plane", "heard", "age", "best", "dry", "hour", "wonder",
                    "better", "laugh", "true.", "thousand", "during", "ago", "hundred", "ran", "am", "check", "remember",
                    "game", "step", "shape", "early", "yes", "hold", "hot", "west", "miss", "ground", "brought", "interest",
                    "heat", "reach", "snow", "fast", "bed", "five", "bring", "sing", "sit", "listen", "perhaps", "six",
                    "fill", "table", "east", "travel", "weight", "less", "language", "morning", "among")
        }

        const val REQUEST_MEDIA = 27
    }

    override fun kPrefCoreAttributes(): CoreAttributeContract.() -> Unit = {
        textColor = { KPrefSample.textColor }
        accentColor = { KPrefSample.accentColor }
    }

    override fun onCreateKPrefs(savedInstanceState: android.os.Bundle?): KPrefAdapterBuilder.() -> Unit = {

        header(R.string.header)

        /**
         * This is how the setup looks like with all the proper tags
         */
        checkbox(title = R.string.checkbox_1, getter = { KPrefSample.check1 }, setter = { KPrefSample.check1 = it },
                builder = {
                    descRes = R.string.desc
                })

        /**
         * Since we know the order, we may omit the tags
         */
        checkbox(R.string.checkbox_2, { KPrefSample.check2 }, { KPrefSample.check2 = it; reloadByTitle(R.string.checkbox_3) })

        /**
         * Since the builder is the last argument and is a lambda, we may write the setup cleanly like so:
         */
        checkbox(R.string.checkbox_3, { KPrefSample.check3 }, { KPrefSample.check3 = it }) {
            descRes = R.string.desc_dependent
            enabler = { KPrefSample.check2 }
            onDisabledClick = { itemView, _, _ ->
                itemView.context.toast("I am still disabled")
                true
            }
        }

        colorPicker(R.string.text_color, { KPrefSample.textColor }, { KPrefSample.textColor = it; reload() }) {
            descRes = R.string.color_custom
            allowCustom = true
        }

        colorPicker(R.string.accent_color, { KPrefSample.accentColor }, {
            KPrefSample.accentColor = it
            reload()
            this@MainActivity.navigationBarColor = it
            toolbarCanvas.ripple(it, RippleCanvas.MIDDLE, RippleCanvas.END, duration = 500L)
        }) {
            descRes = R.string.color_no_custom
            allowCustom = false
        }

        colorPicker(R.string.background_color, { KPrefSample.bgColor }, {
            KPrefSample.bgColor = it; bgCanvas.ripple(it, duration = 500L)
        }) {
            iicon = GoogleMaterial.Icon.gmd_colorize
            descRes = R.string.color_custom_alpha
            allowCustomAlpha = true
            allowCustom = true
        }

        text(R.string.text, { KPrefSample.text }, { KPrefSample.text = it }) {
            descRes = R.string.text_desc
            onClick = { itemView, _, item ->
                itemView.context.materialDialog {
                    title("Type Text")
                    input("Type here", item.pref, { _, input -> item.pref = input.toString() })
                    inputRange(0, 20)
                }
                true
            }
        }

        seekbar(R.string.seekbar, { KPrefSample.seekbar }, { KPrefSample.seekbar = it }) {
            descRes = R.string.kau_lorem_ipsum
            textViewConfigs = {
                minEms = 2
            }
        }

        subItems(R.string.sub_item, subPrefs()) {
            descRes = R.string.sub_item_desc
        }

        plainText(R.string.swipe_showcase) {
            onClick = { _, _, _ -> startActivityWithEdge(SWIPE_EDGE_LEFT); false }
        }

        plainText(R.string.image_showcase) {
            onClick = { _, _, _ -> kauLaunchMediaPicker(ImagePickerActivity::class.java, REQUEST_MEDIA); false }
        }

        plainText(R.string.video_overlay_showcase) {
            onClick = { _, _, _ -> kauLaunchMediaPicker(VideoPickerActivityOverlay::class.java, REQUEST_MEDIA); false }
        }

        plainText(R.string.adapter_showcase) {
            onClick = { _, _, _ ->
                startActivity(AdapterActivity::class.java, bundleBuilder = {
                    withSceneTransitionAnimation(this@MainActivity)
                })
                false
            }
        }

        plainText(R.string.kau_about_app) {
            onClick = { _, _, _ -> kauLaunchAbout(AboutActivity::class.java); false }
        }

        header(R.string.long_prefs)

        checkbox(R.string.checkbox_3, { KPrefSample.check2 }, { KPrefSample.check2 = it; reloadByTitle(R.string.checkbox_3) }) {
            descRes = R.string.kau_lorem_ipsum
        }

        text(R.string.text, { KPrefSample.text }, { KPrefSample.text = it }) {
            descRes = R.string.kau_lorem_ipsum
            textGetter = { string(R.string.kau_lorem_ipsum) }
        }

        timePicker(R.string.time, { KPrefSample.time12 }, { KPrefSample.time12 = it }) {
            descRes = R.string.time_desc_12
            use24HourFormat = false
        }

        timePicker(R.string.time, { KPrefSample.time24 }, { KPrefSample.time24 = it }) {
            descRes = R.string.time_desc_24
            use24HourFormat = true
        }

    }

    fun subPrefs(): KPrefAdapterBuilder.() -> Unit = {
        text(R.string.text, { KPrefSample.text }, { KPrefSample.text = it }) {
            descRes = R.string.text_desc
            onClick = { itemView, _, item ->
                itemView.context.materialDialog {
                    title("Type Text")
                    input("Type here", item.pref, { _, input ->
                        item.pref = input.toString()
                        reloadSelf()
                    })
                    inputRange(0, 20)
                }
                true
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bgCanvas.set(KPrefSample.bgColor)
        toolbarCanvas.set(KPrefSample.accentColor)
        this.navigationBarColor = KPrefSample.accentColor
        if (KPrefSample.version < BuildConfig.VERSION_CODE) {
            KPrefSample.version = BuildConfig.VERSION_CODE
            showChangelog(R.xml.kau_changelog, KPrefSample.textColor) {
                titleColor(KPrefSample.textColor)
                backgroundColor(KPrefSample.bgColor)
                positiveColor(KPrefSample.accentColor)
            }
        }
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
            setDisplayShowHomeEnabled(false)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        if (searchView == null) searchView = bindSearchView(menu, R.id.action_search) {

            textCallback = { query, searchView ->
                val items = wordBank.filter { it.contains(query) }.sorted().map { SearchItem(it) }
                searchView.results = items
            }
            searchCallback = { query, _ ->
                toast("Enter pressed for $query")
                true
            }
            textDebounceInterval = 0
            noResultsFound = R.string.kau_no_results_found
            shouldClearOnClose = false
            onItemClick = { _, _, content, searchView ->
                toast(content)
                searchView.revealClose()
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_changelog -> showChangelog(R.xml.kau_changelog, KPrefSample.textColor) {
                titleColor(KPrefSample.textColor)
                backgroundColor(KPrefSample.bgColor)
                positiveColor(KPrefSample.accentColor)
            }
            R.id.action_settings -> startActivity(AnimActivity::class.java)
            R.id.action_email -> sendEmail(R.string.your_email, R.string.your_subject)
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onBackPressed() {
        if (searchView?.onBackPressed() != true) super.onBackPressed()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_MEDIA -> toast("${kauOnMediaPickerResult(resultCode, data).size} items selected")
        }
    }
}

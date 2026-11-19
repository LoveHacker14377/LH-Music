package com.music.bitchord.data

import com.music.bitchord.data.model.HomeShelf
import com.music.bitchord.data.model.MoodGenreSection
import com.music.bitchord.data.model.ShelfItem
import com.music.bitchord.data.model.Song
import java.util.Locale

data class IndianLanguageOption(
    val id: String,
    val label: String,
    val discoveryTerms: List<String>,
)

data class IndianArtistOption(
    val name: String,
    val languages: Set<String>,
)

/**
 * Process-wide snapshot of the listener's Indian music preferences (languages &
 * singers), mirrored from AppSettings so shared discovery & AutoPlay helpers can
 * personalize feeds dynamically without Android SharedPreferences coupling.
 */
object IndianMusicPreferences {
    val LANGUAGES: List<IndianLanguageOption> = listOf(
        IndianLanguageOption("hindi", "Hindi", listOf("Hindi", "Bollywood")),
        IndianLanguageOption("punjabi", "Punjabi", listOf("Punjabi")),
        IndianLanguageOption("tamil", "Tamil", listOf("Tamil", "Kollywood")),
        IndianLanguageOption("telugu", "Telugu", listOf("Telugu", "Tollywood")),
        IndianLanguageOption("malayalam", "Malayalam", listOf("Malayalam")),
        IndianLanguageOption("kannada", "Kannada", listOf("Kannada")),
        IndianLanguageOption("marathi", "Marathi", listOf("Marathi")),
        IndianLanguageOption("bengali", "Bengali", listOf("Bengali")),
        IndianLanguageOption("gujarati", "Gujarati", listOf("Gujarati")),
        IndianLanguageOption("bhojpuri", "Bhojpuri", listOf("Bhojpuri")),
        IndianLanguageOption("haryanvi", "Haryanvi", listOf("Haryanvi")),
        IndianLanguageOption("odia", "Odia", listOf("Odia")),
    )

    val SUGGESTED_ARTISTS: List<IndianArtistOption> = listOf(
        IndianArtistOption("Arijit Singh", setOf("hindi", "bengali")),
        IndianArtistOption("Shreya Ghoshal", setOf("hindi", "bengali", "tamil", "telugu", "malayalam", "kannada", "marathi")),
        IndianArtistOption("Diljit Dosanjh", setOf("punjabi", "hindi")),
        IndianArtistOption("Pritam", setOf("hindi")),
        IndianArtistOption("A.R. Rahman", setOf("hindi", "tamil", "telugu", "malayalam")),
        IndianArtistOption("Anirudh Ravichander", setOf("tamil", "telugu", "hindi")),
        IndianArtistOption("Karan Aujla", setOf("punjabi", "hindi")),
        IndianArtistOption("AP Dhillon", setOf("punjabi", "hindi")),
        IndianArtistOption("Sidhu Moose Wala", setOf("punjabi")),
        IndianArtistOption("Jubin Nautiyal", setOf("hindi")),
        IndianArtistOption("Armaan Malik", setOf("hindi", "telugu", "kannada")),
        IndianArtistOption("Vishal Mishra", setOf("hindi")),
        IndianArtistOption("Sid Sriram", setOf("tamil", "telugu", "malayalam")),
        IndianArtistOption("Sunidhi Chauhan", setOf("hindi", "punjabi", "marathi")),
        IndianArtistOption("Sonu Nigam", setOf("hindi", "kannada")),
        IndianArtistOption("Shubh", setOf("punjabi")),
        IndianArtistOption("Badshah", setOf("hindi", "punjabi", "haryanvi")),
        IndianArtistOption("Atif Aslam", setOf("hindi", "punjabi")),
        IndianArtistOption("Darshan Raval", setOf("hindi", "gujarati")),
        IndianArtistOption("Sachet-Parampara", setOf("hindi")),
        IndianArtistOption("Ilaiyaraaja", setOf("tamil", "telugu", "malayalam")),
        IndianArtistOption("Devi Sri Prasad", setOf("telugu", "tamil")),
        IndianArtistOption("Ajay-Atul", setOf("marathi", "hindi")),
        IndianArtistOption("Pawan Singh", setOf("bhojpuri")),
        IndianArtistOption("Khesari Lal Yadav", setOf("bhojpuri")),
        IndianArtistOption("Masoom Sharma", setOf("haryanvi")),
        IndianArtistOption("Humane Sagar", setOf("odia")),
        IndianArtistOption("Hanumankind", setOf("hindi", "malayalam")),
    )

    internal val EXTENDED_INDIAN_ARTISTS: List<IndianArtistOption> = (SUGGESTED_ARTISTS + listOf(
        // Bollywood / Hindi classics & contemporaries
        IndianArtistOption("Amit Trivedi", setOf("hindi")),
        IndianArtistOption("Vishal-Shekhar", setOf("hindi")),
        IndianArtistOption("Sachin-Jigar", setOf("hindi", "gujarati")),
        IndianArtistOption("Mithoon", setOf("hindi")),
        IndianArtistOption("Shankar-Ehsaan-Loy", setOf("hindi")),
        IndianArtistOption("Tanishk Bagchi", setOf("hindi")),
        IndianArtistOption("Kishore Kumar", setOf("hindi", "bengali")),
        IndianArtistOption("Lata Mangeshkar", setOf("hindi", "marathi", "bengali")),
        IndianArtistOption("Mohammed Rafi", setOf("hindi", "punjabi")),
        IndianArtistOption("Kumar Sanu", setOf("hindi", "bengali")),
        IndianArtistOption("Udit Narayan", setOf("hindi", "bhojpuri")),
        IndianArtistOption("Alka Yagnik", setOf("hindi")),
        IndianArtistOption("KK", setOf("hindi", "tamil")),
        IndianArtistOption("Mohit Chauhan", setOf("hindi")),
        IndianArtistOption("Rahat Fateh Ali Khan", setOf("hindi", "punjabi")),
        IndianArtistOption("Rekha Bhardwaj", setOf("hindi")),
        IndianArtistOption("Kailash Kher", setOf("hindi")),
        IndianArtistOption("Papon", setOf("hindi")),
        IndianArtistOption("B Praak", setOf("punjabi", "hindi")),
        IndianArtistOption("Neha Kakkar", setOf("hindi", "punjabi")),
        IndianArtistOption("Jasleen Royal", setOf("hindi", "punjabi")),
        IndianArtistOption("Raghav Chaitanya", setOf("hindi")),
        IndianArtistOption("Faheem Abdullah", setOf("hindi")),
        IndianArtistOption("Monali Thakur", setOf("hindi", "bengali")),
        // Indian Indie & Hip-Hop
        IndianArtistOption("Prateek Kuhad", setOf("hindi", "indie")),
        IndianArtistOption("Anuv Jain", setOf("hindi", "indie")),
        IndianArtistOption("Ritviz", setOf("hindi", "indie")),
        IndianArtistOption("When Chai Met Toast", setOf("indie", "malayalam")),
        IndianArtistOption("The Local Train", setOf("hindi", "indie")),
        IndianArtistOption("Lucky Ali", setOf("hindi", "indie")),
        IndianArtistOption("Aditya Rikhari", setOf("hindi", "indie")),
        IndianArtistOption("Mitraz", setOf("hindi", "indie")),
        IndianArtistOption("King", setOf("hindi", "indie")),
        IndianArtistOption("Zaeden", setOf("hindi", "indie")),
        IndianArtistOption("DIVINE", setOf("hindi", "indie")),
        IndianArtistOption("Seedhe Maut", setOf("hindi", "indie")),
        IndianArtistOption("KR\$NA", setOf("hindi", "indie")),
        IndianArtistOption("Raftaar", setOf("hindi", "punjabi")),
        IndianArtistOption("Talwiinder", setOf("punjabi", "indie")),
        IndianArtistOption("Osho Jain", setOf("hindi", "indie")),
        IndianArtistOption("Taba Chake", setOf("hindi", "indie")),
        IndianArtistOption("Nucleya", setOf("indie")),
        // Punjabi & Haryanvi
        IndianArtistOption("Guru Randhawa", setOf("punjabi", "hindi")),
        IndianArtistOption("Harrdy Sandhu", setOf("punjabi", "hindi")),
        IndianArtistOption("Ammy Virk", setOf("punjabi")),
        IndianArtistOption("Gippy Grewal", setOf("punjabi")),
        IndianArtistOption("Satinder Sartaaj", setOf("punjabi")),
        IndianArtistOption("Arjan Dhillon", setOf("punjabi")),
        IndianArtistOption("Parmish Verma", setOf("punjabi")),
        IndianArtistOption("Yo Yo Honey Singh", setOf("hindi", "punjabi")),
        IndianArtistOption("Dhanda Nyoliwala", setOf("haryanvi")),
        // Tamil & Telugu
        IndianArtistOption("Yuvan Shankar Raja", setOf("tamil")),
        IndianArtistOption("Harris Jayaraj", setOf("tamil")),
        IndianArtistOption("G.V. Prakash Kumar", setOf("tamil")),
        IndianArtistOption("Santhosh Narayanan", setOf("tamil")),
        IndianArtistOption("Sai Abhyankkar", setOf("tamil")),
        IndianArtistOption("Dhanush", setOf("tamil")),
        IndianArtistOption("Thaman S", setOf("telugu", "tamil")),
        IndianArtistOption("M.M. Keeravani", setOf("telugu", "hindi")),
        IndianArtistOption("Hesham Abdul Wahab", setOf("malayalam", "telugu")),
        IndianArtistOption("Ram Miriyala", setOf("telugu")),
        IndianArtistOption("Anurag Kulkarni", setOf("telugu")),
        // Malayalam, Kannada, Marathi, Bengali, Gujarati, Regional
        IndianArtistOption("Sushin Shyam", setOf("malayalam")),
        IndianArtistOption("Vidyasagar", setOf("malayalam", "tamil")),
        IndianArtistOption("Gopi Sundar", setOf("malayalam", "telugu")),
        IndianArtistOption("Shaan Rahman", setOf("malayalam")),
        IndianArtistOption("K.S. Chithra", setOf("malayalam", "tamil", "telugu", "kannada", "hindi")),
        IndianArtistOption("K.J. Yesudas", setOf("malayalam", "tamil", "hindi")),
        IndianArtistOption("Dabzee", setOf("malayalam")),
        IndianArtistOption("Arjun Janya", setOf("kannada")),
        IndianArtistOption("Ravi Basrur", setOf("kannada")),
        IndianArtistOption("B. Ajaneesh Loknath", setOf("kannada")),
        IndianArtistOption("Vijay Prakash", setOf("kannada", "hindi")),
        IndianArtistOption("Sanjith Hegde", setOf("kannada")),
        IndianArtistOption("Raghu Dixit", setOf("kannada", "indie")),
        IndianArtistOption("Avadhoot Gupte", setOf("marathi")),
        IndianArtistOption("Bela Shende", setOf("marathi")),
        IndianArtistOption("Sanju Rathod", setOf("marathi")),
        IndianArtistOption("Swapnil Bandodkar", setOf("marathi")),
        IndianArtistOption("Anupam Roy", setOf("bengali", "hindi")),
        IndianArtistOption("Rupam Islam", setOf("bengali")),
        IndianArtistOption("Nachiketa", setOf("bengali")),
        IndianArtistOption("Iman Chakraborty", setOf("bengali")),
        IndianArtistOption("Aditya Gadhvi", setOf("gujarati")),
        IndianArtistOption("Jigardan Gadhavi", setOf("gujarati")),
        IndianArtistOption("Kinjal Dave", setOf("gujarati")),
    )).distinctBy { it.name.lowercase(Locale.ROOT) }

    internal val ARTIST_LANGUAGE_MAP: Map<String, Set<String>> = EXTENDED_INDIAN_ARTISTS.associate {
        it.name.lowercase(Locale.ROOT) to it.languages
    }

    @Volatile
    var selectedLanguages: Set<String> = emptySet()
        private set

    @Volatile
    var selectedArtists: Set<String> = emptySet()
        private set

    @Volatile
    var configured: Boolean = false
        private set

    fun update(languages: Set<String>, artists: Set<String>, configured: Boolean) {
        selectedLanguages = languages
            .map { it.trim().lowercase(Locale.ROOT) }
            .filter { it.isNotEmpty() }
            .toSet()
        selectedArtists = artists
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .toSet()
        this.configured = configured
    }

    fun languageLabels(ids: Set<String> = selectedLanguages): List<String> =
        ids.mapNotNull { id ->
            LANGUAGES.firstOrNull { it.id.equals(id, ignoreCase = true) }?.label
                ?: id.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
        }

    fun hintTokens(): List<String> = buildList {
        add("indian")
        add("india")
        add("hindi")
        add("bollywood")
        add("desi")
        selectedLanguages.forEach { id ->
            val opt = LANGUAGES.firstOrNull { it.id.equals(id, ignoreCase = true) }
            if (opt != null) {
                opt.discoveryTerms.forEach { add(it.lowercase(Locale.ROOT)) }
            } else {
                add(id.lowercase(Locale.ROOT))
            }
        }
        selectedArtists.forEach { add(it.lowercase(Locale.ROOT)) }
    }.distinct()

    /**
     * Dynamically picks peer Indian artists matching [languages] while excluding
     * [exclude], so recommendations always spotlight peers alongside user picks.
     */
    fun peerArtistsFor(
        languages: Set<String> = selectedLanguages,
        exclude: Set<String> = selectedArtists,
        limit: Int = 5,
    ): List<String> {
        val excludedLower = exclude.map { it.trim().lowercase(Locale.ROOT) }.toSet()
        val normLangs = languages.map { it.trim().lowercase(Locale.ROOT) }.toSet()
        val matching = if (normLangs.isEmpty()) {
            SUGGESTED_ARTISTS
        } else {
            EXTENDED_INDIAN_ARTISTS.filter { opt -> opt.languages.any { it in normLangs } }
        }
        val primary = matching
            .asSequence()
            .map { it.name }
            .filterNot { it.lowercase(Locale.ROOT) in excludedLower }
            .distinctBy { it.lowercase(Locale.ROOT) }
            .take(limit)
            .toList()
        if (primary.size >= limit) return primary
        val extra = SUGGESTED_ARTISTS
            .asSequence()
            .map { it.name }
            .filterNot { it.lowercase(Locale.ROOT) in excludedLower }
            .filterNot { name -> primary.any { it.equals(name, ignoreCase = true) } }
            .take(limit - primary.size)
            .toList()
        return primary + extra
    }

    /** Returns diverse unselected Indian languages for broad cross-industry exploration. */
    fun complementaryLanguagesFor(
        selected: Set<String> = selectedLanguages,
        limit: Int = 6,
    ): List<IndianLanguageOption> {
        val norm = selected.map { it.trim().lowercase(Locale.ROOT) }.toSet()
        return LANGUAGES.filterNot { it.id in norm }.take(limit)
    }
}

/**
 * Keeps default discovery surfaces (Home, Explore, Find Your Mood, Top Picks)
 * clean of Chinese/CJK, US/Western charts, Christian/Gospel, Country, and
 * Spanish/Latin defaults, while leaving manual user Search completely unrestricted.
 */
object DiscoveryFilter {
    private val UNWANTED_CHINESE_KEYWORDS = listOf(
        "mandopop",
        "cantopop",
        "c-pop",
        "cpop",
        "chinese",
        "taiwan",
        "taiwanese",
        "hong kong",
        "hokkien",
        "guoyu",
        "huayu",
    )

    private val UNWANTED_REGION_AND_GENRE_REGEXES = listOf(
        // US / UK / Generic non-Indian global charts
        Regex("""\b(united states|u\.?s\.?\s*(?:charts?|top\s*\d+|hits)|top\s*(?:100|50|40|20|10)\s*(?:usa|u\.?s\.?|united states)|billboard|hot\s*100|top songs usa|american pop|uk top\s*\d*|uk charts?|united kingdom)\b""", RegexOption.IGNORE_CASE),
        Regex("""\b(global top\s*\d*|top\s*(?:100|50|40|20|10)\s*global|top songs global|global charts?)\b""", RegexOption.IGNORE_CASE),
        // Christian / Gospel / Worship
        Regex("""\b(christian(?:\s+music|\s+hits|\s+pop|\s+rock)?|gospel(?:\s+music|\s+hits)?|praise\s*(?:&|and)\s*worship|worship music|contemporary christian|hillsong|bethel music|elevation worship|jesus culture|\bccm\b)\b""", RegexOption.IGNORE_CASE),
        // Country / Americana / Bluegrass / Nashville
        Regex("""\b(country music|hot country|today'?s country|country hits|country classics|country roads|country heat|modern country|outlaw country|90s country|2000s country|country's greatest|americana|bluegrass|honky\s*tonk|nashville)\b""", RegexOption.IGNORE_CASE),
        // Spanish / Latin / Música Mexicana / Brazilian regional
        Regex("""\b(spanish|español|espanol|m[uú]sica\s+mexicana|regional\s+mexican|mexicana|latin(?:\s+music|\s+hits|\s+pop|\s+mix|\s+urbano)?|latino|latina|urbano(?:\s+latino)?|reggaeton|bachata|corridos|norteñ[oa]|banda\s+mexicana|m[uú]sica\s+de\s+banda|cumbia|sertanejo|dembow|vallenato|mariachi|flamenco|merengue|trap\s+latino|pop\s+latino|éxitos|exitos)\b""", RegexOption.IGNORE_CASE),
    )

    private val STANDALONE_COUNTRY_REGEX = Regex("""\bcountry\b""", RegexOption.IGNORE_CASE)
    private val YEAR_REGEX = Regex("""\b(19\d{2}|20[0-3]\d)\b""")

    private val INDIAN_SIGNAL_KEYWORDS = listOf(
        "india", "indian", "hindi", "bollywood", "desi", "punjabi", "bhangra",
        "tamil", "kollywood", "telugu", "tollywood", "malayalam", "mollywood",
        "kannada", "sandalwood", "marathi", "bengali", "bangla", "gujarati", "garba",
        "bhojpuri", "haryanvi", "odia", "oriya", "indipop", "indi-pop", "i-pop", "ipop",
        "hindustani", "carnatic", "sufi", "ghazal", "qawwali", "bhajan", "filmi",
        "coke studio", "t-series", "zee music", "saregama", "sony music india",
        "tips official", "yrf", "lofi hindi", "hindi lofi", "bollywood lofi",
        "indian indie", "indian classical", "sitar", "bansuri", "tabla", "raga",
        "90s bollywood", "2000s bollywood", "retro bollywood",
    )

    fun containsCjkScript(text: String?): Boolean {
        if (text.isNullOrBlank()) return false
        var i = 0
        while (i < text.length) {
            val cp = Character.codePointAt(text, i)
            val isHan = cp in 0x4E00..0x9FFF ||
                cp in 0x3400..0x4DBF ||
                cp in 0xF900..0xFAFF ||
                cp in 0x3100..0x312F ||
                cp in 0x31A0..0x31BF ||
                cp in 0x20000..0x2EBEF
            if (isHan) return true
            i += Character.charCount(cp)
        }
        return false
    }

    fun containsIndicScript(text: String?): Boolean {
        if (text.isNullOrBlank()) return false
        var i = 0
        while (i < text.length) {
            val cp = Character.codePointAt(text, i)
            if (cp in 0x0900..0x0D7F) return true // Devanagari..Malayalam
            i += Character.charCount(cp)
        }
        return false
    }

    fun hasIndianSignal(vararg fields: String?): Boolean {
        val combined = fields.filterNotNull().joinToString(" ").trim()
        if (combined.isBlank()) return false
        if (containsIndicScript(combined)) return true
        val lower = combined.lowercase(Locale.ROOT)
        if (INDIAN_SIGNAL_KEYWORDS.any { keyword -> lower.contains(keyword) }) return true
        if (IndianMusicPreferences.ARTIST_LANGUAGE_MAP.keys.any { artist -> artistMatch(lower, artist) }) return true
        if (IndianMusicPreferences.selectedArtists.any { artist -> artistMatch(lower, artist.lowercase(Locale.ROOT)) }) {
            return true
        }
        return false
    }

    internal fun artistMatch(haystackLower: String, artistLower: String): Boolean {
        if (artistLower.length < 3) {
            return Regex("""\b${Regex.escape(artistLower)}\b""").containsMatchIn(haystackLower)
        }
        return haystackLower.contains(artistLower)
    }

    fun isUnwantedDefaultDiscoveryText(vararg fields: String?): Boolean {
        val nonBlank = fields.filterNotNull().filter { it.isNotBlank() }
        if (nonBlank.isEmpty()) return false
        val combinedHasIndianSignal = hasIndianSignal(*fields)
        for (raw in nonBlank) {
            if (containsCjkScript(raw)) return true
            val lower = raw.lowercase(Locale.ROOT)
            if (UNWANTED_CHINESE_KEYWORDS.any { keyword -> lower.contains(keyword) }) return true
            if (UNWANTED_REGION_AND_GENRE_REGEXES.any { it.containsMatchIn(raw) }) return true
            if (!combinedHasIndianSignal &&
                !lower.contains("in your country") &&
                STANDALONE_COUNTRY_REGEX.containsMatchIn(raw)
            ) {
                return true
            }
        }
        return false
    }

    fun isUnwantedDiscoverySong(song: Song): Boolean =
        isUnwantedDefaultDiscoveryText(song.title, song.artist, song.albumName)

    fun isAlbumBrowseId(browseId: String?): Boolean {
        if (browseId.isNullOrBlank()) return false
        return browseId.startsWith("MPRE") ||
            browseId.startsWith("OLAK") ||
            browseId.startsWith("VLOLAK") ||
            browseId.startsWith("local:group:album:")
    }

    fun isAlbumItem(item: ShelfItem): Boolean = isAlbumBrowseId(item.browseId)

    fun sanitizeShelf(
        shelf: HomeShelf,
        stripAlbums: Boolean = false,
        stripChinese: Boolean = true,
    ): HomeShelf? {
        if (stripChinese && isUnwantedDefaultDiscoveryText(shelf.title, shelf.subtitle)) return null
        val filtered = shelf.items.filter { item ->
            val okLanguage = !stripChinese || !isUnwantedDefaultDiscoveryText(item.title, item.subtitle)
            val okAlbum = !stripAlbums || !isAlbumItem(item)
            okLanguage && okAlbum
        }
        if (filtered.isEmpty()) return null
        return if (filtered.size == shelf.items.size) shelf else shelf.copy(items = filtered)
    }

    fun sanitizeMoodGenreSections(sections: List<MoodGenreSection>): List<MoodGenreSection> =
        sections.mapNotNull { section ->
            if (isUnwantedDefaultDiscoveryText(section.title)) return@mapNotNull null
            val filtered = section.items.filterNot { isUnwantedDefaultDiscoveryText(it.title) }
            if (filtered.isEmpty()) null else section.copy(items = filtered)
        }

    fun extractReleaseYear(vararg fields: String?): Int {
        var best = 0
        for (raw in fields) {
            if (raw.isNullOrBlank()) continue
            YEAR_REGEX.findAll(raw).forEach { match ->
                val year = match.value.toIntOrNull() ?: return@forEach
                if (year > best) best = year
            }
        }
        return best
    }

    /** Higher means more recent based strictly on metadata returned by the source. */
    fun recencyScore(song: Song): Int {
        val year = extractReleaseYear(song.albumName, song.title)
        if (year > 0) return year * 10
        val lowerTitle = song.title.lowercase(Locale.ROOT)
        return when {
            "latest" in lowerTitle || "new" in lowerTitle -> 1
            else -> 0
        }
    }
}

/**
 * Dynamic, diversity-preserving recommendation ranker.
 *
 * Treats [IndianMusicPreferences.selectedLanguages] and [IndianMusicPreferences.selectedArtists]
 * as positive ranking boosts rather than exclusive whitelists, while enforcing per-artist caps,
 * back-to-back artist de-clustering, and periodic exploration slots across Indian languages and
 * industries (Bollywood, Indian Indie, Punjabi, Tamil, Telugu, Marathi, Bengali, Malayalam, Kannada).
 */
object DiscoveryRanker {

    private val NON_ARTIST_LABELS = setOf(
        "",
        "youtube music",
        "playlist",
        "album",
        "single",
        "ep",
        "song",
        "chart",
        "video",
        "various artists",
        "unknown artist",
        "t-series",
        "zee music company",
        "sony music india",
        "saregama",
        "tips official",
        "yrf",
    )

    private val LANGUAGE_MATCH_TERMS: List<Pair<String, Regex>> = listOf(
        "hindi" to Regex("""\b(hindi|bollywood|indipop|filmi|sufi|ghazal|90s hindi|2000s hindi)\b""", RegexOption.IGNORE_CASE),
        "punjabi" to Regex("""\b(punjabi|bhangra|pollywood)\b""", RegexOption.IGNORE_CASE),
        "tamil" to Regex("""\b(tamil|kollywood)\b""", RegexOption.IGNORE_CASE),
        "telugu" to Regex("""\b(telugu|tollywood)\b""", RegexOption.IGNORE_CASE),
        "malayalam" to Regex("""\b(malayalam|mollywood)\b""", RegexOption.IGNORE_CASE),
        "kannada" to Regex("""\b(kannada|sandalwood)\b""", RegexOption.IGNORE_CASE),
        "marathi" to Regex("""\b(marathi)\b""", RegexOption.IGNORE_CASE),
        "bengali" to Regex("""\b(bengali|bangla|rabindra)\b""", RegexOption.IGNORE_CASE),
        "gujarati" to Regex("""\b(gujarati|garba|dandiya)\b""", RegexOption.IGNORE_CASE),
        "bhojpuri" to Regex("""\b(bhojpuri)\b""", RegexOption.IGNORE_CASE),
        "haryanvi" to Regex("""\b(haryanvi)\b""", RegexOption.IGNORE_CASE),
        "odia" to Regex("""\b(odia|oriya)\b""", RegexOption.IGNORE_CASE),
        "indie" to Regex("""\b(indie|independent|unplugged|acoustic)\b""", RegexOption.IGNORE_CASE),
    )

    fun inferLanguages(vararg fields: String?): Set<String> {
        val combined = fields.filterNotNull().joinToString(" ").trim()
        if (combined.isBlank()) return emptySet()
        val found = LinkedHashSet<String>()

        // Explicit script detection
        var i = 0
        while (i < combined.length) {
            val cp = Character.codePointAt(combined, i)
            when (cp) {
                in 0x0900..0x097F -> found += if ("marathi" in combined.lowercase(Locale.ROOT)) "marathi" else "hindi"
                in 0x0980..0x09FF -> found += "bengali"
                in 0x0A00..0x0A7F -> found += "punjabi"
                in 0x0A80..0x0AFF -> found += "gujarati"
                in 0x0B00..0x0B7F -> found += "odia"
                in 0x0B80..0x0BFF -> found += "tamil"
                in 0x0C00..0x0C7F -> found += "telugu"
                in 0x0C80..0x0CFF -> found += "kannada"
                in 0x0D00..0x0D7F -> found += "malayalam"
            }
            i += Character.charCount(cp)
        }

        val lower = combined.lowercase(Locale.ROOT)
        LANGUAGE_MATCH_TERMS.forEach { (lang, regex) ->
            if (regex.containsMatchIn(lower)) found += lang
        }
        IndianMusicPreferences.ARTIST_LANGUAGE_MAP.forEach { (artist, langs) ->
            if (DiscoveryFilter.artistMatch(lower, artist)) {
                found += langs
            }
        }
        return found
    }

    fun extractPrimaryArtist(rawSubtitleOrArtist: String?, title: String = ""): String {
        val raw = rawSubtitleOrArtist?.trim().orEmpty()
        if (raw.isBlank()) return ""
        val segments = raw.split('•', '·', '|')
            .map { it.trim() }
            .filter { it.isNotEmpty() }
        for (segment in segments) {
            val lowerSeg = segment.lowercase(Locale.ROOT)
            if (isMetadataDescriptor(lowerSeg)) continue
            val lead = segment
                .split(",", "&", " feat.", " ft.", " featuring ", " x ")
                .firstOrNull()
                ?.trim()
                .orEmpty()
            val normLead = lead.lowercase(Locale.ROOT)
            if (normLead.isNotBlank() && !isMetadataDescriptor(normLead)) {
                return normLead
            }
        }
        // Fallback: if title itself names a known artist card
        val titleLower = title.trim().lowercase(Locale.ROOT)
        if (titleLower in IndianMusicPreferences.ARTIST_LANGUAGE_MAP) return titleLower
        return ""
    }

    private fun isMetadataDescriptor(lower: String): Boolean {
        if (lower in NON_ARTIST_LABELS) return true
        if (lower.matches(Regex("""^(19\d{2}|20[0-3]\d)$"""))) return true
        if (lower.endsWith(" views") || lower.endsWith(" plays") || lower.endsWith(" songs") || lower.endsWith(" subscribers")) {
            return true
        }
        if (lower.matches(Regex("""^\d+[:]\d{2}$"""))) return true
        return false
    }

    fun rankSongs(
        songs: List<Song>,
        languages: Set<String> = IndianMusicPreferences.selectedLanguages,
        artists: Set<String> = IndianMusicPreferences.selectedArtists,
        limit: Int = songs.size,
    ): List<Song> {
        if (songs.isEmpty() || limit <= 0) return emptyList()
        val wrapped = songs
            .filterNot(DiscoveryFilter::isUnwantedDiscoverySong)
            .distinctBy { it.videoId }
            .mapIndexed { idx, song ->
                val leadArtist = song.artists.firstOrNull()?.name ?: song.artist
                RankCandidate(
                    value = song,
                    originalIndex = idx,
                    artistKey = extractPrimaryArtist(leadArtist, song.title),
                    searchableLower = "${song.title} ${song.artist} ${song.albumName.orEmpty()}".lowercase(Locale.ROOT),
                    langs = inferLanguages(song.title, song.artist, song.albumName),
                    isIndian = DiscoveryFilter.hasIndianSignal(song.title, song.artist, song.albumName),
                    year = DiscoveryFilter.extractReleaseYear(song.albumName, song.title),
                    hasNewHint = song.title.contains("latest", ignoreCase = true) ||
                        song.title.contains("new", ignoreCase = true),
                )
            }
        return rankCandidates(wrapped, languages, artists, limit)
    }

    fun rankShelfItems(
        items: List<ShelfItem>,
        languages: Set<String> = IndianMusicPreferences.selectedLanguages,
        artists: Set<String> = IndianMusicPreferences.selectedArtists,
        limit: Int = items.size,
    ): List<ShelfItem> {
        if (items.isEmpty() || limit <= 0) return emptyList()
        val wrapped = items
            .filterNot { DiscoveryFilter.isUnwantedDefaultDiscoveryText(it.title, it.subtitle) }
            .distinctBy { it.videoId ?: it.browseId ?: "${it.title}:${it.subtitle}" }
            .mapIndexed { idx, item ->
                RankCandidate(
                    value = item,
                    originalIndex = idx,
                    artistKey = extractPrimaryArtist(item.subtitle, item.title),
                    searchableLower = "${item.title} ${item.subtitle}".lowercase(Locale.ROOT),
                    langs = inferLanguages(item.title, item.subtitle),
                    isIndian = DiscoveryFilter.hasIndianSignal(item.title, item.subtitle),
                    year = DiscoveryFilter.extractReleaseYear(item.title, item.subtitle),
                    hasNewHint = item.title.contains("latest", ignoreCase = true) ||
                        item.title.contains("new", ignoreCase = true),
                )
            }
        return rankCandidates(wrapped, languages, artists, limit)
    }

    private class RankCandidate<T>(
        val value: T,
        val originalIndex: Int,
        val artistKey: String,
        val searchableLower: String,
        val langs: Set<String>,
        val isIndian: Boolean,
        val year: Int,
        val hasNewHint: Boolean,
    )

    private fun <T> rankCandidates(
        candidates: List<RankCandidate<T>>,
        languages: Set<String>,
        artists: Set<String>,
        limit: Int,
    ): List<T> {
        if (candidates.isEmpty()) return emptyList()
        val targetCount = minOf(limit, candidates.size)
        val normLanguages = languages.map { it.trim().lowercase(Locale.ROOT) }.filter { it.isNotEmpty() }.toSet()
        val normArtists = artists.map { it.trim().lowercase(Locale.ROOT) }.filter { it.isNotEmpty() }.toSet()

        val selectedArtistCap = when {
            targetCount <= 6 -> 1
            targetCount <= 10 -> 2
            else -> 3
        }
        val otherArtistCap = when {
            targetCount <= 6 -> 1
            else -> 2
        }

        // Expand selected languages to include their discovery terms for direct text matches.
        val languageDirectTerms = normLanguages.flatMap { langId ->
            IndianMusicPreferences.LANGUAGES.firstOrNull { it.id == langId }
                ?.discoveryTerms
                ?.map { it.lowercase(Locale.ROOT) }
                ?: listOf(langId)
        }.toSet()

        val remaining = candidates.toMutableList()
        val total = candidates.size.toDouble().coerceAtLeast(1.0)
        val result = ArrayList<T>(targetCount)
        val artistCounts = HashMap<String, Int>()
        val langCounts = HashMap<String, Int>()
        val recentArtists = ArrayDeque<String>(2)
        var lastPrimaryLang: String? = null
        var complementaryCount = 0

        val hasCrossIndustryPool = normLanguages.isNotEmpty() &&
            candidates.any { c -> c.langs.any { it !in normLanguages } }

        for (pos in 0 until targetCount) {
            if (remaining.isEmpty()) break
            // Guarantee complementary Indian scenes get periodic lift so one language/artist never dominates.
            val selectedShareSoFar = if (pos == 0) 0.0 else (pos - complementaryCount).toDouble() / pos
            val explorationBoostActive = hasCrossIndustryPool && pos >= 2 &&
                ((pos + 1) % 3 == 0 || selectedShareSoFar > 0.65)
            val distinctRemainingLangs = remaining.flatMapTo(HashSet()) { it.langs }.size

            var bestIdx = 0
            var bestScore = Double.NEGATIVE_INFINITY

            for (i in remaining.indices) {
                val c = remaining[i]
                val matchesSelectedArtist = normArtists.isNotEmpty() &&
                    normArtists.any { DiscoveryFilter.artistMatch(c.searchableLower, it) }
                val matchesSelectedLanguage = normLanguages.isNotEmpty() && (
                    c.langs.any { it in normLanguages } ||
                        languageDirectTerms.any { term -> Regex("""\b${Regex.escape(term)}\b""").containsMatchIn(c.searchableLower) }
                    )

                val recencyBonus = when {
                    c.year >= 2024 -> 18.0
                    c.year >= 2020 -> 10.0
                    c.year >= 2010 -> 4.0
                    c.year > 0 -> 2.0
                    c.hasNewHint -> 5.0
                    else -> 0.0
                }
                val sourceOrderBonus = (1.0 - c.originalIndex / total) * 8.0

                var score = sourceOrderBonus + recencyBonus
                if (matchesSelectedArtist) {
                    score += 50.0
                    if (c.year >= 2023 || c.hasNewHint) score += 8.0
                }
                if (matchesSelectedLanguage) {
                    score += 28.0
                    if (!matchesSelectedArtist && c.artistKey.isNotEmpty()) score += 6.0
                }
                if (c.isIndian) score += 14.0

                val nonSelectedIndianLangs = c.langs.filter { it !in normLanguages }
                if (nonSelectedIndianLangs.isNotEmpty()) {
                    score += 8.0
                    if (explorationBoostActive) {
                        score += 32.0
                    }
                }

                // Per-artist diversity cap & back-to-back de-clustering.
                if (c.artistKey.isNotEmpty()) {
                    val count = artistCounts[c.artistKey] ?: 0
                    val cap = if (matchesSelectedArtist) selectedArtistCap else otherArtistCap
                    if (count >= cap) {
                        score -= 180.0 + 40.0 * (count - cap)
                    } else {
                        score -= count * 14.0
                        if (recentArtists.lastOrNull() == c.artistKey) {
                            score -= 95.0
                        } else if (c.artistKey in recentArtists) {
                            score -= 35.0
                        }
                    }
                }

                // Industry/language diminishing return & interleaving.
                val primaryLang = c.langs.firstOrNull { it !in normLanguages } ?: c.langs.firstOrNull()
                if (primaryLang != null) {
                    val lCount = langCounts[primaryLang] ?: 0
                    score -= lCount * 5.0
                    if (primaryLang == lastPrimaryLang && distinctRemainingLangs > 1) {
                        score -= 12.0
                    }
                }

                if (score > bestScore) {
                    bestScore = score
                    bestIdx = i
                }
            }

            val chosen = remaining.removeAt(bestIdx)
            result += chosen.value

            if (chosen.artistKey.isNotEmpty()) {
                artistCounts[chosen.artistKey] = (artistCounts[chosen.artistKey] ?: 0) + 1
                if (recentArtists.size >= 2) recentArtists.removeFirst()
                recentArtists.addLast(chosen.artistKey)
            }
            val primaryLang = chosen.langs.firstOrNull { it !in normLanguages } ?: chosen.langs.firstOrNull()
            chosen.langs.forEach { lang ->
                langCounts[lang] = (langCounts[lang] ?: 0) + 1
            }
            if (primaryLang != null) {
                lastPrimaryLang = primaryLang
                if (primaryLang !in normLanguages) complementaryCount++
            } else if (!chosen.searchableLower.let { s -> languageDirectTerms.any { it in s } }) {
                // Unknown/neutral non-selected tag
            }
        }
        return result
    }
}

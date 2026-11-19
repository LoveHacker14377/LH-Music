package com.music.bitchord.data

import com.music.bitchord.data.model.HomeShelf
import com.music.bitchord.data.model.ShelfItem
import com.music.bitchord.data.model.ShelfLayout
import com.music.bitchord.data.model.Song
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class IndianDiscoveryTest {

    @AfterTest
    fun resetPreferences() {
        IndianMusicPreferences.update(emptySet(), emptySet(), configured = false)
    }

    @Test
    fun cjkAndChineseDiscoveryFilterRejectsChineseTextButKeepsIndianAndEnglish() {
        assertTrue(DiscoveryFilter.containsCjkScript("周杰倫"))
        assertTrue(DiscoveryFilter.containsCjkScript("晴天 Jay Chou"))
        assertFalse(DiscoveryFilter.containsCjkScript("Tum Hi Ho - Arijit Singh"))
        assertFalse(DiscoveryFilter.containsCjkScript("नाटू नाटू"))

        assertTrue(DiscoveryFilter.isUnwantedDefaultDiscoveryText("Top Mandopop Hits"))
        assertTrue(DiscoveryFilter.isUnwantedDefaultDiscoveryText("Cantopop Essentials"))
        assertTrue(DiscoveryFilter.isUnwantedDefaultDiscoveryText("Taiwanese Indie"))
        assertFalse(DiscoveryFilter.isUnwantedDefaultDiscoveryText("Bollywood Romance 2025"))
        assertFalse(DiscoveryFilter.isUnwantedDefaultDiscoveryText("Punjabi Party Mix"))
    }

    @Test
    fun discoveryFilterRejectsUsChartsChristianCountryAndSpanishLatinDefaultsWithoutFalsePositives() {
        // US / Global / UK non-Indian charts
        assertTrue(DiscoveryFilter.isUnwantedDefaultDiscoveryText("Top 100 Songs United States"))
        assertTrue(DiscoveryFilter.isUnwantedDefaultDiscoveryText("US Charts · Top 50"))
        assertTrue(DiscoveryFilter.isUnwantedDefaultDiscoveryText("Billboard Hot 100"))
        assertTrue(DiscoveryFilter.isUnwantedDefaultDiscoveryText("Global Top 100"))

        // Christian / Gospel
        assertTrue(DiscoveryFilter.isUnwantedDefaultDiscoveryText("Contemporary Christian Hits"))
        assertTrue(DiscoveryFilter.isUnwantedDefaultDiscoveryText("Sunday Gospel & Worship"))
        assertTrue(DiscoveryFilter.isUnwantedDefaultDiscoveryText("Praise & Worship Essentials"))

        // Country
        assertTrue(DiscoveryFilter.isUnwantedDefaultDiscoveryText("Hot Country"))
        assertTrue(DiscoveryFilter.isUnwantedDefaultDiscoveryText("Today's Country Classics"))
        assertTrue(DiscoveryFilter.isUnwantedDefaultDiscoveryText("Country"))
        assertTrue(DiscoveryFilter.isUnwantedDefaultDiscoveryText("Nashville Hits"))

        // Spanish / Latin / Música Mexicana
        assertTrue(DiscoveryFilter.isUnwantedDefaultDiscoveryText("Música Mexicana 2025"))
        assertTrue(DiscoveryFilter.isUnwantedDefaultDiscoveryText("Latin Hits · Urbano"))
        assertTrue(DiscoveryFilter.isUnwantedDefaultDiscoveryText("Reggaeton & Bachata"))
        assertTrue(DiscoveryFilter.isUnwantedDefaultDiscoveryText("Top 50 Español"))

        // Legitimate Indian tracks & idioms (including Hindi "Banda") stay safe
        assertFalse(DiscoveryFilter.isUnwantedDefaultDiscoveryText("Apna Bana Le", "Arijit Singh · Bhediya"))
        assertFalse(DiscoveryFilter.isUnwantedDefaultDiscoveryText("Banda Kaam Ka", "Diljit Dosanjh"))
        assertFalse(DiscoveryFilter.isUnwantedDefaultDiscoveryText("Top in your country", "Bollywood Hits"))
        assertFalse(DiscoveryFilter.isUnwantedDefaultDiscoveryText("Hanumankind - Big Dawgs", "Indian Hip-Hop"))

        // Manual search region remains unrestricted for non-CJK queries
        assertNull(LocaleTags.ytSearchGl("en"))
    }

    @Test
    fun sanitizeShelfStripsAlbumsChineseAndUnrelatedGlobalGenresFromMoodAndHomeShelves() {
        val mixedShelf = HomeShelf(
            title = "Find Your Mood",
            subtitle = "Curated Stations",
            layout = ShelfLayout.CARDS,
            items = listOf(
                ShelfItem("Aashiqui 2 Album", "Arijit Singh", null, null, "MPREb_12345"),
                ShelfItem("Old Album OLAK", "Various Artists", null, null, "OLAK5uy_99999"),
                ShelfItem("Mandopop Chill", "中文歌单", null, null, "VLPL_chinese"),
                ShelfItem("Hot Country", "Nashville", null, null, "VLPL_country"),
                ShelfItem("Música Mexicana", "Latin", null, null, "VLPL_latin"),
                ShelfItem("Gospel Worship", "Christian", null, null, "VLPL_christian"),
                ShelfItem("Bollywood Acoustic Vibes", "Curated Playlist", null, null, "VLPL_indian_mood"),
                ShelfItem("Kesariya", "Pritam · Arijit Singh", null, "BddP6PYo2gs", null),
            ),
        )

        val cleaned = assertNotNull(
            DiscoveryFilter.sanitizeShelf(mixedShelf, stripAlbums = true, stripChinese = true),
        )
        assertEquals(
            listOf("Bollywood Acoustic Vibes", "Kesariya"),
            cleaned.items.map { it.title },
        )

        val chineseShelf = mixedShelf.copy(title = "Top Mandopop Hits")
        assertNull(DiscoveryFilter.sanitizeShelf(chineseShelf, stripAlbums = true, stripChinese = true))
    }

    @Test
    fun recencyScorePrefersNewerReleaseMetadataOverOlderTracks() {
        val recent = Song("v1", "Sajni", "Arijit Singh", null, albumName = "Laapataa Ladies (2024)")
        val classic = Song("v2", "Tujhe Dekha To", "Kumar Sanu", null, albumName = "DDLJ (1995)")
        val unlabeled = Song("v3", "Soulful Track", "Shreya Ghoshal", null, albumName = "Single")

        assertTrue(DiscoveryFilter.recencyScore(recent) > DiscoveryFilter.recencyScore(classic))
        assertTrue(DiscoveryFilter.recencyScore(classic) > DiscoveryFilter.recencyScore(unlabeled))
    }

    @Test
    fun discoveryCursorBlendsLeadStageAndCoversDiverseIndianIndustriesWithoutDuplicates() {
        val cursor = YtMusicRepository.buildIndianDiscoveryCursor(
            languages = setOf("hindi"),
            artists = setOf("Arijit Singh", "  arijit singh "),
        )

        val titles = cursor.stages.map { it.title }
        assertTrue(cursor.hasMore)
        assertEquals("Fresh Indian Mix for You", titles[0])
        assertTrue(cursor.stages[0].extraQueries.any { "Arijit Singh" in it })
        assertTrue(cursor.stages[0].extraQueries.size >= 2)
        assertTrue(titles.any { "Arijit Singh & Similar Indian Voices" == it })
        assertTrue(titles.any { "New Hindi & Indie Hits" == it })
        assertTrue(titles.any { "Indian Indie & Emerging Voices" == it })
        assertTrue(titles.any { "Punjabi & Haryanvi Fresh Hits" == it })
        assertTrue(titles.any { "Tamil & Telugu New Releases" == it })
        assertTrue(titles.any { "Malayalam, Kannada & Marathi Gems" == it })
        assertTrue(titles.any { "Bengali, Gujarati & Regional India" == it })
        assertTrue(titles.any { "All-Time Indian Essentials" == it })
        assertTrue(titles.any { "Indian Throwbacks: 90s & 2000s" == it })
        assertTrue(titles.any { "Mindful Indian Instrumentals" == it })
        assertEquals(titles.distinct().size, titles.size)
    }

    @Test
    fun rankSongsBoostsHindiAndArijitSinghWhilePreservingMultiArtistAndMultiLanguageDiversity() {
        val pool = listOf(
            Song("a1", "O Maahi", "Arijit Singh", null, albumName = "Dunki (2023)"),
            Song("a2", "Sajni", "Arijit Singh", null, albumName = "Laapataa Ladies (2024)"),
            Song("a3", "Satranga", "Arijit Singh", null, albumName = "Animal (2023)"),
            Song("a4", "Soulmate", "Badshah, Arijit Singh", null, albumName = "Ek Tha Raja (2024)"),
            Song("a5", "Ve Kamleya", "Arijit Singh, Shreya Ghoshal", null, albumName = "Rocky Aur Rani (2023)"),
            Song("a6", "Tum Hi Ho", "Arijit Singh", null, albumName = "Aashiqui 2 (2013)"),
            Song("h1", "Jo Tum Mere Ho", "Anuv Jain", null, albumName = "Single (2024)"),
            Song("h2", "Ishq", "Faheem Abdullah", null, albumName = "Lost;Found (2024)"),
            Song("h3", "Pehle Bhi Main", "Vishal Mishra", null, albumName = "Animal (2023)"),
            Song("h4", "Mere Mehboob", "Kishore Kumar", null, albumName = "Classic (1975)"),
            Song("p1", "Lover", "Diljit Dosanjh", null, albumName = "MoonChild Era (2021)"),
            Song("p2", "Softly", "Karan Aujla", null, albumName = "Making Memories (2023)"),
            Song("t1", "Hukum", "Anirudh Ravichander", null, albumName = "Jailer (2023)"),
            Song("t2", "Munbe Vaa", "A.R. Rahman, Shreya Ghoshal", null, albumName = "Sillunu Oru Kaadhal (2006)"),
            Song("te1", "Naatu Naatu", "M.M. Keeravani", null, albumName = "RRR (2022)"),
            Song("m1", "Zingaat", "Ajay-Atul", null, albumName = "Sairat (2016) · Marathi"),
            Song("b1", "Amake Amar Moto Thakte Dao", "Anupam Roy", null, albumName = "Autograph (2010) · Bengali"),
            Song("ml1", "Illuminati", "Sushin Shyam", null, albumName = "Aavesham (2024) · Malayalam"),
            Song("k1", "Belageddu", "Vijay Prakash", null, albumName = "Kirik Party (2016) · Kannada"),
            Song("us1", "Texas Hold Em", "Country Stars", null, albumName = "Hot Country (2024)"),
        )

        val ranked = DiscoveryRanker.rankSongs(
            songs = pool,
            languages = setOf("hindi"),
            artists = setOf("Arijit Singh"),
            limit = 15,
        )

        assertEquals(15, ranked.size)
        // Unwanted Country track must never appear
        assertFalse(ranked.any { it.videoId == "us1" })
        // Recent Arijit Singh track must get boosted to top priority
        assertTrue(ranked.take(2).any { "Arijit Singh" in it.artist && DiscoveryFilter.recencyScore(it) >= 20230 })
        // Must NEVER be Arijit-only (capped at <= 3 out of 15 and never back-to-back)
        val arijitLeadCount = ranked.count {
            DiscoveryRanker.extractPrimaryArtist(it.artist, it.title) == "arijit singh"
        }
        assertTrue(arijitLeadCount in 1..3, "Expected 1..3 Arijit lead slots, got $arijitLeadCount")
        for (i in 1 until ranked.size) {
            val prevArtist = DiscoveryRanker.extractPrimaryArtist(ranked[i - 1].artist, ranked[i - 1].title)
            val currArtist = DiscoveryRanker.extractPrimaryArtist(ranked[i].artist, ranked[i].title)
            assertFalse(prevArtist.isNotEmpty() && prevArtist == currArtist, "Back-to-back artist $currArtist at $i")
        }
        // Must include non-Hindi regional diversity (Punjabi, Tamil, Telugu, Marathi, Bengali, Malayalam, Kannada)
        val coveredLangs = ranked.flatMapTo(HashSet()) { DiscoveryRanker.inferLanguages(it.title, it.artist, it.albumName) }
        assertTrue("hindi" in coveredLangs)
        assertTrue(
            listOf("punjabi", "tamil", "telugu", "marathi", "bengali", "malayalam", "kannada")
                .count { it in coveredLangs } >= 4,
            "Expected strong cross-industry coverage, got $coveredLangs",
        )
    }

    @Test
    fun rankSongsAdaptsDynamicallyToNonHindiPreferencesWithoutHardcoding() {
        val pool = listOf(
            Song("a1", "Sajni", "Arijit Singh", null, albumName = "Laapataa Ladies (2024)"),
            Song("t1", "Fear Song", "Anirudh Ravichander", null, albumName = "Devara (2024) Tamil"),
            Song("t2", "Chuttamalle", "Anirudh Ravichander", null, albumName = "Devara (2024)"),
            Song("t3", "Vaseegara", "Harris Jayaraj", null, albumName = "Minnale (2001) Tamil"),
            Song("p1", "G.O.A.T.", "Diljit Dosanjh", null, albumName = "Punjabi (2020)"),
            Song("ml1", "Aavesham Theme", "Sushin Shyam", null, albumName = "Malayalam (2024)"),
            Song("m1", "Gulabi Sadi", "Sanju Rathod", null, albumName = "Marathi (2024)"),
        )

        val ranked = DiscoveryRanker.rankSongs(
            songs = pool,
            languages = setOf("tamil"),
            artists = setOf("Anirudh Ravichander"),
            limit = 6,
        )

        assertEquals(6, ranked.size)
        // Selected artist for Tamil prefs leads without excluding Punjabi/Hindi/Malayalam/Marathi
        assertTrue(ranked.first().artist.contains("Anirudh Ravichander"))
        val artistsOut = ranked.map { DiscoveryRanker.extractPrimaryArtist(it.artist, it.title) }
        assertTrue(artistsOut.distinct().size >= 5)
    }

    @Test
    fun rankShelfItemsBoostsPreferredLanguagePlaylistsWithoutCollidingOnEditorialPublisher() {
        val playlists = listOf(
            ShelfItem("Hot Country Hits", "Playlist • YouTube Music", null, null, "VL_us_country"),
            ShelfItem("Tamil Kollywood Vibes", "Playlist • YouTube Music", null, null, "VL_tamil"),
            ShelfItem("Indie India Acoustic", "Playlist • YouTube Music", null, null, "VL_indie"),
            ShelfItem("Hindi All-Time Essentials", "Playlist • YouTube Music", null, null, "VL_hindi"),
            ShelfItem("Punjabi Bangers", "Playlist • YouTube Music", null, null, "VL_punjabi"),
            ShelfItem("Marathi Throwbacks", "Playlist • YouTube Music", null, null, "VL_marathi"),
        )

        val ranked = DiscoveryRanker.rankShelfItems(
            items = playlists,
            languages = setOf("hindi"),
            artists = setOf("Arijit Singh"),
            limit = 6,
        )

        // All 5 Indian playlists are kept (editorial "YouTube Music" publisher does not trip artist cap),
        // unwanted Country playlist is dropped, and Hindi All-Time Essentials leads.
        assertEquals(5, ranked.size)
        assertEquals("VL_hindi", ranked.first().browseId)
        assertFalse(ranked.any { it.browseId == "VL_us_country" })
    }

    @Test
    fun musicPreferencesUpdateNormalizesValuesAndBuildsDistinctHintTokens() {
        IndianMusicPreferences.update(
            languages = setOf(" Tamil ", "punjabi", ""),
            artists = setOf("Shreya Ghoshal", " "),
            configured = true,
        )

        assertTrue(IndianMusicPreferences.configured)
        assertEquals(setOf("tamil", "punjabi"), IndianMusicPreferences.selectedLanguages)
        assertEquals(setOf("Shreya Ghoshal"), IndianMusicPreferences.selectedArtists)
        val hints = IndianMusicPreferences.hintTokens()
        assertTrue("kollywood" in hints)
        assertTrue("tamil" in hints)
        assertTrue("punjabi" in hints)
        assertTrue("shreya ghoshal" in hints)
        assertEquals(hints.distinct().size, hints.size)
    }

    @Test
    fun albumBrowseIdHelperMatchesRemoteAndLocalAlbumsOnly() {
        assertTrue(DiscoveryFilter.isAlbumBrowseId("MPREb_12345"))
        assertTrue(DiscoveryFilter.isAlbumBrowseId("OLAK5uy_abc"))
        assertTrue(DiscoveryFilter.isAlbumBrowseId("VLOLAK5uy_abc"))
        assertTrue(DiscoveryFilter.isAlbumBrowseId("local:group:album:downloads:Single"))
        assertFalse(DiscoveryFilter.isAlbumBrowseId("VLPL_playlist_id"))
        assertFalse(DiscoveryFilter.isAlbumBrowseId("UC_artist_id"))
        assertFalse(DiscoveryFilter.isAlbumBrowseId(null))
    }
}

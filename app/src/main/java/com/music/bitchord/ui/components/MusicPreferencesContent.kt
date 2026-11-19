package com.music.bitchord.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.music.bitchord.R
import com.music.bitchord.data.IndianMusicPreferences
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Multi-select picker for Indian languages and favorite Indian singers.
 *
 * Used both on the dark mesh Onboarding flow (`darkSurface = true`) and inside
 * the Settings Liquid Glass sheet (`darkSurface = false`).
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MusicPreferencesSelector(
    selectedLanguages: Set<String>,
    selectedArtists: Set<String>,
    onToggleLanguage: (String) -> Unit,
    onToggleArtist: (String) -> Unit,
    onSearchArtists: suspend (String) -> List<String>,
    darkSurface: Boolean,
    modifier: Modifier = Modifier,
) {
    var artistQuery by rememberSaveable { mutableStateOf("") }
    var searching by remember { mutableStateOf(false) }
    var remoteResults by remember { mutableStateOf<List<String>>(emptyList()) }

    LaunchedEffect(artistQuery) {
        val trimmed = artistQuery.trim()
        if (trimmed.length < 2) {
            remoteResults = emptyList()
            searching = false
            return@LaunchedEffect
        }
        searching = true
        delay(320)
        remoteResults = runCatching { onSearchArtists(trimmed) }.getOrDefault(emptyList())
        searching = false
    }

    val headingColor = if (darkSurface) Color.White else MaterialTheme.colorScheme.onSurface
    val subtleColor = if (darkSurface) {
        Color.White.copy(alpha = 0.65f)
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    val prioritizedSuggestions = remember(selectedLanguages) {
        val suggested = IndianMusicPreferences.SUGGESTED_ARTISTS
        if (selectedLanguages.isEmpty()) {
            suggested.map { it.name }
        } else {
            val matched = suggested.filter { opt -> opt.languages.any { it in selectedLanguages } }
            val rest = suggested.filterNot { opt -> opt.languages.any { it in selectedLanguages } }
            (matched + rest).map { it.name }
        }
    }

    val visibleArtists = remember(artistQuery, remoteResults, prioritizedSuggestions, selectedArtists) {
        val needle = artistQuery.trim()
        val combined = LinkedHashSet<String>()
        if (needle.isEmpty()) {
            selectedArtists.forEach { combined.add(it) }
            prioritizedSuggestions.forEach { combined.add(it) }
        } else {
            remoteResults.forEach { combined.add(it) }
            prioritizedSuggestions
                .filter { it.contains(needle, ignoreCase = true) }
                .forEach { combined.add(it) }
            selectedArtists
                .filter { it.contains(needle, ignoreCase = true) }
                .forEach { combined.add(it) }
        }
        combined.toList()
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(R.string.preferences_languages_label),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.W600,
                color = headingColor,
            )
            if (selectedLanguages.isNotEmpty()) {
                Text(
                    text = stringResource(R.string.preferences_selected_count, selectedLanguages.size),
                    style = MaterialTheme.typography.labelMedium,
                    color = subtleColor,
                )
            }
        }
        Spacer(Modifier.height(10.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            IndianMusicPreferences.LANGUAGES.forEach { lang ->
                val isSelected = selectedLanguages.any { it.equals(lang.id, ignoreCase = true) }
                PreferencePill(
                    label = lang.label,
                    selected = isSelected,
                    darkSurface = darkSurface,
                    onClick = { onToggleLanguage(lang.id) },
                    modifier = Modifier.testTag("pref_lang_${lang.id}"),
                )
            }
        }

        Spacer(Modifier.height(22.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(R.string.preferences_artists_label),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.W600,
                color = headingColor,
            )
            if (selectedArtists.isNotEmpty()) {
                Text(
                    text = stringResource(R.string.preferences_selected_count, selectedArtists.size),
                    style = MaterialTheme.typography.labelMedium,
                    color = subtleColor,
                )
            }
        }
        Spacer(Modifier.height(10.dp))

        ArtistSearchField(
            query = artistQuery,
            onQueryChange = { artistQuery = it },
            darkSurface = darkSurface,
        )

        if (searching) {
            Spacer(Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(14.dp),
                    strokeWidth = 2.dp,
                    color = headingColor,
                )
                Text(
                    text = stringResource(R.string.preferences_artist_searching),
                    style = MaterialTheme.typography.bodySmall,
                    color = subtleColor,
                )
            }
        }

        Spacer(Modifier.height(12.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            visibleArtists.forEachIndexed { index, artist ->
                val isSelected = selectedArtists.any { it.equals(artist, ignoreCase = true) }
                PreferencePill(
                    label = artist,
                    selected = isSelected,
                    darkSurface = darkSurface,
                    onClick = { onToggleArtist(artist) },
                    modifier = Modifier.testTag("pref_artist_chip_$index"),
                )
            }
        }
    }
}

@Composable
private fun ArtistSearchField(
    query: String,
    onQueryChange: (String) -> Unit,
    darkSurface: Boolean,
) {
    val containerColor = if (darkSurface) {
        Color.White.copy(alpha = 0.14f)
    } else {
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.65f)
    }
    val textColor = if (darkSurface) Color.White else MaterialTheme.colorScheme.onSurface
    val hintColor = if (darkSurface) {
        Color.White.copy(alpha = 0.45f)
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp)
            .clip(CircleShape)
            .background(containerColor)
            .padding(start = 16.dp, end = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Rounded.Search,
            contentDescription = null,
            tint = hintColor,
            modifier = Modifier.size(18.dp),
        )
        Spacer(Modifier.width(10.dp))
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterStart,
        ) {
            if (query.isEmpty()) {
                Text(
                    text = stringResource(R.string.preferences_artist_search_hint),
                    style = MaterialTheme.typography.bodyMedium,
                    color = hintColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            BasicTextField(
                value = query,
                onValueChange = onQueryChange,
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = textColor,
                    fontSize = 15.sp,
                ),
                cursorBrush = SolidColor(if (darkSurface) Color.White else MaterialTheme.colorScheme.primary),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("pref_artist_search_input"),
            )
        }
        if (query.isNotEmpty()) {
            IconButton(
                onClick = { onQueryChange("") },
                modifier = Modifier.size(48.dp),
            ) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = stringResource(R.string.close_search),
                    tint = hintColor,
                    modifier = Modifier.size(18.dp),
                )
            }
        }
    }
}

@Composable
private fun PreferencePill(
    label: String,
    selected: Boolean,
    darkSurface: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val bgColor = when {
        darkSurface && selected -> Color.White
        darkSurface -> Color.White.copy(alpha = 0.14f)
        selected -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.65f)
    }
    val contentColor = when {
        darkSurface && selected -> Color.Black
        darkSurface -> Color.White
        selected -> MaterialTheme.colorScheme.onPrimary
        else -> MaterialTheme.colorScheme.onSurface
    }
    val borderColor = when {
        selected -> Color.Transparent
        darkSurface -> Color.White.copy(alpha = 0.2f)
        else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)
    }

    Row(
        modifier = modifier
            .heightIn(min = 48.dp)
            .clip(CircleShape)
            .background(bgColor)
            .border(width = 1.dp, color = borderColor, shape = CircleShape)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        if (selected) {
            Icon(
                imageVector = Icons.Rounded.Check,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(16.dp),
            )
        }
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = if (selected) FontWeight.W700 else FontWeight.W500,
                fontSize = 14.sp,
            ),
            color = contentColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicPreferencesSheet(
    initialLanguages: Set<String>,
    initialArtists: Set<String>,
    onSearchArtists: suspend (String) -> List<String>,
    onSave: (Set<String>, Set<String>) -> Unit,
    onDismiss: () -> Unit,
) {
    var selectedLanguages by remember(initialLanguages) { mutableStateOf(initialLanguages) }
    var selectedArtists by remember(initialArtists) { mutableStateOf(initialArtists) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    fun dismissSheet() {
        scope.launch { sheetState.hide() }.invokeOnCompletion { onDismiss() }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        contentWindowInsets = { WindowInsets(0, 0, 0, 0) },
    ) {
        BackHandler { dismissSheet() }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 20.dp),
        ) {
            Text(
                text = stringResource(R.string.music_preferences),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.W700,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.music_preferences_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = false)
                    .heightIn(max = 460.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                MusicPreferencesSelector(
                    selectedLanguages = selectedLanguages,
                    selectedArtists = selectedArtists,
                    onToggleLanguage = { id ->
                        selectedLanguages = if (id in selectedLanguages) {
                            selectedLanguages - id
                        } else {
                            selectedLanguages + id
                        }
                    },
                    onToggleArtist = { artist ->
                        val existing = selectedArtists.firstOrNull { it.equals(artist, ignoreCase = true) }
                        selectedArtists = if (existing != null) {
                            selectedArtists - existing
                        } else {
                            selectedArtists + artist
                        }
                    },
                    onSearchArtists = onSearchArtists,
                    darkSurface = false,
                )
                Spacer(Modifier.height(16.dp))
            }

            Spacer(Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                TextButton(
                    onClick = {
                        selectedLanguages = emptySet()
                        selectedArtists = emptySet()
                    },
                    enabled = selectedLanguages.isNotEmpty() || selectedArtists.isNotEmpty(),
                    modifier = Modifier
                        .heightIn(min = 48.dp)
                        .testTag("music_preferences_clear_button"),
                ) {
                    Text(stringResource(R.string.music_preferences_clear))
                }
                Spacer(Modifier.weight(1f))
                Button(
                    onClick = {
                        onSave(selectedLanguages, selectedArtists)
                        dismissSheet()
                    },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                    modifier = Modifier
                        .heightIn(min = 48.dp)
                        .testTag("music_preferences_save_button"),
                ) {
                    Text(
                        text = stringResource(R.string.music_preferences_save),
                        fontWeight = FontWeight.W600,
                    )
                }
            }
        }
    }
}

package com.music.bitchord.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.music.bitchord.R
import com.music.bitchord.data.settings.AppFont

// Apple Music's signature red. Kept for Replay rank badges and signature highlights.
val AccentRed = Color(0xFFFA2D48)

// ============================================================================
// iOS 26 Liquid Glass Accent & Surface Design Tokens
// ============================================================================
val ElectricIndigo = Color(0xFF5E5CE6)          // Signature iOS chromatic accent
val ElectricIndigoDark = Color(0xFF7D7AFF)      // Radiant indigo for dark OLED glass
val ElectricCyan = Color(0xFF0AC8FF)            // Refractive liquid glass cyan highlight
val ElectricCyanDark = Color(0xFF38D9FF)        // High-luminance cyan glow
val LiquidGlassSpecular = Color(0x33FFFFFF)     // 20% specular rim highlight
val LiquidGlassSpecularDark = Color(0x1AFFFFFF) // 10% dark specular highlight
val OledBlack = Color(0xFF000000)               // Pure OLED true black
val IceWhiteSurface = Color(0xFFF6F8FC)         // Crystalline ice-white glass base

private val DarkColors = darkColorScheme(
    primary = ElectricIndigoDark,
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF1E1B4B),
    onPrimaryContainer = Color(0xFFE0E0FF),
    secondary = ElectricIndigo,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF2E2C6A),
    onSecondaryContainer = Color(0xFFE0E0FF),
    tertiary = ElectricCyan,
    onTertiary = Color.Black,
    tertiaryContainer = Color(0xFF00384D),
    onTertiaryContainer = Color(0xFFB5EFFF),
    background = OledBlack,
    onBackground = Color(0xFFF5F5F7),
    surface = Color(0xFF0A0A0F),
    onSurface = Color(0xFFF5F5F7),
    surfaceVariant = Color(0xFF14141C),
    onSurfaceVariant = Color(0xFF9898A6),
    surfaceContainer = Color(0xFF181822),
    surfaceContainerHigh = Color(0xFF22222E),
    surfaceContainerHighest = Color(0xFF2C2C3A),
    surfaceContainerLow = Color(0xFF121218),
    surfaceContainerLowest = Color(0xFF06060A),
    surfaceBright = Color(0xFF383848),
    surfaceDim = Color(0xFF0A0A0F),
    outline = Color(0xFF282838),
    outlineVariant = Color(0xFF1E1E28),
    scrim = Color(0xCC000000),
    inverseSurface = IceWhiteSurface,
    inverseOnSurface = Color(0xFF0A0A0F),
    inversePrimary = ElectricIndigo,
)

private val LightColors = lightColorScheme(
    primary = ElectricIndigo,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE5E5FE),
    onPrimaryContainer = Color(0xFF1A1860),
    secondary = Color(0xFF4F46E5),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFEEF0FF),
    onSecondaryContainer = Color(0xFF1E1B4B),
    tertiary = Color(0xFF0096C7),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFD0F4FF),
    onTertiaryContainer = Color(0xFF003544),
    background = Color(0xFFFFFFFF),
    onBackground = Color(0xFF0A0A0E),
    surface = IceWhiteSurface,
    onSurface = Color(0xFF0A0A0E),
    surfaceVariant = Color(0xFFECEFF5),
    onSurfaceVariant = Color(0xFF606470),
    surfaceContainer = Color(0xFFF1F4F9),
    surfaceContainerHigh = Color(0xFFE8ECF3),
    surfaceContainerHighest = Color(0xFFDFE4ED),
    surfaceContainerLow = Color(0xFFF8FAFD),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceBright = Color(0xFFFFFFFF),
    surfaceDim = Color(0xFFE5E9F0),
    outline = Color(0xFFD0D6E2),
    outlineVariant = Color(0xFFE2E6EF),
    scrim = Color(0x66000000),
    inverseSurface = Color(0xFF14141C),
    inverseOnSurface = Color(0xFFF5F5F7),
    inversePrimary = ElectricIndigoDark,
)

/**
 * SF Pro Display, the face Apple Music itself is set in, drawn one weight
 * lighter than it is asked for.
 *
 * Measured against Apple Music's own screens, every weight here came out a
 * cut heavier than the same text there: its titles are Semibold where these
 * asked for Bold, its metadata Medium where these asked for Semibold. The
 * shift is made once, in the family, so it reaches the type scale and every
 * weight set in place alike and keeps the steps between them: Heavy draws
 * Bold, Bold draws Semibold, Semibold draws Medium, Medium draws Regular.
 * Regular stays Regular — no lighter cut is bundled, and Apple's body text is
 * Regular too. A FontWeight in this app therefore names the step it sits at in
 * the scale, not the cut it draws.
 *
 * Compose synthesises nothing, so a weight outside these falls back to the
 * nearest one listed.
 */
val SFProDisplay = FontFamily(
    Font(R.font.sf_pro_display_regular, FontWeight.W400),
    Font(R.font.sf_pro_display_regular, FontWeight.W500),
    Font(R.font.sf_pro_display_medium, FontWeight.W600),
    Font(R.font.sf_pro_display_semibold, FontWeight.W700),
    Font(R.font.sf_pro_display_bold, FontWeight.W800),
)

/*
 * Letter spacing measured off Apple Music's own screens: the spacing the
 * bundled Display cut needs, at the weight it draws, to set a line to the
 * width Apple sets it. Apple sets text under 20pt in SF Pro Text, which is
 * spaced looser than Display, so the small sizes open up — about +0.25 to
 * +0.4 at 12 to 14 — and the spacing closes as the size grows, to just under
 * nothing for semibold titles at 18 to 24. The large titles had nothing to
 * measure against and follow the same trend.
 */
private val BitChordTypography = Typography(
    displayLarge = TextStyle(fontWeight = FontWeight.W800, fontSize = 34.sp, letterSpacing = (-0.5).sp),
    headlineLarge = TextStyle(fontWeight = FontWeight.W800, fontSize = 30.sp, letterSpacing = (-0.4).sp),
    headlineMedium = TextStyle(fontWeight = FontWeight.W700, fontSize = 22.sp, letterSpacing = (-0.25).sp),
    titleLarge = TextStyle(fontWeight = FontWeight.W700, fontSize = 20.sp, letterSpacing = (-0.2).sp),
    titleMedium = TextStyle(fontWeight = FontWeight.W600, fontSize = 16.sp, letterSpacing = 0.05.sp),
    bodyLarge = TextStyle(fontWeight = FontWeight.W400, fontSize = 16.sp, letterSpacing = 0.2.sp),
    bodyMedium = TextStyle(fontWeight = FontWeight.W400, fontSize = 14.sp, letterSpacing = 0.3.sp),
    labelMedium = TextStyle(fontWeight = FontWeight.W600, fontSize = 12.sp, letterSpacing = 0.25.sp),
    labelSmall = TextStyle(fontWeight = FontWeight.W600, fontSize = 11.sp, letterSpacing = 0.3.sp),
).withFamily(SFProDisplay)

/**
 * SF Pro Display as the app first shipped it, before [SFProDisplay] moved every
 * weight a cut lighter: each weight draws its own cut, Heavy included. Kept for
 * [AppFont.LEGACY].
 */
val SFProDisplayLegacy = FontFamily(
    Font(R.font.sf_pro_display_regular, FontWeight.W400),
    Font(R.font.sf_pro_display_medium, FontWeight.W500),
    Font(R.font.sf_pro_display_semibold, FontWeight.W600),
    Font(R.font.sf_pro_display_bold, FontWeight.W700),
    Font(R.font.sf_pro_display_heavy, FontWeight.W800),
)

// Heavy, tight typography — the original scale [AppFont.LEGACY] restores.
private val LegacyTypography = Typography(
    displayLarge = TextStyle(fontWeight = FontWeight.W800, fontSize = 34.sp, letterSpacing = (-0.8).sp),
    headlineLarge = TextStyle(fontWeight = FontWeight.W800, fontSize = 30.sp, letterSpacing = (-0.7).sp),
    headlineMedium = TextStyle(fontWeight = FontWeight.W700, fontSize = 22.sp, letterSpacing = (-0.4).sp),
    titleLarge = TextStyle(fontWeight = FontWeight.W700, fontSize = 20.sp, letterSpacing = (-0.3).sp),
    titleMedium = TextStyle(fontWeight = FontWeight.W600, fontSize = 16.sp, letterSpacing = (-0.2).sp),
    bodyLarge = TextStyle(fontWeight = FontWeight.W400, fontSize = 16.sp),
    bodyMedium = TextStyle(fontWeight = FontWeight.W400, fontSize = 14.sp),
    labelMedium = TextStyle(fontWeight = FontWeight.W600, fontSize = 12.sp),
    labelSmall = TextStyle(fontWeight = FontWeight.W600, fontSize = 11.sp),
).withFamily(SFProDisplayLegacy)

/** Applies [family] to every style in the scale, so nothing is left on Roboto. */
private fun Typography.withFamily(family: FontFamily) = Typography(
    displayLarge = displayLarge.copy(fontFamily = family),
    displayMedium = displayMedium.copy(fontFamily = family),
    displaySmall = displaySmall.copy(fontFamily = family),
    headlineLarge = headlineLarge.copy(fontFamily = family),
    headlineMedium = headlineMedium.copy(fontFamily = family),
    headlineSmall = headlineSmall.copy(fontFamily = family),
    titleLarge = titleLarge.copy(fontFamily = family),
    titleMedium = titleMedium.copy(fontFamily = family),
    titleSmall = titleSmall.copy(fontFamily = family),
    bodyLarge = bodyLarge.copy(fontFamily = family),
    bodyMedium = bodyMedium.copy(fontFamily = family),
    bodySmall = bodySmall.copy(fontFamily = family),
    labelLarge = labelLarge.copy(fontFamily = family),
    labelMedium = labelMedium.copy(fontFamily = family),
    labelSmall = labelSmall.copy(fontFamily = family),
)

@Composable
fun BitChordTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    font: AppFont = AppFont.DEFAULT,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = when (font) {
            AppFont.DEFAULT -> BitChordTypography
            AppFont.LEGACY -> LegacyTypography
        },
        content = content,
    )
}

/**
 * LH Music primary theme entry point, delivering the iOS 26-inspired Liquid Glass design system.
 */
@Composable
fun LHMusicTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    font: AppFont = AppFont.DEFAULT,
    content: @Composable () -> Unit,
) = BitChordTheme(darkTheme = darkTheme, font = font, content = content)


/**
 * Draws the status and navigation bar glyphs dark or light.
 *
 * `enableEdgeToEdge()` decides this from the *system* dark-mode setting, which
 * is the wrong input the moment the in-app theme disagrees with it: Light theme
 * on a phone in dark mode left white icons on a white bar, invisible. The bars
 * have to follow the content the app is actually painting. The player supplies
 * its own stable light-icon value and paints contrast behind it; every other
 * surface follows the theme. Hence a parameter rather than reading it here.
 */
@Composable
fun SystemBarIcons(dark: Boolean) {
    val view = LocalView.current
    if (view.isInEditMode) return
    val window = findWindow(view) ?: return
    SideEffect {
        WindowCompat.getInsetsController(window, view).apply {
            isAppearanceLightStatusBars = dark
            isAppearanceLightNavigationBars = dark
        }
    }
}


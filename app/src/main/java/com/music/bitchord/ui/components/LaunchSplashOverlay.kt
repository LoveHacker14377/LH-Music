package com.music.bitchord.ui.components

import android.widget.ImageView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.music.bitchord.AppIcon
import com.music.bitchord.R
import com.music.bitchord.ui.theme.OledBlack
import kotlinx.coroutines.launch

/**
 * Branded cold-launch splash overlay matching the pure-black window background
 * in `themes.xml`. Displays `"Love Hacker"` cleanly above the active LH Music
 * launcher icon before fading out into the app UI.
 */
@Composable
fun LaunchSplashOverlay(
    visible: Boolean,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val currentIcon = remember(context) { AppIcon.current(context) }

    val textAlpha = remember { Animatable(0f) }
    val textOffsetY = remember { Animatable(12f) }
    val iconScale = remember { Animatable(0.92f) }
    val iconAlpha = remember { Animatable(0f) }

    LaunchedEffect(visible) {
        if (visible) {
            launch {
                textAlpha.animateTo(1f, tween(durationMillis = 320, easing = FastOutSlowInEasing))
            }
            launch {
                textOffsetY.animateTo(0f, tween(durationMillis = 360, easing = FastOutSlowInEasing))
            }
            launch {
                iconAlpha.animateTo(1f, tween(durationMillis = 280, easing = FastOutSlowInEasing))
            }
            launch {
                iconScale.animateTo(1f, tween(durationMillis = 380, easing = FastOutSlowInEasing))
            }
        }
    }

    AnimatedVisibility(
        visible = visible,
        enter = EnterTransition.None,
        exit = fadeOut(animationSpec = tween(durationMillis = 260)),
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .testTag("launch_splash_overlay")
                .background(OledBlack)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {},
                )
                .systemBarsPadding()
                .padding(horizontal = 32.dp),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = stringResource(R.string.splash_developer_branding),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.W600,
                        letterSpacing = 0.6.sp,
                    ),
                    color = Color(0xFFF5F5F7),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .testTag("splash_developer_branding_text")
                        .graphicsLayer {
                            alpha = textAlpha.value
                            translationY = textOffsetY.value
                        },
                )

                Spacer(Modifier.height(28.dp))

                val preview = currentIcon.previewRes
                Box(
                    modifier = Modifier
                        .size(96.dp)
                        .graphicsLayer {
                            alpha = iconAlpha.value
                            scaleX = iconScale.value
                            scaleY = iconScale.value
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    if (preview == null) {
                        AndroidView(
                            factory = { ctx ->
                                ImageView(ctx).apply {
                                    setImageResource(currentIcon.iconRes)
                                }
                            },
                            modifier = Modifier
                                .size(96.dp)
                                .clip(RoundedCornerShape(24.dp)),
                        )
                    } else {
                        Image(
                            painter = painterResource(preview),
                            contentDescription = stringResource(R.string.app_name),
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(96.dp)
                                .scale(1.5f),
                        )
                    }
                }
            }
        }
    }
}

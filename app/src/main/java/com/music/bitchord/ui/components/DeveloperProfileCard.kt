package com.music.bitchord.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.OpenInNew
import androidx.compose.material.icons.rounded.Code
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.music.bitchord.R
import com.music.bitchord.ui.theme.ElectricCyan
import com.music.bitchord.ui.theme.ElectricIndigo
import com.music.bitchord.ui.theme.ElectricIndigoDark

private const val DEVELOPER_INSTAGRAM_URL = "https://www.instagram.com/_love__hacker__143_/"

private val DeveloperCardShape = RoundedCornerShape(18.dp)
private val InstagramPillShape = RoundedCornerShape(12.dp)
private val RoleBadgeShape = RoundedCornerShape(50)

/**
 * Dedicated Developer Profile card styled to match the iOS 26 Liquid Glass
 * aesthetic. Represents the app developer exclusively — not a signed-in user.
 */
@Composable
fun DeveloperProfileCard(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val isLight = MaterialTheme.colorScheme.surface.luminance() > 0.5f
    val glassEnabled = LocalLiquidGlassEnabled.current && isGlassSupported()

    val accentStart = if (isLight) ElectricIndigo else ElectricIndigoDark
    val accentEnd = ElectricCyan
    val sheenBrush = Brush.linearGradient(
        colors = listOf(
            accentStart.copy(alpha = if (isLight) 0.08f else 0.14f),
            Color.Transparent,
            accentEnd.copy(alpha = if (isLight) 0.06f else 0.10f),
        ),
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag("developer_profile_card")
            .lightweightLiquidGlass(
                shape = DeveloperCardShape,
                fallbackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
            .background(sheenBrush)
            .padding(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .border(
                        width = 1.5.dp,
                        brush = Brush.linearGradient(listOf(accentStart, accentEnd)),
                        shape = CircleShape,
                    )
                    .padding(3.dp),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painter = painterResource(R.drawable.developer_love_hacker),
                    contentDescription = stringResource(R.string.developer_photo_cd),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(66.dp)
                        .clip(CircleShape),
                )
            }

            Spacer(Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(3.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        text = stringResource(R.string.developer_display_name),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false),
                    )
                }

                Text(
                    text = stringResource(R.string.developer_real_name),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Spacer(Modifier.height(2.dp))

                Row(
                    modifier = Modifier
                        .clip(RoleBadgeShape)
                        .background(
                            accentStart.copy(alpha = if (isLight) 0.12f else 0.18f),
                            shape = RoleBadgeShape,
                        )
                        .border(
                            width = 0.5.dp,
                            color = accentStart.copy(alpha = 0.35f),
                            shape = RoleBadgeShape,
                        )
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Code,
                        contentDescription = null,
                        tint = accentStart,
                        modifier = Modifier.size(13.dp),
                    )
                    Text(
                        text = stringResource(R.string.developer_role_badge),
                        style = MaterialTheme.typography.labelMedium,
                        color = accentStart,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }

        Spacer(Modifier.height(14.dp))

        val pillFill = if (glassEnabled) {
            if (isLight) Color.White.copy(alpha = 0.55f) else Color.White.copy(alpha = 0.08f)
        } else {
            MaterialTheme.colorScheme.surfaceContainerHigh
        }
        val pillBorder = if (isLight) {
            MaterialTheme.colorScheme.outline.copy(alpha = 0.65f)
        } else {
            Color.White.copy(alpha = 0.14f)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp)
                .testTag("follow_instagram_link")
                .clip(InstagramPillShape)
                .background(pillFill, shape = InstagramPillShape)
                .border(0.75.dp, pillBorder, InstagramPillShape)
                .clickable(role = Role.Button) {
                    runCatching {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(DEVELOPER_INSTAGRAM_URL))
                        context.startActivity(intent)
                    }
                }
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(R.string.follow_on_instagram),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f),
            )
            Spacer(Modifier.width(8.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.OpenInNew,
                contentDescription = stringResource(R.string.follow_on_instagram),
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(18.dp),
            )
        }
    }
}

package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.AuthProvider
import com.example.ui.theme.*

@Composable
fun SocialLoginDialog(
    currentProvider: AuthProvider,
    onDismiss: () -> Unit,
    onLogin: (provider: AuthProvider, username: String, email: String) -> Unit
) {
    var customUsername by remember { mutableStateOf("Player_Titan") }
    var customEmail by remember { mutableStateOf("gamer.pro@example.com") }

    val socialProviders = listOf(
        AuthProvider.GOOGLE to ("Continue with Google" to Color(0xFFEA4335)),
        AuthProvider.APPLE to ("Continue with Apple ID" to Color(0xFFF8FAFC)),
        AuthProvider.DISCORD to ("Continue with Discord" to Color(0xFF5865F2)),
        AuthProvider.TWITTER_X to ("Continue with X / Twitter" to Color(0xFF1DA1F2)),
        AuthProvider.PHONE_OTP to ("Continue with Phone SMS" to VijayaEmerald),
        AuthProvider.GUEST to ("Instant Trial Guest" to TextSecondary)
    )

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = ObsidianSurface,
            border = ButtonDefaults.outlinedButtonBorder.copy(
                width = 1.dp,
                brush = androidx.compose.ui.graphics.SolidColor(ObsidianCardBorder)
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(VijayaCyan.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.LockPerson,
                                contentDescription = null,
                                tint = VijayaCyan,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Fast Social Onboarding",
                                color = TextPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp
                            )
                            Text(
                                text = "Player Identity & Instant Security",
                                color = TextSecondary,
                                fontSize = 12.sp
                            )
                        }
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = TextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Welcome Bonus Banner
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = VijayaGold.copy(alpha = 0.15f),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = Brush.horizontalGradient(listOf(VijayaGold, VijayaEmerald))
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "🎁",
                            fontSize = 22.sp
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "$5.00 Free Cash Welcome Bonus",
                                color = VijayaGoldLight,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                            Text(
                                text = "Instant credited to bonus balance for all new players!",
                                color = TextPrimary,
                                fontSize = 11.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "SELECT SOCIAL LOGIN",
                    color = TextSecondary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                socialProviders.forEach { (provider, meta) ->
                    val (label, accentColor) = meta
                    val isCurrent = provider == currentProvider

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isCurrent) VijayaCyan.copy(alpha = 0.15f) else ObsidianSurfaceVariant)
                            .border(
                                width = 1.dp,
                                color = if (isCurrent) VijayaCyan else ObsidianCardBorder,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable {
                                val generatedUser = when (provider) {
                                    AuthProvider.GOOGLE -> "GooglePlayer_${(100..999).random()}"
                                    AuthProvider.APPLE -> "AppleGamer_${(100..999).random()}"
                                    AuthProvider.DISCORD -> "DiscordPro_${(100..999).random()}"
                                    AuthProvider.TWITTER_X -> "X_Champion_${(100..999).random()}"
                                    AuthProvider.PHONE_OTP -> "SMS_User_${(1000..9999).random()}"
                                    AuthProvider.GUEST -> "Guest_Trial_${(100..999).random()}"
                                }
                                onLogin(provider, generatedUser, "${generatedUser.lowercase()}@verified.play")
                            }
                            .padding(horizontal = 14.dp, vertical = 10.dp)
                            .testTag("social_login_btn_${provider.name}"),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(RoundedCornerShape(5.dp))
                                    .background(accentColor)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = label,
                                color = TextPrimary,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        if (isCurrent) {
                            Text(
                                text = "Active",
                                color = VijayaCyan,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.ArrowForwardIos,
                                contentDescription = null,
                                tint = TextMuted,
                                modifier = Modifier.size(12.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "🔒 By logging in, identity is cryptographically bound to anti-cheat telemetry for fair play.",
                    color = TextMuted,
                    fontSize = 10.sp,
                    lineHeight = 14.sp
                )
            }
        }
    }
}

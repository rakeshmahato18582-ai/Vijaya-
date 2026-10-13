package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.GameId
import com.example.data.model.UserAccount
import com.example.ui.components.WalletBalanceCard
import com.example.ui.theme.*

@Composable
fun HomeScreen(
    userAccount: UserAccount,
    onAddMoneyClick: () -> Unit,
    onWithdrawClick: () -> Unit,
    onPlayGame: (GameId, Double) -> Unit,
    onViewAllGames: () -> Unit,
    onViewVipTiers: () -> Unit,
    onViewAntiCheat: () -> Unit,
    onReferralClick: () -> Unit
) {
    val liveCashouts = listOf(
        Triple("ShadowNova", "$18.50 via PayPal", "1m ago"),
        Triple("CryptoKing99", "$42.00 via USDT TRC-20", "3m ago"),
        Triple("Aarav_Gamer", "₹1,850 via Instant UPI", "5m ago"),
        Triple("Elena_V", "€25.00 via Revolut", "8m ago")
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(ObsidianBackground)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Onboarding & Welcome Bonus Card
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        Brush.horizontalGradient(listOf(VijayaGold, VijayaEmerald)),
                        RoundedCornerShape(20.dp)
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "🔥", fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "NEW ACCOUNT BONUS",
                                color = VijayaGold,
                                fontWeight = FontWeight.Black,
                                fontSize = 11.sp,
                                letterSpacing = 1.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "$5.00 Free Cash Bonus Credited",
                            color = TextPrimary,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Play practice or cash matches. Minimum deposit just $1.00!",
                            color = TextSecondary,
                            fontSize = 12.sp
                        )
                    }

                    Button(
                        onClick = onReferralClick,
                        colors = ButtonDefaults.buttonColors(containerColor = VijayaGold, contentColor = Color.Black),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("hero_invite_friends_button")
                    ) {
                        Text("Invite +$5", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }
        }

        // Wallet Balance Card
        item {
            WalletBalanceCard(
                userAccount = userAccount,
                onAddMoneyClick = onAddMoneyClick,
                onWithdrawClick = onWithdrawClick
            )
        }

        // Fair-Play Anti-Cheat Telemetry Banner
        item {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = ObsidianSurfaceVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { onViewAntiCheat() }
                    .border(1.dp, VijayaCyan.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
                    .testTag("home_anti_cheat_banner")
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(VijayaCyan.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Shield,
                                contentDescription = null,
                                tint = VijayaCyan,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Anti-Cheat Fair Play Shield",
                                    color = TextPrimary,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(VijayaEmerald.copy(alpha = 0.2f))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "100% FAIR",
                                        color = VijayaEmerald,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            Text(
                                text = "Touch latency jitter: ${userAccount.antiCheat.touchCadenceJitterMs}ms • Trust: ${userAccount.antiCheat.trustScore}/100",
                                color = TextMuted,
                                fontSize = 11.sp
                            )
                        }
                    }

                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "Details",
                        tint = TextSecondary
                    )
                }
            }
        }

        // Featured Games Quick Play Section
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "FEATURED CASH MODES",
                    color = TextSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )

                TextButton(onClick = onViewAllGames) {
                    Text("View All", color = VijayaGold, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }

        // Featured Game 1: Aviator Crash
        item {
            GameCardItem(
                title = GameId.AVIATOR.title,
                subtitle = GameId.AVIATOR.subtitle,
                description = GameId.AVIATOR.description,
                accentColor = VijayaGold,
                userAccount = userAccount,
                entryFeeUsd = 1.00,
                prizeUsd = 3.50,
                onPracticeClick = { onPlayGame(GameId.AVIATOR, 0.0) },
                onCashDuelClick = { onPlayGame(GameId.AVIATOR, 1.00) }
            )
        }

        // Featured Game 2: Ludo Master Duel
        item {
            GameCardItem(
                title = GameId.LUDO.title,
                subtitle = GameId.LUDO.subtitle,
                description = GameId.LUDO.description,
                accentColor = Color(0xFFEC4899),
                userAccount = userAccount,
                entryFeeUsd = 2.00,
                prizeUsd = 3.60,
                onPracticeClick = { onPlayGame(GameId.LUDO, 0.0) },
                onCashDuelClick = { onPlayGame(GameId.LUDO, 2.00) }
            )
        }

        // Featured Game 3: Cyber Fishing Ocean
        item {
            GameCardItem(
                title = GameId.FISHING.title,
                subtitle = GameId.FISHING.subtitle,
                description = GameId.FISHING.description,
                accentColor = VijayaCyan,
                userAccount = userAccount,
                entryFeeUsd = 1.00,
                prizeUsd = 2.50,
                onPracticeClick = { onPlayGame(GameId.FISHING, 0.0) },
                onCashDuelClick = { onPlayGame(GameId.FISHING, 1.00) }
            )
        }

        // Featured Game 4: Speed Math Rush
        item {
            GameCardItem(
                title = GameId.SPEED_MATH.title,
                subtitle = GameId.SPEED_MATH.subtitle,
                description = GameId.SPEED_MATH.description,
                accentColor = VijayaEmerald,
                userAccount = userAccount,
                entryFeeUsd = 1.00,
                prizeUsd = 1.80,
                onPracticeClick = { onPlayGame(GameId.SPEED_MATH, 0.0) },
                onCashDuelClick = { onPlayGame(GameId.SPEED_MATH, 1.00) }
            )
        }

        // Live Real-Time Cashout Ticker (Transparency requirement)
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(VijayaEmerald)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "LIVE CASHOUT TICKER",
                                color = VijayaEmeraldLight,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                letterSpacing = 1.sp
                            )
                        }

                        Text(
                            text = "Instant Redemptions",
                            color = TextMuted,
                            fontSize = 11.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    liveCashouts.forEach { (player, amountMethod, time) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "⚡", fontSize = 12.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = player,
                                    color = TextPrimary,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 13.sp
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = amountMethod,
                                    color = VijayaEmerald,
                                    fontSize = 12.sp
                                )
                            }
                            Text(
                                text = time,
                                color = TextMuted,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GameCardItem(
    title: String,
    subtitle: String,
    description: String,
    accentColor: Color,
    userAccount: UserAccount,
    entryFeeUsd: Double,
    prizeUsd: Double,
    onPracticeClick: () -> Unit,
    onCashDuelClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, ObsidianCardBorder, RoundedCornerShape(18.dp))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = title,
                        color = TextPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = subtitle,
                        color = accentColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                // Prize badge
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = accentColor.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = "Win ${userAccount.currency.format(prizeUsd)}",
                        color = accentColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = description,
                color = TextSecondary,
                fontSize = 12.sp,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = onPracticeClick,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = TextPrimary),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = androidx.compose.ui.graphics.SolidColor(ObsidianCardBorder)
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Practice ($0)", fontSize = 12.sp)
                }

                Button(
                    onClick = onCashDuelClick,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = accentColor,
                        contentColor = Color.Black
                    ),
                    modifier = Modifier.weight(1.2f)
                ) {
                    Text(
                        text = "Cash Duel (${userAccount.currency.format(entryFeeUsd)})",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

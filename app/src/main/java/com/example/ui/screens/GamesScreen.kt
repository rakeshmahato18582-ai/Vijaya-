package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.GameId
import com.example.data.model.UserAccount
import com.example.ui.theme.*

@Composable
fun GamesScreen(
    userAccount: UserAccount,
    onStartGame: (GameId, Double) -> Unit
) {
    var selectedFilter by remember { mutableStateOf("ALL") }

    val stakeOptions = listOf(
        0.0 to "Practice ($0)",
        1.00 to "Entry $1.00",
        2.00 to "Entry $2.00",
        5.00 to "Entry $5.00"
    )

    val allGames = listOf(
        Triple(GameId.AVIATOR, VijayaGold, Icons.Default.FlightTakeoff),
        Triple(GameId.LUDO, Color(0xFFEC4899), Icons.Default.Casino),
        Triple(GameId.FISHING, VijayaCyan, Icons.Default.Water),
        Triple(GameId.SPEED_MATH, VijayaEmerald, Icons.Default.Calculate),
        Triple(GameId.CYBER_REFLEX, VijayaBlue, Icons.Default.TouchApp),
        Triple(GameId.COLOR_BLITZ, VijayaPurple, Icons.Default.Palette)
    )

    val filteredGames = when (selectedFilter) {
        "CRASH" -> allGames.filter { it.first == GameId.AVIATOR }
        "BOARD" -> allGames.filter { it.first == GameId.LUDO }
        "ARCADE" -> allGames.filter { it.first == GameId.FISHING }
        "SKILL" -> allGames.filter { it.first == GameId.SPEED_MATH || it.first == GameId.CYBER_REFLEX || it.first == GameId.COLOR_BLITZ }
        else -> allGames
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(ObsidianBackground)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column {
                Text(
                    text = "Multi-Game Arena",
                    color = TextPrimary,
                    fontWeight = FontWeight.Black,
                    fontSize = 22.sp
                )
                Text(
                    text = "Ludo, Aviator, Cyber Fishing & Skill modes with $1 low deposit.",
                    color = TextSecondary,
                    fontSize = 13.sp
                )
            }
        }

        // Category Filter Chips
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                listOf("ALL", "CRASH", "BOARD", "ARCADE", "SKILL").forEach { filter ->
                    val isSelected = selectedFilter == filter
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = if (isSelected) VijayaGold else ObsidianSurfaceVariant,
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .clickable { selectedFilter = filter }
                    ) {
                        Text(
                            text = filter,
                            color = if (isSelected) Color.Black else TextSecondary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }

        // Games list with stake selection
        items(filteredGames) { (gameId, color, iconVector) ->
            var selectedStake by remember { mutableStateOf(1.00) }

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, ObsidianCardBorder, RoundedCornerShape(20.dp))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(color.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = iconVector,
                                    contentDescription = null,
                                    tint = color,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = gameId.title,
                                        color = TextPrimary,
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(color.copy(alpha = 0.15f))
                                            .padding(horizontal = 5.dp, vertical = 1.dp)
                                    ) {
                                        Text(
                                            text = gameId.category,
                                            color = color,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                                Text(
                                    text = gameId.subtitle,
                                    color = color,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = ObsidianSurfaceVariant
                        ) {
                            Text(
                                text = "⏱️ ${gameId.estimatedDuration}",
                                color = TextMuted,
                                fontSize = 11.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = gameId.description,
                        color = TextSecondary,
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "SELECT MATCH STAKE",
                        color = TextMuted,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // Stakes chips
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        stakeOptions.forEach { (stake, _) ->
                            val isSelected = selectedStake == stake
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (isSelected) color else ObsidianSurfaceVariant,
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable { selectedStake = stake }
                                    .padding(vertical = 8.dp)
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = if (stake == 0.0) "Free" else "$${stake.toInt()}",
                                        color = if (isSelected) Color.Black else TextPrimary,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp
                                    )
                                    Text(
                                        text = if (stake == 0.0) "Practice" else "Win ${userAccount.currency.format(stake * 1.8)}",
                                        color = if (isSelected) Color.Black.copy(alpha = 0.7f) else TextMuted,
                                        fontSize = 9.sp
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { onStartGame(gameId, selectedStake) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = color,
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("play_game_btn_${gameId.name}")
                    ) {
                        Text(
                            text = if (selectedStake == 0.0) "Play Free Practice" else "Enter Tournament (${userAccount.currency.format(selectedStake)})",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

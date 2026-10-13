package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.GameId
import com.example.data.model.MatchResult
import com.example.data.model.UserAccount
import com.example.ui.theme.*
import com.example.ui.viewmodel.ActiveGameState

@Composable
fun GameplayScreen(
    userAccount: UserAccount,
    gameState: ActiveGameState,
    onMathOptionSelected: (Int) -> Unit,
    onReflexTargetTapped: (Int) -> Unit,
    onColorOptionSelected: (Int) -> Unit,
    onAviatorCashout: () -> Unit,
    onLudoRollDice: () -> Unit,
    onFishingTargetTapped: (Int) -> Unit,
    onPlayAgain: () -> Unit,
    onExitGame: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ObsidianBackground)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onExitGame,
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(ObsidianSurfaceVariant)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Exit Game",
                        tint = TextSecondary
                    )
                }

                Text(
                    text = gameState.gameId.title.uppercase(),
                    color = TextPrimary,
                    fontWeight = FontWeight.Black,
                    fontSize = 15.sp,
                    letterSpacing = 1.sp
                )

                // Anti-Cheat live status
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(ObsidianSurfaceVariant)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = null,
                        tint = VijayaCyan,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "VERIFIED",
                        color = VijayaCyan,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Score & Duel HUD
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "YOU (${userAccount.username.take(8)})",
                            color = VijayaGold,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                        Text(
                            text = "${gameState.playerScore} Pts",
                            color = TextPrimary,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black
                        )
                    }

                    // Stake Tag
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = ObsidianSurfaceVariant
                    ) {
                        Text(
                            text = if (gameState.entryFeeUsd > 0) "Stake: ${userAccount.currency.format(gameState.entryFeeUsd)}" else "Practice Match",
                            color = VijayaEmeraldLight,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = gameState.opponentName,
                            color = TextSecondary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                        Text(
                            text = "${gameState.opponentScore} Pts",
                            color = TextSecondary,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Arena By Game Type
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                when (gameState.gameId) {
                    GameId.AVIATOR -> {
                        AviatorArena(
                            gameState = gameState,
                            userAccount = userAccount,
                            onCashout = onAviatorCashout
                        )
                    }
                    GameId.LUDO -> {
                        LudoArena(
                            gameState = gameState,
                            onRollDice = onLudoRollDice
                        )
                    }
                    GameId.FISHING -> {
                        FishingArena(
                            gameState = gameState,
                            onTargetTapped = onFishingTargetTapped
                        )
                    }
                    GameId.SPEED_MATH -> {
                        val question = gameState.currentMathQuestion
                        if (question != null) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "SOLVE RAPIDLY (${gameState.timeRemainingSeconds}s)",
                                    color = VijayaGold,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.sp
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = question.prompt,
                                    color = TextPrimary,
                                    fontSize = 38.sp,
                                    fontWeight = FontWeight.Black
                                )
                                Spacer(modifier = Modifier.height(24.dp))
                                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                    question.options.chunked(2).forEach { rowOpts ->
                                        Row(
                                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            rowOpts.forEach { opt ->
                                                Button(
                                                    onClick = { onMathOptionSelected(opt) },
                                                    shape = RoundedCornerShape(14.dp),
                                                    colors = ButtonDefaults.buttonColors(
                                                        containerColor = ObsidianSurfaceVariant,
                                                        contentColor = TextPrimary
                                                    ),
                                                    border = BorderStroke(1.dp, VijayaGold.copy(alpha = 0.3f)),
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .height(64.dp)
                                                        .testTag("math_btn_$opt")
                                                ) {
                                                    Text(text = "$opt", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    GameId.CYBER_REFLEX -> {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "TAP GLOWING TARGET QUICKLY (${gameState.timeRemainingSeconds}s)",
                                color = VijayaCyan,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(3),
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                items(9) { idx ->
                                    val isTarget = idx == gameState.activeReflexTargetIndex
                                    Box(
                                        modifier = Modifier
                                            .aspectRatio(1f)
                                            .clip(RoundedCornerShape(16.dp))
                                            .background(if (isTarget) VijayaCyan else ObsidianSurfaceVariant)
                                            .border(
                                                width = if (isTarget) 2.dp else 1.dp,
                                                color = if (isTarget) Color.White else ObsidianCardBorder,
                                                shape = RoundedCornerShape(16.dp)
                                            )
                                            .clickable { onReflexTargetTapped(idx) }
                                            .testTag("reflex_btn_$idx"),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (isTarget) {
                                            Icon(
                                                imageVector = Icons.Default.FlashOn,
                                                contentDescription = null,
                                                tint = Color.Black,
                                                modifier = Modifier.size(36.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                    GameId.COLOR_BLITZ -> {
                        val colorQuestion = gameState.currentColorQuestion
                        if (colorQuestion != null) {
                            val colorMap = mapOf(
                                "RED" to VijayaCrimson,
                                "BLUE" to VijayaBlue,
                                "GREEN" to VijayaEmerald,
                                "YELLOW" to VijayaGold
                            )
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = colorQuestion.promptRule.uppercase(),
                                    color = VijayaPurple,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Black
                                )
                                Spacer(modifier = Modifier.height(14.dp))
                                Text(
                                    text = colorQuestion.wordText,
                                    color = colorMap[colorQuestion.wordColorName] ?: TextPrimary,
                                    fontSize = 42.sp,
                                    fontWeight = FontWeight.Black
                                )
                                Spacer(modifier = Modifier.height(24.dp))
                                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                    colorQuestion.options.chunked(2).forEach { rowOpts ->
                                        Row(
                                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            rowOpts.forEach { opt ->
                                                val optIndex = colorQuestion.options.indexOf(opt)
                                                val pillColor = colorMap[opt] ?: TextPrimary
                                                Button(
                                                    onClick = { onColorOptionSelected(optIndex) },
                                                    shape = RoundedCornerShape(14.dp),
                                                    colors = ButtonDefaults.buttonColors(
                                                        containerColor = ObsidianSurfaceVariant,
                                                        contentColor = TextPrimary
                                                    ),
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .height(60.dp)
                                                        .testTag("color_btn_$opt")
                                                ) {
                                                    Text(text = opt, color = pillColor, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Overlay Outcome Dialog
        AnimatedVisibility(
            visible = gameState.isFinished,
            modifier = Modifier.fillMaxSize()
        ) {
            val record = gameState.resultRecord
            val isVictory = record?.result == MatchResult.VICTORY

            Surface(
                color = Color.Black.copy(alpha = 0.90f),
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
                        border = ButtonDefaults.outlinedButtonBorder.copy(
                            brush = Brush.horizontalGradient(
                                if (isVictory) listOf(VijayaGold, VijayaEmerald) else listOf(VijayaCrimson, ObsidianCardBorder)
                            )
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = if (isVictory) "🏆 VICTORY!" else "GAME OVER",
                                color = if (isVictory) VijayaGold else VijayaCrimson,
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Black
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            if (record != null && record.entryFeeUsd > 0) {
                                if (isVictory) {
                                    Text(
                                        text = "+${userAccount.currency.format(record.prizeUsd)} CASH WON",
                                        color = VijayaEmeraldLight,
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "Instant credit to your secure wallet balance",
                                        color = TextSecondary,
                                        fontSize = 12.sp
                                    )
                                } else {
                                    Text(
                                        text = "-${userAccount.currency.format(record.entryFeeUsd)} Match Stake",
                                        color = TextMuted,
                                        fontSize = 15.sp
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = ObsidianSurfaceVariant,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(12.dp)
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceAround
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text("Your Score", color = TextSecondary, fontSize = 11.sp)
                                        Text("${gameState.playerScore}", color = TextPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                                    }
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(gameState.opponentName, color = TextSecondary, fontSize = 11.sp)
                                        Text("${gameState.opponentScore}", color = TextSecondary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.Verified, contentDescription = null, tint = VijayaCyan, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Anti-Cheat Certified: #${record?.replayToken?.take(10)}", color = VijayaCyan, fontSize = 11.sp)
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                OutlinedButton(
                                    onClick = onExitGame,
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Lobby")
                                }

                                Button(
                                    onClick = onPlayAgain,
                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = VijayaGold, contentColor = Color.Black),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Play Again", fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// --- AVIATOR CUSTOM ARENA ---
@Composable
fun AviatorArena(
    gameState: ActiveGameState,
    userAccount: UserAccount,
    onCashout: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Flight Canvas Display
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFF070B14))
                .border(1.dp, if (gameState.aviatorCrashed) VijayaCrimson else VijayaGold.copy(alpha = 0.5f), RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val w = size.width
                val h = size.height

                // Draw curve
                val progress = ((gameState.aviatorMultiplier - 1.0f) / 5.0f).coerceIn(0.05f, 0.95f)
                val endX = w * progress
                val endY = h - (h * progress * 0.85f)

                val path = Path().apply {
                    moveTo(20f, h - 20f)
                    quadraticTo(endX * 0.5f, h - 20f, endX, endY)
                }

                drawPath(
                    path = path,
                    color = if (gameState.aviatorCrashed) VijayaCrimson else VijayaGold,
                    style = Stroke(width = 6.dp.toPx())
                )

                // Draw glowing jet node
                drawCircle(
                    color = if (gameState.aviatorCrashed) VijayaCrimson else VijayaGoldLight,
                    radius = 12.dp.toPx(),
                    center = Offset(endX, endY)
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                if (gameState.aviatorCrashed) {
                    Text(
                        text = "FLEW AWAY!",
                        color = VijayaCrimson,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        text = "Crashed @ ${String.format("%.2f", gameState.aviatorMultiplier)}x",
                        color = TextSecondary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                } else if (gameState.aviatorCashedOut) {
                    Text(
                        text = "CASHED OUT!",
                        color = VijayaEmerald,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        text = "@ ${String.format("%.2f", gameState.aviatorCashoutMultiplier)}x Multiplier",
                        color = VijayaEmeraldLight,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                } else {
                    Text(
                        text = "${String.format("%.2f", gameState.aviatorMultiplier)}x",
                        color = VijayaGold,
                        fontSize = 54.sp,
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        text = "SOARING TO THE MOON...",
                        color = TextMuted,
                        fontSize = 12.sp,
                        letterSpacing = 2.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Big Cashout Action Button
        val currentCashoutValue = gameState.entryFeeUsd * gameState.aviatorMultiplier
        Button(
            onClick = onCashout,
            enabled = !gameState.aviatorCrashed && !gameState.aviatorCashedOut,
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = VijayaEmerald,
                contentColor = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .testTag("aviator_cashout_button")
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "CASHOUT NOW",
                    fontWeight = FontWeight.Black,
                    fontSize = 18.sp
                )
                Text(
                    text = "Win ${userAccount.currency.format(currentCashoutValue)}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }
        }
    }
}

// --- LUDO CUSTOM ARENA ---
@Composable
fun LudoArena(
    gameState: ActiveGameState,
    onRollDice: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Board Progress Card
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .border(1.dp, VijayaGold.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "LUDO MASTER BOARD (GOAL: CELL 24)",
                    color = VijayaGold,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp
                )

                // Track Visualizer: 24 cells track
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Player Pawn Track
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Your Pawn (🟡)", color = VijayaGold, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Text("Cell ${gameState.ludoPlayerPosition} / 24", color = TextPrimary, fontSize = 12.sp)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        LinearProgressIndicator(
                            progress = { gameState.ludoPlayerPosition / 24f },
                            color = VijayaGold,
                            trackColor = ObsidianSurfaceVariant,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(12.dp)
                                .clip(RoundedCornerShape(6.dp))
                        )
                    }

                    // Opponent Pawn Track
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Opponent (🔴)", color = VijayaCrimson, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Text("Cell ${gameState.ludoOpponentPosition} / 24", color = TextPrimary, fontSize = 12.sp)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        LinearProgressIndicator(
                            progress = { gameState.ludoOpponentPosition / 24f },
                            color = VijayaCrimson,
                            trackColor = ObsidianSurfaceVariant,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(12.dp)
                                .clip(RoundedCornerShape(6.dp))
                        )
                    }
                }

                // 3D Styled Dice Display
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = ObsidianSurfaceVariant,
                    border = BorderStroke(1.dp, VijayaGold),
                    modifier = Modifier.size(80.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "${gameState.ludoDiceValue}",
                            color = VijayaGold,
                            fontSize = 42.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }

                Text(
                    text = gameState.ludoLastRollLog,
                    color = TextSecondary,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Turns Remaining: ${gameState.ludoTurnsRemaining}",
                    color = TextMuted,
                    fontSize = 11.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Roll Dice Button
        Button(
            onClick = onRollDice,
            enabled = gameState.ludoTurnsRemaining > 0 && !gameState.isFinished,
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = VijayaGold,
                contentColor = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .testTag("ludo_roll_dice_button")
        ) {
            Icon(imageVector = Icons.Default.Casino, contentDescription = null, modifier = Modifier.size(22.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "ROLL DICE (${gameState.ludoTurnsRemaining} left)",
                fontWeight = FontWeight.Black,
                fontSize = 16.sp
            )
        }
    }
}

// --- FISHING CUSTOM ARENA ---
@Composable
fun FishingArena(
    gameState: ActiveGameState,
    onTargetTapped: (Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Deep Sea Aquarium
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF041226)),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .border(1.dp, VijayaCyan.copy(alpha = 0.5f), RoundedCornerShape(20.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "DEEP SEA HARPOON CANNON",
                        color = VijayaCyan,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )

                    Text(
                        text = "🎯 ${gameState.fishShotsRemaining} Shots Left",
                        color = VijayaEmeraldLight,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Tap any sea creature to fire laser harpoon!",
                    color = TextMuted,
                    fontSize = 11.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Swimming Fish Targets Grid
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(gameState.fishTargets.size) { index ->
                        val target = gameState.fishTargets[index]
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = ObsidianSurfaceVariant.copy(alpha = 0.7f),
                            border = BorderStroke(1.dp, VijayaCyan.copy(alpha = 0.4f)),
                            modifier = Modifier
                                .clip(RoundedCornerShape(14.dp))
                                .clickable { onTargetTapped(target.id) }
                                .testTag("fish_target_${target.id}")
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = target.iconEmoji, fontSize = 36.sp)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = target.name,
                                    color = TextPrimary,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "+${target.points} pts (${target.multiplier}x)",
                                    color = VijayaCyan,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Surface(
            shape = RoundedCornerShape(12.dp),
            color = ObsidianSurfaceVariant,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total Sea Bounty Caught: ${gameState.totalFishCaught}",
                    color = TextSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = "${gameState.playerScore} Pts",
                    color = VijayaEmeraldLight,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black
                )
            }
        }
    }
}

package com.example.data.model

enum class GameId(
    val title: String,
    val subtitle: String,
    val description: String,
    val category: String,
    val iconName: String,
    val estimatedDuration: String
) {
    AVIATOR(
        title = "Aviator Crash",
        subtitle = "Sky Multiplier & Instant Cashout",
        description = "Watch the plane soar as multiplier climbs from 1.00x up to 50x! Cashout before the crash to win big.",
        category = "CRASH",
        iconName = "flight_takeoff",
        estimatedDuration = "15-30 sec"
    ),
    LUDO(
        title = "Ludo Master Duel",
        subtitle = "Skill & Strategic Dice Rush",
        description = "Roll the dice, maneuver your 4 tokens strategically, capture opponent tokens, and race home for the cash pot!",
        category = "BOARD",
        iconName = "casino",
        estimatedDuration = "45 sec"
    ),
    FISHING(
        title = "Cyber Fishing Ocean",
        subtitle = "Harpoon Catch & Treasure Multipliers",
        description = "Fire laser cannons at rare deep-sea fish, cyber sharks, and golden pearls for instant multiplier catches!",
        category = "ARCADE",
        iconName = "water",
        estimatedDuration = "30 sec"
    ),
    SPEED_MATH(
        title = "Speed Math Rush",
        subtitle = "Rapid Arithmetic Battles",
        description = "Calculate solutions under lightning 20s timers. Combos boost final prize multiplier!",
        category = "SKILL",
        iconName = "calculate",
        estimatedDuration = "25 sec"
    ),
    CYBER_REFLEX(
        title = "Cyber Reflex Tap",
        subtitle = "Precision Target Reaction",
        description = "Hit glowing quantum nodes with millisecond accuracy. Sub-50ms bot taps are rejected by anti-cheat!",
        category = "REFLEX",
        iconName = "touch_app",
        estimatedDuration = "30 sec"
    ),
    COLOR_BLITZ(
        title = "Color Blitz Stroop",
        subtitle = "Cognitive Dual-Task Duel",
        description = "Test cognitive reflexes under sensory illusions. Match word versus font tint under pressure!",
        category = "PUZZLE",
        iconName = "palette",
        estimatedDuration = "20 sec"
    )
}

enum class MatchResult {
    VICTORY,
    DEFEAT,
    DRAW
}

data class GameRecord(
    val id: String,
    val gameId: GameId,
    val playedAt: Long,
    val result: MatchResult,
    val entryFeeUsd: Double,
    val prizeUsd: Double,
    val netProfitUsd: Double,
    val playerScore: Int,
    val opponentScore: Int,
    val opponentName: String,
    val replayToken: String,
    val reflexLatencyMs: Long,
    val antiCheatCertified: Boolean = true
)

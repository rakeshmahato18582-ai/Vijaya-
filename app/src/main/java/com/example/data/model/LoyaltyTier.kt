package com.example.data.model

import androidx.compose.ui.graphics.Color

enum class LoyaltyTier(
    val title: String,
    val minXp: Int,
    val rakebackPercent: Double,
    val withdrawalSpeedDesc: String,
    val badgeColor: Long,
    val perks: List<String>
) {
    BRONZE(
        title = "Bronze Challenger",
        minXp = 0,
        rakebackPercent = 5.0,
        withdrawalSpeedDesc = "Standard (15 mins)",
        badgeColor = 0xFFCD7F32,
        perks = listOf("5% Loss Rakeback", "Standard $2.00 Min Cashout", "24/7 Chat Support")
    ),
    SILVER(
        title = "Silver Striker",
        minXp = 500,
        rakebackPercent = 8.0,
        withdrawalSpeedDesc = "Fast (8 mins)",
        badgeColor = 0xFFC0C0C0,
        perks = listOf("8% Loss Rakeback", "Weekly $2 Reload Bonus", "Fast 8-Min Processing")
    ),
    GOLD(
        title = "Gold Master",
        minXp = 2000,
        rakebackPercent = 12.0,
        withdrawalSpeedDesc = "Express (3 mins)",
        badgeColor = 0xFFF59E0B,
        perks = listOf("12% Loss Rakeback", "Express 3-Min Cashout", "Exclusive Gold Tournaments", "No Deposit Fees")
    ),
    PLATINUM(
        title = "Platinum Elite",
        minXp = 6000,
        rakebackPercent = 16.0,
        withdrawalSpeedDesc = "Priority Instant (60s)",
        badgeColor = 0xFF06B6D4,
        perks = listOf("16% Loss Rakeback", "Priority Instant Cashouts", "Personal Account Host", "Zero Withdrawal Fees")
    ),
    DIAMOND(
        title = "Diamond Legend",
        minXp = 15000,
        rakebackPercent = 20.0,
        withdrawalSpeedDesc = "VIP Instant Flash",
        badgeColor = 0xFF8B5CF6,
        perks = listOf("20% Loss Rakeback", "VIP Flash Payouts", "Unlimited Daily Withdrawals", "$1,000 Private Tournaments")
    );

    fun nextTier(): LoyaltyTier? {
        val values = entries
        val currentIndex = values.indexOf(this)
        return if (currentIndex < values.size - 1) values[currentIndex + 1] else null
    }
}

package com.example.data.model

enum class AuthProvider(val title: String, val iconResName: String) {
    GOOGLE("Google", "google"),
    APPLE("Apple ID", "apple"),
    DISCORD("Discord", "discord"),
    TWITTER_X("X / Twitter", "twitter"),
    PHONE_OTP("Phone SMS", "phone"),
    GUEST("Instant Guest", "guest")
}

enum class KycStatus(val title: String, val isApproved: Boolean) {
    UNVERIFIED("Not Submitted", false),
    IN_REVIEW("Under Compliance Review", false),
    LEVEL_1_VERIFIED("Level 1 Verified (Global)", true),
    LEVEL_2_VIP("Level 2 VIP Unlimited", true)
}

data class UserAccount(
    val id: String = "VIJAYA-USR-88392",
    val username: String = "PhoenixMaster",
    val email: String = "phoenix.gamer@gmail.com",
    val authProvider: AuthProvider = AuthProvider.GOOGLE,
    val kycStatus: KycStatus = KycStatus.LEVEL_1_VERIFIED,
    val cashBalanceUsd: Double = 18.50,
    val bonusBalanceUsd: Double = 5.00, // $5.00 New Account Welcome Bonus
    val totalEarningsUsd: Double = 142.80,
    val totalWithdrawnUsd: Double = 95.00,
    val totalDepositedUsd: Double = 25.00,
    val xp: Int = 2450,
    val tier: LoyaltyTier = LoyaltyTier.GOLD,
    val currency: Currency = Currency.USD,
    val referralCode: String = "VIJAYA-GOLD777",
    val referralCount: Int = 4,
    val referralBonusEarnedUsd: Double = 20.00,
    val skillMmr: Int = 1845,
    val matchesPlayed: Int = 28,
    val wins: Int = 20,
    val losses: Int = 8,
    val antiCheat: AntiCheatTelemetry = AntiCheatTelemetry()
) {
    val winRatePercent: Double
        get() = if (matchesPlayed > 0) (wins.toDouble() / matchesPlayed) * 100.0 else 0.0

    val netProfitUsd: Double
        get() = totalEarningsUsd - totalDepositedUsd
}

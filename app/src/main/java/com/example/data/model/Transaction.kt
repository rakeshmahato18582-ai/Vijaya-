package com.example.data.model

enum class TransactionType(val displayName: String, val isPositive: Boolean) {
    DEPOSIT("Add Money Deposit", true),
    WITHDRAWAL("Secure Cashout", false),
    MATCH_PRIZE("Match Victory Prize", true),
    MATCH_ENTRY("Tournament Entry", false),
    WELCOME_BONUS("New Account Bonus", true),
    REFERRAL_REWARD("Friend Referral Reward", true),
    RAKEBACK_CASH("VIP Tier Rakeback", true)
}

enum class TransactionStatus {
    COMPLETED,
    PROCESSING,
    VERIFYING,
    CANCELLED
}

data class Transaction(
    val id: String,
    val type: TransactionType,
    val amountUsd: Double,
    val timestamp: Long,
    val status: TransactionStatus,
    val method: String, // e.g., "PayPal", "USDT TRC-20", "Instant UPI", "Visa/Mastercard"
    val destinationAccount: String = "",
    val txHash: String, // SHA-256 encrypted verification proof
    val details: String = ""
)

package com.example.data.model

enum class MessageSender {
    USER,
    VIJAYA_BOT,
    SENIOR_SPECIALIST
}

data class SupportMessage(
    val id: String,
    val sender: MessageSender,
    val text: String,
    val timestamp: Long,
    val isLiveAgent: Boolean = false
)

enum class BugCategory(val label: String) {
    PAYMENT_OR_CASH_OUT("Deposit / Cashout Issue"),
    GAMEPLAY_GLITCH("Game Lag / Scoring Issue"),
    ANTI_CHEAT_INQUIRY("Anti-Cheat False Flag Appeal"),
    LOGIN_OR_ACCOUNT("Login / Social Auth Issue"),
    SUGGESTION("Feedback & Feature Request")
}

data class BugReport(
    val id: String,
    val title: String,
    val category: BugCategory,
    val description: String,
    val includeDeviceLogs: Boolean,
    val status: String = "Pending Investigation",
    val submittedAt: Long = System.currentTimeMillis()
)

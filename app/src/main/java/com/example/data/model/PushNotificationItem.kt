package com.example.data.model

enum class NotificationCategory {
    CASH_OUT,
    DEPOSIT_CONFIRMED,
    TOURNAMENT_ALERT,
    SECURITY_SHIELD,
    BONUS_REWARD
}

data class PushNotificationItem(
    val id: String,
    val title: String,
    val message: String,
    val timestamp: Long,
    val category: NotificationCategory,
    val isRead: Boolean = false
)

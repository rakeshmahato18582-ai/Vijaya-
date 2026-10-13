package com.example.data.model

data class AntiCheatTelemetry(
    val lastCheckTimestamp: Long = System.currentTimeMillis(),
    val averageReflexMs: Long = 210,
    val touchCadenceJitterMs: Long = 18,
    val deviceIntegrityVerified: Boolean = true,
    val botSignatureDetected: Boolean = false,
    val trustScore: Int = 99, // 0 - 100
    val activeFlags: List<String> = emptyList(),
    val totalMatchesScanned: Int = 18,
    val cleanMatchesRatio: Float = 1.0f
) {
    val statusText: String
        get() = when {
            trustScore >= 90 -> "Trusted Fair Play Pass"
            trustScore >= 70 -> "Standard Monitoring Active"
            else -> "Flagged for Verification"
        }
}

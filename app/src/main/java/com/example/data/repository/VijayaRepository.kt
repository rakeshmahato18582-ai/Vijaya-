package com.example.data.repository

import com.example.data.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.util.UUID

class VijayaRepository {

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    private val _userAccount = MutableStateFlow(
        UserAccount(
            id = "VIJAYA-USR-88392",
            username = "PhoenixMaster",
            email = "phoenix.gamer@gmail.com",
            authProvider = AuthProvider.GOOGLE,
            kycStatus = KycStatus.LEVEL_1_VERIFIED,
            cashBalanceUsd = 24.50,
            bonusBalanceUsd = 5.00, // $5.00 Welcome bonus already credited
            totalEarningsUsd = 168.40,
            totalWithdrawnUsd = 85.00,
            totalDepositedUsd = 30.00,
            xp = 2450,
            tier = LoyaltyTier.GOLD,
            currency = Currency.USD,
            referralCode = "VIJAYA-GOLD777",
            referralCount = 4,
            referralBonusEarnedUsd = 20.00,
            skillMmr = 1845,
            matchesPlayed = 28,
            wins = 20,
            losses = 8
        )
    )
    val userAccount: StateFlow<UserAccount> = _userAccount.asStateFlow()

    private val _transactions = MutableStateFlow<List<Transaction>>(
        listOf(
            Transaction(
                id = "TX-" + UUID.randomUUID().toString().take(8).uppercase(),
                type = TransactionType.WELCOME_BONUS,
                amountUsd = 5.00,
                timestamp = System.currentTimeMillis() - 86400000 * 3,
                status = TransactionStatus.COMPLETED,
                method = "Vijaya Onboarding Reward",
                txHash = generateHash("welcome_bonus_initial"),
                details = "Promotional Welcome Playthrough Bonus credited"
            ),
            Transaction(
                id = "TX-" + UUID.randomUUID().toString().take(8).uppercase(),
                type = TransactionType.DEPOSIT,
                amountUsd = 10.00,
                timestamp = System.currentTimeMillis() - 86400000 * 2,
                status = TransactionStatus.COMPLETED,
                method = "Visa Card (AES-256)",
                txHash = generateHash("deposit_card_10"),
                details = "Instant deposit via 256-bit Encrypted Gateway"
            ),
            Transaction(
                id = "TX-" + UUID.randomUUID().toString().take(8).uppercase(),
                type = TransactionType.MATCH_PRIZE,
                amountUsd = 3.60,
                timestamp = System.currentTimeMillis() - 86400000,
                status = TransactionStatus.COMPLETED,
                method = "Speed Math Cash Duel",
                txHash = generateHash("prize_math_rush_1"),
                details = "1st Place Prize (Anti-Cheat Certified)"
            ),
            Transaction(
                id = "TX-" + UUID.randomUUID().toString().take(8).uppercase(),
                type = TransactionType.WITHDRAWAL,
                amountUsd = 12.00,
                timestamp = System.currentTimeMillis() - 43200000,
                status = TransactionStatus.COMPLETED,
                method = "PayPal",
                destinationAccount = "phoenix.gamer@gmail.com",
                txHash = generateHash("cashout_paypal_12"),
                details = "Express 3-Minute Gold Tier payout"
            )
        )
    )
    val transactions: StateFlow<List<Transaction>> = _transactions.asStateFlow()

    private val _gameHistory = MutableStateFlow<List<GameRecord>>(
        listOf(
            GameRecord(
                id = "MATCH-1001",
                gameId = GameId.SPEED_MATH,
                playedAt = System.currentTimeMillis() - 86400000,
                result = MatchResult.VICTORY,
                entryFeeUsd = 2.00,
                prizeUsd = 3.60,
                netProfitUsd = 1.60,
                playerScore = 1840,
                opponentScore = 1420,
                opponentName = "ViperStrike_99",
                replayToken = generateHash("match_replay_1001"),
                reflexLatencyMs = 215,
                antiCheatCertified = true
            ),
            GameRecord(
                id = "MATCH-1002",
                gameId = GameId.CYBER_REFLEX,
                playedAt = System.currentTimeMillis() - 48000000,
                result = MatchResult.VICTORY,
                entryFeeUsd = 1.00,
                prizeUsd = 1.80,
                netProfitUsd = 0.80,
                playerScore = 2100,
                opponentScore = 1950,
                opponentName = "AlphaPro",
                replayToken = generateHash("match_replay_1002"),
                reflexLatencyMs = 192,
                antiCheatCertified = true
            ),
            GameRecord(
                id = "MATCH-1003",
                gameId = GameId.COLOR_BLITZ,
                playedAt = System.currentTimeMillis() - 24000000,
                result = MatchResult.DEFEAT,
                entryFeeUsd = 1.00,
                prizeUsd = 0.0,
                netProfitUsd = -1.00,
                playerScore = 1120,
                opponentScore = 1380,
                opponentName = "NexusQueen",
                replayToken = generateHash("match_replay_1003"),
                reflexLatencyMs = 248,
                antiCheatCertified = true
            )
        )
    )
    val gameHistory: StateFlow<List<GameRecord>> = _gameHistory.asStateFlow()

    private val _supportMessages = MutableStateFlow<List<SupportMessage>>(
        listOf(
            SupportMessage(
                id = "MSG-1",
                sender = MessageSender.VIJAYA_BOT,
                text = "Welcome to Vijaya 24/7 Player Concierge! How can we assist your gaming and instant cashouts today?",
                timestamp = System.currentTimeMillis() - 3600000
            )
        )
    )
    val supportMessages: StateFlow<List<SupportMessage>> = _supportMessages.asStateFlow()

    private val _bugReports = MutableStateFlow<List<BugReport>>(emptyList())
    val bugReports: StateFlow<List<BugReport>> = _bugReports.asStateFlow()

    private val _notifications = MutableStateFlow<List<PushNotificationItem>>(
        listOf(
            PushNotificationItem(
                id = "NOTIF-1",
                title = "🎉 $5.00 Welcome Bonus Credited",
                message = "Your new player welcome reward is ready to play in Speed Math and Cyber Reflex cash modes!",
                timestamp = System.currentTimeMillis() - 86400000 * 2,
                category = NotificationCategory.BONUS_REWARD
            ),
            PushNotificationItem(
                id = "NOTIF-2",
                title = "⚡ Express Cashout Completed",
                message = "$12.00 sent to your PayPal. Gold VIP tier processing completed in under 2 minutes.",
                timestamp = System.currentTimeMillis() - 43200000,
                category = NotificationCategory.CASH_OUT
            ),
            PushNotificationItem(
                id = "NOTIF-3",
                title = "🛡️ Anti-Cheat Integrity Audit",
                message = "All 28 match replays verified 100% clean human telemetry. Trust score: 99/100.",
                timestamp = System.currentTimeMillis() - 14400000,
                category = NotificationCategory.SECURITY_SHIELD
            )
        )
    )
    val notifications: StateFlow<List<PushNotificationItem>> = _notifications.asStateFlow()

    // --- CURRENCY & PROFILE ---
    fun setCurrency(currency: Currency) {
        _userAccount.update { it.copy(currency = currency) }
    }

    fun loginWithSocial(provider: AuthProvider, username: String, email: String) {
        _userAccount.update {
            it.copy(
                authProvider = provider,
                username = username,
                email = email
            )
        }
        pushNotification(
            title = "🔐 Secure Social Sign-in",
            message = "Authenticated with ${provider.title} account: $email. 256-bit session token created.",
            category = NotificationCategory.SECURITY_SHIELD
        )
    }

    fun submitKyc(idType: String, docNumber: String) {
        _userAccount.update { it.copy(kycStatus = KycStatus.IN_REVIEW) }
        coroutineScope.launch {
            delay(1500)
            _userAccount.update { it.copy(kycStatus = KycStatus.LEVEL_2_VIP) }
            pushNotification(
                title = "✅ Identity Verification Approved",
                message = "Your KYC verification has been certified. Unlimited instant withdrawals unlocked!",
                category = NotificationCategory.SECURITY_SHIELD
            )
        }
    }

    // --- DEPOSIT (Strictly $1.00 min deposit requirement) ---
    fun deposit(amountUsd: Double, paymentMethod: String): Result<Transaction> {
        if (amountUsd < 1.00) {
            return Result.failure(IllegalArgumentException("Minimum deposit requirement is $1.00"))
        }

        val tx = Transaction(
            id = "DEP-" + UUID.randomUUID().toString().take(8).uppercase(),
            type = TransactionType.DEPOSIT,
            amountUsd = amountUsd,
            timestamp = System.currentTimeMillis(),
            status = TransactionStatus.COMPLETED,
            method = paymentMethod,
            txHash = generateHash("dep_${System.currentTimeMillis()}_$amountUsd"),
            details = "Deposit confirmed via secure gateway"
        )

        _transactions.update { listOf(tx) + it }
        _userAccount.update {
            val newTotalDep = it.totalDepositedUsd + amountUsd
            val newBalance = it.cashBalanceUsd + amountUsd
            val newXp = it.xp + (amountUsd * 20).toInt()
            it.copy(
                cashBalanceUsd = newBalance,
                totalDepositedUsd = newTotalDep,
                xp = newXp,
                tier = resolveTier(newXp)
            )
        }

        pushNotification(
            title = "💵 Deposit Confirmed",
            message = "+${_userAccount.value.currency.format(amountUsd)} added to your wallet via $paymentMethod.",
            category = NotificationCategory.DEPOSIT_CONFIRMED
        )

        return Result.success(tx)
    }

    // --- WITHDRAWAL (Low minimum cashout limit) ---
    fun getMinimumWithdrawal(method: String): Double {
        return when (method) {
            "PayPal" -> 2.00
            "Revolut / Wise" -> 2.00
            "Instant UPI" -> 1.20
            "Crypto USDT (TRC-20)" -> 3.00
            "Bank Wire / SEPA" -> 5.00
            else -> 2.00
        }
    }

    fun requestWithdrawal(amountUsd: Double, method: String, destinationAccount: String): Result<Transaction> {
        val minLimit = getMinimumWithdrawal(method)
        if (amountUsd < minLimit) {
            return Result.failure(IllegalArgumentException("Minimum cashout for $method is $minLimit USD"))
        }
        if (amountUsd > _userAccount.value.cashBalanceUsd) {
            return Result.failure(IllegalArgumentException("Insufficient real cash balance ($${String.format("%.2f", _userAccount.value.cashBalanceUsd)})"))
        }

        val txId = "WTH-" + UUID.randomUUID().toString().take(8).uppercase()
        val tx = Transaction(
            id = txId,
            type = TransactionType.WITHDRAWAL,
            amountUsd = amountUsd,
            timestamp = System.currentTimeMillis(),
            status = TransactionStatus.PROCESSING,
            method = method,
            destinationAccount = destinationAccount,
            txHash = generateHash("wth_${System.currentTimeMillis()}_$amountUsd"),
            details = "${_userAccount.value.tier.withdrawalSpeedDesc} payout initiated"
        )

        _userAccount.update {
            it.copy(
                cashBalanceUsd = it.cashBalanceUsd - amountUsd,
                totalWithdrawnUsd = it.totalWithdrawnUsd + amountUsd
            )
        }
        _transactions.update { listOf(tx) + it }

        pushNotification(
            title = "⚡ Cashout Processing",
            message = "${_userAccount.value.currency.format(amountUsd)} to $destinationAccount ($method) is being dispatched.",
            category = NotificationCategory.CASH_OUT
        )

        // Simulate instant / fast payout speed
        coroutineScope.launch {
            delay(2200)
            _transactions.update { list ->
                list.map { if (it.id == txId) it.copy(status = TransactionStatus.COMPLETED) else it }
            }
            pushNotification(
                title = "🎉 Cashout Completed Successfully",
                message = "${_userAccount.value.currency.format(amountUsd)} has been transferred to $destinationAccount ($method).",
                category = NotificationCategory.CASH_OUT
            )
        }

        return Result.success(tx)
    }

    // --- GAMEPLAY COMPLETION & ANTI-CHEAT VERIFICATION ---
    fun recordMatchOutcome(
        gameId: GameId,
        entryFeeUsd: Double,
        playerScore: Int,
        opponentScore: Int,
        opponentName: String,
        averageReflexLatencyMs: Long,
        cadenceJitterMs: Long
    ): GameRecord {
        val isVictory = playerScore > opponentScore
        val isDraw = playerScore == opponentScore
        val prizeUsd = if (isVictory) (entryFeeUsd * 1.80) else if (isDraw) entryFeeUsd else 0.0
        val netProfit = prizeUsd - entryFeeUsd

        // Anti-cheat verification
        val isImpossibleReflex = averageReflexLatencyMs < 60
        val isSuspiciousCadence = cadenceJitterMs < 2
        val passedAntiCheat = !isImpossibleReflex && !isSuspiciousCadence

        val replayToken = generateHash("match_${UUID.randomUUID()}_$playerScore")

        val record = GameRecord(
            id = "MATCH-" + UUID.randomUUID().toString().take(6).uppercase(),
            gameId = gameId,
            playedAt = System.currentTimeMillis(),
            result = if (isVictory) MatchResult.VICTORY else if (isDraw) MatchResult.DRAW else MatchResult.DEFEAT,
            entryFeeUsd = entryFeeUsd,
            prizeUsd = prizeUsd,
            netProfitUsd = netProfit,
            playerScore = playerScore,
            opponentScore = opponentScore,
            opponentName = opponentName,
            replayToken = replayToken,
            reflexLatencyMs = averageReflexLatencyMs,
            antiCheatCertified = passedAntiCheat
        )

        _gameHistory.update { listOf(record) + it }

        // Update balance and career stats
        _userAccount.update { current ->
            val newWins = if (isVictory) current.wins + 1 else current.wins
            val newLosses = if (!isVictory && !isDraw) current.losses + 1 else current.losses
            val newMatches = current.matchesPlayed + 1
            val newCash = current.cashBalanceUsd - entryFeeUsd + prizeUsd
            val newEarnings = current.totalEarningsUsd + prizeUsd
            val newXp = current.xp + (if (isVictory) 50 else 20)
            val mmrDelta = if (isVictory) 18 else if (isDraw) 0 else -12
            val newMmr = (current.skillMmr + mmrDelta).coerceAtLeast(1000)

            val updatedTelemetry = current.antiCheat.copy(
                lastCheckTimestamp = System.currentTimeMillis(),
                averageReflexMs = averageReflexLatencyMs,
                touchCadenceJitterMs = cadenceJitterMs,
                botSignatureDetected = !passedAntiCheat,
                trustScore = if (passedAntiCheat) 99 else 65,
                totalMatchesScanned = current.antiCheat.totalMatchesScanned + 1
            )

            current.copy(
                cashBalanceUsd = newCash,
                totalEarningsUsd = newEarnings,
                matchesPlayed = newMatches,
                wins = newWins,
                losses = newLosses,
                xp = newXp,
                tier = resolveTier(newXp),
                skillMmr = newMmr,
                antiCheat = updatedTelemetry
            )
        }

        // Add ledger transactions if played with cash
        if (entryFeeUsd > 0) {
            val entryTx = Transaction(
                id = "ENTRY-" + UUID.randomUUID().toString().take(6).uppercase(),
                type = TransactionType.MATCH_ENTRY,
                amountUsd = entryFeeUsd,
                timestamp = System.currentTimeMillis() - 25000,
                status = TransactionStatus.COMPLETED,
                method = gameId.title,
                txHash = generateHash("entry_${record.id}"),
                details = "Wager stake for cash tournament"
            )
            _transactions.update { listOf(entryTx) + it }

            if (isVictory && prizeUsd > 0) {
                val prizeTx = Transaction(
                    id = "WIN-" + UUID.randomUUID().toString().take(6).uppercase(),
                    type = TransactionType.MATCH_PRIZE,
                    amountUsd = prizeUsd,
                    timestamp = System.currentTimeMillis(),
                    status = TransactionStatus.COMPLETED,
                    method = gameId.title,
                    txHash = generateHash("prize_${record.id}"),
                    details = "1st Place Winner Prize (Anti-Cheat Certified)"
                )
                _transactions.update { listOf(prizeTx) + it }

                pushNotification(
                    title = "🏆 Victory! +${_userAccount.value.currency.format(prizeUsd)} Won",
                    message = "You defeated $opponentName in ${gameId.title}! Funds added directly to wallet.",
                    category = NotificationCategory.BONUS_REWARD
                )
            }
        }

        return record
    }

    // --- REFERRALS ---
    fun redeemReferralCode(code: String): Result<Double> {
        val cleanCode = code.trim().uppercase()
        if (cleanCode.isEmpty() || cleanCode == _userAccount.value.referralCode) {
            return Result.failure(IllegalArgumentException("Cannot use your own referral code"))
        }

        val bonus = 5.00
        _userAccount.update {
            it.copy(
                bonusBalanceUsd = it.bonusBalanceUsd + bonus,
                referralCount = it.referralCount + 1,
                referralBonusEarnedUsd = it.referralBonusEarnedUsd + bonus
            )
        }

        val tx = Transaction(
            id = "REF-" + UUID.randomUUID().toString().take(8).uppercase(),
            type = TransactionType.REFERRAL_REWARD,
            amountUsd = bonus,
            timestamp = System.currentTimeMillis(),
            status = TransactionStatus.COMPLETED,
            method = "Friend Referral Bonus ($cleanCode)",
            txHash = generateHash("ref_${System.currentTimeMillis()}"),
            details = "$5.00 friend signup incentive credited"
        )
        _transactions.update { listOf(tx) + it }

        pushNotification(
            title = "🎁 $5.00 Referral Bonus",
            message = "Referral code $cleanCode applied! $5.00 added to your bonus funds.",
            category = NotificationCategory.BONUS_REWARD
        )

        return Result.success(bonus)
    }

    // --- 24/7 SUPPORT CHAT ---
    fun sendSupportMessage(text: String) {
        val userMsg = SupportMessage(
            id = "MSG-" + UUID.randomUUID().toString().take(6),
            sender = MessageSender.USER,
            text = text,
            timestamp = System.currentTimeMillis()
        )
        _supportMessages.update { it + userMsg }

        coroutineScope.launch {
            delay(1200)
            val replyText = generateBotReply(text)
            val botMsg = SupportMessage(
                id = "MSG-" + UUID.randomUUID().toString().take(6),
                sender = MessageSender.SENIOR_SPECIALIST,
                text = replyText,
                timestamp = System.currentTimeMillis(),
                isLiveAgent = true
            )
            _supportMessages.update { it + botMsg }
        }
    }

    // --- BUG REPORTING ---
    fun submitBugReport(title: String, category: BugCategory, description: String, includeLogs: Boolean): BugReport {
        val report = BugReport(
            id = "BUG-" + UUID.randomUUID().toString().take(6).uppercase(),
            title = title,
            category = category,
            description = description,
            includeDeviceLogs = includeLogs,
            status = "Under Active Review by Security Team"
        )
        _bugReports.update { listOf(report) + it }

        pushNotification(
            title = "🛡️ Bug Report Logged (#${report.id})",
            message = "Thanks for keeping Vijaya safe and fast. Our QA engineers are reviewing your ticket.",
            category = NotificationCategory.SECURITY_SHIELD
        )
        return report
    }

    fun pushNotification(title: String, message: String, category: NotificationCategory) {
        val notif = PushNotificationItem(
            id = "NOTIF-" + UUID.randomUUID().toString().take(6),
            title = title,
            message = message,
            timestamp = System.currentTimeMillis(),
            category = category
        )
        _notifications.update { listOf(notif) + it }
    }

    fun markAllNotificationsRead() {
        _notifications.update { list -> list.map { it.copy(isRead = true) } }
    }

    private fun resolveTier(xp: Int): LoyaltyTier {
        return when {
            xp >= LoyaltyTier.DIAMOND.minXp -> LoyaltyTier.DIAMOND
            xp >= LoyaltyTier.PLATINUM.minXp -> LoyaltyTier.PLATINUM
            xp >= LoyaltyTier.GOLD.minXp -> LoyaltyTier.GOLD
            xp >= LoyaltyTier.SILVER.minXp -> LoyaltyTier.SILVER
            else -> LoyaltyTier.BRONZE
        }
    }

    private fun generateHash(input: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }.take(16).uppercase()
    }

    private fun generateBotReply(userMessage: String): String {
        val lower = userMessage.lowercase()
        return when {
            lower.contains("withdraw") || lower.contains("cashout") ->
                "Vijaya cashouts are processed immediately! Our minimum cashout limit starts at only $2.00 on PayPal/Revolut and ₹100 on UPI. For Gold/Diamond members, transfers complete in under 2 minutes."
            lower.contains("deposit") || lower.contains("add money") ->
                "You can add money with as little as $1.00 USD! We support Cards, PayPal, USDT Crypto, UPI, and Pix with zero transaction fees."
            lower.contains("anti-cheat") || lower.contains("cheat") || lower.contains("ban") ->
                "Vijaya uses military-grade anti-cheat telemetry. We inspect touch latency jitter, reaction time thresholds, and input device entropy to guarantee 100% fair play. Every match replay is cryptographically signed."
            lower.contains("bonus") || lower.contains("reward") ->
                "All newly registered players receive an instant $5.00 Welcome Bonus upon onboarding! You also earn $5.00 for every friend who joins using your referral code."
            else ->
                "Thank you for contacting Vijaya 24/7 Priority Support. A Senior Support Specialist has been assigned to your session. Your account is verified with 256-bit encryption."
        }
    }
}

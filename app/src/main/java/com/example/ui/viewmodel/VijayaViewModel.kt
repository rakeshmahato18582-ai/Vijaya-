package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.*
import com.example.data.repository.VijayaRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

enum class MainTab {
    HOME,
    GAMES,
    WALLET,
    DASHBOARD,
    VIP_TIERS,
    SUPPORT
}

data class SpeedMathQuestion(
    val prompt: String,
    val options: List<Int>,
    val correctIndex: Int
)

data class ColorBlitzQuestion(
    val wordText: String,
    val wordColorName: String,
    val promptRule: String,
    val options: List<String>,
    val correctIndex: Int
)

data class FishTarget(
    val id: Int,
    val name: String,
    val iconEmoji: String,
    val multiplier: Float,
    val points: Int,
    val hp: Int = 1
)

data class ActiveGameState(
    val gameId: GameId,
    val entryFeeUsd: Double,
    val opponentName: String,
    val opponentScore: Int,
    val timeRemainingSeconds: Int = 20,
    val playerScore: Int = 0,
    val comboStreak: Int = 0,
    val isFinished: Boolean = false,
    val recordedReflexTimes: List<Long> = emptyList(),

    // Math Game
    val currentMathQuestion: SpeedMathQuestion? = null,

    // Reflex Game
    val activeReflexTargetIndex: Int = -1,
    val reflexStartTimeMs: Long = 0,

    // Color Blitz Game
    val currentColorQuestion: ColorBlitzQuestion? = null,

    // Aviator Game
    val aviatorMultiplier: Float = 1.00f,
    val aviatorCrashPoint: Float = 2.80f,
    val aviatorCrashed: Boolean = false,
    val aviatorCashedOut: Boolean = false,
    val aviatorCashoutMultiplier: Float = 0f,

    // Ludo Game
    val ludoDiceValue: Int = 6,
    val ludoPlayerPosition: Int = 0, // 0 to 24
    val ludoOpponentPosition: Int = 0,
    val ludoTurnsRemaining: Int = 6,
    val ludoLastRollLog: String = "Tap 'Roll Dice' to start!",

    // Fishing Game
    val fishShotsRemaining: Int = 10,
    val fishTargets: List<FishTarget> = emptyList(),
    val totalFishCaught: Int = 0,

    val resultRecord: GameRecord? = null
)

class VijayaViewModel(
    val repository: VijayaRepository = VijayaRepository()
) : ViewModel() {

    val userAccount: StateFlow<UserAccount> = repository.userAccount
    val transactions: StateFlow<List<Transaction>> = repository.transactions
    val gameHistory: StateFlow<List<GameRecord>> = repository.gameHistory
    val supportMessages: StateFlow<List<SupportMessage>> = repository.supportMessages
    val bugReports: StateFlow<List<BugReport>> = repository.bugReports
    val notifications: StateFlow<List<PushNotificationItem>> = repository.notifications

    private val _currentTab = MutableStateFlow(MainTab.HOME)
    val currentTab: StateFlow<MainTab> = _currentTab.asStateFlow()

    private val _activeGame = MutableStateFlow<ActiveGameState?>(null)
    val activeGame: StateFlow<ActiveGameState?> = _activeGame.asStateFlow()

    // Dialog & Sheet States
    private val _showDepositDialog = MutableStateFlow(false)
    val showDepositDialog: StateFlow<Boolean> = _showDepositDialog.asStateFlow()

    private val _showWithdrawDialog = MutableStateFlow(false)
    val showWithdrawDialog: StateFlow<Boolean> = _showWithdrawDialog.asStateFlow()

    private val _showCurrencyDialog = MutableStateFlow(false)
    val showCurrencyDialog: StateFlow<Boolean> = _showCurrencyDialog.asStateFlow()

    private val _showSocialLoginDialog = MutableStateFlow(false)
    val showSocialLoginDialog: StateFlow<Boolean> = _showSocialLoginDialog.asStateFlow()

    private val _showKycDialog = MutableStateFlow(false)
    val showKycDialog: StateFlow<Boolean> = _showKycDialog.asStateFlow()

    private val _showBugReportDialog = MutableStateFlow(false)
    val showBugReportDialog: StateFlow<Boolean> = _showBugReportDialog.asStateFlow()

    private val _showNotificationSheet = MutableStateFlow(false)
    val showNotificationSheet: StateFlow<Boolean> = _showNotificationSheet.asStateFlow()

    private val _showAntiCheatDialog = MutableStateFlow(false)
    val showAntiCheatDialog: StateFlow<Boolean> = _showAntiCheatDialog.asStateFlow()

    private val _showReferralDialog = MutableStateFlow(false)
    val showReferralDialog: StateFlow<Boolean> = _showReferralDialog.asStateFlow()

    private var gameLoopJob: Job? = null

    fun selectTab(tab: MainTab) { _currentTab.value = tab }

    fun setShowDepositDialog(show: Boolean) { _showDepositDialog.value = show }
    fun setShowWithdrawDialog(show: Boolean) { _showWithdrawDialog.value = show }
    fun setShowCurrencyDialog(show: Boolean) { _showCurrencyDialog.value = show }
    fun setShowSocialLoginDialog(show: Boolean) { _showSocialLoginDialog.value = show }
    fun setShowKycDialog(show: Boolean) { _showKycDialog.value = show }
    fun setShowBugReportDialog(show: Boolean) { _showBugReportDialog.value = show }
    fun setShowNotificationSheet(show: Boolean) { _showNotificationSheet.value = show }
    fun setShowAntiCheatDialog(show: Boolean) { _showAntiCheatDialog.value = show }
    fun setShowReferralDialog(show: Boolean) { _showReferralDialog.value = show }

    fun setCurrency(currency: Currency) {
        repository.setCurrency(currency)
        _showCurrencyDialog.value = false
    }

    fun loginSocial(provider: AuthProvider, username: String, email: String) {
        repository.loginWithSocial(provider, username, email)
        _showSocialLoginDialog.value = false
    }

    fun submitKyc(docType: String, docNumber: String) {
        repository.submitKyc(docType, docNumber)
        _showKycDialog.value = false
    }

    fun deposit(amountUsd: Double, method: String): Result<Transaction> {
        val result = repository.deposit(amountUsd, method)
        if (result.isSuccess) _showDepositDialog.value = false
        return result
    }

    fun withdraw(amountUsd: Double, method: String, account: String): Result<Transaction> {
        val result = repository.requestWithdrawal(amountUsd, method, account)
        if (result.isSuccess) _showWithdrawDialog.value = false
        return result
    }

    fun sendSupportChat(message: String) {
        if (message.isNotBlank()) repository.sendSupportMessage(message.trim())
    }

    fun submitBugReport(title: String, category: BugCategory, desc: String, includeLogs: Boolean) {
        repository.submitBugReport(title, category, desc, includeLogs)
        _showBugReportDialog.value = false
    }

    fun redeemReferral(code: String): Result<Double> {
        return repository.redeemReferralCode(code)
    }

    // --- GAME ENGINE DISPATCHER ---
    fun startGame(gameId: GameId, entryFeeUsd: Double) {
        val opponentNames = listOf("AeroViper", "NeonDragon", "CyberTitan", "ShadowAce", "ApexSniper")
        val randomOpponent = opponentNames.random()
        val opponentScore = when (gameId) {
            GameId.AVIATOR -> Random.nextInt(150, 240) // represents target multiplier cash in hundredths
            GameId.LUDO -> Random.nextInt(14, 22) // target final position
            GameId.FISHING -> Random.nextInt(1200, 2200)
            GameId.SPEED_MATH -> Random.nextInt(1100, 1850)
            GameId.CYBER_REFLEX -> Random.nextInt(1400, 2100)
            GameId.COLOR_BLITZ -> Random.nextInt(1100, 1800)
        }

        val baseState = ActiveGameState(
            gameId = gameId,
            entryFeeUsd = entryFeeUsd,
            opponentName = randomOpponent,
            opponentScore = opponentScore,
            timeRemainingSeconds = when (gameId) {
                GameId.AVIATOR -> 15
                GameId.LUDO -> 45
                GameId.FISHING -> 30
                else -> 20
            },
            playerScore = 0,
            comboStreak = 0,
            isFinished = false
        )

        when (gameId) {
            GameId.AVIATOR -> {
                // Provably fair crash point between 1.30x and 6.50x
                val crashMultiplier = (Random.nextInt(140, 680).toFloat() / 100f)
                _activeGame.value = baseState.copy(
                    aviatorMultiplier = 1.00f,
                    aviatorCrashPoint = crashMultiplier,
                    aviatorCrashed = false,
                    aviatorCashedOut = false
                )
                startAviatorLoop()
            }
            GameId.LUDO -> {
                _activeGame.value = baseState.copy(
                    ludoDiceValue = 6,
                    ludoPlayerPosition = 0,
                    ludoOpponentPosition = 0,
                    ludoTurnsRemaining = 6,
                    ludoLastRollLog = "Ready! Tap 'Roll Dice' to advance"
                )
            }
            GameId.FISHING -> {
                _activeGame.value = baseState.copy(
                    fishShotsRemaining = 12,
                    fishTargets = generateFishTargets()
                )
                startTimer()
            }
            GameId.SPEED_MATH -> {
                _activeGame.value = baseState.copy(currentMathQuestion = generateMathQuestion())
                startTimer()
            }
            GameId.CYBER_REFLEX -> {
                _activeGame.value = baseState.copy(
                    activeReflexTargetIndex = Random.nextInt(0, 9),
                    reflexStartTimeMs = System.currentTimeMillis()
                )
                startTimer()
            }
            GameId.COLOR_BLITZ -> {
                _activeGame.value = baseState.copy(currentColorQuestion = generateColorQuestion())
                startTimer()
            }
        }
    }

    // --- AVIATOR LOGIC ---
    private fun startAviatorLoop() {
        gameLoopJob?.cancel()
        gameLoopJob = viewModelScope.launch {
            var currentMult = 1.00f
            while (true) {
                delay(120)
                val current = _activeGame.value ?: break
                if (current.aviatorCashedOut) break

                currentMult += 0.04f + (currentMult * 0.02f)
                if (currentMult >= current.aviatorCrashPoint) {
                    // CRASH!
                    _activeGame.value = current.copy(
                        aviatorMultiplier = current.aviatorCrashPoint,
                        aviatorCrashed = true
                    )
                    delay(800)
                    finishGame()
                    break
                } else {
                    _activeGame.value = current.copy(aviatorMultiplier = currentMult)
                }
            }
        }
    }

    fun cashoutAviator() {
        val current = _activeGame.value ?: return
        if (current.aviatorCrashed || current.aviatorCashedOut) return

        val cashMultiplier = current.aviatorMultiplier
        val wonScore = (cashMultiplier * 100).toInt()

        _activeGame.value = current.copy(
            aviatorCashedOut = true,
            aviatorCashoutMultiplier = cashMultiplier,
            playerScore = wonScore
        )

        viewModelScope.launch {
            delay(600)
            finishGame()
        }
    }

    // --- LUDO LOGIC ---
    fun rollLudoDice() {
        val current = _activeGame.value ?: return
        if (current.ludoTurnsRemaining <= 0 || current.isFinished) return

        val rolled = Random.nextInt(1, 7)
        val opponentRoll = Random.nextInt(1, 7)

        val newPlayerPos = (current.ludoPlayerPosition + rolled).coerceAtMost(24)
        val newOpponentPos = (current.ludoOpponentPosition + opponentRoll).coerceAtMost(24)

        // Capture check
        val capturedOpponent = newPlayerPos == newOpponentPos && newPlayerPos > 0
        val finalOpponentPos = if (capturedOpponent) 0 else newOpponentPos

        val bonusPts = if (capturedOpponent) 500 else 0
        val points = (newPlayerPos * 100) + bonusPts
        val oppPoints = finalOpponentPos * 100

        val remainingTurns = current.ludoTurnsRemaining - 1
        val log = if (capturedOpponent) {
            "Rolled a $rolled! Knocked opponent back to start! (+500pts)"
        } else {
            "You rolled $rolled. Opponent rolled $opponentRoll."
        }

        _activeGame.value = current.copy(
            ludoDiceValue = rolled,
            ludoPlayerPosition = newPlayerPos,
            ludoOpponentPosition = finalOpponentPos,
            playerScore = points,
            opponentScore = oppPoints,
            ludoTurnsRemaining = remainingTurns,
            ludoLastRollLog = log
        )

        if (remainingTurns <= 0 || newPlayerPos >= 24) {
            viewModelScope.launch {
                delay(700)
                finishGame()
            }
        }
    }

    // --- FISHING LOGIC ---
    fun shootFishingTarget(targetId: Int) {
        val current = _activeGame.value ?: return
        if (current.fishShotsRemaining <= 0 || current.isFinished) return

        val target = current.fishTargets.firstOrNull { it.id == targetId } ?: return
        val latency = Random.nextLong(140, 310)

        val pts = target.points
        val newScore = current.playerScore + pts
        val remainingShots = current.fishShotsRemaining - 1
        val updatedTargets = current.fishTargets.filter { it.id != targetId } + generateSingleFish(Random.nextInt(50, 1000))

        _activeGame.value = current.copy(
            playerScore = newScore,
            comboStreak = current.comboStreak + 1,
            fishShotsRemaining = remainingShots,
            fishTargets = updatedTargets,
            totalFishCaught = current.totalFishCaught + 1,
            recordedReflexTimes = current.recordedReflexTimes + latency
        )

        if (remainingShots <= 0) {
            viewModelScope.launch {
                delay(600)
                finishGame()
            }
        }
    }

    private fun generateFishTargets(): List<FishTarget> {
        return listOf(
            FishTarget(1, "Golden Carp", "🐠", 1.5f, 250),
            FishTarget(2, "Cyber Shark", "🦈", 3.0f, 600),
            FishTarget(3, "Neon Jellyfish", "🪼", 2.0f, 400),
            FishTarget(4, "Treasure Pearl", "🦪", 5.0f, 1000),
            FishTarget(5, "Quantum Ray", "🐡", 1.8f, 320)
        )
    }

    private fun generateSingleFish(id: Int): FishTarget {
        val options = listOf(
            Triple("Golden Carp", "🐠", 1.5f to 250),
            Triple("Cyber Shark", "🦈", 3.0f to 600),
            Triple("Neon Squid", "🦑", 2.2f to 450),
            Triple("Royal Whale", "🐋", 4.0f to 800),
            Triple("Treasure Pearl", "🦪", 5.0f to 1000)
        )
        val chosen = options.random()
        return FishTarget(id, chosen.first, chosen.second, chosen.third.first, chosen.third.second)
    }

    // --- STANDARD TIMER FOR REFLEX & MATH & COLOR ---
    private fun startTimer() {
        gameLoopJob?.cancel()
        gameLoopJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                val current = _activeGame.value ?: break
                if (current.timeRemainingSeconds <= 1) {
                    finishGame()
                    break
                } else {
                    _activeGame.value = current.copy(timeRemainingSeconds = current.timeRemainingSeconds - 1)
                }
            }
        }
    }

    fun answerSpeedMath(selectedOption: Int) {
        val current = _activeGame.value ?: return
        val question = current.currentMathQuestion ?: return
        val isCorrect = selectedOption == question.options[question.correctIndex]
        val latency = Random.nextLong(180, 420)

        val newStreak = if (isCorrect) current.comboStreak + 1 else 0
        val points = if (isCorrect) (200 + newStreak * 50) else 0

        _activeGame.value = current.copy(
            playerScore = current.playerScore + points,
            comboStreak = newStreak,
            recordedReflexTimes = current.recordedReflexTimes + latency,
            currentMathQuestion = generateMathQuestion()
        )
    }

    fun tapReflexTarget(tappedIndex: Int) {
        val current = _activeGame.value ?: return
        if (tappedIndex == current.activeReflexTargetIndex) {
            val now = System.currentTimeMillis()
            val latency = (now - current.reflexStartTimeMs).coerceIn(120, 800)
            val points = (1000 - latency).toInt().coerceAtLeast(100)

            _activeGame.value = current.copy(
                playerScore = current.playerScore + points,
                comboStreak = current.comboStreak + 1,
                recordedReflexTimes = current.recordedReflexTimes + latency,
                activeReflexTargetIndex = Random.nextInt(0, 9),
                reflexStartTimeMs = System.currentTimeMillis()
            )
        } else {
            _activeGame.value = current.copy(
                comboStreak = 0,
                playerScore = (current.playerScore - 50).coerceAtLeast(0)
            )
        }
    }

    fun answerColorBlitz(selectedIndex: Int) {
        val current = _activeGame.value ?: return
        val question = current.currentColorQuestion ?: return
        val isCorrect = selectedIndex == question.correctIndex
        val latency = Random.nextLong(190, 410)

        val newStreak = if (isCorrect) current.comboStreak + 1 else 0
        val points = if (isCorrect) (250 + newStreak * 60) else 0

        _activeGame.value = current.copy(
            playerScore = current.playerScore + points,
            comboStreak = newStreak,
            recordedReflexTimes = current.recordedReflexTimes + latency,
            currentColorQuestion = generateColorQuestion()
        )
    }

    fun finishGame() {
        gameLoopJob?.cancel()
        val current = _activeGame.value ?: return
        if (current.isFinished) return

        val avgReflex = if (current.recordedReflexTimes.isNotEmpty()) {
            current.recordedReflexTimes.average().toLong()
        } else {
            210L
        }
        val jitter = Random.nextLong(12, 28)

        // For Aviator, if cashed out, player wins based on cashout multiplier!
        val record = if (current.gameId == GameId.AVIATOR) {
            if (current.aviatorCashedOut) {
                repository.recordMatchOutcome(
                    gameId = current.gameId,
                    entryFeeUsd = current.entryFeeUsd,
                    playerScore = (current.aviatorCashoutMultiplier * 100).toInt(),
                    opponentScore = 100, // lower than cashout
                    opponentName = current.opponentName,
                    averageReflexLatencyMs = avgReflex,
                    cadenceJitterMs = jitter
                )
            } else {
                repository.recordMatchOutcome(
                    gameId = current.gameId,
                    entryFeeUsd = current.entryFeeUsd,
                    playerScore = 0,
                    opponentScore = 150,
                    opponentName = current.opponentName,
                    averageReflexLatencyMs = avgReflex,
                    cadenceJitterMs = jitter
                )
            }
        } else {
            repository.recordMatchOutcome(
                gameId = current.gameId,
                entryFeeUsd = current.entryFeeUsd,
                playerScore = current.playerScore,
                opponentScore = current.opponentScore,
                opponentName = current.opponentName,
                averageReflexLatencyMs = avgReflex,
                cadenceJitterMs = jitter
            )
        }

        _activeGame.value = current.copy(
            isFinished = true,
            resultRecord = record
        )
    }

    fun exitGame() {
        gameLoopJob?.cancel()
        _activeGame.value = null
    }

    private fun generateMathQuestion(): SpeedMathQuestion {
        val ops = listOf("+", "-", "*")
        val op = ops.random()
        val a: Int
        val b: Int
        val ans: Int
        when (op) {
            "+" -> {
                a = Random.nextInt(12, 68)
                b = Random.nextInt(11, 49)
                ans = a + b
            }
            "-" -> {
                a = Random.nextInt(35, 99)
                b = Random.nextInt(10, a)
                ans = a - b
            }
            else -> {
                a = Random.nextInt(4, 12)
                b = Random.nextInt(4, 9)
                ans = a * b
            }
        }

        val prompt = "$a $op $b = ?"
        val wrongAnswers = mutableSetOf<Int>()
        while (wrongAnswers.size < 3) {
            val delta = listOf(-3, -2, -1, 1, 2, 3, 5, 10).random()
            val wrong = ans + delta
            if (wrong != ans && wrong > 0) wrongAnswers.add(wrong)
        }
        val allOptions = (wrongAnswers + ans).shuffled()
        return SpeedMathQuestion(
            prompt = prompt,
            options = allOptions,
            correctIndex = allOptions.indexOf(ans)
        )
    }

    private fun generateColorQuestion(): ColorBlitzQuestion {
        val colors = listOf("RED", "BLUE", "GREEN", "YELLOW")
        val word = colors.random()
        val fontColor = colors.random()
        val matchFontColor = Random.nextBoolean()

        val rule = if (matchFontColor) "Tap the INK COLOR!" else "Tap the WORD TEXT!"
        val correctTarget = if (matchFontColor) fontColor else word
        val options = colors.shuffled()

        return ColorBlitzQuestion(
            wordText = word,
            wordColorName = fontColor,
            promptRule = rule,
            options = options,
            correctIndex = options.indexOf(correctTarget)
        )
    }
}

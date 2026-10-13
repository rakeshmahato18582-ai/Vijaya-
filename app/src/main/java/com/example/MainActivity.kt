package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.model.GameId
import com.example.ui.components.TopHeaderBar
import com.example.ui.screens.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.MainTab
import com.example.ui.viewmodel.VijayaViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VijayaTheme {
                VijayaApp()
            }
        }
    }
}

@Composable
fun VijayaApp(viewModel: VijayaViewModel = viewModel()) {
    val userAccount by viewModel.userAccount.collectAsStateWithLifecycle()
    val transactions by viewModel.transactions.collectAsStateWithLifecycle()
    val gameHistory by viewModel.gameHistory.collectAsStateWithLifecycle()
    val supportMessages by viewModel.supportMessages.collectAsStateWithLifecycle()
    val bugReports by viewModel.bugReports.collectAsStateWithLifecycle()
    val notifications by viewModel.notifications.collectAsStateWithLifecycle()

    val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()
    val activeGame by viewModel.activeGame.collectAsStateWithLifecycle()

    val showDepositDialog by viewModel.showDepositDialog.collectAsStateWithLifecycle()
    val showWithdrawDialog by viewModel.showWithdrawDialog.collectAsStateWithLifecycle()
    val showCurrencyDialog by viewModel.showCurrencyDialog.collectAsStateWithLifecycle()
    val showSocialLoginDialog by viewModel.showSocialLoginDialog.collectAsStateWithLifecycle()
    val showKycDialog by viewModel.showKycDialog.collectAsStateWithLifecycle()
    val showBugReportDialog by viewModel.showBugReportDialog.collectAsStateWithLifecycle()
    val showNotificationSheet by viewModel.showNotificationSheet.collectAsStateWithLifecycle()
    val showAntiCheatDialog by viewModel.showAntiCheatDialog.collectAsStateWithLifecycle()
    val showReferralDialog by viewModel.showReferralDialog.collectAsStateWithLifecycle()

    val unreadNotifs = notifications.count { !it.isRead }

    // Back handling
    BackHandler(enabled = activeGame != null || currentTab != MainTab.HOME) {
        if (activeGame != null) {
            viewModel.exitGame()
        } else {
            viewModel.selectTab(MainTab.HOME)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = ObsidianBackground,
        topBar = {
            if (activeGame == null) {
                TopHeaderBar(
                    userAccount = userAccount,
                    unreadNotifCount = unreadNotifs,
                    onWalletClick = { viewModel.selectTab(MainTab.WALLET) },
                    onCurrencyClick = { viewModel.setShowCurrencyDialog(true) },
                    onProfileClick = { viewModel.setShowSocialLoginDialog(true) },
                    onNotificationsClick = { viewModel.setShowNotificationSheet(true) },
                    onAntiCheatClick = { viewModel.setShowAntiCheatDialog(true) }
                )
            }
        },
        bottomBar = {
            if (activeGame == null) {
                NavigationBar(
                    containerColor = ObsidianSurface,
                    tonalElevation = 8.dp,
                    modifier = Modifier
                        .navigationBarsPadding()
                        .testTag("main_navigation_bar")
                ) {
                    NavigationBarItem(
                        selected = currentTab == MainTab.HOME,
                        onClick = { viewModel.selectTab(MainTab.HOME) },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                        label = { Text("Home", fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Black,
                            selectedTextColor = VijayaGold,
                            indicatorColor = VijayaGold,
                            unselectedIconColor = TextSecondary,
                            unselectedTextColor = TextSecondary
                        ),
                        modifier = Modifier.testTag("nav_item_home")
                    )

                    NavigationBarItem(
                        selected = currentTab == MainTab.GAMES,
                        onClick = { viewModel.selectTab(MainTab.GAMES) },
                        icon = { Icon(Icons.Default.SportsEsports, contentDescription = "Games") },
                        label = { Text("Games", fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Black,
                            selectedTextColor = VijayaGold,
                            indicatorColor = VijayaGold,
                            unselectedIconColor = TextSecondary,
                            unselectedTextColor = TextSecondary
                        ),
                        modifier = Modifier.testTag("nav_item_games")
                    )

                    NavigationBarItem(
                        selected = currentTab == MainTab.WALLET,
                        onClick = { viewModel.selectTab(MainTab.WALLET) },
                        icon = { Icon(Icons.Default.AccountBalanceWallet, contentDescription = "Wallet") },
                        label = { Text("Wallet", fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Black,
                            selectedTextColor = VijayaGold,
                            indicatorColor = VijayaGold,
                            unselectedIconColor = TextSecondary,
                            unselectedTextColor = TextSecondary
                        ),
                        modifier = Modifier.testTag("nav_item_wallet")
                    )

                    NavigationBarItem(
                        selected = currentTab == MainTab.DASHBOARD,
                        onClick = { viewModel.selectTab(MainTab.DASHBOARD) },
                        icon = { Icon(Icons.Default.QueryStats, contentDescription = "Stats") },
                        label = { Text("Stats", fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Black,
                            selectedTextColor = VijayaGold,
                            indicatorColor = VijayaGold,
                            unselectedIconColor = TextSecondary,
                            unselectedTextColor = TextSecondary
                        ),
                        modifier = Modifier.testTag("nav_item_dashboard")
                    )

                    NavigationBarItem(
                        selected = currentTab == MainTab.VIP_TIERS,
                        onClick = { viewModel.selectTab(MainTab.VIP_TIERS) },
                        icon = { Icon(Icons.Default.WorkspacePremium, contentDescription = "VIP") },
                        label = { Text("VIP", fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Black,
                            selectedTextColor = VijayaGold,
                            indicatorColor = VijayaGold,
                            unselectedIconColor = TextSecondary,
                            unselectedTextColor = TextSecondary
                        ),
                        modifier = Modifier.testTag("nav_item_vip")
                    )

                    NavigationBarItem(
                        selected = currentTab == MainTab.SUPPORT,
                        onClick = { viewModel.selectTab(MainTab.SUPPORT) },
                        icon = { Icon(Icons.Default.SupportAgent, contentDescription = "Support") },
                        label = { Text("Support", fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Black,
                            selectedTextColor = VijayaGold,
                            indicatorColor = VijayaGold,
                            unselectedIconColor = TextSecondary,
                            unselectedTextColor = TextSecondary
                        ),
                        modifier = Modifier.testTag("nav_item_support")
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val game = activeGame
            if (game != null) {
                GameplayScreen(
                    userAccount = userAccount,
                    gameState = game,
                    onMathOptionSelected = { viewModel.answerSpeedMath(it) },
                    onReflexTargetTapped = { viewModel.tapReflexTarget(it) },
                    onColorOptionSelected = { viewModel.answerColorBlitz(it) },
                    onAviatorCashout = { viewModel.cashoutAviator() },
                    onLudoRollDice = { viewModel.rollLudoDice() },
                    onFishingTargetTapped = { viewModel.shootFishingTarget(it) },
                    onPlayAgain = { viewModel.startGame(game.gameId, game.entryFeeUsd) },
                    onExitGame = { viewModel.exitGame() }
                )
            } else {
                when (currentTab) {
                    MainTab.HOME -> {
                        HomeScreen(
                            userAccount = userAccount,
                            onAddMoneyClick = { viewModel.setShowDepositDialog(true) },
                            onWithdrawClick = { viewModel.setShowWithdrawDialog(true) },
                            onPlayGame = { gameId, stake -> viewModel.startGame(gameId, stake) },
                            onViewAllGames = { viewModel.selectTab(MainTab.GAMES) },
                            onViewVipTiers = { viewModel.selectTab(MainTab.VIP_TIERS) },
                            onViewAntiCheat = { viewModel.setShowAntiCheatDialog(true) },
                            onReferralClick = { viewModel.setShowReferralDialog(true) }
                        )
                    }
                    MainTab.GAMES -> {
                        GamesScreen(
                            userAccount = userAccount,
                            onStartGame = { gameId, stake -> viewModel.startGame(gameId, stake) }
                        )
                    }
                    MainTab.WALLET -> {
                        WalletScreen(
                            userAccount = userAccount,
                            transactions = transactions,
                            onAddMoneyClick = { viewModel.setShowDepositDialog(true) },
                            onWithdrawClick = { viewModel.setShowWithdrawDialog(true) },
                            onCurrencyClick = { viewModel.setShowCurrencyDialog(true) }
                        )
                    }
                    MainTab.DASHBOARD -> {
                        DashboardScreen(
                            userAccount = userAccount,
                            gameHistory = gameHistory,
                            transactions = transactions
                        )
                    }
                    MainTab.VIP_TIERS -> {
                        VipRewardsScreen(
                            userAccount = userAccount,
                            onReferralClick = { viewModel.setShowReferralDialog(true) }
                        )
                    }
                    MainTab.SUPPORT -> {
                        SupportScreen(
                            messages = supportMessages,
                            bugReports = bugReports,
                            onSendMessage = { viewModel.sendSupportChat(it) },
                            onReportBugClick = { viewModel.setShowBugReportDialog(true) }
                        )
                    }
                }
            }

            // Dialogs
            if (showDepositDialog) {
                DepositDialog(
                    userAccount = userAccount,
                    onDismiss = { viewModel.setShowDepositDialog(false) },
                    onConfirmDeposit = { amt, method -> viewModel.deposit(amt, method) }
                )
            }

            if (showWithdrawDialog) {
                WithdrawDialog(
                    userAccount = userAccount,
                    onDismiss = { viewModel.setShowWithdrawDialog(false) },
                    onConfirmWithdrawal = { amt, method, acc -> viewModel.withdraw(amt, method, acc) }
                )
            }

            if (showCurrencyDialog) {
                CurrencySelectorDialog(
                    currentCurrency = userAccount.currency,
                    onDismiss = { viewModel.setShowCurrencyDialog(false) },
                    onSelectCurrency = { viewModel.setCurrency(it) }
                )
            }

            if (showSocialLoginDialog) {
                SocialLoginDialog(
                    currentProvider = userAccount.authProvider,
                    onDismiss = { viewModel.setShowSocialLoginDialog(false) },
                    onLogin = { provider, user, email -> viewModel.loginSocial(provider, user, email) }
                )
            }

            if (showKycDialog) {
                KycDialog(
                    userAccount = userAccount,
                    onDismiss = { viewModel.setShowKycDialog(false) },
                    onSubmitKyc = { type, num -> viewModel.submitKyc(type, num) }
                )
            }

            if (showBugReportDialog) {
                BugReportDialog(
                    onDismiss = { viewModel.setShowBugReportDialog(false) },
                    onSubmitBug = { title, cat, desc, logs -> viewModel.submitBugReport(title, cat, desc, logs) }
                )
            }

            if (showNotificationSheet) {
                NotificationSheet(
                    notifications = notifications,
                    onDismiss = { viewModel.setShowNotificationSheet(false) }
                )
            }

            if (showAntiCheatDialog) {
                AntiCheatDialog(
                    userAccount = userAccount,
                    onDismiss = { viewModel.setShowAntiCheatDialog(false) }
                )
            }

            if (showReferralDialog) {
                ReferralDialog(
                    userAccount = userAccount,
                    onDismiss = { viewModel.setShowReferralDialog(false) },
                    onRedeemCode = { viewModel.redeemReferral(it) }
                )
            }
        }
    }
}

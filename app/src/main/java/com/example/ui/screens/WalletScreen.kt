package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Transaction
import com.example.data.model.TransactionStatus
import com.example.data.model.UserAccount
import com.example.ui.components.WalletBalanceCard
import com.example.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun WalletScreen(
    userAccount: UserAccount,
    transactions: List<Transaction>,
    onAddMoneyClick: () -> Unit,
    onWithdrawClick: () -> Unit,
    onCurrencyClick: () -> Unit
) {
    val dateFormat = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(ObsidianBackground)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column {
                Text(
                    text = "Encrypted Cash Wallet",
                    color = TextPrimary,
                    fontWeight = FontWeight.Black,
                    fontSize = 22.sp
                )
                Text(
                    text = "Secure instant deposits & low-minimum global cashouts.",
                    color = TextSecondary,
                    fontSize = 13.sp
                )
            }
        }

        // Wallet Card
        item {
            WalletBalanceCard(
                userAccount = userAccount,
                onAddMoneyClick = onAddMoneyClick,
                onWithdrawClick = onWithdrawClick
            )
        }

        // Low Minimum Policy Highlights
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "GLOBAL PAYMENT SPECIFICATIONS",
                        color = TextSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = ObsidianSurfaceVariant,
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text("Min Deposit", color = TextMuted, fontSize = 11.sp)
                                Text("$1.00 USD", color = VijayaGold, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                Text("≈ ${userAccount.currency.format(1.0)}", color = TextSecondary, fontSize = 10.sp)
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = ObsidianSurfaceVariant,
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text("Min Cashout", color = TextMuted, fontSize = 11.sp)
                                Text("$2.00 USD", color = VijayaEmerald, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                Text("Low threshold", color = TextSecondary, fontSize = 10.sp)
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = ObsidianSurfaceVariant,
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text("Payout Speed", color = TextMuted, fontSize = 11.sp)
                                Text("Express", color = VijayaCyan, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                Text("Gold Tier (3m)", color = TextSecondary, fontSize = 10.sp)
                            }
                        }
                    }
                }
            }
        }

        // Encryption and Financial Data Protection Card
        item {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = ObsidianSurfaceVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, VijayaCyan.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(VijayaCyan.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.EnhancedEncryption,
                            contentDescription = null,
                            tint = VijayaCyan,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "256-Bit Financial Vault Encryption",
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                        Text(
                            text = "All payment destinations and transaction records are cryptographically secured with zero-knowledge vault keys.",
                            color = TextSecondary,
                            fontSize = 11.sp,
                            lineHeight = 15.sp
                        )
                    }
                }
            }
        }

        // Transaction Ledger Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "TRANSACTION LEDGER",
                    color = TextSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )

                Text(
                    text = "${transactions.size} Records",
                    color = TextMuted,
                    fontSize = 11.sp
                )
            }
        }

        // Transaction items
        items(transactions, key = { it.id }) { tx ->
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, ObsidianCardBorder, RoundedCornerShape(14.dp))
                    .testTag("tx_item_${tx.id}")
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (tx.type.isPositive) VijayaEmerald.copy(alpha = 0.15f)
                                        else VijayaGold.copy(alpha = 0.15f)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = if (tx.type.isPositive) Icons.Default.ArrowDownward else Icons.Default.ArrowUpward,
                                    contentDescription = null,
                                    tint = if (tx.type.isPositive) VijayaEmerald else VijayaGold,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = tx.type.displayName,
                                    color = TextPrimary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                                Text(
                                    text = tx.method,
                                    color = TextMuted,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = (if (tx.type.isPositive) "+" else "-") + userAccount.currency.format(tx.amountUsd),
                                color = if (tx.type.isPositive) VijayaEmeraldLight else VijayaGoldLight,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(
                                        when (tx.status) {
                                            TransactionStatus.COMPLETED -> VijayaEmerald.copy(alpha = 0.2f)
                                            TransactionStatus.PROCESSING -> VijayaGold.copy(alpha = 0.2f)
                                            else -> VijayaCrimson.copy(alpha = 0.2f)
                                        }
                                    )
                                    .padding(horizontal = 4.dp, vertical = 1.dp)
                            ) {
                                Text(
                                    text = tx.status.name,
                                    color = when (tx.status) {
                                        TransactionStatus.COMPLETED -> VijayaEmerald
                                        TransactionStatus.PROCESSING -> VijayaGold
                                        else -> VijayaCrimson
                                    },
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Hash: #${tx.txHash}",
                            color = TextMuted,
                            fontSize = 10.sp
                        )
                        Text(
                            text = dateFormat.format(Date(tx.timestamp)),
                            color = TextMuted,
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }
    }
}

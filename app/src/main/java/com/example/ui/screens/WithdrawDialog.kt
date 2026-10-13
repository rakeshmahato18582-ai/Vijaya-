package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.UserAccount
import com.example.ui.theme.*

@Composable
fun WithdrawDialog(
    userAccount: UserAccount,
    onDismiss: () -> Unit,
    onConfirmWithdrawal: (amountUsd: Double, method: String, account: String) -> Unit
) {
    var selectedMethod by remember { mutableStateOf("PayPal") }
    var amountInput by remember { mutableStateOf("10.00") }
    var destinationInput by remember { mutableStateOf("phoenix.gamer@gmail.com") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isProcessing by remember { mutableStateOf(false) }

    data class CashoutOption(
        val name: String,
        val minUsd: Double,
        val speed: String,
        val placeholder: String
    )

    val methods = listOf(
        CashoutOption("PayPal", 2.00, userAccount.tier.withdrawalSpeedDesc, "Enter PayPal Email Address"),
        CashoutOption("Revolut / Wise", 2.00, userAccount.tier.withdrawalSpeedDesc, "Enter Revolut Tag / Revtag"),
        CashoutOption("Crypto USDT (TRC-20)", 3.00, "Block Confirmation (1-2 min)", "Enter USDT TRC20 Wallet Address"),
        CashoutOption("Instant UPI", 1.20, "Instant Direct UPI", "Enter UPI ID (e.g., gamer@okaxis)"),
        CashoutOption("Bank Wire / SEPA", 5.00, "1-2 Business Hours", "Enter IBAN or Account Number")
    )

    val activeOption = methods.first { it.name == selectedMethod }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = ObsidianSurface,
            border = ButtonDefaults.outlinedButtonBorder.copy(
                width = 1.dp,
                brush = androidx.compose.ui.graphics.SolidColor(ObsidianCardBorder)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(VijayaEmerald.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Payments,
                                contentDescription = null,
                                tint = VijayaEmerald,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Redeem & Cashout",
                                color = TextPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp
                            )
                            Text(
                                text = "Low minimums starting at $2.00",
                                color = VijayaEmeraldLight,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = TextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Available Cash Info
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = ObsidianSurfaceVariant,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Available Withdrawable Cash",
                                color = TextSecondary,
                                fontSize = 11.sp
                            )
                            Text(
                                text = userAccount.currency.format(userAccount.cashBalanceUsd),
                                color = TextPrimary,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        // VIP speed perk
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "VIP Payout Speed",
                                color = TextSecondary,
                                fontSize = 11.sp
                            )
                            Text(
                                text = userAccount.tier.withdrawalSpeedDesc,
                                color = VijayaEmerald,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Cashout Methods
                Text(
                    text = "SELECT CASHOUT METHOD",
                    color = TextSecondary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                methods.forEach { opt ->
                    val isSelected = selectedMethod == opt.name
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 3.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSelected) VijayaEmerald.copy(alpha = 0.12f) else ObsidianSurfaceVariant)
                            .border(
                                width = 1.dp,
                                color = if (isSelected) VijayaEmerald else Color.Transparent,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clickable {
                                selectedMethod = opt.name
                                errorMessage = null
                            }
                            .padding(horizontal = 10.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = isSelected,
                                onClick = { selectedMethod = opt.name },
                                colors = RadioButtonDefaults.colors(selectedColor = VijayaEmerald)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Column {
                                Text(
                                    text = opt.name,
                                    color = TextPrimary,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = "Low Min: $${String.format("%.2f", opt.minUsd)} • ${opt.speed}",
                                    color = VijayaEmeraldLight,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Amount to Cashout
                OutlinedTextField(
                    value = amountInput,
                    onValueChange = {
                        amountInput = it
                        errorMessage = null
                    },
                    label = { Text("Cashout Amount ($ USD)") },
                    prefix = { Text("$", color = VijayaEmerald, fontWeight = FontWeight.Bold) },
                    suffix = {
                        TextButton(
                            onClick = {
                                amountInput = String.format("%.2f", userAccount.cashBalanceUsd)
                            }
                        ) {
                            Text("MAX", color = VijayaGold, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = VijayaEmerald,
                        unfocusedBorderColor = ObsidianCardBorder,
                        focusedLabelColor = VijayaEmerald
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("withdraw_amount_input")
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Destination field
                OutlinedTextField(
                    value = destinationInput,
                    onValueChange = {
                        destinationInput = it
                        errorMessage = null
                    },
                    label = { Text(activeOption.placeholder) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = VijayaEmerald,
                        unfocusedBorderColor = ObsidianCardBorder,
                        focusedLabelColor = VijayaEmerald
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("withdraw_destination_input")
                )

                if (errorMessage != null) {
                    Text(
                        text = errorMessage ?: "",
                        color = VijayaCrimson,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Anti-Cheat & Fast Processing Guarantee
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = null,
                        tint = VijayaCyan,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Zero cashout fee • Identity verified • Encrypted automated dispatch",
                        color = TextSecondary,
                        fontSize = 11.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val parsed = amountInput.toDoubleOrNull()
                        when {
                            parsed == null || parsed < activeOption.minUsd -> {
                                errorMessage = "Minimum cashout for ${activeOption.name} is $${String.format("%.2f", activeOption.minUsd)}"
                            }
                            parsed > userAccount.cashBalanceUsd -> {
                                errorMessage = "Insufficient cash balance. Available: $${String.format("%.2f", userAccount.cashBalanceUsd)}"
                            }
                            destinationInput.isBlank() -> {
                                errorMessage = "Please enter your destination account address"
                            }
                            else -> {
                                isProcessing = true
                                onConfirmWithdrawal(parsed, activeOption.name, destinationInput.trim())
                            }
                        }
                    },
                    enabled = !isProcessing,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VijayaEmerald,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("confirm_withdraw_submit_button")
                ) {
                    if (isProcessing) {
                        CircularProgressIndicator(
                            color = Color.Black,
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        val parsed = amountInput.toDoubleOrNull() ?: activeOption.minUsd
                        Text(
                            text = "Redeem $${String.format("%.2f", parsed)} Now",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }
    }
}

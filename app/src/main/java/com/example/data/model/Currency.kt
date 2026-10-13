package com.example.data.model

enum class Currency(
    val code: String,
    val symbol: String,
    val nameString: String,
    val usdRate: Double // 1 USD = X in this currency
) {
    USD("USD", "$", "US Dollar", 1.0),
    EUR("EUR", "€", "Euro", 0.92),
    GBP("GBP", "£", "British Pound", 0.79),
    INR("INR", "₹", "Indian Rupee", 86.50),
    JPY("JPY", "¥", "Japanese Yen", 152.0),
    CAD("CAD", "CA$", "Canadian Dollar", 1.36),
    BRL("BRL", "R$", "Brazilian Real", 5.45),
    PHP("PHP", "₱", "Philippine Peso", 58.20);

    fun format(usdAmount: Double): String {
        val converted = usdAmount * usdRate
        return when (this) {
            JPY -> "$symbol${converted.toInt()}"
            INR, PHP, BRL -> "$symbol${String.format("%.2f", converted)}"
            else -> "$symbol${String.format("%.2f", converted)}"
        }
    }
}

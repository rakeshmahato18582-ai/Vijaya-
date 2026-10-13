package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.repository.VijayaRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Vijaya", appName)
    }

    @Test
    fun `enforce minimum deposit requirement of one dollar`() {
        val repo = VijayaRepository()
        val lowResult = repo.deposit(0.50, "PayPal")
        assertTrue(lowResult.isFailure)

        val validResult = repo.deposit(1.00, "PayPal")
        assertTrue(validResult.isSuccess)
    }

    @Test
    fun `enforce low minimum cashout limit`() {
        val repo = VijayaRepository()
        assertEquals(2.00, repo.getMinimumWithdrawal("PayPal"), 0.01)
        assertEquals(2.00, repo.getMinimumWithdrawal("Revolut / Wise"), 0.01)
        assertEquals(3.00, repo.getMinimumWithdrawal("Crypto USDT (TRC-20)"), 0.01)
        assertEquals(1.20, repo.getMinimumWithdrawal("Instant UPI"), 0.01)
    }
}

package io.ar.invest.data

import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class StockKtTest {

    @Test
    fun isValidIsin_invalid_symbols() {
        // letters only
        assertFalse(isValidIsin("ABCDEFGHIJKL"), "at least one digit")

        // must end with check digit
        assertFalse(isValidIsin("US45256BAD3A"), "check digit")

        // only alphanumerics
        assertFalse(isValidIsin("US64118Q107$"), "dollar symbol not allowed")

        // only alphanumerics
        assertFalse(isValidIsin("US64118Q 076"), "space not allowed")

        // upper case only
        assertFalse(isValidIsin("US64118q1076"), "lower case Q")

        // upper case only
        assertFalse(isValidIsin("uS64118Q1076"), "lower case U")
    }

    @Test
    fun isValidIsin_too_short() {
        // TEST
        assertFalse(isValidIsin("XS088441001"), "too few characters")
    }

    @Test
    fun isValidIsin_too_long() {
        // TEST
        assertFalse(isValidIsin("US64118Q10765"), "too much characters")
    }

    @Test
    fun isValidIsin_valid() {
        // TEST
        assertTrue(isValidIsin("US64118Q1076"))

        // TEST
        assertTrue(isValidIsin("XS0884410019"))
    }

    @Test
    fun isValidWkn_invalid_length() {
        // TEST
        assertFalse(isValidWkn("BAY00"), "too few characters")

        // TEST
        assertFalse(isValidWkn("8513999"), "too much characters")
    }

    @Test
    fun isValidWkn_invalid_symbols() {
        // invalid: O
        assertFalse(isValidWkn("BAY0O1"), "letter O is not allowed")

        // invalid: I
        assertFalse(isValidWkn("85I399"), "letter I is not allowed")

        // invalid: dollar
        assertFalse(isValidWkn("85139$"), "symbol $ is not allowed")

        // invalid: space
        assertFalse(isValidWkn("85 399"), "space is not allowed")
    }

    @Test
    fun isValidWkn_valid() {
        // TEST
        assertTrue(isValidWkn("BAY001"))

        // TEST
        assertTrue(isValidWkn("851399"))

        // TEST
        assertTrue(isValidWkn("A0Q4DC"))
    }
}
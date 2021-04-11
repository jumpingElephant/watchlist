package io.ar.invest.data

import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class StockKtTest {

    @Test
    fun isValidIsin_invalid_symbols() {
        // letters only
        assertFalse(Isin("ABCDEFGHIJKL").isValid(), "at least one digit")

        // must end with check digit
        assertFalse(Isin("US45256BAD3A").isValid(), "check digit")

        // only alphanumerics
        assertFalse(Isin("US64118Q107$").isValid(), "dollar symbol not allowed")

        // only alphanumerics
        assertFalse(Isin("US64118Q 076").isValid(), "space not allowed")

        // upper case only
        assertFalse(Isin("US64118q1076").isValid(), "lower case Q")

        // upper case only
        assertFalse(Isin("uS64118Q1076").isValid(), "lower case U")
    }

    @Test
    fun isValidIsin_too_short() {
        // TEST
        assertFalse(Isin("XS088441001").isValid(), "too few characters")
    }

    @Test
    fun isValidIsin_too_long() {
        // TEST
        assertFalse(Isin("US64118Q10765").isValid(), "too much characters")
    }

    @Test
    fun isValidIsin_valid() {
        // TEST
        assertTrue(Isin("US64118Q1076").isValid())

        // TEST
        assertTrue(Isin("XS0884410019").isValid())
    }

    @Test
    fun isValidWkn_invalid_length() {
        // TEST
        assertFalse(Wkn("BAY00").isValid("BAY00"), "too few characters")

        // TEST
        assertFalse(Wkn("8513999").isValid("8513999"), "too much characters")
    }

    @Test
    fun isValidWkn_invalid_symbols() {
        // invalid: O
        assertFalse(Wkn("BAY0O1").isValid("BAY0O1"), "letter O is not allowed")

        // invalid: I
        assertFalse(Wkn("85I399").isValid("85I399"), "letter I is not allowed")

        // invalid: dollar
        assertFalse(Wkn("85139$").isValid("85139$"), "symbol $ is not allowed")

        // invalid: space
        assertFalse(Wkn("85 399").isValid("85 399"), "space is not allowed")
    }

    @Test
    fun isValidWkn_valid() {
        // TEST
        assertTrue(Wkn("BAY001").isValid("BAY001"))

        // TEST
        assertTrue(Wkn("851399").isValid("851399"))

        // TEST
        assertTrue(Wkn("A0Q4DC").isValid("A0Q4DC"))
    }
}
package io.ar.invest.data

data class Stock(
    val name: String,
    val isin: Isin,
    val wkn: Wkn,
    val stockType: String
)

private val wknRegex = Regex("""\A([A-HJ-NP-Z0-9]{6})\Z""")
private val isinRegex = Regex("""\A([A-Z]{2})((?![A-Z]{9}\b)[A-Z0-9]{9})[0-9]\Z""")

data class Wkn(val value: String) {

    fun isValid(wkn: String): Boolean {
        return wknRegex.matches(wkn)
    }

    override fun toString(): String {
        return value
    }
}

data class Isin(val value: String) {

    fun isValid(): Boolean {
        return isinRegex.matches(value)
    }

    override fun toString(): String {
        return value
    }
}
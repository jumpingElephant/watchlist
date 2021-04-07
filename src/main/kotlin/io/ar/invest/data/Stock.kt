package io.ar.invest.data

data class Stock(
    val name: String,
    val isin: String,
    val wkn: String,
    val stockType: String
)

val isinRegex = Regex("""\A([A-Z]{2})((?![A-Z]{9}\b)[A-Z0-9]{9})[0-9]\Z""")
fun isValidIsin(isin: String): Boolean {
    return isinRegex.matches(isin)
}

val wknRegex = Regex("""\A([A-HJ-NP-Z0-9]{6})\Z""")
fun isValidWkn(wkn: String): Boolean {
    return wknRegex.matches(wkn)
}

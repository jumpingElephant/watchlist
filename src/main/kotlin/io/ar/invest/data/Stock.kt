package io.ar.invest.data

data class Stock(
    val name: String,
    val isin: String,
    val wkn: String,
    val stockType: String
)
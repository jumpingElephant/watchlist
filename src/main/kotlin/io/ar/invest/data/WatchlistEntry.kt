package io.ar.invest.data

import org.dizitart.no2.objects.Id
import java.util.*

data class WatchlistEntry(
    @Id val id: UUID,
    val name: String,
    val isin: String,
    val wkn: String
)
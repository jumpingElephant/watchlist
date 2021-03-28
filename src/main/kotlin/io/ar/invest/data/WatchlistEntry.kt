package io.ar.invest.data

import org.dizitart.no2.objects.Id
import java.util.*

data class WatchlistEntry(
    @Id val id: UUID,
    val stock: Stock,
    val graphDisplayed: Boolean = false
)
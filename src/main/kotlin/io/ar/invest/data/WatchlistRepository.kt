package io.ar.invest.data

import org.dizitart.kno2.getRepository
import org.dizitart.kno2.nitrite
import org.dizitart.no2.objects.filters.ObjectFilters
import java.util.*

class WatchlistRepository {
    private val db = nitrite {
        compress = false
        autoCommit = true
    }

    constructor() {
        val watchlistEntries = db.getRepository<WatchlistEntry>(key = "watchlist")
        listOf(
            WatchlistEntry(
                name = "International Business Machines Corporation",
                isin = "US4592001014",
                wkn = "851399",
                id = UUID.randomUUID()
            ),
            WatchlistEntry(name = "Deutsche Telekom", isin = "DE0005557508", wkn = "555750", id = UUID.randomUUID())
        ).forEach {
            watchlistEntries.insert(it)
        }
    }

    fun getWatchlist(wkn: String? = null): List<WatchlistEntry> {
        val watchlistEntries = db.getRepository<WatchlistEntry>(key = "watchlist")
        return if (wkn == null) {
            watchlistEntries.find().toList()
        } else {
            watchlistEntries.find(ObjectFilters.eq("wkn", wkn)).toList()
        }
    }
}
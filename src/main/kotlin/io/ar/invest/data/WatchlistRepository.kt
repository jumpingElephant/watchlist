package io.ar.invest.data

import org.dizitart.kno2.getRepository
import org.dizitart.kno2.nitrite
import org.dizitart.no2.objects.filters.ObjectFilters
import java.io.File
import java.util.*

fun getWatchlistFile(): File {
    val userHome = System.getProperty("user.home")
    val fileLocation = File("$userHome/.watchlist/")
    val file = File(fileLocation, "watchlist.dat")
    if (!fileLocation.exists()) {
        fileLocation.mkdirs()
    }
    return file
}

class WatchlistRepository {

    private val db = nitrite {
        compress = false
        autoCommit = true
        file = getWatchlistFile()
    }

    init {
        val watchlistEntries = watchlistObjectRepository()
        if (watchlistEntries.find().count() <= 0) {
            listOf(
                WatchlistEntry(
                    stock = Stock(
                        name = "International Business Machines Corporation",
                        isin = "US4592001014",
                        wkn = "851399",
                        stockType = "Aktie"
                    ),
                    id = UUID.randomUUID()
                ),
                WatchlistEntry(
                    stock = Stock(
                        name = "Deutsche Telekom",
                        isin = "DE0005557508",
                        wkn = "555750",
                        stockType = "Aktie"
                    ),
                    id = UUID.randomUUID()
                )
            ).forEach {
                watchlistEntries.insert(it)
            }
        }
    }

    fun getWatchlist(wkn: String? = null): List<WatchlistEntry> {
        val watchlistEntries = watchlistObjectRepository()
        return if (wkn == null) {
            watchlistEntries.find().sortedBy { it.stock.name }.toList()
        } else {
            watchlistEntries.find(ObjectFilters.eq("wkn", wkn)).sortedBy { it.stock.name }.toList()
        }
    }

    private fun watchlistObjectRepository() = db.getRepository<WatchlistEntry>(key = "watchlist")

    fun updateWatchlistEntry(entry: WatchlistEntry) {
        watchlistObjectRepository().update(entry)
    }
}
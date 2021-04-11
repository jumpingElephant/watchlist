package io.ar.invest.data

import io.ar.invest.getUserHome
import org.dizitart.kno2.getRepository
import org.dizitart.kno2.nitrite
import org.dizitart.no2.Document
import org.dizitart.no2.objects.ObjectRepository
import org.dizitart.no2.objects.filters.ObjectFilters
import java.io.File
import java.util.*

fun getWatchlistFile(): File {
    val userHome = getUserHome()
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
        migrateWatchlistEntries()

        val watchlistEntries = watchlistObjectRepository()
        if (watchlistEntries.find().count() <= 0) {
            listOf(
                WatchlistEntry(
                    stock = Stock(
                        name = "International Business Machines Corporation",
                        isin = Isin("US4592001014"),
                        wkn = Wkn("851399"),
                        stockType = "Aktie"
                    ),
                    id = UUID.randomUUID()
                ),
                WatchlistEntry(
                    stock = Stock(
                        name = "Deutsche Telekom",
                        isin = Isin("DE0005557508"),
                        wkn = Wkn("555750"),
                        stockType = "Aktie"
                    ),
                    id = UUID.randomUUID()
                )
            ).forEach {
                watchlistEntries.insert(it)
            }
        }
    }

    private fun migrateWatchlistEntries() {
        val watchlistEntries = watchlistObjectRepository()
        migrateIsinStringToObjectType(watchlistEntries)
        migrateWknStringToObjectType(watchlistEntries)
        watchlistEntries.find().forEach(watchlistEntries::update)
    }

    private fun migrateIsinStringToObjectType(watchlistEntries: ObjectRepository<WatchlistEntry>) {
        watchlistEntries.documentCollection
            .find()
            .toList()
            .map { we -> we.get("stock") as Document }
            .filter { we -> we.get("isin") is String }
            .map { stockDocument: Document ->
                val isinValue = stockDocument.remove("isin") as String
                stockDocument["isin"] = Document(mapOf(Pair("value", isinValue)))
                stockDocument
            }
    }

    private fun migrateWknStringToObjectType(watchlistEntries: ObjectRepository<WatchlistEntry>) {
        watchlistEntries.documentCollection
            .find()
            .toList()
            .map { we -> we.get("stock") as Document }
            .filter { we -> we.get("wkn") is String }
            .map { stockDocument: Document ->
                val wknValue = stockDocument.remove("wkn") as String
                stockDocument["wkn"] = Document(mapOf(Pair("value", wknValue)))
                stockDocument
            }
    }

    fun getWatchlist(wkn: String? = null): List<WatchlistEntry> {
        val watchlistEntries = watchlistObjectRepository()
        return if (wkn == null) {
            watchlistEntries.find().sortedBy { it.stock.name.toLowerCase() }.toList()
        } else {
            watchlistEntries.find(ObjectFilters.eq("wkn", wkn)).sortedBy { it.stock.name.toLowerCase() }.toList()
        }
    }

    private fun watchlistObjectRepository() = db.getRepository<WatchlistEntry>(key = "watchlist")

    fun updateWatchlistEntry(entry: WatchlistEntry) {
        watchlistObjectRepository().update(entry)
    }

    fun deleteWatchlistEntry(entry: WatchlistEntry) {
        watchlistObjectRepository().remove(ObjectFilters.eq("id", entry.id))
    }

    fun insertWatchlistEntry(stock: Stock) {
        watchlistObjectRepository()
            .insert(
                WatchlistEntry(
                    stock = stock,
                    id = UUID.randomUUID()
                )
            )
    }
}
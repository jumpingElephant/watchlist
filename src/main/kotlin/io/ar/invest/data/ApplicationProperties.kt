package io.ar.invest.data

import io.ar.invest.getUserHome
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

enum class WatchlistMode {
    Listing, Creating
}

fun getPropertyFile(): File {
    val userHome = getUserHome()
    val fileLocation = File("$userHome/.watchlist/")
    val file = File(fileLocation, "watchlist.properties")
    if (!fileLocation.exists()) {
        fileLocation.mkdirs()
    }
    return file
}

class ApplicationProperties {
    private val properties = Properties()

    private val watchlistModeKey = "watchlistMode"

    init {
        val propertyFile = getPropertyFile()
        if (propertyFile.exists()) {
            properties.load(FileInputStream(propertyFile))
        }
    }

    fun getWatchlistMode(): WatchlistMode {
        val propertyValue = properties.getProperty(watchlistModeKey)
        return if (propertyValue != null) {
            WatchlistMode.valueOf(propertyValue)
        } else {
            WatchlistMode.Listing
        }
    }

    fun setWatchlistMode(watchlistMode: WatchlistMode) {
        properties.setProperty(watchlistModeKey, watchlistMode.toString())
        properties.store(FileOutputStream(getPropertyFile()), createFileComment())
    }

    private fun createFileComment() = LocalDateTime.now().format(
        DateTimeFormatter.ofLocalizedDateTime(
            FormatStyle.LONG, FormatStyle.SHORT
        )
    )
}
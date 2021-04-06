package io.ar.invest

import androidx.compose.desktop.Window
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.unit.IntSize
import io.ar.invest.data.ApplicationProperties
import io.ar.invest.data.StockRepository
import io.ar.invest.data.WatchlistRepository
import io.ar.invest.theme.DarkColorPalette
import io.ar.invest.ui.WatchlistView

val LocalWatchlistRepository = compositionLocalOf<WatchlistRepository> { error("No Watchlist found!") }
val LocalApplicationProperties = compositionLocalOf<ApplicationProperties> { error("No Application Properties found!") }
val LocalStockRepository = compositionLocalOf<StockRepository> { error("No Stocks found!") }

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
fun main() = Window(
    title = "Watchlist",
    size = IntSize(962, 768)
) {
    val cache = StockRepository
    val applicationProperties = ApplicationProperties()
    val watchlist = WatchlistRepository()
    CompositionLocalProvider(
        LocalWatchlistRepository provides watchlist,
        LocalApplicationProperties provides applicationProperties,
        LocalStockRepository provides cache
    ) {
        MaterialTheme(
            colors = DarkColorPalette
        ) {
            DisableSelection {
                WatchlistView()
            }
        }
    }
}

fun getUserHome(): String = System.getProperty("user.home")
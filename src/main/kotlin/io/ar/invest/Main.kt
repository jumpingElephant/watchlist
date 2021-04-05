import androidx.compose.desktop.Window
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.IntSize
import io.ar.invest.data.StockRepository
import io.ar.invest.data.WatchlistRepository
import io.ar.invest.theme.DarkColorPalette

val LocalWatchlistRepository = compositionLocalOf<WatchlistRepository> { error("No Watchlist found!") }
val LocalStockRepository = compositionLocalOf<StockRepository> { error("No Stocks found!") }

@ExperimentalMaterialApi
fun main() = Window(
    title = "Watchlist",
    size = IntSize(962, 768)
) {
    val cache = StockRepository
    val watchlist = WatchlistRepository()
    CompositionLocalProvider(
        LocalWatchlistRepository provides watchlist,
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
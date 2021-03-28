import androidx.compose.desktop.Window
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.IntSize
import io.ar.invest.data.StockRepository
import io.ar.invest.data.WatchlistRepository

val LocalWatchlistRepository = compositionLocalOf<WatchlistRepository> { error("No Watchlist found!") }
val LocalStockRepository = compositionLocalOf<StockRepository> { error("No Stocks found!") }

fun main() = Window(
    title = "Watchlist",
    size = IntSize(1440, 768)
) {
    val cache = StockRepository
    val watchlist = WatchlistRepository()
    CompositionLocalProvider(
        LocalWatchlistRepository provides watchlist,
        LocalStockRepository provides cache
    ) {
        MaterialTheme {
            WatchlistView()
        }
    }
}
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.runtime.Composable
import io.ar.invest.ui.WatchlistBody

val watchlistRepository = LocalWatchlistRepository
val stockRepository = LocalStockRepository

@ExperimentalMaterialApi
@Composable
fun WatchlistView() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Hi Alex")
                },
                navigationIcon = {
                    Button(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.QueryStats,
                            contentDescription = "Hi"
                        )
                    }
                }
            )
        },
        content = {
            Watchlist()
        },
        bottomBar = { Text("bottom") }
    )
}

@ExperimentalMaterialApi
@Composable
fun Watchlist() {
    Column {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Stocks") },
                    actions = {
                        Button(onClick = {}) {
                            Text("Add")
                        }
                    }
                )
            }
        ) {
            Column {
                val current = watchlistRepository.current
                WatchlistBody(
                    watchlist = watchlistRepository.current.getWatchlist(),
                    updateWatchlistEntry = { current.updateWatchlistEntry(it) }
                )
            }
        }
    }
}

val darkThemeColors = darkColors()

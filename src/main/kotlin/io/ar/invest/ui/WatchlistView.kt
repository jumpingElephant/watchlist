import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.runtime.Composable
import io.ar.invest.theme.DarkColorPalette

val watchlistRepository = LocalWatchlistRepository
val stockRepository = LocalStockRepository

@Composable
fun WatchlistView() {
    MaterialTheme(
        colors = DarkColorPalette
    ) {
        DisableSelection {
            Main()
        }
    }
}

@Composable
fun Main() {
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
                WatchlistBody(
                    stocks = watchlistRepository.current.getWatchlist()
                )
            }
        }
    }
}

val darkThemeColors = darkColors()
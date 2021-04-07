package io.ar.invest.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import io.ar.invest.LocalApplicationProperties
import io.ar.invest.LocalStockRepository
import io.ar.invest.LocalWatchlistRepository
import io.ar.invest.data.Stock
import io.ar.invest.data.WatchlistMode

val watchlistRepository = LocalWatchlistRepository
val applicationProperties = LocalApplicationProperties

@Suppress("unused")
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
    val appState = applicationProperties.current
    var mode by remember { mutableStateOf(appState.getWatchlistMode()) }
    fun switchMode(watchlistMode: WatchlistMode) {
        mode = watchlistMode
        appState.setWatchlistMode(mode)
    }
    Column {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Stocks") },
                    actions = {
                        Button(
                            onClick =
                            {
                                when (mode) {
                                    WatchlistMode.Listing -> switchMode(WatchlistMode.Creating)
                                    WatchlistMode.Creating -> switchMode(WatchlistMode.Listing)
                                }
                            }) {
                            Text(
                                text = when (mode) {
                                    WatchlistMode.Listing -> "Add"
                                    WatchlistMode.Creating -> "Show list"
                                }
                            )
                        }
                    }
                )
            }
        ) {
            Column() {
                val repository = watchlistRepository.current
                when (mode) {
                    WatchlistMode.Listing -> {
                        var watchlist by remember { mutableStateOf(repository.getWatchlist()) }
                        WatchlistBody(
                            watchlist = watchlist,
                            updateWatchlistEntry = { repository.updateWatchlistEntry(it) },
                            deleteWatchlistEntry = {
                                repository.deleteWatchlistEntry(it)
                                watchlist = repository.getWatchlist()
                            }
                        )
                    }
                    WatchlistMode.Creating -> {
                        val (name, onNameChange) = remember { mutableStateOf("") }
                        val (isin, onIsinChange) = remember { mutableStateOf("") }
                        val (wkn, onWknChange) = remember { mutableStateOf("") }
                        Column {
                            FormTextField(
                                value = name,
                                onValueChange = onNameChange,
                                label = { Text("Name") }
                            )
                            FormTextField(
                                value = isin,
                                onValueChange = onIsinChange,
                                label = { Text("ISIN") }
                            )
                            FormTextField(
                                value = wkn,
                                onValueChange = onWknChange,
                                label = { Text("WKN") }
                            )
                            Row(
                                horizontalArrangement = Arrangement.End,
                                modifier = Modifier
                                    .wrapContentSize()
                            ) {
                                Button(onClick = {
                                    repository.insertWatchlistEntry(Stock(name, isin, wkn, "Aktie"))
                                    switchMode(WatchlistMode.Listing)
                                }) {
                                    Text("Create")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FormTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: @Composable (() -> Unit)? = null,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = label,
        singleLine = true,
    )
}
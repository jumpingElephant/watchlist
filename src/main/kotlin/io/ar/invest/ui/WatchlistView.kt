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
import io.ar.invest.data.WatchlistEntry
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
    var mode by remember {
        mutableStateOf(
            when (appState.getWatchlistMode()) {
                WatchlistMode.Editing -> WatchlistMode.Listing
                else -> appState.getWatchlistMode()
            }
        )
    }
    var editedWatchlistEntry: WatchlistEntry? = null
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
                        when (mode) {
                            WatchlistMode.Creating ->
                                Button(
                                    onClick = { switchMode(WatchlistMode.Listing) }
                                ) {
                                    Text(text = "Show list")
                                }
                            WatchlistMode.Listing ->
                                Button(
                                    onClick = { switchMode(WatchlistMode.Creating) }
                                ) {
                                    Text(text = "Add")
                                }
                            WatchlistMode.Editing ->
                                Button(
                                    onClick = {
                                        switchMode(WatchlistMode.Listing)
                                        editedWatchlistEntry = null
                                    }
                                ) {
                                    Text(text = "Cancel")
                                }
                        }
                    }
                )
            }
        ) {
            Column {
                val repository = watchlistRepository.current
                when (mode) {
                    WatchlistMode.Listing -> {
                        var watchlist by remember { mutableStateOf(repository.getWatchlist()) }
                        WatchlistBody(
                            watchlist = watchlist,
                            updateWatchlistEntry = { repository.updateWatchlistEntry(it) },
                            editWatchlistEntry = {
                                switchMode(WatchlistMode.Editing)
                                editedWatchlistEntry = it
                            },
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
                        StockForm(
                            submitText = "Create",
                            name = name, onNameChange = onNameChange,
                            isin = isin, onIsinChange = onIsinChange,
                            wkn = wkn, onWknChange = onWknChange,
                            onSubmit = {
                                repository.insertWatchlistEntry(Stock(name, isin, wkn, "Aktie"))
                                switchMode(WatchlistMode.Listing)
                            }
                        )
                    }
                    WatchlistMode.Editing -> {
                        val (name, onNameChange) = remember { mutableStateOf(editedWatchlistEntry!!.stock.name) }
                        val (isin, onIsinChange) = remember { mutableStateOf(editedWatchlistEntry!!.stock.isin) }
                        val (wkn, onWknChange) = remember { mutableStateOf(editedWatchlistEntry!!.stock.wkn) }
                        StockForm(
                            submitText = "Update",
                            name = name, onNameChange = onNameChange,
                            isin = isin, onIsinChange = onIsinChange,
                            wkn = wkn, onWknChange = onWknChange,
                            onSubmit = {
                                repository.updateWatchlistEntry(
                                    editedWatchlistEntry!!.copy(stock = Stock(name, isin, wkn, "Aktie"))
                                )
                                switchMode(WatchlistMode.Listing)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun StockForm(
    name: String,
    onNameChange: (String) -> Unit,
    isin: String,
    onIsinChange: (String) -> Unit,
    wkn: String,
    onWknChange: (String) -> Unit,
    onSubmit: () -> Unit,
    submitText: String
) {
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
            Button(onClick = onSubmit) {
                Text(submitText)
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

package io.ar.invest.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Addchart
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.unit.dp
import io.ar.invest.data.WatchlistEntry
import io.ar.invest.theme.WatchlistColors

@ExperimentalMaterialApi
@Composable
fun WatchlistBody(
    watchlist: List<WatchlistEntry>,
    updateWatchlistEntry: (WatchlistEntry) -> Unit
) {
    LazyColumn {
        items(
            items = watchlist,
            key = { it.id }
        ) {
            val (graphDisplayed, onGraphDisplayedChange) = remember { mutableStateOf(it.graphDisplayed) }
            WatchlistItem(
                item = it,
                graphDisplayed = graphDisplayed,
                onGraphDisplayedChange = { newValue ->
                    updateWatchlistEntry(it.copy(graphDisplayed = newValue))
                    onGraphDisplayedChange(newValue)
                }
            )
            Divider()
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun WatchlistItem(
    item: WatchlistEntry,
    graphDisplayed: Boolean = false,
    onGraphDisplayedChange: (Boolean) -> Unit
) {
    var isHighlighted by remember { mutableStateOf(false) }
    ListItem(
        text = { Text(item.stock.name) },
        secondaryText = {
            Row(modifier = Modifier.padding(top = 3.dp)) {
                Text("ISIN: ${item.stock.isin}", style = MaterialTheme.typography.overline)
                Spacer(modifier = Modifier.width(7.dp))
                Text("WKN: ${item.stock.wkn}", style = MaterialTheme.typography.overline)
            }
        },
        overlineText = { Text(item.stock.stockType) },
        trailing = {
            IconButton(
                onClick = {
                    onGraphDisplayedChange(graphDisplayed.not())
                },
            ) {
                Icon(
                    imageVector = if (graphDisplayed) Icons.Default.BarChart else Icons.Default.Addchart,
                    tint = Color.White,
                    contentDescription = "Add to chart",
                    modifier = Modifier
                )
            }
        },
        modifier = Modifier
            .background(if (isHighlighted) WatchlistColors.detailsBg else Color.Transparent)
            .pointerMoveFilter(
                onEnter = { isHighlighted = true; false },
                onExit = { isHighlighted = false; false })
    )
}

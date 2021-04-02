package io.ar.invest.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Addchart
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.unit.dp
import io.ar.invest.data.WatchlistEntry
import io.ar.invest.theme.WatchlistColors
import io.ar.invest.theme.divider

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
        }
    }
}

@Composable
fun WatchlistItem(
    item: WatchlistEntry,
    graphDisplayed: Boolean = false,
    onGraphDisplayedChange: (Boolean) -> Unit
) {
    var isHighlighted by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 0.dp)
            .border(border = BorderStroke(1.dp, color = divider), shape = CutCornerShape(2.dp))
            .background(color = if (isHighlighted) WatchlistColors.reactionBg else Color.Transparent)
            .padding(horizontal = 20.dp, vertical = 9.dp)
            .pointerMoveFilter(onEnter = { isHighlighted = true; false }, onExit = { isHighlighted = false;false })
    ) {
        Column {
            Text(text = item.stock.name)
            Row(modifier = Modifier.padding(top = 3.dp)) {
                Text("ISIN: ${item.stock.isin}", style = MaterialTheme.typography.overline)
                Spacer(modifier = Modifier.width(7.dp))
                Text("WKN: ${item.stock.wkn}", style = MaterialTheme.typography.overline)
            }
        }
        IconButton(
            onClick = {},
        ) {
            Icon(
                imageVector = Icons.Default.AddShoppingCart,
                contentDescription = "New Order",
                tint = Color.White,
                modifier = Modifier
            )
        }
        IconButton(
            onClick = {},
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingBasket,
                tint = Color.White,
                contentDescription = "?New Order?",
                modifier = Modifier
            )
        }
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
    }
}

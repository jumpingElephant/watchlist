import androidx.compose.foundation.BorderStroke
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.ar.invest.data.WatchlistEntry
import io.ar.invest.theme.divider

@Composable
fun WatchlistBody(
    stocks: List<WatchlistEntry>
) {
    LazyColumn {
        items(
            items = stocks,
            key = { it::isin }
        ) {
            var (graphDisplayed, onGraphDisplayedChange) = remember { mutableStateOf(false) }
            WatchlistItem(
                item = it,
                graphDisplayed = graphDisplayed,
                onGraphDisplayedChange = onGraphDisplayedChange
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 0.dp)
            .border(border = BorderStroke(1.dp, color = divider), shape = CutCornerShape(2.dp))
            .padding(horizontal = 20.dp, vertical = 9.dp)
    ) {
        Column {
            Text(text = item.name)
            Row(modifier = Modifier.padding(top = 3.dp)) {
                Text("ISIN: ${item.isin}", style = MaterialTheme.typography.overline)
                Spacer(modifier = Modifier.width(7.dp))
                Text("WKN: ${item.wkn}", style = MaterialTheme.typography.overline)
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

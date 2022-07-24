package app.covidstats.ui.views

import androidx.compose.runtime.Composable
import app.covidstats.model.opers.Model
import app.covidstats.ui.Stats

@Composable
fun StatsView(
    searchHandler: ((() -> Unit)?) -> Unit,
    model: Model
) {
    searchHandler(null)
    Stats(
        model,
        onFavoriteAdd = { model.addFavoriteLocation(it) },
        onFavoriteRemove = { model.removeFavoriteLocation(it) }
    )
}
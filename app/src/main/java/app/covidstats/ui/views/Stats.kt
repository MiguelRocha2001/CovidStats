package app.covidstats.ui.views

import androidx.compose.runtime.Composable
import app.covidstats.model.opers.Model
import app.covidstats.ui.CovidStats

@Composable
fun StatsView(
    searchHandler: ((() -> Unit)?) -> Unit,
    model: Model
) {
    searchHandler(null)
    CovidStats(
        model,
        onFavoriteAdd = { model.addFavoriteLocation(it) },
        onFavoriteRemove = { model.removeFavoriteLocation(it) }
    )
}
package app.covidstats.ui.views

import androidx.compose.runtime.Composable
import app.covidstats.model.Model
import app.covidstats.ui.MoreCovidInformation

@Composable
fun MoreInfoView(
    searchHandler: ((() -> Unit)?) -> Unit,
    model: Model
) {
    searchHandler(null)
    MoreCovidInformation(model.moreCovidInfo)
}
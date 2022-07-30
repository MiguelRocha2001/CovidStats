package app.covidstats.ui.navigation

import androidx.compose.runtime.Composable
import app.covidstats.model.opers.Model
import app.covidstats.ui.MoreCovidInformation

@Composable
fun MoreInfoView(
    searchHandler: ((() -> Unit)?) -> Unit,
    model: Model
) {
    searchHandler(null)
    MoreCovidInformation(model.appInfo, model.moreCovidInfo)
}
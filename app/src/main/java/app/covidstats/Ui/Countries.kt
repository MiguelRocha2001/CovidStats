package app.covidstats.Ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.covidstats.model.continents.Continent

@Composable
fun displayCountries(continent: Continent, onClick: (String) -> Unit) {
    LazyColumn(Modifier.fillMaxWidth()) {

    }
}
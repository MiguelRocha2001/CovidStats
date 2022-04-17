package app.covidstats.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun MoreCovidInformation(externalLink: String) {
    Column() {
        Title(title = "More Info")
        Text(text = externalLink)
    }
}
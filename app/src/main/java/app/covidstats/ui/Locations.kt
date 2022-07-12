package app.covidstats.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.covidstats.model.Model

/**
 * Display a list of [locations] and after pressing one, calls [onLocationClick].
 * @param additional is a list of additional locations to display.
 */
@Composable
fun Locations(
    title: String = "Select Location",
    locations: List<String>?,
    onLocationClick: (String) -> Unit,
    vararg additional: Pair<String, (String) -> Unit>
) {
    if (locations == null)
        LoadingPage()
    else {
        Column {
            Title(title = title, textAlign = TextAlign.Center)
            LazyColumn(
                Modifier.fillMaxWidth()
            ) {
                additional.forEach {
                    item { Location(it.first, DARK_GREY_WITH_TRANSPARENCY2, it.second) }
                }
                locations.forEach{ country ->
                    item { Location(country) { onLocationClick(country) } }
                }
            }
        }
    }
}

@Composable
private fun Location(locationName: String, backgroundColor: Color = MaterialTheme.colorScheme.background, onClick: (String) -> Unit) {
    Button(
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        modifier = Modifier.fillMaxWidth(),
        elevation = null,
        onClick = { onClick(locationName) }
    ) {
        Row(
            verticalAlignment = CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
                .background(color = if (Model.continents.any {it == locationName}) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surface)
        ) {
            Text(
                text = locationName.uppercase(),
                color = if (Model.continents.any {it == locationName}) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface,
                fontSize = 20.sp,
                letterSpacing = 3.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
            )
        }
    }
}
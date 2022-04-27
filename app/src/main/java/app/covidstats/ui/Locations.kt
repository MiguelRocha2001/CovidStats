package app.covidstats.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
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
            Title(title = title, textAlign = TextAlign.Center, fontColor = DARK_BLUE)
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
private fun Location(locationName: String, backgroundColor: Color = DARK_GREY_WITH_TRANSPARENCY, onClick: (String) -> Unit) {
    Button(
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
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
                .background(color = if (Model.continents.any {it == locationName}) DARK_GREY_WITH_TRANSPARENCY2 else DARK_GREY_WITH_TRANSPARENCY)
        ) {
            Text(
                text = locationName.uppercase(),
                color = Color.White,
                fontSize = 20.sp,
                letterSpacing = 3.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
            )
        }
    }
}
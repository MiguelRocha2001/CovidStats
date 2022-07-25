package app.covidstats.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.covidstats.error.ServerError
import app.covidstats.model.data.app.*
import app.covidstats.ui.commons.OnServerError

/**
 * Display a list of [locations] and after pressing one, calls [onLocationClick].
 * @param additionalLocations is a list of additional locations to display.
 */
@Composable
fun Locations(
    title: String? = null,
    locations: Locations?,
    onLocationClick: (String) -> Unit,
    additionalComposable: @Composable (() -> Unit)? = null,
    vararg additionalLocations: Pair<String, (String) -> Unit>
) {
    if (locations != null) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (title != null)
                Title(title = title, textAlign = TextAlign.Center)
            when (locations) {
                is LocationsSuccess -> {
                    if (additionalComposable != null) {
                        additionalComposable()
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    val locationsToDisplay = locations.locations
                    OnSuccess(locationsToDisplay, additionalLocations, onLocationClick)
                }
                is LocationsError -> {
                    val error = locations.error
                    if (error is ServerError) {
                        OnServerError()
                    }
                }
                is LocationsLoading -> {
                    OnLoading()
                }
            }
        }
    }
}

@Composable
fun OnLoading() {
    CircularProgressIndicator(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.size(50.dp)
    )
}

@Composable
private fun OnSuccess(
    generalLocations: List<String>,
    specialLocations: Array<out Pair<String, (String) -> Unit>>,
    onLocationClick: (String) -> Unit
) {
    LazyColumn(
        Modifier.fillMaxWidth()
    ) {
        specialLocations.forEach {
            item { Location(it.first, MaterialTheme.colorScheme.secondaryContainer, it.second) }
        }
        generalLocations.forEach { country ->
            item { Location(country, MaterialTheme.colorScheme.surface) { onLocationClick(country) } }
        }
    }
}

@Composable
private fun Location(locationName: String, backgroundColor: Color, onClick: (String) -> Unit) {
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
                .background(
                    color = backgroundColor
                )
        ) {
            Text(
                text = locationName.uppercase(),
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 20.sp,
                letterSpacing = 3.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
            )
        }
    }
}
package app.covidstats.ui

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
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

/**
 * This Composable will generate a lazy list of locations.
 * It will adapt the view weather is in landscape or portrait mode.
 */
@Composable
private fun OnSuccess(
    generalLocations: List<String>,
    specialLocations: Array<out Pair<String, (String) -> Unit>>,
    onLocationClick: (String) -> Unit
) {
    val configuration = LocalConfiguration.current
    LazyColumn(
        Modifier.fillMaxWidth()
    ) {
        specialLocations.forEach {
            item { Location(it.first, MaterialTheme.colorScheme.secondaryContainer, it.second) }
        }

        when (configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                generalLocations.forEachIndexed { index, country ->
                    locationsLandscape(index, country, generalLocations, onLocationClick)
                }
            }
            else -> {
                generalLocations.forEach { country ->
                    item { Location(country, MaterialTheme.colorScheme.surface) { onLocationClick(country) } }
                }
            }
        }
    }
}

private fun LazyListScope.locationsLandscape(
    index: Int,
    country: String,
    locations: List<String>,
    onLocationClick: (String) -> Unit
) {
    if (index % 2 == 0) {
        item {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                LocationLandscape(country, MaterialTheme.colorScheme.surface) {
                    onLocationClick(country)
                }
                if (index + 1 < locations.size)
                    LocationLandscape(locations[index + 1], MaterialTheme.colorScheme.surface) {
                        onLocationClick(locations[index + 1])
                    }
            }
        }
    }
}

@Composable
private fun LocationLandscape(locationName: String, backgroundColor: Color, onClick: (String) -> Unit) {
    LocationButton(locationName, onClick) {
        LocationText(locationName)
    }
}

@Composable
private fun Location(locationName: String, backgroundColor: Color, onClick: (String) -> Unit) {
    LocationButton(locationName, onClick) {
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
            LocationText(locationName)
        }
    }
}

@Composable
private fun LocationButton(locationName: String, onClick: (String) -> Unit, content: @Composable () -> Unit) {
    return  Button(
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        elevation = null,
        onClick = { onClick(locationName) }
    ) { content() }
}

@Composable
private fun LocationText(locationName: String) {
    return Text(
        text = locationName.uppercase(),
        color = MaterialTheme.colorScheme.secondary,
        fontSize = 20.sp,
        letterSpacing = 3.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier
    )
}

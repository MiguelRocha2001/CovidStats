package app.covidstats.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
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
    title: String,
    locations: Locations?,
    onLocationClick: (String) -> Unit,
    additionalComposable: @Composable (() -> Unit)? = null,
    vararg additionalLocations: Pair<String, (String) -> Unit>
) {
    if (locations != null) {
        when (locations) {
            is LocationsSuccess -> {
                if (additionalComposable != null) {
                    additionalComposable()
                    Spacer(modifier = Modifier.height(16.dp))
                }
                val locationsToDisplay = locations.locations
                OnSuccess(title, locationsToDisplay, additionalLocations, onLocationClick)
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
    title: String,
    generalLocations: List<String>,
    specialLocations: Array<out Pair<String, (String) -> Unit>>,
    onLocationClick: (String) -> Unit
) {
    val configuration = LocalConfiguration.current
    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            LocationsLandscape(configuration, title, generalLocations, specialLocations, onLocationClick)
        }
        else -> {
            LocationsPortrait(configuration, title, generalLocations, specialLocations, onLocationClick)
        }
    }
}

@Composable
private fun LocationsLandscape(
    configuration: Configuration,
    title: String,
    locations: List<String>,
    specialLocations: Array<out Pair<String, (String) -> Unit>>,
    onClick: (String) -> Unit
) {
    if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        Row {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Title(title)
            }
            Box(modifier = Modifier.weight(2f)) {
                LocationsListing(configuration, locations, specialLocations, onClick)
            }
        }
    }
}

@Composable
private fun LocationsPortrait(
    configuration: Configuration,
    title: String,
    generalLocations: List<String>,
    specialLocations: Array<out Pair<String, (String) -> Unit>>,
    onClick: (String) -> Unit
) {
    if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
        Column {
            Title(title)
            LocationsListing(configuration, generalLocations, specialLocations, onClick)
        }
    }
}

@Composable
private fun LocationsListing(
    configuration: Configuration,
    locations: List<String>,
    specialLocations: Array<out Pair<String, (String) -> Unit>>,
    onClick: (String) -> Unit
) {
    LazyColumn(
        content = {
            when (configuration.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> {
                    locationsListingLandscape(locations, specialLocations, onClick)
                }
                else -> {
                    locationsListingPortrait(locations, specialLocations, onClick)
                }
            }
        }
    )
}

private fun LazyListScope.locationsListingLandscape(
    locations: List<String>,
    specialLocations: Array<out Pair<String, (String) -> Unit>>,
    onClick: (String) -> Unit
) {

    @Composable
    fun RowScope.MyBox(content: @Composable () -> Unit) {
        val modifier = Modifier.weight(1f)
        val boxContentAlignment = Alignment.Center
        return Box(
            modifier = modifier,
            contentAlignment = boxContentAlignment
        ) {
            content()
        }
    }

    locations.forEachIndexed { index, location ->
        if (index % 2 == 0) {
            item {
                Row {
                    MyBox { Location(location, Color.Transparent, onClick) }
                    if (index + 1 < locations.size)
                        MyBox { Location(locations[index + 1], Color.Transparent, onClick) }
                }
            }
        }
    }
}

private fun LazyListScope.locationsListingPortrait(
    locations: List<String>,
    specialLocations: Array<out Pair<String, (String) -> Unit>>,
    onClick: (String) -> Unit
) {
    locations.forEach { location ->
        item { Location(location, Color.Transparent, onClick) }
    }
}

@Composable
private fun Location(locationName: String, backgroundColor: Color, onClick: (String) -> Unit) {
    LocationButton(locationName, backgroundColor, onClick) {
        LocationText(locationName)
    }
}

@Composable
private fun LocationButton(locationName: String, backgroundColor: Color, onClick: (String) -> Unit, content: @Composable () -> Unit) {
    return Button(
        modifier = Modifier.size(Dp.Unspecified),
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
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

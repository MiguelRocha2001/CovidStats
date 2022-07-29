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
    standardLocations: Pair<Locations, (String) -> Unit>?,
    specialLocations: Pair<Locations, (String) -> Unit>?,
    additionalComposable: @Composable (() -> Unit)? = null,
) {
    if (standardLocations != null) {
        when (val locations = standardLocations.first) {
            is LocationsSuccess -> {
                if (additionalComposable != null) {
                    additionalComposable()
                    Spacer(modifier = Modifier.height(16.dp))
                }
                OnSuccess(
                    title,
                    locations to standardLocations.second,
                    specialLocations as Pair<LocationsSuccess, (String) -> Unit>?, // TODO: remove this cast
                )
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
    standardLocations: Pair<LocationsSuccess, (String) -> Unit>,
    specialLocations: Pair<LocationsSuccess, (String) -> Unit>?,
) {
    val configuration = LocalConfiguration.current
    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            LocationsLandscape(configuration, title, standardLocations, specialLocations)
        }
        else -> {
            LocationsPortrait(configuration, title, standardLocations, specialLocations)
        }
    }
}

@Composable
private fun LocationsLandscape(
    configuration: Configuration,
    title: String,
    standardLocations: Pair<LocationsSuccess, (String) -> Unit>,
    specialLocations: Pair<LocationsSuccess, (String) -> Unit>?,
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
                LocationsListing(configuration, standardLocations, specialLocations)
            }
        }
    }
}

@Composable
private fun LocationsPortrait(
    configuration: Configuration,
    title: String,
    standardLocations: Pair<LocationsSuccess, (String) -> Unit>,
    specialLocations: Pair<LocationsSuccess, (String) -> Unit>?,
) {
    if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
        Column(
            modifier = Modifier.padding(start = PADDING_START)
        ) {
            Title(title)
            LocationsListing(configuration, standardLocations, specialLocations)
        }
    }
}

@Composable
private fun LocationsListing(
    configuration: Configuration,
    standardLocations: Pair<LocationsSuccess, (String) -> Unit>,
    specialLocations: Pair<LocationsSuccess, (String) -> Unit>?,
) {
    val locationBackgroundColor = Color.Transparent
    val specialLocationBackgroundColor = MaterialTheme.colorScheme.inversePrimary
    LazyColumn(
        content = {
            when (configuration.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> {
                    locationsListingLandscape(
                        Triple(standardLocations.first, locationBackgroundColor, standardLocations.second),
                        specialLocations?.let {
                            Triple(it.first, specialLocationBackgroundColor, it.second)
                        }
                    )
                }
                else -> {
                    locationsListingPortrait(
                        Triple(standardLocations.first, locationBackgroundColor, standardLocations.second),
                        specialLocations?.let {
                            Triple(it.first, specialLocationBackgroundColor, it.second)
                        }
                    )
                }
            }
        }
    )
}

private fun LazyListScope.locationsListingLandscape(
    standardLocations: Triple<LocationsSuccess, Color, (String) -> Unit>,
    specialLocations: Triple<LocationsSuccess, Color, (String) -> Unit>?,
) {
    @Composable
    fun RowScope.MyBox(content: @Composable () -> Unit) {
        val modifier = Modifier.weight(1f)
        val boxContentAlignment = Alignment.CenterStart
        return Box(
            modifier = modifier,
            contentAlignment = boxContentAlignment
        ) {
            content()
        }
    }

    val standardLocationsSize = standardLocations.first.locations.size
    val specialLocationsSize = specialLocations?.first?.locations?.size ?: 0
    val locationsSize = standardLocationsSize + specialLocationsSize
    repeat(locationsSize) {
        if (it % 2 == 0) {
            val firstLocation = if (specialLocations != null && it < specialLocations.first.locations.size) {
                Triple(specialLocations.first.locations[it], specialLocations.second, specialLocations.third)
            } else {
                Triple(standardLocations.first.locations[it - specialLocationsSize], standardLocations.second, standardLocations.third)
            }
            val secondLocation =
                if(it + 1 < locationsSize) {
                    if (specialLocations != null && it + 1 < specialLocations.first.locations.size) {
                        Triple(specialLocations.first.locations[it + 1], specialLocations.second, specialLocations.third)
                    } else {
                        Triple(standardLocations.first.locations[it - specialLocationsSize + 1], standardLocations.second, standardLocations.third)
                    }
                } else null

            item {
                Row {
                    MyBox { Location(firstLocation.first, firstLocation.second, firstLocation.third) }
                    if (secondLocation != null) {
                        MyBox { Location(secondLocation.first, secondLocation.second, firstLocation.third) }
                    }
                }
            }
        }
    }
}

private fun LazyListScope.locationsListingPortrait(
    standardLocations: Triple<LocationsSuccess, Color, (String) -> Unit>,
    specialLocations: Triple<LocationsSuccess, Color, (String) -> Unit>?,
) {
    specialLocations?.first?.locations?.forEach { location ->
        item { Location(location, specialLocations.second, specialLocations.third) }
    }
    standardLocations.first.locations.forEach { location ->
        item { Location(location, standardLocations.second, standardLocations.third) }
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
        shape = Shapes.Full,
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
        lineHeight = 28.sp,
        modifier = Modifier
    )
}

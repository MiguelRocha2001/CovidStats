package app.covidstats.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import app.covidstats.R
import app.covidstats.model.Option

@Composable
fun getIcon(option: Option): ImageVector {
    return when (option) {
        Option.CONTINENT -> ImageVector.vectorResource(id = R.drawable.ic_australia)
        Option.WORLD -> ImageVector.vectorResource(id = R.drawable.ic_globe)
        Option.FAVORITES -> Icons.Filled.Favorite
        Option.INFO -> Icons.Filled.Info
        Option.EXIT -> Icons.Filled.ExitToApp
    }

}
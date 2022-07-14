package app.covidstats.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun TopAppBar(
    toggleMenu: () -> Unit
) {
    SmallTopAppBar(
        title = {
            Text(text = "Covid Stats")
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(),
        navigationIcon = {
            IconButton(onClick = { toggleMenu() }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = null
                )
            }
        }
    )
}
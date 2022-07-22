package app.covidstats.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun TopAppBar(
    toggleMenu: () -> Unit,
    searchAction: (() -> Unit)? = null
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
                    contentDescription = "Toggle Menu"
                )
            }
        },
        actions = {
            if (searchAction != null) {
                ActionOptions(searchAction)
            }
        }
    )
}

@Composable
private fun ActionOptions(searchAction: () -> Unit) = Row {
    IconButton(onClick = searchAction) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null
        )
    }
}
@file:OptIn(ExperimentalMaterial3Api::class)

package app.covidstats.ui

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope

enum class Option {
    HOME, FAVORITES, WORLD, CONTINENT, NEWS, INFO, EXIT
}

@Composable
fun NavigationMenu(
    scope: CoroutineScope,
    drawerState: DrawerState,
    onOptionClick: (Option) -> Unit,
    content: @Composable () -> Unit,
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { DrawerContent(onOptionClick) },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                content()
            }
        }
    )
}

@Composable
fun DrawerContent(onOptionClick: (Option) -> Unit) {
        var selectedItem by remember { mutableStateOf(0) }
        val items = listOf("Home", "Favorites", "World", "Continent", "News", "Info", "Exit")
        val icons = listOf(Icons.Filled.Home, Icons.Filled.Favorite, Icons.Filled.Lock, Icons.Filled.Lock, Icons.Filled.Lock, Icons.Filled.Lock, Icons.Filled.Lock)
        items.forEachIndexed { index, item ->
            NavigationDrawerItem(
                label = { Text(text = item) },
                selected = index == selectedItem,
                icon = {
                    Icon(
                        imageVector = icons[index],
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                },
                onClick = { onOptionClick(Option.valueOf(item.toUpperCase())) },
            )
        }
}
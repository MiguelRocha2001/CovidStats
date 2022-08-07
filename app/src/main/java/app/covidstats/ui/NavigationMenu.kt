@file:OptIn(ExperimentalMaterial3Api::class)

package app.covidstats.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.covidstats.model.data.app.Option

@Composable
fun NavigationMenu(
    selected: Option,
    drawerState: DrawerState,
    onOptionClick: (Option) -> Unit,
    content: @Composable () -> Unit,
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { DrawerContent(selected, onOptionClick) },
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
fun DrawerContent(selected: Option, onOptionClick: (Option) -> Unit) {
        var selectedItem by remember { mutableStateOf(selected.ordinal) }
        Option.values().forEachIndexed { index, option ->
            NavigationDrawerItem(
                label = { Text(text = option.name.capitalizeText()) },
                selected = index == selectedItem,
                icon = {
                    Icon(
                        imageVector = getIcon(option),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                },
                onClick = {
                    onOptionClick(Option.valueOf(option.name.toUpperCase()))
                    selectedItem = option.ordinal
                          },
            )
        }
}
package app.covidstats.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import app.covidstats.MainActivity
import com.plcoding.material3app.ui.theme.Material3AppTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainWindow(mainActivity: MainActivity) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    Material3AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                topBar = {
                    SmallTopAppBar(
                        title = {
                            Text(text = "Covid Stats")
                        },
                        colors = TopAppBarDefaults.smallTopAppBarColors(),
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = null
                                )
                            }
                        }
                    )
                },
                floatingActionButton = {
                    FloatingActionButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            ) { values ->
                Column(Modifier.padding(values)) {
                    Nav(
                        scope,
                        drawerState,
                        onOpenClick = { scope.launch { drawerState.open() } },
                        onCloseClick = { scope.launch { drawerState.close() } }
                    )
                    // Navigation(mainActivity)
                }
            }
        }
    }
}
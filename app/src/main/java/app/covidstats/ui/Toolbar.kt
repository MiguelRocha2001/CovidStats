package app.covidstats.ui
/*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private val showDialog = mutableStateOf(false)

@Composable
fun ToolBarSample() {
    Column {
        TopAppBar(
            elevation = 4.dp,
            title = {
                Text("Android Toolbar Bar sample")
            },
            backgroundColor = MaterialTheme.colors.primarySurface,
            navigationIcon = {
                IconButton(onClick = {/* Do Something*/ }) {
                    Icon(Icons.Filled.ArrowBack, null)
                }
            }, actions = {
                IconButton(onClick = {/* Do Something*/ }) {
                    Icon(Icons.Filled.Share, null)
                }
                IconButton(onClick = { showDialog.value = true }) {
                    Icon(Icons.Filled.Settings, null)
                }
            })

        Text("Hello World")

    }
}

@Composable
fun showSettingDropDownMenu() {
    var expanded by remember { mutableStateOf(true) }
    val items = listOf(
        "Add", "Edit", "Save", "Delete"
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 20.dp)
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(100.dp)
                .background(
                    Color.White
                )
        ) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    showDialog.value = false
                }) {
                    Text(text = s)
                    if (index < 3) {
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }


    }
}

@Preview
@Composable
fun DefaultPreview() {
    showSettingDropDownMenu()
}


 */
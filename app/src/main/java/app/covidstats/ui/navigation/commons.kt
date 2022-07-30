package app.covidstats.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

internal fun getSearchTextField(onValueChange: (String) -> Unit): @Composable () -> Unit = {
    var text by remember { mutableStateOf("") }
    TextField(
        value = text,
        onValueChange = ({ location ->
            text = location
            onValueChange(location)
        }),
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = null
            )
        },
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colorScheme.primary,
            containerColor = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            disabledTextColor = Color.Transparent
        )
    )
}
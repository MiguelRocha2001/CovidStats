package app.covidstats.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun Favorites(
    @PreviewParameter(UserPreviewParameterProvider::class) favoriteLocations: List<String>,
    onClick: (String) -> Unit = {}
) {
    val textModifier = Modifier.padding(16.dp)
    Column(modifier = textModifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Title(title = "Favorites", textAlign = TextAlign.Center)
        if (favoriteLocations.isEmpty()) {
            Text("No favorites yet", fontSize = 24.sp, textAlign = TextAlign.Center)
        } else {
            favoriteLocations.forEach {
                OutlinedButton(
                    modifier = Modifier.width(350.dp).padding(bottom = 16.dp),
                    onClick = { onClick(it) },
                    shape = CircleShape,
                    elevation = ButtonDefaults.elevation(8.dp)
                ) {
                    Text(
                        text = it,
                        fontSize = 14.sp,
                        letterSpacing = 1.5.sp,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }
    }
}

class UserPreviewParameterProvider : PreviewParameterProvider<List<String>> {
    override val values = sequenceOf(
        listOf("London", "New York", "Paris")
    )
}

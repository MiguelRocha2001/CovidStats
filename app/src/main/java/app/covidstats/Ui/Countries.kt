package app.covidstats.Ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.covidstats.model.continents.Continent
import app.covidstats.model.continents.Europe
import app.covidstats.model.continents.NorthAmerica

@Composable
fun DisplayCountries(continent: Continent, onClick: (String) -> Unit) {
    LazyColumn(Modifier.fillMaxWidth()) {
        continent.countries.forEach { country ->
            item(country.string) {
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    onClick = { onClick(country.string) }
                ) {
                    Row(
                        verticalAlignment = CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .height(60.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            //text = country.string.replaceFirstChar { it.uppercaseChar() },
                            text = country.string.uppercase(),
                            fontSize = 20.sp,
                            letterSpacing = 3.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                        )
                    }
                }
                /*Button(
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    modifier = Modifier
                        .padding(Dp(0f))
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(Dp(0f)),
                    onClick = { onClick(country.string) }
                ) {
                    painterResource(id = country.imageRes).apply {
                        Image(
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(10.dp),
                            painter = this,
                            contentDescription = "$country Flag",
                        )
                    }
                }

                 */
            }
        }
    }
}

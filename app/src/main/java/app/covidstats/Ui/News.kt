package app.covidstats.Ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.covidstats.model.data.news.Item

@Composable
fun CovidNews(news: List<Item>?) {
    news?.apply {
        Column() {
            Text(
                text = "News",
                fontSize = 31.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(top = 15.dp),
            )
            Spacer(modifier = Modifier.height(20.dp))
            news.forEach { item ->
                Column(modifier = Modifier
                    .padding(10.dp)
                    .border(2.dp, GREY, RoundedCornerShape(10.dp))
                    .padding(15.dp)) {
                    Text(text = item.title.short, fontSize = 18.sp, modifier = Modifier.padding(bottom = 10.dp))
                    Text(text = item.pubDate)
                    Text(text = item.links.shortUrl)
                }
            }
        }
    }
}
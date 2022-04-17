package app.covidstats.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.covidstats.model.data.news.Item

@Composable
fun CovidNews(news: List<Item>?) {
    news?.apply {
        Column() {
            Title(title = "News")
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
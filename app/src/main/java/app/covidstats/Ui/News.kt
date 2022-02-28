package app.covidstats.Ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
            news.forEach { item ->
                Column(modifier = Modifier.padding(vertical = 10.dp)) {
                    Text(text = item.title.short, fontSize = 20.sp, modifier = Modifier.padding(vertical = 5.dp))
                    Text(text = item.pubDate)
                    Text(text = item.links.shortUrl)
                }
            }
        }
    }
}
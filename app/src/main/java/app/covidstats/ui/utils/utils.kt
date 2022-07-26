package app.covidstats.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import java.util.*

fun String.capitalizeText(): String {
    val str = this
    val wordsBeforeCapitalized = str.split(" ")
    val wordsAfterCapitalized = wordsBeforeCapitalized.map { word -> word.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
    }}
    return wordsAfterCapitalized.joinToString (separator = " ")
}
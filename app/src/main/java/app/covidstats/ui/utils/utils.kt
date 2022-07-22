package app.covidstats.ui

import java.util.*

fun String.capitalizeText(): String {
    val str = this
    val wordsBeforeCapitalized = str.split(" ")
    val wordsAfterCapitalized = wordsBeforeCapitalized.map { word -> word.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
    }}
    return wordsAfterCapitalized.joinToString (separator = " ")
}
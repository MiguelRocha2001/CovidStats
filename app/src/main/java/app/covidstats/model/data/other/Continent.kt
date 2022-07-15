package app.covidstats.model.data.other

enum class Continent {
    EUROPE, AFRICA, NORTH_AMERICA, SOUTH_AMERICA, ASIA, AUSTRALIA_OCEANIA
}

fun String.toContinent(): Continent? =
    when (this.replaceSeparatorAndToLowercase()) {
        "africa" -> Continent.AFRICA
        "asia" -> Continent.ASIA
        "europe" -> Continent.EUROPE
        "north america" -> Continent.NORTH_AMERICA
        "australia oceania" -> Continent.AUSTRALIA_OCEANIA
        "south america" -> Continent.SOUTH_AMERICA
        else -> null
    }

fun Continent.formattedName(): String =
    name.formattedName()

fun String.formattedName(): String {
    return replaceSeparatorAndToLowercase()
        .split(" ")
        .map { word -> word.replaceFirstChar { it.uppercase() } }
        .joinToString(" ") { it }
}

/**
 * Replaces all separators with spaces and lowercase the string.
 */
fun String.replaceSeparatorAndToLowercase() =
    replace("-", " ").replace("_", " ").lowercase()
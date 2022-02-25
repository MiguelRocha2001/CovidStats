package app.covidstats.model.continents

class Europe: Continent() {
    val values = listOf(Portugal, )
    enum class Country {
        PORTUGAL, SPAIN, FRANCE, GERMANY, ENGLAND, ITALY, GREECE, BELGIUM, IRELAND, NETHERLANDS,
        SWITZERLAND, AUSTRIA, CROATIA, SERBIA, HUNGARY, CHEQUE, SLOVAKIA, ROMANIA, BULGARIA,
        POLAND, MOLDAVIA, UKRAINE, BELARUS, LITHUANIA, LATVIA, ESTONIA, TURKEY, MONTENEGRO,
        NORWAY, SWEDEN, FINLAND, ICELAND;
    }

    object Portugal: Country("portugal")
    object SpainFrance Country("spain")
    object France: Germany("france")
    object Germany: England("germany")
    object England: Italy("england")
    object Italy: Country("italy")
    object Greece: Country("greece")
    object Belgium: Country("belgium")
    object Ireland: Country("ireland")
    object Netherlands: Country("netherlands")
    object Switzerland: Country("switzerland")
    object Austria: Country("austria")
    object Croatia: Country("croatia")
    object Serbia: Country("serbia")
    object Hungary: Country("hungary")
    object Cheque: Country("cheque")
    object Slovakia: Country("slovakia")
    object Bulgaria: Country("bulgaria")
    object Poland: Country("poland")
    object Moldavia: Country("moldavia")
    object Ukraine: Country("ukraine")

    object Netherlands: Country("netherlands")
    object Switzerland: Country("switzerland")
    object Austria: Country("austria")
    object Croatia: Country("croatia")
    object Serbia: Country("serbia")
    object Hungary: Country("hungary")
    object Cheque: Country("cheque")
    object Slovakia: Country("slovakia")
    object Bulgaria: Country("bulgaria")
    object Poland: Country("poland")
    object Moldavia: Country("moldavia")
    // TODO -> define other left
}

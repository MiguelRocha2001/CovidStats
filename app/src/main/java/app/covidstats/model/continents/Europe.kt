package app.covidstats.model.continents

import app.covidstats.R

class Europe: Continent("europe") {
    override val imageRes = R.drawable.europe

    override val countries = listOf(Portugal(), Spain, France, Germany, England, Italy, Greece, Belgium, Ireland, Netherlands, Switzerland, Austria, Croatia,
    Serbia, Hungary, Cheque, Slovakia, Bulgaria, Poland, Moldavia, Ukraine, Belarus, Lithuania, Latvia, Estonia, Turkey, Montenegro, Norway,
    Sweden, Finland, Iceland)

    class Portugal(override val imageRes: Int = R.drawable.portugal) : Country("portugal")
    class Spain(override val imageRes: Int = R.drawable.Spain): Country("spain")
    class France(override val imageRes: Int = R.drawable.France): Country("france")
    class Germany(override val imageRes: Int = R.drawable.Germany): Country("germany")
    object Germany: Country("germany")
    object England: Country("england")
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
    object Belarus: Country("belarus")
    object Lithuania: Country("lithuania")
    object Latvia: Country("latvia")
    object Estonia: Country("estonia")
    object Turkey: Country("turkey")
    object Montenegro: Country("montenegro")
    object Norway: Country("norway")
    object Sweden: Country("sweden")
    object Finland: Country("finland")
    object Iceland: Country("iceland")
}

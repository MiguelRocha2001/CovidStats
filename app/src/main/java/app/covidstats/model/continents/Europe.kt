package app.covidstats.model.continents

import app.covidstats.R

class Europe: Continent("europe") {
    override val imageRes = R.drawable.europe

    override val countries = listOf(Portugal(), Spain(), France(), Germany(), England(), Italy(), Greece(), Belgium(), Ireland(), Netherlands(),
        /*Switzerland, Austria, Croatia,
        Serbia, Hungary, Cheque, Slovakia, Bulgaria, Poland, Moldavia, Ukraine, Belarus, Lithuania, Latvia, Estonia, Turkey, Montenegro, Norway,
        Sweden, Finland, Iceland
         */
    )

    class Portugal(override val imageRes: Int = R.drawable.portugal) : Country("portugal")
    class Spain(override val imageRes: Int = R.drawable.spain): Country("spain")
    class France(override val imageRes: Int = R.drawable.france): Country("france")
    class Germany(override val imageRes: Int = R.drawable.germany): Country("germany")
    class England(override val imageRes: Int = R.drawable.united_kingdom): Country("england")
    class Italy(override val imageRes: Int = R.drawable.italy): Country("italy")
    class Greece(override val imageRes: Int = R.drawable.greece): Country("greece")
    class Belgium(override val imageRes: Int = R.drawable.belgium): Country("belgium")
    class Ireland(override val imageRes: Int = R.drawable.ireland): Country("ireland")
    class Netherlands(override val imageRes: Int = R.drawable.netherlands): Country("netherlands")
    /*object Switzerland: Country("switzerland")
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
     */
}

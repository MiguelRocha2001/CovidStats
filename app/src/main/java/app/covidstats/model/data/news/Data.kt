package app.covidstats.model.data.news

data class Data(
    val id: String,
    val name: String,
    val pk_0_250_0: Pk02500,
    val pk_12_0_0: Pk1200,
    val pk_18_0_0: Pk1800,
    val pk_18_0_1: Pk1801,
    val pk_19_0_0: Pk1900,
    val pk_19_0_1: Pk1901,
    val pk_30_61_2: Pk30612,
    val pk_33_517_1: Pk335171,
    val pk_33_581_2: Pk335812,
    val pk_33_768_1: Pk337681,
    val pk_33_805_2: Pk338052,
    val pk_33_807_2: Pk338072,
    val pk_33_848_0: Pk338480,
    val pk_33_849_0: Pk338490,
    val pk_3_1_0: Pk310,
    val pk_4_0_1: Pk401,
    val pk_5_0_1: Pk501,
    val series: List<List<Double>>,
    val short_name: String,
    val symbol: String,
    val type: Int
)
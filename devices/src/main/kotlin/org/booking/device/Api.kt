package org.booking.device

private const val gsmDropCount = "GSM".length + 1
private const val umtsDropCount = "UMTS".length + 1
private const val lteDropCount = "LTE".length + 1

data class DeviceSearchApiItem(
    val Success: Int,
    val data: SearchData
)

data class SearchData(
    val results: List<SearchResults>
)

data class SearchResults(
    val score: Float,
    val _meta: Meta
)

data class Meta(
    val id: String
)

data class DeviceApiItem(
    val Success: Int,
    val data: ProductList
)

data class ProductList(
    val product: List<ProductListItem>
)

fun DeviceApiItem.toDevice() = this.data.product.first().let { product ->
    Device(
        name = product.Product.Model,
        technology = "???", // TODO
        secondGen = product.Inside._2g().joinToString(","),
        thirdGen = product.Inside._3g().joinToString(","),
        fourthGen = product.Inside._4g().joinToString(","),
    )
}

data class ProductListItem(
    val Inside: Inside,
    val Product: Product,
)

data class Product(
    val Brand: String,
    val Model: String,
)

data class Inside(
    val Battery: Map<String, String>,
    val Cellular: Map<String, String>
) {
    private val allBands = Cellular.entries
        .filter { (k, _) -> k.contains("SIM") && k.contains("Frequencies") }
        .filter { it.value.isNotEmpty() }
        .joinToString(";") { it.value }
        .replace("),", ");")
        .replace("bands", ";")
        .split(";")
        .map { it.trim() }
        .filter { it.isNotEmpty() }

    fun _2g(): List<String> {
        return allBands.filter { it.contains("GSM") }.map { it.drop(gsmDropCount) }
    }

    fun _3g(): List<String> {
        return allBands.filter { it.contains("UMTS") }.map { it.drop(umtsDropCount) }
    }

    fun _4g(): List<String> {
        return allBands.filter { it.contains("LTE") }.map { it.drop(lteDropCount) }
    }
}
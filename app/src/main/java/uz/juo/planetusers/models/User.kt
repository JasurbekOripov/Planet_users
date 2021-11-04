package uz.juo.planetusers.models

import java.io.Serializable

data class User(
    var `data`: List<Data> = arrayListOf(),
    val page: Int=0,
    val per_page: Int=0,
    val support: Support=Support("",""),
    val total: Int=0,
    val total_pages: Int=0
):Serializable
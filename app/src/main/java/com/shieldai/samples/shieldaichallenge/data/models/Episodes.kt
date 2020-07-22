package com.shieldai.samples.shieldaichallenge.data.models

import org.json.JSONObject

//data class Episode(
//    val jsonObject: JSONObject
//){
//    val _links: Links = jsonObject.optJSONObject("_links"),
//    val airdate: String,
//    val airstamp: String,
//    val airtime: String,
//    val id: Int,
//    val image: Image,
//    val name: String,
//    val number: Int,
//    val runtime: Int,
//    val season: Int,
//    val summary: String,
//    val url: String
//}

data class Links(
    val self: Self
)

data class Image(
    val medium: String,
    val original: String
)

data class Self(
    val href: String
)
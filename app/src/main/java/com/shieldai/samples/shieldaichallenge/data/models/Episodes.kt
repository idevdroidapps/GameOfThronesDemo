package com.shieldai.samples.shieldaichallenge.data.models

data class Episode(
    val airdate: String,
    val airstamp: String,
    val airtime: String,
    val id: Int,
    val image: Image,
    val name: String,
    val number: Int,
    val runtime: Int,
    val season: Int,
    val summary: String,
    val url: String
)

data class Image(
    val medium: String,
    val original: String
)
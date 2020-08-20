package com.example.samples.gameofthrones.data.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Episode(
  @ColumnInfo(name = "airdate") val airdate: String?,
  @ColumnInfo(name = "airstamp") val airstamp: String?,
  @ColumnInfo(name = "airtime") val airtime: String?,
  @PrimaryKey @ColumnInfo(name = "id") val id: Int,
  @Embedded val image: Image?,
  @ColumnInfo(name = "name") val name: String?,
  @ColumnInfo(name = "number") val number: Int?,
  @ColumnInfo(name = "runtime") val runtime: Int?,
  @ColumnInfo(name = "season") val season: Int?,
  @ColumnInfo(name = "summary") val summary: String?,
  @ColumnInfo(name = "url") val url: String?,
  @Embedded val _links: Links?
)
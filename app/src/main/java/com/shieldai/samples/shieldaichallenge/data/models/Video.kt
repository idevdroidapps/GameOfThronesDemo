package com.shieldai.samples.shieldaichallenge.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Video(
  @ColumnInfo(name = "description") val description: String?,
  @ColumnInfo(name = "sources") val sources: List<String>?,
  @ColumnInfo(name = "subtitle") val subtitle: String?,
  @ColumnInfo(name = "thumb") val thumb: String?,
  @ColumnInfo(name = "title") val title: String?
)
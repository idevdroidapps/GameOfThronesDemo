package com.shieldai.samples.shieldaichallenge.data.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Video(
  @PrimaryKey @ColumnInfo(name = "videoId") var videoId: Int,
  @ColumnInfo(name = "description") val description: String?,
  @Embedded val sources: Sources?,
  @ColumnInfo(name = "subtitle") val subtitle: String?,
  @ColumnInfo(name = "thumb") val thumb: String?,
  @ColumnInfo(name = "title") val title: String?
)
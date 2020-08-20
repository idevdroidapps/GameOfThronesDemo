package com.example.samples.gameofthrones.data.models

import androidx.room.ColumnInfo

data class Self(
  @ColumnInfo(name = "href") val href: String?
)
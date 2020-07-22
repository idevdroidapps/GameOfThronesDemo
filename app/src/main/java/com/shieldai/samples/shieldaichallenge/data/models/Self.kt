package com.shieldai.samples.shieldaichallenge.data.models

import androidx.room.ColumnInfo

data class Self(
  @ColumnInfo(name = "href") val href: String?
)
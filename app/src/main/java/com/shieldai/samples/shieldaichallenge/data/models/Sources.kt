package com.shieldai.samples.shieldaichallenge.data.models

import androidx.room.ColumnInfo

data class Sources(
  @ColumnInfo(name = "sources") val sources: List<String>?
)
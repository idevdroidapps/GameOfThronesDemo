package com.shieldai.samples.shieldaichallenge.data.models

import androidx.room.Embedded


data class Links(
  @Embedded val self: Self?
)
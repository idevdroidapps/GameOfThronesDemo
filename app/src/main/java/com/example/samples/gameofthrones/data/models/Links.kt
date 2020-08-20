package com.example.samples.gameofthrones.data.models

import androidx.room.Embedded

data class Links(
  @Embedded val self: Self?
)
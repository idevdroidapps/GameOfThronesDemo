package com.shieldai.samples.shieldaichallenge.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RoomConverters {

  @TypeConverter
  fun fromStringList(value: List<String>): String {
    val gson = Gson()
    val type = object : TypeToken<List<String>>() {}.type
    return gson.toJson(value, type)
  }

  @TypeConverter
  fun toStringList(value: String): List<String> {
    val gson = Gson()
    val type = object : TypeToken<List<String>>() {}.type
    return gson.fromJson(value, type)
  }

}
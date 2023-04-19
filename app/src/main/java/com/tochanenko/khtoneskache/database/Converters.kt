package com.tochanenko.khtoneskache.database

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class Converters {
    @TypeConverter
    fun fromStringList(value: List<String>) = Json.encodeToString(value)

    @TypeConverter
    fun toStringList(value: String) = Json.decodeFromString<List<String>>(value)

    @TypeConverter
    fun fromIntList(value: List<Int>) = Json.encodeToString(value)

    @TypeConverter
    fun toIntList(value: String) = Json.decodeFromString<List<Int>>(value)
}
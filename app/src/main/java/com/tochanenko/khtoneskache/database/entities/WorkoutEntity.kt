package com.tochanenko.khtoneskache.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout")
data class WorkoutEntity (
    @PrimaryKey
    var id: Long = 0,
    var name: String = "",
    var time: Long = 0
)
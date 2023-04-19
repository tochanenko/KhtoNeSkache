package com.tochanenko.khtoneskache.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout")
data class WorkoutEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var exerciseList: List<Int>,
    var time: Long
)
package com.tochanenko.khtoneskache.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout")
data class WorkoutEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var exerciseList: List<String>,
    var time: Long
)
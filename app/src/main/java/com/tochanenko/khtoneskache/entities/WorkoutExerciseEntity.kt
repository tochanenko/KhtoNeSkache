package com.tochanenko.khtoneskache.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout-exercise")
data class WorkoutExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var exerciseId: Int,
    var duration: Int,
    var times: Int,
    var time: Long
)
package com.tochanenko.khtoneskache.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise-set")
data class ExerciseSetEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var exerciseId: Int,
    var exerciseName: String,
    var exerciseDescription: String,
    var duration: Int = 0,
    var times: Int = 0,
    var time: Long = 0
)
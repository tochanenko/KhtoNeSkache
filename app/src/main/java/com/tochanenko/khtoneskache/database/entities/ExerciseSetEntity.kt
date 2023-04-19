package com.tochanenko.khtoneskache.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise-set")
data class ExerciseSetEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var exerciseId: Int,
    var duration: Int,
    var times: Int,
    var time: Long
)
package com.tochanenko.khtoneskache.database.entities

data class WorkoutWithExercisesEntity(
    val workout: WorkoutEntity,
    val exercises: List<ExerciseSetEntity>
)

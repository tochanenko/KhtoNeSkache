package com.tochanenko.khtoneskache.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.tochanenko.khtoneskache.database.entities.ExerciseSetEntity
import com.tochanenko.khtoneskache.database.entities.WorkoutWithExercisesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseSetDao {
    @Insert
    suspend fun insert(exerciseSetEntity: ExerciseSetEntity)

    @Insert
    suspend fun insertAll(exerciseSetEntities: List<ExerciseSetEntity>)

    @Update
    suspend fun update(exerciseSetEntity: ExerciseSetEntity)

    @Delete
    suspend fun delete(exerciseSetEntity: ExerciseSetEntity)

    @Query("SELECT * FROM `exercise-set`")
    fun fetchAllExerciseSets(): Flow<List<ExerciseSetEntity>>

    @Query("SELECT * FROM `exercise-set` WHERE workoutId = :id")
    fun fetchExercisesForWorkout(id: Long): Flow<List<ExerciseSetEntity>>
}
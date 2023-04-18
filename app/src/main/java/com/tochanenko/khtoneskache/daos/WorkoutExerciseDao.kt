package com.tochanenko.khtoneskache.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.tochanenko.khtoneskache.entities.WorkoutExerciseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutExerciseDao {
    @Insert
    suspend fun insert(workoutExerciseEntity: WorkoutExerciseEntity)

    @Update
    suspend fun update(workoutExerciseEntity: WorkoutExerciseEntity)

    @Delete
    suspend fun delete(workoutExerciseEntity: WorkoutExerciseEntity)

    @Query("SELECT * FROM `workout-exercise`")
    fun fetchAllWorkoutExercises(): Flow<List<WorkoutExerciseEntity>>
}
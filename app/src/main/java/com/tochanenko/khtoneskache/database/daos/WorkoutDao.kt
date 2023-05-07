package com.tochanenko.khtoneskache.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.tochanenko.khtoneskache.database.entities.WorkoutEntity
import com.tochanenko.khtoneskache.database.entities.WorkoutWithExercisesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {
    @Insert
    suspend fun insert(workoutEntity: WorkoutEntity)

    @Upsert
    suspend fun upsert(workoutEntity: WorkoutEntity)

    @Update
    suspend fun update(workoutEntity: WorkoutEntity)

    @Delete
    suspend fun delete(workoutEntity: WorkoutEntity)

    @Query("SELECT * FROM workout")
    fun fetchAllWorkouts(): Flow<List<WorkoutEntity>>

    @Query("SELECT * FROM workout WHERE id=:workoutId LIMIT 1")
    fun getById(workoutId: Long): Flow<WorkoutEntity>
}
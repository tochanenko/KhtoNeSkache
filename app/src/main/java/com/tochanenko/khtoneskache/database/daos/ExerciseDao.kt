package com.tochanenko.khtoneskache.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.tochanenko.khtoneskache.database.entities.ExerciseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Insert
    suspend fun insert(exerciseEntity: ExerciseEntity)

    @Update
    suspend fun update(exerciseEntity: ExerciseEntity)

    @Delete
    suspend fun delete(exerciseEntity: ExerciseEntity)

    @Query("SELECT * FROM exercise")
    fun fetchAllExercises(): Flow<List<ExerciseEntity>>
}
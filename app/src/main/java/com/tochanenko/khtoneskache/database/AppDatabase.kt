package com.tochanenko.khtoneskache.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tochanenko.khtoneskache.database.daos.ExerciseDao
import com.tochanenko.khtoneskache.database.daos.WorkoutDao
import com.tochanenko.khtoneskache.database.daos.WorkoutExerciseDao
import com.tochanenko.khtoneskache.database.entities.ExerciseEntity
import com.tochanenko.khtoneskache.database.entities.WorkoutEntity
import com.tochanenko.khtoneskache.database.entities.WorkoutExerciseEntity

@Database(
    entities = [
        ExerciseEntity::class,
        WorkoutEntity::class,
        WorkoutExerciseEntity::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun exerciseDao(): ExerciseDao
    abstract fun workoutDao(): WorkoutDao
    abstract fun workoutExerciseDao(): WorkoutExerciseDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_database"
                    ).fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}
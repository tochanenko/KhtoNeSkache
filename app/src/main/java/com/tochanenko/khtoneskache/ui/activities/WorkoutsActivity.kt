package com.tochanenko.khtoneskache.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.tochanenko.khtoneskache.KhtoNeSkacheApp
import com.tochanenko.khtoneskache.database.entities.ExerciseSetEntity
import com.tochanenko.khtoneskache.databinding.ActivityWorkoutsBinding
import com.tochanenko.khtoneskache.database.entities.WorkoutWithExercisesEntity
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class WorkoutsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWorkoutsBinding
    private var workouts = arrayListOf<WorkoutWithExercisesEntity>()
    private var exercises = arrayListOf<ExerciseSetEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val workoutDao = (application as KhtoNeSkacheApp).db.workoutDao()
        val exerciseSetDao = (application as KhtoNeSkacheApp).db.workoutExerciseDao()

        Log.e("WKLOG", "LIST WORKOUTS ACTIVITY")

        lifecycleScope.launch {

            workoutDao.fetchAllWorkouts().map { workoutList ->
                workoutList.map {
                    async {
                        exerciseSetDao.fetchExercisesForWorkout(it.id).first()
                    }
                }.awaitAll()
            }.collect { workoutsNewList ->
                workoutsNewList.forEach { a -> exercises.addAll(a) }

                workoutDao.fetchAllWorkouts().collect { workoutList ->
                    workoutList.forEach { workout ->
                        workouts.add(
                            WorkoutWithExercisesEntity(
                                workout,
                                exercises.filter { it.workoutId == workout.id }
                            )
                        )
                    }

                    Log.e("WKLOG", "All fetched info:")
                    workouts.forEach {
                        Log.e("WKLOG", "Workout: ${it.workout.name}")
                        it.exercises.forEach { el ->
                            Log.e(
                                "WKLOG",
                                "\t${el.exerciseName} : ${el.duration} / ${el.times}"
                            )
                        }
                    }
                }
            }
        }
    }
}
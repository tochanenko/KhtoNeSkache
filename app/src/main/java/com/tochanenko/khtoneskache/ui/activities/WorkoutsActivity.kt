package com.tochanenko.khtoneskache.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tochanenko.khtoneskache.KhtoNeSkacheApp
import com.tochanenko.khtoneskache.R
import com.tochanenko.khtoneskache.database.daos.ExerciseSetDao
import com.tochanenko.khtoneskache.database.daos.WorkoutDao
import com.tochanenko.khtoneskache.database.entities.ExerciseSetEntity
import com.tochanenko.khtoneskache.databinding.ActivityWorkoutsBinding
import com.tochanenko.khtoneskache.database.entities.WorkoutWithExercisesEntity
import com.tochanenko.khtoneskache.ui.ActivityResultCode
import com.tochanenko.khtoneskache.ui.adapters.WorkoutAdapter
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString

class WorkoutsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWorkoutsBinding
    private var workouts = arrayListOf<WorkoutWithExercisesEntity>()
    private var exercises = arrayListOf<ExerciseSetEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView(workouts)
        fetchWorkouts()
    }

    private fun setupRecyclerView(
        workouts: ArrayList<WorkoutWithExercisesEntity>
    ) {
        val workoutAdapter = WorkoutAdapter(
            workouts,
            deleteListener = { deleteId -> deleteWorkout(workouts[deleteId].workout.id) },
            editListener = { editId -> editWorkout(editId) }
        )

        binding.rvWorkouts.layoutManager = LinearLayoutManager(this)
        binding.rvWorkouts.adapter = workoutAdapter
    }

    private fun editWorkout(id: Int) {
        val intent = Intent(this, WorkoutAddUpdateActivity::class.java)
        intent.putExtra("WORKOUT_ID", workouts[id].workout.id)
        startActivity(intent)
    }

    private fun fetchWorkouts() {
        val workoutDao = (application as KhtoNeSkacheApp).db.workoutDao()
        val exerciseSetDao = (application as KhtoNeSkacheApp).db.workoutExerciseDao()

        lifecycleScope.launch {
            workoutDao.fetchAllWorkouts().map { workoutList ->
                workoutList.map {
                    async {
                        exerciseSetDao.fetchExercisesForWorkout(it.id).first()
                    }
                }.awaitAll()
            }.collect { workoutsNewList ->
                workoutDao.fetchAllWorkouts().collect { workoutList ->
                    workoutsNewList.forEach { a ->
                        Log.e("WKLOG", "For workout: ${a.size}")
                        exercises.addAll(a)
                    }

                    Log.e("WKLOG", "Workouts: ${workoutList.size}")
                    workoutList.forEach { workout ->
                        workouts.add(
                            WorkoutWithExercisesEntity(
                                workout,
                                exercises.filter { it.workoutId == workout.id }
                            )
                        )
                    }

                    binding.rvWorkouts.adapter?.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        workouts.clear()
        exercises.clear()
        fetchWorkouts()
    }

    private fun deleteWorkout(id: Long) {

    }
}
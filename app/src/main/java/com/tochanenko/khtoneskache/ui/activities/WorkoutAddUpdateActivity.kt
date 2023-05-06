package com.tochanenko.khtoneskache.ui.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tochanenko.khtoneskache.KhtoNeSkacheApp
import com.tochanenko.khtoneskache.R
import com.tochanenko.khtoneskache.database.entities.ExerciseEntity
import com.tochanenko.khtoneskache.database.entities.ExerciseSetEntity
import com.tochanenko.khtoneskache.database.entities.WorkoutEntity
import com.tochanenko.khtoneskache.databinding.ActivityWorkoutAddUpdateBinding
import com.tochanenko.khtoneskache.ui.ActivityResultCode
import com.tochanenko.khtoneskache.ui.adapters.SelectExercisesAdapter
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString


class WorkoutAddUpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWorkoutAddUpdateBinding
    private lateinit var materialAlertDialogBuilder: MaterialAlertDialogBuilder
    private val workoutId = System.currentTimeMillis()

    private var exercises: ArrayList<ExerciseSetEntity> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdd.setOnClickListener {
            saveWorkout()
        }

        setupRecyclerView(exercises)

        materialAlertDialogBuilder = MaterialAlertDialogBuilder(this)

        val activityResultLaunch: ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result?.resultCode == ActivityResultCode.ADD_EXERCISE_TO_WORKOUT.value) {
                val data: Intent = result.data!!
                val exerciseAmount: String = data.getStringExtra("exercise_amount")!!
                val exerciseAmountType: String = data.getStringExtra("exercise_amount_type")!!
                val exercise = Json.decodeFromString<ExerciseEntity>(
                    data.getStringExtra("exercise_entity")!!
                )

                val newExercise = ExerciseSetEntity(
                    workoutId = workoutId,
                    exerciseId = exercise.id,
                    exerciseName = exercise.name,
                    exerciseDescription = exercise.description,
                    duration = if (exerciseAmountType == "Seconds") exerciseAmount.toInt() else 0,
                    times = if (exerciseAmountType == "Times") exerciseAmount.toInt() else 0,
                )
                exercises.add(newExercise)
                Log.e(
                    "EXERCISE_ADD",
                    "Name: ${newExercise.exerciseName}, $exerciseAmount $exerciseAmountType"
                )
//                binding.rvExercises.adapter?.notifyItemInserted(exercises.size - 1)
                binding.rvExercises.adapter?.notifyDataSetChanged()
            }
        }

        binding.btnSelectExercise.setOnClickListener {
            activityResultLaunch.launch(Intent(this, ExerciseForWorkoutActivity::class.java))
        }
    }

    private fun setupRecyclerView(
        exercises: ArrayList<ExerciseSetEntity>
    ) {
        val selectExercisesAdapter = SelectExercisesAdapter(exercises)

        binding.rvExercises.layoutManager = LinearLayoutManager(this)
        binding.rvExercises.adapter = selectExercisesAdapter

        val dividerItemDecoration = DividerItemDecoration(
            binding.rvExercises.context,
            LinearLayoutManager(this).orientation
        )
        dividerItemDecoration.setDrawable(
            ContextCompat.getDrawable(
                applicationContext,
                R.drawable.recycler_view_divider
            )!!
        )
        binding.rvExercises.addItemDecoration(dividerItemDecoration)
    }

    private fun saveWorkout() {
        val exerciseSetDao = (application as KhtoNeSkacheApp).db.workoutExerciseDao()
        val workoutDao = (application as KhtoNeSkacheApp).db.workoutDao()

        val name = binding.etName.editText?.text.toString()

        lifecycleScope.launch {
            workoutDao.insert(
                WorkoutEntity(
                    id = workoutId,
                    name = name,
                    time = 0
                )
            )
            exerciseSetDao.insertAll(exercises)

            finish()
        }
    }
}
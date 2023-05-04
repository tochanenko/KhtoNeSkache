package com.tochanenko.khtoneskache.ui.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tochanenko.khtoneskache.R
import com.tochanenko.khtoneskache.database.entities.ExerciseSetEntity
import com.tochanenko.khtoneskache.databinding.ActivityWorkoutAddUpdateBinding
import com.tochanenko.khtoneskache.ui.adapters.SelectExercisesAdapter


class WorkoutAddUpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWorkoutAddUpdateBinding
    private lateinit var materialAlertDialogBuilder: MaterialAlertDialogBuilder

    private var exercises: ArrayList<ExerciseSetEntity> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdd.setOnClickListener {

        }

        setupRecyclerView(exercises)

        materialAlertDialogBuilder = MaterialAlertDialogBuilder(this)

        val activityResultLaunch: ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback<ActivityResult?> { result ->
                if (result?.resultCode == 123) {
                    val data: Intent = result.data!!
                    val myStr = data.getStringExtra("MyData")
                    if (!TextUtils.isEmpty(myStr)) {
                        binding.tvTest.text = myStr
                    }
                }
            })

        binding.btnSelectExercise.setOnClickListener {
            activityResultLaunch.launch(Intent(this, ExerciseForWorkoutActivity::class.java))
        }
    }

    private fun setupRecyclerView(
        exercises: ArrayList<ExerciseSetEntity>
    ) {
        if (exercises.isNotEmpty()) {
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
    }
}
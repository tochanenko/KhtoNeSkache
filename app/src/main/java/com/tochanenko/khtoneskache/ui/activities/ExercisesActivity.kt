package com.tochanenko.khtoneskache.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tochanenko.khtoneskache.KhtoNeSkacheApp
import com.tochanenko.khtoneskache.R
import com.tochanenko.khtoneskache.database.daos.ExerciseDao
import com.tochanenko.khtoneskache.database.daos.ExerciseSetDao
import com.tochanenko.khtoneskache.database.entities.ExerciseEntity
import com.tochanenko.khtoneskache.databinding.ActivityExercisesBinding
import com.tochanenko.khtoneskache.ui.adapters.ExerciseAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ExercisesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExercisesBinding
    private lateinit var materialAlertDialogBuilder: MaterialAlertDialogBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExercisesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val exerciseDao = (application as KhtoNeSkacheApp).db.exerciseDao()
        val exerciseSetDao = (application as KhtoNeSkacheApp).db.workoutExerciseDao()

        materialAlertDialogBuilder = MaterialAlertDialogBuilder(this)

        binding.tbTop.setNavigationOnClickListener {
            finish()
        }

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, ExerciseAddUpdateActivity::class.java))
        }

        binding.tbTop.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.add -> {
                    startActivity(Intent(this, ExerciseAddUpdateActivity::class.java))
                    true
                }
                else -> false
            }
        }

        lifecycleScope.launch {
            exerciseDao.fetchAllExercises().collect {
                val list = ArrayList(it)
                setupRecyclerView(list, exerciseDao, exerciseSetDao)
            }
        }
    }

    private fun setupRecyclerView(
        exercises: ArrayList<ExerciseEntity>,
        exerciseDao: ExerciseDao,
        exerciseSetDao: ExerciseSetDao
    ) {
        val exerciseAdapter = ExerciseAdapter(
            exercises,
            deleteListener = { deleteId ->
                deleteExercise(
                    exercises[deleteId].id,
                    exerciseDao,
                    exerciseSetDao,
                    exercises[deleteId].name
                )
            },
            editListener = { editId -> editExercise(exercises[editId].id) }
        )

        binding.rvExercises.layoutManager = LinearLayoutManager(this)
        binding.rvExercises.adapter = exerciseAdapter
    }

    private fun deleteExercise(
        id: Int,
        exerciseDao: ExerciseDao,
        exerciseSetDao: ExerciseSetDao,
        name: String
    ) {
        // TODO Fix RecyclerView not updating on deletion of last exercise
        materialAlertDialogBuilder
            .setTitle("Delete Exercise")
            .setMessage("Do you want to delete $name? This exercise will be also deleted from all Workouts. This operation can not be undone!")
            .setPositiveButton("Delete") { dialog, _ ->
                lifecycleScope.launch(Dispatchers.IO) {
                    exerciseDao.delete(ExerciseEntity(id = id))
                    exerciseSetDao.deleteExerciseSetsByExerciseId(id)
                    dialog.dismiss()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    private fun editExercise(id: Int) {
        val intent = Intent(this, ExerciseAddUpdateActivity::class.java)
        intent.putExtra("EXERCISE_ID", id)
        startActivity(intent)
    }
}
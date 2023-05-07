package com.tochanenko.khtoneskache.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tochanenko.khtoneskache.KhtoNeSkacheApp
import com.tochanenko.khtoneskache.Muscle
import com.tochanenko.khtoneskache.R
import com.tochanenko.khtoneskache.database.daos.ExerciseDao
import com.tochanenko.khtoneskache.database.entities.ExerciseEntity
import com.tochanenko.khtoneskache.databinding.ActivityExercisesBinding
import com.tochanenko.khtoneskache.ui.adapters.ExerciseAdapter
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


class ExercisesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExercisesBinding
    private lateinit var materialAlertDialogBuilder: MaterialAlertDialogBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExercisesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val exerciseDao = (application as KhtoNeSkacheApp).db.exerciseDao()

        materialAlertDialogBuilder = MaterialAlertDialogBuilder(this)

        lifecycleScope.launch {
            exerciseDao.fetchAllExercises().collect {
                val list = ArrayList(it)
                setupRecyclerView(list, exerciseDao)
            }
        }
    }

    private fun setupRecyclerView(
        exercises: ArrayList<ExerciseEntity>,
        exerciseDao: ExerciseDao
    ) {
        val exerciseAdapter = ExerciseAdapter(
            exercises,
            deleteListener = { deleteId ->
                deleteExercise(
                    exercises[deleteId].id,
                    exerciseDao,
                    exercises[deleteId].name
                )
            },
            editListener = { editId -> editExercise(exercises[editId].id) }
        )

        binding.rvExercises.layoutManager = LinearLayoutManager(this)
        binding.rvExercises.adapter = exerciseAdapter

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

    private fun deleteExercise(id: Int, exerciseDao: ExerciseDao, name: String) {
        // TODO Fix RecyclerView not updating on deletion of last exercise
        materialAlertDialogBuilder
            .setTitle("Delete Exercise")
            .setMessage("Do you want to delete $name? This operation can not be undone")
            .setPositiveButton("Delete") { dialog, _ ->
                lifecycleScope.launch {
                    exerciseDao.delete(ExerciseEntity(id = id))
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
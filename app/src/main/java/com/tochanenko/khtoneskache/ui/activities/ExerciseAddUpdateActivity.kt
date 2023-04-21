package com.tochanenko.khtoneskache.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tochanenko.khtoneskache.KhtoNeSkacheApp
import com.tochanenko.khtoneskache.Muscle
import com.tochanenko.khtoneskache.R
import com.tochanenko.khtoneskache.database.entities.ExerciseEntity
import com.tochanenko.khtoneskache.databinding.ActivityExerciseAddUpdateBinding
import com.tochanenko.khtoneskache.ui.adapters.SelectMusclesAdapter
import kotlinx.coroutines.launch

class ExerciseAddUpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExerciseAddUpdateBinding
    private lateinit var materialAlertDialogBuilder: MaterialAlertDialogBuilder
    private lateinit var customAlertDialogView: View
    private var muscles = arrayListOf<Int>()
    private var exerciseId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val exerciseDao = (application as KhtoNeSkacheApp).db.exerciseDao()
        exerciseId = intent.extras?.getInt("EXERCISE_ID") ?: -1

        if (exerciseId != -1) {
            lifecycleScope.launch {
                exerciseDao.getExerciseById(exerciseId).collect {
                    binding.etName.editText?.setText(it.name)
                    binding.etDescription.editText?.setText(it.description)
                    muscles = it.muscles.toCollection(ArrayList())
                    binding.muscles.text = muscles.map { Muscle.fromId(it).title }.toString()
                    // TODO Add image resource
                    binding.btnAdd.text = "Edit Exercise"
                }

            }
        }

        binding.btnAdd.setOnClickListener {
            if (!fieldsEmpty()) {
                lifecycleScope.launch {
                    if (exerciseId == -1) {
                        exerciseDao.insert(
                            ExerciseEntity(
                                name = binding.etName.editText?.text.toString(),
                                description = binding.etDescription.editText?.text.toString(),
                                image = "",
                                muscles = muscles.toList()
                            )
                        )
                    } else {
                        exerciseDao.update(
                            ExerciseEntity(
                                id = exerciseId,
                                name = binding.etName.editText?.text.toString(),
                                description = binding.etDescription.editText?.text.toString(),
                                image = "",
                                muscles = muscles.toList()
                            )
                        )
                    }
                    finish()
                }
            }
        }

        materialAlertDialogBuilder = MaterialAlertDialogBuilder(this)

        binding.btnAddMuscle.setOnClickListener {
            customAlertDialogView = LayoutInflater.from(this)
                .inflate(R.layout.select_muscle_dialog, null, false)
            launchCustomAlertDialogForSelectedMuscle()
        }
    }

    private fun fieldsEmpty(): Boolean {
        return binding.etName.editText?.text.toString().isEmpty()
                || binding.etDescription.editText?.text.toString().isEmpty()
                || muscles.isEmpty()
    }

    private fun launchCustomAlertDialogForSelectedMuscle() {
        val recyclerView: RecyclerView = customAlertDialogView.findViewById(R.id.rv_muscles)

        val adapter =
            setupMusclesRecyclerView(enumValues<Muscle>().toCollection(ArrayList()), recyclerView)

        materialAlertDialogBuilder.setView(customAlertDialogView)
            .setTitle("Select Muscle")
            .setPositiveButton("Select") { dialog, _ ->
                muscles.add(adapter?.getSelectedItem()!!)
                binding.muscles.text = muscles.map { Muscle.fromId(it).title }.toString()
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun setupMusclesRecyclerView(
        muscles: ArrayList<Muscle>,
        recyclerView: RecyclerView
    ): SelectMusclesAdapter? {
        if (muscles.isNotEmpty()) {
            val muscleAdapter = SelectMusclesAdapter(muscles)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = muscleAdapter

            val dividerItemDecoration = DividerItemDecoration(
                recyclerView.context,
                LinearLayoutManager(this).orientation
            )
            dividerItemDecoration.setDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.recycler_view_divider
                )!!
            )
            recyclerView.addItemDecoration(dividerItemDecoration)
            return muscleAdapter
        }
        return null
    }
}
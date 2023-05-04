package com.tochanenko.khtoneskache.ui.activities

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.annotation.MenuRes
import androidx.appcompat.widget.PopupMenu
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

        binding.btnSelectExercise.setOnClickListener {
            startActivity(Intent(this, ExerciseForWorkoutActivity::class.java))
        }

        /* binding.btnSelectMeasure.setOnClickListener {
            showMenu(it, R.menu.measure_menu)
        } */
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

    /* private fun showMenu(v: View, @MenuRes menuRes: Int) {
        val popup = PopupMenu(v.context, v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            binding.btnSelectMeasure.backgroundTintList = ColorStateList.valueOf(getColor(R.color.green_100))
            when (menuItem.itemId) {
                R.id.option_times -> {
                    binding.btnSelectMeasure.text = "Times"
                }
                R.id.option_seconds -> {
                    binding.btnSelectMeasure.text = "Seconds"
                }
            }
            true
        }
        popup.setOnDismissListener {
            // Respond to popup being dismissed.
        }
        popup.show()
    } */
}
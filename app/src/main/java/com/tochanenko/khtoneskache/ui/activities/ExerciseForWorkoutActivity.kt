package com.tochanenko.khtoneskache.ui.activities

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.MenuRes
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tochanenko.khtoneskache.KhtoNeSkacheApp
import com.tochanenko.khtoneskache.Muscle
import com.tochanenko.khtoneskache.R
import com.tochanenko.khtoneskache.database.daos.ExerciseDao
import com.tochanenko.khtoneskache.database.entities.ExerciseEntity
import com.tochanenko.khtoneskache.databinding.ActivityExerciseForWorkoutBinding
import com.tochanenko.khtoneskache.databinding.BottomSheetSelectExerciseBinding
import com.tochanenko.khtoneskache.ui.adapters.ExerciseSelectAdapter
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString

class ExerciseForWorkoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExerciseForWorkoutBinding
    private lateinit var exercises: ArrayList<ExerciseEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseForWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val exerciseDao = (application as KhtoNeSkacheApp).db.exerciseDao()

        lifecycleScope.launch {
            exerciseDao.fetchAllExercises().collect {
                exercises = ArrayList(it)
                setupRecyclerView(exercises, exerciseDao)
            }
        }
    }

    private fun setupRecyclerView(
        exercises: ArrayList<ExerciseEntity>,
        exerciseDao: ExerciseDao
    ) {
        if (exercises.isNotEmpty()) {
            val exerciseAdapter = ExerciseSelectAdapter(
                exercises,
                onClickListener = { clickId -> onItemClickListener(clickId) }
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
    }

    private fun onItemClickListener(id: Int) {
        val modalBottomSheet = ModalBottomSheet()
        val bundle = Bundle()
        bundle.putString("exercise", Json.encodeToString(exercises[id]))
        modalBottomSheet.arguments = bundle
        modalBottomSheet.show(supportFragmentManager, ModalBottomSheet.TAG)
    }
}

class ModalBottomSheet : BottomSheetDialogFragment() {
    private lateinit var bottomSheetBinding: BottomSheetSelectExerciseBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bottomSheetBinding = BottomSheetSelectExerciseBinding.inflate(inflater, container, false)
        return bottomSheetBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val exercise: ExerciseEntity = Json.decodeFromString(arguments?.getString("exercise")!!)

        bottomSheetBinding.tvName.text = exercise.name
        bottomSheetBinding.tvDescription.text = exercise.description
        bottomSheetBinding.tvMuscles.text =
            if (exercise.muscles.isEmpty()) "No Muscles are training"
            else exercise.muscles.map { Muscle.fromId(it).title }.toString()

        bottomSheetBinding.btnSelectMeasure.setOnClickListener {
            showMenu(it, R.menu.measure_menu)
        }
    }

    private fun showMenu(v: View, @MenuRes menuRes: Int) {
        val popup = PopupMenu(v.context, v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
//            bottomSheetBinding.btnSelectMeasure.backgroundTintList = ColorStateList.valueOf(getColor(R.color.green_100))
            when (menuItem.itemId) {
                R.id.option_times -> {
                    bottomSheetBinding.btnSelectMeasure.text = "Times"
                }
                R.id.option_seconds -> {
                    bottomSheetBinding.btnSelectMeasure.text = "Seconds"
                }
            }
            true
        }
        popup.setOnDismissListener {
            // Respond to popup being dismissed.
        }
        popup.show()
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}
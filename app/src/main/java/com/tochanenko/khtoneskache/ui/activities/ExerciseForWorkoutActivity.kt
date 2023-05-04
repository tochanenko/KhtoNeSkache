package com.tochanenko.khtoneskache.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tochanenko.khtoneskache.KhtoNeSkacheApp
import com.tochanenko.khtoneskache.R
import com.tochanenko.khtoneskache.database.daos.ExerciseDao
import com.tochanenko.khtoneskache.database.entities.ExerciseEntity
import com.tochanenko.khtoneskache.databinding.ActivityExerciseForWorkoutBinding
import com.tochanenko.khtoneskache.databinding.BottomSheetSelectExerciseBinding
import com.tochanenko.khtoneskache.ui.adapters.ExerciseSelectAdapter
import kotlinx.coroutines.launch

class ExerciseForWorkoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExerciseForWorkoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseForWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val exerciseDao = (application as KhtoNeSkacheApp).db.exerciseDao()

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

        bottomSheetBinding.tvName.text = "EXERCISE NAME"
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}
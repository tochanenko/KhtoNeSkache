package com.tochanenko.khtoneskache.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tochanenko.khtoneskache.KhtoNeSkacheApp
import com.tochanenko.khtoneskache.R
import com.tochanenko.khtoneskache.database.entities.ExerciseEntity
import com.tochanenko.khtoneskache.databinding.ActivityExercisesBinding
import com.tochanenko.khtoneskache.ui.adapters.ExerciseAdapter
import kotlinx.coroutines.launch


class ExercisesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExercisesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExercisesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val exerciseDao = (application as KhtoNeSkacheApp).db.exerciseDao()

        lifecycleScope.launch {
            exerciseDao.fetchAllExercises().collect {
                val list = ArrayList(it)
                setupRecyclerView(list)
            }
        }
    }

    private fun setupRecyclerView(
        exercises: ArrayList<ExerciseEntity>
    ) {
        if (exercises.isNotEmpty()) {
            val exerciseAdapter = ExerciseAdapter(exercises)

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
}
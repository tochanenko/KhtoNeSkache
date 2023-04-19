package com.tochanenko.khtoneskache.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.tochanenko.khtoneskache.KhtoNeSkacheApp
import com.tochanenko.khtoneskache.R
import com.tochanenko.khtoneskache.database.entities.ExerciseEntity
import com.tochanenko.khtoneskache.databinding.ActivityExerciseAddUpdateBinding
import kotlinx.coroutines.launch

class ExerciseAddUpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExerciseAddUpdateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val exerciseDao = (application as KhtoNeSkacheApp).db.exerciseDao()

        binding.btnAdd.setOnClickListener {
            if (!fieldsEmpty()) {
                lifecycleScope.launch {
                    exerciseDao.insert(
                        ExerciseEntity(
                            name = binding.etName.editText?.text.toString(),
                            description = binding.etDescription.editText?.text.toString(),
                            image = "",
                            muscles = listOf()
                        )
                    )
                    finish()
                }
            }
        }
    }

    private fun fieldsEmpty(): Boolean {
        return binding.etName.editText?.text.toString().isNullOrEmpty()
                && binding.etDescription.editText?.text.toString().isNullOrEmpty()
    }
}
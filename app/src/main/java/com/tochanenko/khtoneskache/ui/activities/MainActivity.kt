package com.tochanenko.khtoneskache.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import com.tochanenko.khtoneskache.KhtoNeSkacheApp
import com.tochanenko.khtoneskache.R
import com.tochanenko.khtoneskache.database.entities.ExerciseEntity
import com.tochanenko.khtoneskache.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.tbTop.setNavigationOnClickListener {
            binding.dlMain.open()
        }

        binding.tbTop.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.profile -> {
                    finish()
                    true
                }

                else -> false
            }
        }

        binding.nvMain.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_exercises -> {
                    startActivity(Intent(this, ExercisesActivity::class.java))
                }

                R.id.nav_workouts -> {
                    startActivity(Intent(this, WorkoutsActivity::class.java))
                }

                else -> false
            }
            menuItem.isChecked = false
            binding.dlMain.close()
            true
        }

        val exerciseDao = (application as KhtoNeSkacheApp).db.exerciseDao()

//        binding.btnPopulateExercises.setOnClickListener {
//            lifecycleScope.launch {
//                exerciseDao.insert(ExerciseEntity(
//                    name = "Підтягування",
//                    description = "Підтягування руками на перекладені",
//                    image = "Hello",
//                    muscles = listOf()
//                ))
//                exerciseDao.insert(ExerciseEntity(
//                    name = "Віджимання",
//                    description = "Віджимання обома руками від землі. Руки прямі 90 кут за тілом, а ноги прямі розставлені на ширині плечей.",
//                    image = "Hello",
//                    muscles = listOf()
//                ))
//                exerciseDao.insert(ExerciseEntity(
//                    name = "Прес",
//                    description = "Качання пресу, коли дружбан притримує твої ніжки",
//                    image = "Hello",
//                    muscles = listOf()
//                ))
//            }
//        }
    }
}
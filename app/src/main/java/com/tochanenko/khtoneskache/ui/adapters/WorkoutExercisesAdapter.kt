package com.tochanenko.khtoneskache.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tochanenko.khtoneskache.R
import com.tochanenko.khtoneskache.database.entities.ExerciseSetEntity
import com.tochanenko.khtoneskache.databinding.ExerciseCountedSimpleRowBinding

class WorkoutExercisesAdapter(
    private val items: ArrayList<ExerciseSetEntity>
) : RecyclerView.Adapter<WorkoutExercisesAdapter.ViewHolder>() {
    class ViewHolder(binding: ExerciseCountedSimpleRowBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvName = binding.tvName
        val tvAmount = binding.tvAmount
        val clMain = binding.clMain
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ExerciseCountedSimpleRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val item = items[position]

        holder.tvAmount.text =
            if (item.duration != 0) "${item.duration} sec."
            else "${item.times} rep."
        holder.tvName.text = item.exerciseName

        if (position % 2 == 0) {
            holder.clMain.setBackgroundColor(ContextCompat.getColor(context, R.color.transparent))
        } else {
            holder.clMain.setBackgroundColor(ContextCompat.getColor(context, R.color.purple_50))
        }
    }
}
package com.tochanenko.khtoneskache.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tochanenko.khtoneskache.R
import com.tochanenko.khtoneskache.database.entities.ExerciseSetEntity
import com.tochanenko.khtoneskache.databinding.ExerciseCountedRowBinding

class SelectExercisesAdapter(
    private val items: ArrayList<ExerciseSetEntity>
) : RecyclerView.Adapter<SelectExercisesAdapter.ViewHolder>() {
    class ViewHolder(binding: ExerciseCountedRowBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvName = binding.tvName
        val tvDescription = binding.tvDescription
        val tvAmount = binding.tvAmount
        val clMain = binding.clMain
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ExerciseCountedRowBinding.inflate(
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

        holder.tvName.text = item.exerciseName
        holder.tvDescription.text = item.exerciseDescription
        holder.tvAmount.text = getAmount(item.duration, item.times)

        if (position % 2 == 0) {
            holder.clMain.setBackgroundColor(ContextCompat.getColor(context, R.color.purple_100))
        } else {
            holder.clMain.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        }
    }

    private fun getAmount(duration: Int, times: Int): String =
        if (times != 0) {
            "$times reps."
        } else if (duration > 61) {
            "${duration / 60}:${duration % 60}"
        } else "${duration}s"
}
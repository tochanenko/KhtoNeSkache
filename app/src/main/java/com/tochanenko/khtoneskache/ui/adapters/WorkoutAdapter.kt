package com.tochanenko.khtoneskache.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tochanenko.khtoneskache.databinding.WorkoutRowBinding
import com.tochanenko.khtoneskache.database.entities.WorkoutWithExercisesEntity

class WorkoutAdapter(
    private val items: ArrayList<WorkoutWithExercisesEntity>,
    private val editListener: (id: Int) -> Unit,
    private val deleteListener: (id: Int) -> Unit
) : RecyclerView.Adapter<WorkoutAdapter.ViewHolder>() {
    class ViewHolder(binding: WorkoutRowBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvName = binding.tvName
        val rvExercises = binding.rvExercises
        val btnDelete = binding.btnDelete
        val btnEdit = binding.btnEdit
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            WorkoutRowBinding.inflate(
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

        holder.tvName.text = item.workout.name

        holder.btnDelete.setOnClickListener {
            deleteListener.invoke(position)
        }

        holder.btnEdit.setOnClickListener {
            editListener.invoke(position)
        }

        // TODO ListView with Exercises
        holder.rvExercises
    }
}
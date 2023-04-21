package com.tochanenko.khtoneskache.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tochanenko.khtoneskache.Muscle
import com.tochanenko.khtoneskache.R
import com.tochanenko.khtoneskache.database.entities.ExerciseEntity
import com.tochanenko.khtoneskache.databinding.ExerciseItemRowBinding

class ExerciseAdapter(
    private val items: ArrayList<ExerciseEntity>,
    private val editListener: (id: Int) -> Unit,
    private val deleteListener: (id: Int) -> Unit
) : RecyclerView.Adapter<ExerciseAdapter.ViewHolder>() {
    class ViewHolder(binding: ExerciseItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        val ivImage = binding.ivImage
        val tvName = binding.tvName
        val tvDescription = binding.tvDescription
        val tvMuscles = binding.tvMuscles
        val clMain = binding.clMain
        val btnDelete = binding.btnDelete
        val btnEdit = binding.btnEdit
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ExerciseItemRowBinding.inflate(
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

        holder.tvName.text = item.name
        holder.tvDescription.text = item.description
        holder.tvMuscles.text =
            if (item.muscles.isEmpty()) "No Muscles are training"
            else item.muscles.map { Muscle.fromId(it).title }.toString()

        if (position % 2 == 0) {
            holder.clMain.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    androidx.appcompat.R.color.material_grey_100
                )
            )
        } else {
            holder.clMain.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        }

        holder.btnDelete.setOnClickListener {
            deleteListener.invoke(position)
        }

        holder.btnEdit.setOnClickListener {
            editListener.invoke(position)
        }
    }
}
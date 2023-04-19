package com.tochanenko.khtoneskache.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tochanenko.khtoneskache.R
import com.tochanenko.khtoneskache.database.entities.ExerciseEntity
import com.tochanenko.khtoneskache.databinding.ExerciseItemRowBinding

class ExerciseAdapter(
    private val items: ArrayList<ExerciseEntity>
) : RecyclerView.Adapter<ExerciseAdapter.ViewHolder>() {
    class ViewHolder(binding: ExerciseItemRowBinding): RecyclerView.ViewHolder(binding.root) {
        val ivImage = binding.ivImage
        val tvName = binding.tvName
        val tvDescription = binding.tvDescription
        val clMain = binding.clMain
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

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val item = items[position]

        holder.tvName.text = item.name
        holder.tvDescription.text = item.description

        if (position % 2 == 0) {
            holder.clMain.setBackgroundColor(ContextCompat.getColor(context, androidx.appcompat.R.color.material_grey_100))
        } else {
            holder.clMain.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        }
    }
}
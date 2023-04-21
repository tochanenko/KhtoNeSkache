package com.tochanenko.khtoneskache.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tochanenko.khtoneskache.Muscle
import com.tochanenko.khtoneskache.R
import com.tochanenko.khtoneskache.databinding.MuscleRowBinding

class SelectMusclesAdapter(
    private val items: ArrayList<Muscle>
) : RecyclerView.Adapter<SelectMusclesAdapter.ViewHolder>() {
    class ViewHolder(binding: MuscleRowBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvName = binding.tvName
        val ivImage = binding.ivImage
        val clMain = binding.clMain
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        MuscleRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val item = items[position]

        holder.tvName.text = item.title
        // TODO Add Image resource from item.image

        if (position % 2 == 0) {
            holder.clMain.setBackgroundColor(ContextCompat.getColor(context, androidx.appcompat.R.color.material_grey_100))
        } else {
            holder.clMain.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        }
    }
}
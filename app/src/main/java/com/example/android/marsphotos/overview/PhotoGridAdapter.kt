package com.example.android.marsphotos.overview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.marsphotos.R
import com.example.android.marsphotos.databinding.GridViewItemBinding
import com.example.android.marsphotos.network.MarsPhoto

class PhotoGridAdapter :
    ListAdapter<MarsPhoto, PhotoGridAdapter.MarsPhotoViewHolder>(DiffCallback) {
    object DiffCallback : DiffUtil.ItemCallback<MarsPhoto>() {
        override fun areItemsTheSame(oldItem: MarsPhoto, newItem: MarsPhoto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MarsPhoto, newItem: MarsPhoto): Boolean {
            return oldItem == newItem
        }

    }

    class MarsPhotoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = GridViewItemBinding.bind(view)
        fun bind(item: MarsPhoto) {
            binding.photo = item
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MarsPhotoViewHolder {
        return MarsPhotoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.grid_view_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MarsPhotoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


}
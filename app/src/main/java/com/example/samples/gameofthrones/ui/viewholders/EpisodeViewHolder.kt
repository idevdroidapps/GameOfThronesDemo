package com.example.samples.gameofthrones.ui.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.samples.gameofthrones.data.models.Episode
import com.example.samples.gameofthrones.databinding.ListItemBinding
import com.example.samples.gameofthrones.ui.viewmodels.MainViewModel

class EpisodeViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

  fun bind(
    item: Episode,
    viewModel: MainViewModel
  ) {
    binding.episode = item
    binding.viewModel = viewModel
    binding.position = layoutPosition
    binding.root.isSelected = layoutPosition == viewModel.currentPosition.value
    binding.executePendingBindings()
  }

  companion object {
    fun from(parent: ViewGroup): EpisodeViewHolder {
      val layoutInflater =
        LayoutInflater.from(parent.context)
      val binding = ListItemBinding.inflate(layoutInflater, parent, false)
      return EpisodeViewHolder(binding)
    }
  }

}

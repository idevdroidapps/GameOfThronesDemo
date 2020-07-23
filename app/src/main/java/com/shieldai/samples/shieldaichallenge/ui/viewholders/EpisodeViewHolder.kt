package com.shieldai.samples.shieldaichallenge.ui.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shieldai.samples.shieldaichallenge.data.models.Episode
import com.shieldai.samples.shieldaichallenge.databinding.ListItemBinding
import com.shieldai.samples.shieldaichallenge.ui.clicklisteners.EpisodeClickListener

class EpisodeViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

  fun bind(
    item: Episode,
    clickListener: EpisodeClickListener
  ) {
    binding.episode = item
    binding.executePendingBindings()
    binding.clickListener = clickListener
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

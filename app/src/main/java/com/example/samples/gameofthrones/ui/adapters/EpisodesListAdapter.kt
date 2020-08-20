package com.example.samples.gameofthrones.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.samples.gameofthrones.data.models.Episode
import com.example.samples.gameofthrones.ui.viewholders.EpisodeViewHolder
import com.example.samples.gameofthrones.ui.viewmodels.MainViewModel

class EpisodesListAdapter(private val viewModel: MainViewModel) :
  ListAdapter<Episode, EpisodeViewHolder>(DiffCallback) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
    return EpisodeViewHolder.from(parent)
  }

  override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
    getItem(position)?.let {
      holder.bind(it, viewModel)
    }
  }

  companion object DiffCallback : DiffUtil.ItemCallback<Episode>() {
    override fun areItemsTheSame(
      oldItem: Episode,
      newItem: Episode
    ): Boolean {
      return oldItem === newItem
    }

    override fun areContentsTheSame(
      oldItem: Episode,
      newItem: Episode
    ): Boolean {
      return oldItem.id == newItem.id
    }
  }

}
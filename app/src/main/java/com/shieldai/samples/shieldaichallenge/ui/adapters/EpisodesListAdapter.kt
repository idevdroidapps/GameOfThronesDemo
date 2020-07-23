package com.shieldai.samples.shieldaichallenge.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.shieldai.samples.shieldaichallenge.data.models.Episode
import com.shieldai.samples.shieldaichallenge.ui.clicklisteners.EpisodeClickListener
import com.shieldai.samples.shieldaichallenge.ui.viewholders.EpisodeViewHolder

class EpisodesListAdapter(private val clickListener: EpisodeClickListener) :
  ListAdapter<Episode, EpisodeViewHolder>(DiffCallback) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
    return EpisodeViewHolder.from(parent)
  }

  override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
    getItem(position)?.let {
      holder.bind(it, clickListener)
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
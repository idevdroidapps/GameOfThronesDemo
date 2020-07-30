package com.shieldai.samples.shieldaichallenge.ui.adapters

import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.shieldai.samples.shieldaichallenge.data.models.Episode
import com.shieldai.samples.shieldaichallenge.ui.viewholders.EpisodeViewHolder
import com.shieldai.samples.shieldaichallenge.ui.viewmodels.MainViewModel

class EpisodesListAdapter(private val viewModel: MainViewModel) :
  PagingDataAdapter<Episode, EpisodeViewHolder>(DiffCallback) {

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
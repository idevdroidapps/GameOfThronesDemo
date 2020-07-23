package com.shieldai.samples.shieldaichallenge.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.shieldai.samples.shieldaichallenge.data.models.Episode
import com.shieldai.samples.shieldaichallenge.data.repositories.EpisodeRepository

class MainViewModel(repo: EpisodeRepository) : ViewModel() {

  val episodes: LiveData<List<Episode>> = repo.getEpisodes()

  private var _episode = MutableLiveData<Episode>()
  val episode: LiveData<Episode> get() = _episode

  fun currentEpisode(episode: Episode) {
    _episode.value = episode
  }

}
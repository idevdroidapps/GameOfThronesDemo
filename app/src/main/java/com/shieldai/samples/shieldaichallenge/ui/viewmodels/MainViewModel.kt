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

  private var _position = MutableLiveData<Int>()
  val position: LiveData<Int> get() = _position

  fun currentEpisode(episode: Episode, position: Int) {
    _episode.value = episode
    _position.value = position
  }

}
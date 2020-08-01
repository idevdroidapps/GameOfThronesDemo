package com.shieldai.samples.shieldaichallenge.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shieldai.samples.shieldaichallenge.data.models.Episode
import com.shieldai.samples.shieldaichallenge.data.repositories.EpisodeRepository

class MainViewModel(private val repo: EpisodeRepository) : ViewModel() {

  val episodes = repo.episodes

  val firstEpisode: LiveData<Episode> get() = repo.firstEpisode

  private var _currentEpisode = MutableLiveData<Episode>()
  val currentEpisode: LiveData<Episode> get() = _currentEpisode

  private var _currentPosition = MutableLiveData<Int>()
  val currentPosition: LiveData<Int> get() = _currentPosition

  var previousPosition = 0

  init {
    _currentPosition.value = previousPosition
  }

  fun onItemClick(episode: Episode, position: Int) {
    _currentEpisode.value = episode
    _currentPosition.value = position
  }

  fun setCurrentPosition(selected: Int) {
    _currentPosition.value = selected
  }

  fun setCurrentEpisode(episode: Episode) {
    _currentEpisode.value = episode
  }

}
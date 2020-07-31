package com.shieldai.samples.shieldaichallenge.ui.viewmodels

import androidx.lifecycle.*
import com.shieldai.samples.shieldaichallenge.data.models.Episode
import com.shieldai.samples.shieldaichallenge.data.repositories.EpisodeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val repo: EpisodeRepository) : ViewModel() {

  val episodes = repo.episodes

  val firstEpisode: LiveData<Episode> get() = repo.firstEpisode

  private var _episode = MutableLiveData<Episode>()
  val episode: LiveData<Episode> get() = _episode

  private var _currentPosition = MutableLiveData<Int>()
  val currentPosition: LiveData<Int> get() = _currentPosition

  var previousPosition = 0

  init {
    _currentPosition.value = previousPosition
  }

  fun onItemClick(episode: Episode, position: Int) {
    _episode.value = episode
    _currentPosition.value = position
  }

  fun setPosition(selected: Int) {
    _currentPosition.value = selected
  }

  fun setEpisode(episode: Episode) {
    _episode.value = episode
  }

}
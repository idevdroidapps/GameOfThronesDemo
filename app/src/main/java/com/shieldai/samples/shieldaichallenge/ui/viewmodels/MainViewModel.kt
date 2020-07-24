package com.shieldai.samples.shieldaichallenge.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shieldai.samples.shieldaichallenge.data.models.Episode
import com.shieldai.samples.shieldaichallenge.data.repositories.EpisodeRepository

class MainViewModel(private val repo: EpisodeRepository) : ViewModel() {

  val episodes: LiveData<List<Episode>> = repo.getEpisodes()

  val firstEpisode: LiveData<Episode> = repo.getFirstEpisode()

  private var _episode = MutableLiveData<Episode>()
  val episode: LiveData<Episode> get() = _episode

  private var _currentPosition = MutableLiveData<Int>()
  val currentPosition: LiveData<Int> get() = _currentPosition

  var previousPosition = 0

  fun onItemClick(episode: Episode, position: Int) {
    _episode.value = episode
    _currentPosition.value = position
  }

  fun setPosition(selected: Int){
    _currentPosition.value = selected
  }

  fun setEpisode(episode: Episode){
    _episode.value = episode
  }

}
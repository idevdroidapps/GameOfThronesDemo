package com.shieldai.samples.shieldaichallenge.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shieldai.samples.shieldaichallenge.data.models.Episode
import com.shieldai.samples.shieldaichallenge.data.repositories.EpisodeRepository

class MainViewModel(repo: EpisodeRepository) : ViewModel() {

  val episodes: LiveData<List<Episode>> = repo.getEpisodes()

  private var _episode = MutableLiveData<Episode>()
  val episode: LiveData<Episode> get() = _episode

  private var _currentSelected = MutableLiveData<Int>()
  val currentSelected: LiveData<Int> get() = _currentSelected

  var previouslySelected = 0

  fun onItemClick(episode: Episode, position: Int) {
    _episode.value = episode
    _currentSelected.value = position
  }

  fun setSelected(selected: Int){
    _currentSelected.value = selected
  }

  fun setEpisode(episode: Episode){
    _episode.value = episode
  }

}
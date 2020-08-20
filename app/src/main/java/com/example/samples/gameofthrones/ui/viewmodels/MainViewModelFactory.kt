package com.example.samples.gameofthrones.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.samples.gameofthrones.data.repositories.EpisodeRepository

class MainViewModelFactory(
  private val repository: EpisodeRepository
) : ViewModelProvider.Factory {
  @Suppress("unchecked_cast")
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
      return MainViewModel(repository) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}
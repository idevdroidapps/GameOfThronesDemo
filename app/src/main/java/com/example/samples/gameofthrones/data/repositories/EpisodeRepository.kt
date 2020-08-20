package com.example.samples.gameofthrones.data.repositories

import androidx.lifecycle.LiveData
import com.example.samples.gameofthrones.data.database.EpisodeDao
import com.example.samples.gameofthrones.data.models.Episode

class EpisodeRepository private constructor(private val dao: EpisodeDao) {

  fun getEpisodes(): LiveData<List<Episode>> = dao.getEpisodes()

  fun getFirstEpisode(): LiveData<Episode> = dao.getFirstEpisode()

  companion object {
    // For Singleton instantiation
    @Volatile
    private var instance: EpisodeRepository? = null

    fun getInstance(episodeDao: EpisodeDao) =
      instance ?: synchronized(this) {
        instance ?: EpisodeRepository(episodeDao).also { instance = it }
      }
  }
}
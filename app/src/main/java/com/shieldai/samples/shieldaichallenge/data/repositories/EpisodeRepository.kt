package com.shieldai.samples.shieldaichallenge.data.repositories

import androidx.lifecycle.LiveData
import com.shieldai.samples.shieldaichallenge.data.database.EpisodeDao
import com.shieldai.samples.shieldaichallenge.data.models.Episode

class EpisodeRepository private constructor(private val dao: EpisodeDao) {

  fun getEpisodes(): LiveData<List<Episode>> = dao.getEpisodes()

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
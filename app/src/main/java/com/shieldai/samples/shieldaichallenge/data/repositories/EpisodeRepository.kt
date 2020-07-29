package com.shieldai.samples.shieldaichallenge.data.repositories

import com.shieldai.samples.shieldaichallenge.data.database.EpisodeDao
import com.shieldai.samples.shieldaichallenge.data.models.Episode
import kotlinx.coroutines.flow.Flow

class EpisodeRepository private constructor(private val dao: EpisodeDao) {

  val episodesFlow: Flow<List<Episode>> get() = dao.getEpisodes()

  val firstEpisodeFlow: Flow<Episode> get() = dao.getFirstEpisode()

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
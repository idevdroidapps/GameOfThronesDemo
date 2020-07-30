package com.shieldai.samples.shieldaichallenge.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.shieldai.samples.shieldaichallenge.data.database.EpisodeDao
import com.shieldai.samples.shieldaichallenge.data.models.Episode
import kotlinx.coroutines.flow.Flow

class EpisodeRepository private constructor(private val dao: EpisodeDao) {

  val episodes = Pager(PagingConfig(pageSize = 15, enablePlaceholders = true)) {
    dao.getEpisodes()
  }.flow

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
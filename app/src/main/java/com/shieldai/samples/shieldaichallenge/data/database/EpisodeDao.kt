package com.shieldai.samples.shieldaichallenge.data.database

import androidx.paging.DataSource
import androidx.room.*
import com.shieldai.samples.shieldaichallenge.data.models.Episode

@Dao
interface EpisodeDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertEpisode(episode: Episode): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  @JvmSuppressWildcards
  suspend fun insertEpisodes(episodes: List<Episode>)

  @Update
  fun update(episode: Episode)

  @Delete
  fun deleteEpisode(episode: Episode)

  @Query("DELETE FROM Episode")
  fun clearAllEpisodes()

  @Query("SELECT * FROM Episode")
  fun getEpisodes(): DataSource.Factory<Int, Episode>

}
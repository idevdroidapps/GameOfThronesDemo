package com.shieldai.samples.shieldaichallenge.data.database

import androidx.lifecycle.LiveData
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
  suspend fun update(episode: Episode)

  @Delete
  suspend fun deleteEpisode(episode: Episode)

  @Query("DELETE FROM Episode")
  suspend fun clearAllEpisodes()

  @Query("SELECT * FROM Episode")
  fun getEpisodes(): LiveData<List<Episode>>

}
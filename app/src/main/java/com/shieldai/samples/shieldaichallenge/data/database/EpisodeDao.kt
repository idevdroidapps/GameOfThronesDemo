package com.shieldai.samples.shieldaichallenge.data.database

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.shieldai.samples.shieldaichallenge.data.models.Episode
import com.shieldai.samples.shieldaichallenge.data.models.Video

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

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertVideo(video: Video): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  @JvmSuppressWildcards
  suspend fun insertVideos(videos: List<Video>)

  @Update
  suspend fun update(video: Video)

  @Delete
  suspend fun deleteVideo(video: Video)

  @Transaction
  @Query("SELECT * FROM Episode LIMIT 1")
  fun getFirstEpisode(): LiveData<Episode>

  @Transaction
  @Query("SELECT * FROM Episode")
  fun getEpisodes(): PagingSource<Int, Episode>

}
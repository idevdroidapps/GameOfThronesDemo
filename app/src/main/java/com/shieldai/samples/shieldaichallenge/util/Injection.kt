package com.shieldai.samples.shieldaichallenge.util

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.shieldai.samples.shieldaichallenge.data.database.EpisodeDao
import com.shieldai.samples.shieldaichallenge.data.database.EpisodeDatabase
import com.shieldai.samples.shieldaichallenge.data.repositories.EpisodeRepository
import com.shieldai.samples.shieldaichallenge.ui.viewmodels.MainViewModelFactory

object Injection {

  /**
   * Creates an instance of [EpisodeDao]
   */
  private fun provideEpisodesDao(context: Context): EpisodeDao {
    return EpisodeDatabase.getInstance(context).dao
  }

  /**
   * Creates an instance of [EpisodeRepository]
   */
  private fun provideEpisodesRepo(context: Context): EpisodeRepository {
    return EpisodeRepository.getInstance(
      provideEpisodesDao(
        context
      )
    )
  }

  /**
   * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
   * [ViewModel] objects.
   */
  fun provideMainViewModelFactory(
    context: Context
  ): ViewModelProvider.Factory {
    return MainViewModelFactory(
      provideEpisodesRepo(
        context
      )
    )
  }

}
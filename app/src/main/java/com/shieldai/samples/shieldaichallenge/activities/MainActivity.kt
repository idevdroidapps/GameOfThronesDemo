package com.shieldai.samples.shieldaichallenge.activities

import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.gson.Gson
import com.shieldai.samples.shieldaichallenge.R
import com.shieldai.samples.shieldaichallenge.data.models.Episode
import com.shieldai.samples.shieldaichallenge.databinding.ActivityMainBinding
import com.shieldai.samples.shieldaichallenge.ui.adapters.EpisodesListAdapter
import com.shieldai.samples.shieldaichallenge.ui.viewmodels.MainViewModel
import com.shieldai.samples.shieldaichallenge.util.Injection

class MainActivity : AppCompatActivity() {

  lateinit var viewModel: MainViewModel
  lateinit var binding: ActivityMainBinding
  private var exoPlayer: SimpleExoPlayer? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // Get ViewModel
    viewModel = ViewModelProvider(
      this@MainActivity,
      Injection.provideMainViewModelFactory(this)
    ).get(MainViewModel::class.java)

    // DataBinding
    binding =
      DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
    binding.viewModel = viewModel
    binding.lifecycleOwner = this

    // Get List Adapter
    val listAdapter = listAdapter(viewModel, binding)
    binding.recyclerViewEpisodes.setHasFixedSize(true)
    binding.recyclerViewEpisodes.adapter = listAdapter

    // Upon observed changes of currentSelected
    observeSelectionChanges(viewModel, binding, listAdapter)

    // Restore Selected Position and Episode
    restorePreviousEpisode(viewModel)

  }

  override fun onStart() {
    super.onStart()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      initExoPlayer()
    }
  }

  override fun onResume() {
    super.onResume()
    if ((Build.VERSION.SDK_INT < Build.VERSION_CODES.N || exoPlayer == null)) {
      initExoPlayer()
    }
  }

  override fun onPause() {
    super.onPause()
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
      releasePlayer()
    }
  }

  override fun onStop() {
    super.onStop()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      releasePlayer()
    }
  }

  private fun buildMediaSource(sourceUrl: String): MediaSource {
    val uri = Uri.parse(sourceUrl)
    val dataSourceFactory = DefaultDataSourceFactory(this, "exoplayer_shieldai")
    return ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
  }

  private fun initExoPlayer() {
    exoPlayer = SimpleExoPlayer.Builder(this@MainActivity).build()
    binding.exoPlayer.player = exoPlayer
  }

  private fun listAdapter(
    viewModel: MainViewModel,
    binding: ActivityMainBinding
  ): EpisodesListAdapter {
    val listAdapter = EpisodesListAdapter(viewModel)
    listAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
      override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
        if (itemCount != 0) {
          viewModel.currentPosition.value?.let {
            binding.recyclerViewEpisodes.layoutManager?.scrollToPosition(it)
          }
        }
      }
    })
    return listAdapter
  }

  private fun releasePlayer() {
    exoPlayer?.apply {
      release()
    }
    exoPlayer = null
  }

  private fun observeSelectionChanges(
    viewModel: MainViewModel,
    binding: ActivityMainBinding,
    listAdapter: EpisodesListAdapter
  ) {
    viewModel.currentPosition.observe(this, Observer {
      // Set Previous Item to Unselected
      val previousVH =
        binding.recyclerViewEpisodes.findViewHolderForAdapterPosition(viewModel.previousPosition)
      previousVH?.itemView?.isSelected = false
      listAdapter.notifyItemChanged(viewModel.previousPosition)
      // Set Current Item to Selected
      val currentVH = binding.recyclerViewEpisodes.findViewHolderForAdapterPosition(it)
      currentVH?.itemView?.isSelected = true
      listAdapter.notifyItemChanged(it)
      // Start Video
      startPlayer()
      // Reset previouslySelected
      viewModel.previousPosition = it
      // Save to SharedPreferences
      saveToSharedPreferences(viewModel, it)
    })
  }

  private fun restorePreviousEpisode(viewModel: MainViewModel) {
    val prefMngr = PreferenceManager.getDefaultSharedPreferences(this)
    val episodeJson = prefMngr.getString(PREF_PREVIOUS_EPISODE, "")
    val restoredPosition = prefMngr.getInt(PREF_PREVIOUS_POSITION, 0)
    val restoredEpisode = Gson().fromJson(episodeJson, Episode::class.java)
    if (restoredEpisode != null) {
      viewModel.setCurrentEpisode(restoredEpisode)
    } else {
      viewModel.firstEpisode.observe(this@MainActivity, Observer { episode ->
        episode?.let {
          viewModel.setCurrentEpisode(it)
        }
      })
    }
    viewModel.setCurrentPosition(restoredPosition)
  }

  private fun saveToSharedPreferences(viewModel: MainViewModel, selected: Int) {
    val previousEpisode = viewModel.currentEpisode
    val jsonEpisode = Gson().toJson(previousEpisode.value)

    val editor = PreferenceManager.getDefaultSharedPreferences(this).edit()
    editor.putString(PREF_PREVIOUS_EPISODE, jsonEpisode)
    editor.putInt(PREF_PREVIOUS_POSITION, selected)
    editor.apply()
  }

  private fun startPlayer() {
    viewModel.currentEpisode.value?.let { episode ->
      episode.video?.let { video ->
        video.sources?.let { sourceList ->
          val mediaSource = buildMediaSource(sourceList.first())
          exoPlayer?.playWhenReady = true
          exoPlayer?.prepare(mediaSource)
        }
      }
    }
  }

  companion object {
    private const val PREF_PREVIOUS_POSITION = "pref_previous_selected"
    private const val PREF_PREVIOUS_EPISODE = "pref_previous_episode"
  }

}


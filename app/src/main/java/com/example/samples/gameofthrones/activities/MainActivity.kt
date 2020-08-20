package com.example.samples.gameofthrones.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.example.samples.gameofthrones.R
import com.example.samples.gameofthrones.data.models.Episode
import com.example.samples.gameofthrones.databinding.ActivityMainBinding
import com.example.samples.gameofthrones.ui.adapters.EpisodesListAdapter
import com.example.samples.gameofthrones.ui.viewmodels.MainViewModel
import com.example.samples.gameofthrones.util.Injection

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // Get ViewModel
    val viewModel = ViewModelProvider(
      this@MainActivity,
      Injection.provideMainViewModelFactory(this)
    ).get(MainViewModel::class.java)

    // Get List Adapter
    val listAdapter = EpisodesListAdapter(viewModel)

    // DataBinding
    val binding: ActivityMainBinding =
      DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
    binding.viewmodel = viewModel
    binding.lifecycleOwner = this
    binding.recyclerViewEpisodes.adapter = listAdapter

    // Upon observed changes of currentSelected
    observeSelectionChanges(viewModel, binding, listAdapter)

    // Restore Selected Position and Episode
    restorePreviousEpisode(viewModel, binding)

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
      // Reset previouslySelected
      viewModel.previousPosition = it
      // Save to SharedPreferences
      saveToSharedPreferences(viewModel, it)
    })
  }

  private fun restorePreviousEpisode(
    viewModel: MainViewModel,
    binding: ActivityMainBinding
  ) {
    val prefMngr = PreferenceManager.getDefaultSharedPreferences(this)
    val restoredPosition = prefMngr.getInt(PREF_PREVIOUS_POSITION, 0)
    val episodeJson = prefMngr.getString(PREF_PREVIOUS_EPISODE, "")
    val restoredEpisode = Gson().fromJson(episodeJson, Episode::class.java)
    if (restoredEpisode != null) {
      viewModel.setEpisode(restoredEpisode)
    } else {
      viewModel.firstEpisode.observe(this@MainActivity, Observer { episode ->
        episode?.let {
          viewModel.setEpisode(it)
        }
      })
    }
    viewModel.setPosition(restoredPosition)
    binding.recyclerViewEpisodes.post {
      binding.recyclerViewEpisodes.layoutManager?.scrollToPosition(restoredPosition)
    }
  }

  private fun saveToSharedPreferences(viewModel: MainViewModel, selected: Int) {
    val previousEpisode = viewModel.episode
    val jsonEpisode = Gson().toJson(previousEpisode.value)

    val editor = PreferenceManager.getDefaultSharedPreferences(this).edit()
    editor.putString(PREF_PREVIOUS_EPISODE, jsonEpisode)
    editor.putInt(PREF_PREVIOUS_POSITION, selected)
    editor.apply()
  }

  companion object {
    private const val PREF_PREVIOUS_POSITION = "pref_previous_selected"
    private const val PREF_PREVIOUS_EPISODE = "pref_previous_episode"
  }

}


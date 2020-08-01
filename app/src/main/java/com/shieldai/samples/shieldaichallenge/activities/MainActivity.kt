package com.shieldai.samples.shieldaichallenge.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.shieldai.samples.shieldaichallenge.R
import com.shieldai.samples.shieldaichallenge.data.models.Episode
import com.shieldai.samples.shieldaichallenge.databinding.ActivityMainBinding
import com.shieldai.samples.shieldaichallenge.ui.adapters.EpisodesListAdapter
import com.shieldai.samples.shieldaichallenge.ui.viewmodels.MainViewModel
import com.shieldai.samples.shieldaichallenge.util.Injection

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // Get ViewModel
    val viewModel = ViewModelProvider(
      this@MainActivity,
      Injection.provideMainViewModelFactory(this)
    ).get(MainViewModel::class.java)

    // DataBinding
    val binding: ActivityMainBinding =
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

  private fun listAdapter(
    viewModel: MainViewModel,
    binding: ActivityMainBinding
  ): EpisodesListAdapter {
    val listAdapter = EpisodesListAdapter(viewModel)
    listAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
      override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
        if(itemCount != 0){
          viewModel.currentPosition.value?.let {
            binding.recyclerViewEpisodes.layoutManager?.scrollToPosition(it)
          }
        }
      }
    })
    return listAdapter
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

  private fun restorePreviousEpisode(viewModel: MainViewModel) {
    val prefMngr = PreferenceManager.getDefaultSharedPreferences(this)
    val episodeJson = prefMngr.getString(PREF_PREVIOUS_EPISODE, "")
    val restoredPosition = prefMngr.getInt(PREF_PREVIOUS_POSITION, 0)
    val restoredEpisode = Gson().fromJson(episodeJson, Episode::class.java)
    if (restoredEpisode != null) {
      viewModel.setCurrentEpisode(restoredEpisode)
    } else {
      viewModel.firstEpisode.observe(this@MainActivity, Observer { episodeWithVideo ->
        episodeWithVideo?.let {
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

  companion object {
    private const val PREF_PREVIOUS_POSITION = "pref_previous_selected"
    private const val PREF_PREVIOUS_EPISODE = "pref_previous_episode"
  }

}


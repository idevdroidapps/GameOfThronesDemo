package com.shieldai.samples.shieldaichallenge.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import com.shieldai.samples.shieldaichallenge.R
import com.shieldai.samples.shieldaichallenge.databinding.ActivityMainBinding
import com.shieldai.samples.shieldaichallenge.ui.adapters.EpisodesListAdapter
import com.shieldai.samples.shieldaichallenge.ui.clicklisteners.EpisodeClickListener
import com.shieldai.samples.shieldaichallenge.ui.viewmodels.MainViewModel
import com.shieldai.samples.shieldaichallenge.ui.di.Injection
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val viewModel = ViewModelProvider(
      this@MainActivity,
      Injection.provideMainViewModelFactory(this)
    ).get(MainViewModel::class.java)

    val listAdapter = EpisodesListAdapter(viewModel)
    val binding: ActivityMainBinding =
      DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
    binding.viewmodel = viewModel
    binding.lifecycleOwner = this
    binding.recyclerViewEpisodes.adapter = listAdapter

    // Upon observed changes of currentSelected
    observeSelectionChanges(viewModel, binding, listAdapter)

  }

  private fun observeSelectionChanges(
    viewModel: MainViewModel,
    binding: ActivityMainBinding,
    listAdapter: EpisodesListAdapter
  ) {
    viewModel.currentSelected.observe(this, Observer {
      // Set Previous Item to Unselected
      val previousVH =
        binding.recyclerViewEpisodes.findViewHolderForAdapterPosition(viewModel.previouslySelected)
      previousVH?.itemView?.isSelected = false
      listAdapter.notifyItemChanged(viewModel.previouslySelected)
      // Set Current Item to Selected
      val currentVH = binding.recyclerViewEpisodes.findViewHolderForAdapterPosition(it)
      currentVH?.itemView?.isSelected = true
      listAdapter.notifyItemChanged(it)
      // Reset previouslySelected
      viewModel.previouslySelected = it
      // Save to SharedPreferences
      saveToSharedPreferences(it)
    })
  }

  private fun saveToSharedPreferences(selected: Int) {
    val editor = PreferenceManager.getDefaultSharedPreferences(this).edit()
    editor.putInt(PREF_PREVIOUS_SELECTED, selected)
    editor.apply()
  }

  companion object {
    private const val PREF_PREVIOUS_SELECTED = "pref_previous_selected"
  }

}


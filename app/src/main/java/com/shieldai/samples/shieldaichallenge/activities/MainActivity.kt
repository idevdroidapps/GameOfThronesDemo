package com.shieldai.samples.shieldaichallenge.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
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

    val clickListener = EpisodeClickListener{ episode ->
      viewModel.currentEpisode(episode)
    }
    val listAdapter = EpisodesListAdapter(clickListener)

    val binding: ActivityMainBinding =
      DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
    binding.viewmodel = viewModel
    binding.lifecycleOwner = this
    binding.recyclerViewEpisodes.adapter = listAdapter

  }


}


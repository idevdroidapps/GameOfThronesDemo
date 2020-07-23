package com.shieldai.samples.shieldaichallenge.ui.adapters

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.shieldai.samples.shieldaichallenge.data.models.Episode

@BindingAdapter("episodesData")
fun bindEpisodesRecyclerView(
  recyclerView: RecyclerView,
  data: LiveData<List<Episode>>?
) {
  val adapter = recyclerView.adapter as EpisodesListAdapter
  adapter.submitList(data?.value)
}

@BindingAdapter("originalImage")
fun ImageView.originalImage(originalUrl: String?) {
  originalUrl?.let { url ->
    val options = RequestOptions()
      .diskCacheStrategy(DiskCacheStrategy.NONE)
      .fitCenter()
    try {
      Glide
        .with(this.context)
        .load(url)
        .apply(options)
        .into(this)
    } catch (e: Exception) {
      Log.e("Glide", "Original Image Failed in Glide")
    }

  }
}

@BindingAdapter("mediumImage")
fun ImageView.mediumImage(mediumUrl: String?) {
  mediumUrl?.let { url ->
    val options = RequestOptions()
      .override(200, 100)
    try {
      Glide
        .with(this.context)
        .load(url)
        .apply(options)
        .into(this)
    } catch (e: Exception) {
      Log.e("Glide", "Medium Image Failed in Glide")
    }

  }
}

package com.example.samples.gameofthrones.ui.adapters

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.text.Html
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.samples.gameofthrones.data.models.Episode

@BindingAdapter("episodesData")
fun bindEpisodesRecyclerView(
  recyclerView: RecyclerView,
  data: LiveData<List<Episode>>?
) {
  val adapter = recyclerView.adapter as EpisodesListAdapter
  adapter.submitList(data?.value)
}

@BindingAdapter("htmlText")
fun TextView.setHtmlText(summary: String?){
  summary?.let {
    text = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
      Html.fromHtml(it, Html.FROM_HTML_MODE_COMPACT)
    } else {
      Html.fromHtml(it)
    }
  }
}

@BindingAdapter("itemTitle")
fun TextView.setItemTitle(episode: Episode?){
  episode?.let {
    val itemText = "S${episode.season} Ep${episode.number}: ${episode.name}"
    text = itemText
  }
}

@BindingAdapter("originalImage")
fun ImageView.originalImage(originalUrl: String?) {
  originalUrl?.let { url ->
    val options = RequestOptions()
      .override(1280, 720)
      .diskCacheStrategy(DiskCacheStrategy.NONE)
      .placeholder(ColorDrawable(Color.WHITE))
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
      .diskCacheStrategy(DiskCacheStrategy.NONE)
      .placeholder(ColorDrawable(Color.WHITE))
      .fitCenter()
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

package com.shieldai.samples.shieldaichallenge.ui.adapters

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.text.Html
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.shieldai.samples.shieldaichallenge.data.models.Episode
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalCoroutinesApi::class)
@BindingAdapter("episodesData")
fun bindEpisodesRecyclerView(
  recyclerView: RecyclerView,
  data: Flow<PagingData<Episode>>?
) {
  val adapter = recyclerView.adapter as EpisodesListAdapter
  GlobalScope.launch(Dispatchers.Main) {
    data?.collectLatest {
      adapter.submitData(it)
    }
  }
}

@BindingAdapter("htmlText")
fun TextView.setHtmlText(summary: String?){
  summary?.let {
    text = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
      Html.fromHtml(it, Html.FROM_HTML_MODE_COMPACT)
    } else {
      @Suppress("DEPRECATION")
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

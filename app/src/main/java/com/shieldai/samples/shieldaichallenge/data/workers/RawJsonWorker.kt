package com.shieldai.samples.shieldaichallenge.data.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.shieldai.samples.shieldaichallenge.R
import com.shieldai.samples.shieldaichallenge.data.database.EpisodeDatabase
import com.shieldai.samples.shieldaichallenge.data.models.Episode
import com.shieldai.samples.shieldaichallenge.data.models.Video
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.*

class RawJsonWorker(private val context: Context, workerParams: WorkerParameters) :
  CoroutineWorker(context, workerParams) {

  override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
    try {
      val dao = EpisodeDatabase.getInstance(context).dao
      var episodes: List<Episode> = emptyList()
      var videos: List<Video> = emptyList()

      var jsonString = readFileFromRawDirectory(R.raw.game_of_thrones_episodes)
      jsonString?.let { json ->
        episodes = json.parseJson(EPISODES)
      }

      jsonString = readFileFromRawDirectory(R.raw.google_movie_samples)
      jsonString?.let { json ->
        videos = json.parseJson(VIDEOS)
      }

      episodes.forEachIndexed { index, episode ->
        episode.video = videos[index]
      }
      dao.insertEpisodes(episodes)

    } catch (e: Exception) {
      Result.failure()
    }
    Result.success()
  }

  private fun readFileFromRawDirectory(resourceId: Int): String? {
    val iStream: InputStream = context.resources.openRawResource(resourceId)
    var byteStream: ByteArrayOutputStream? = null
    try {
      val buffer = ByteArray(iStream.available())
      iStream.read(buffer)
      byteStream = ByteArrayOutputStream()
      byteStream.write(buffer)
      byteStream.close()
      iStream.close()
    } catch (e: IOException) {
      Result.failure()
    }
    return byteStream.toString()
  }

  @Suppress("UNCHECKED_CAST")
  private fun <T> String.parseJson(listType: String): List<T> {
    val itemList = ArrayList<T>()
    try {
      val jsonObject: JSONObject? = JSONObject(this)
      if (jsonObject != null) {
        val items = if (listType == EPISODES) {
          jsonObject.optJSONArray(EPISODES)
        } else {
          jsonObject.optJSONArray(VIDEOS)
        }
        items?.let {
          for (i in 0 until it.length()) {
            if (listType == EPISODES) {
              itemList.add(
                Gson().fromJson(it.getJSONObject(i).toString(), Episode::class.java) as T
              )
            } else {
              val item = it.getJSONObject(i).toString()
              val video = Gson().fromJson(item, Video::class.java)
              itemList.add(video as T)
            }
          }
        }
      } else {
        throw JSONException("JSON Exception")
      }
    } catch (e: Throwable) {
      Result.failure()
    }
    return itemList
  }

  companion object {
    const val EPISODES = "episodes"
    const val VIDEOS = "videos"
  }

}
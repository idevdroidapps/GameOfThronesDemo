package com.example.samples.gameofthrones.data.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.example.samples.gameofthrones.R
import com.example.samples.gameofthrones.data.database.EpisodeDatabase
import com.example.samples.gameofthrones.data.models.Episode
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
      val jsonString = readFileFromRawDirectory(R.raw.game_of_thrones_episodes)
      jsonString?.let { json ->
        val episodes = parseEpisodes(json)
        val dao = EpisodeDatabase.getInstance(context).dao
        dao.insertEpisodes(episodes)
      }
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

  private fun parseEpisodes(jsonString: String): List<Episode> {
    val episodeList = ArrayList<Episode>()
    try {
      val jsonObject: JSONObject? = JSONObject(jsonString)
      if (jsonObject != null) {
        val episodes = jsonObject.optJSONArray("episodes")
        episodes?.let {
          for (i in 0 until it.length()) {
            val episode = Gson().fromJson(it.getJSONObject(i).toString(), Episode::class.java)
            episodeList.add(episode)
          }
        }
      } else {
        throw JSONException("JSON Exception")
      }
    } catch (e: Throwable) {
      Result.failure()
    }
    return episodeList
  }

}
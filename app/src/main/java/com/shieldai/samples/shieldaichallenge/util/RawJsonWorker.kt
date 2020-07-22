package com.shieldai.samples.shieldaichallenge.util

import android.content.Context
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.shieldai.samples.shieldaichallenge.R
import com.shieldai.samples.shieldaichallenge.data.models.Episode
import com.shieldai.samples.shieldaichallenge.data.models.Image
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.ArrayList

class RawJsonWorker(val context: Context, workerParams: WorkerParameters) :
  CoroutineWorker(context, workerParams) {

  override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
    try {
      val jsonString = readFileFromRawDirectory(R.raw.game_of_thrones_episodes)
      jsonString?.let { json ->
        val episodes = parseEpisodes(json)
        episodes.forEach {
          Log.d("JSON", it.name)
          Log.d("JSON", it.image.original)
            //TODO INSERT TO ROOM DB
        }
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
      e.printStackTrace()
    }
    return byteStream.toString()
  }

  private fun parseEpisodes(jsonString: String): List<Episode> {
    val episodeList = ArrayList<Episode>()
    try {
      val jsonObject: JSONObject? = JSONObject(jsonString)
      if (jsonObject != null) {
        val episodes = jsonObject.optJSONArray("episodes")
        if (episodes != null) {
          for (i in 0 until episodes.length()) {
            val episode = Gson().fromJson(episodes.getJSONObject(i).toString(), Episode::class.java)
            episodeList.add(episode)
          }
        }
      } else {
        throw JSONException("JSON Exception")
      }
    } catch (e: Throwable) {
      e.printStackTrace()
    }
    return episodeList
  }

}
package com.shieldai.samples.shieldaichallenge.util

import android.content.Context
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
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

  fun parseEpisodes(jsonString: String): List<Episode> {
    val episodeList = ArrayList<Episode>()
    try {
      val jsonObject: JSONObject? = JSONObject(jsonString)
      if (jsonObject != null) {
        val episodes = jsonObject.optJSONArray("episodes")
        if (episodes != null) {
          for (i in 0 until episodes.length()) {

            val episode = episodes.getJSONObject(i)
            val id = episode.optInt("id")
            val url = episode.optString("url")
            val name = episode.optString("name")
            val season = episode.optInt("season")
            val number = episode.optInt("number")
            val airdate = episode.optString("airdate")
            val airtime = episode.optString("airtime")
            val airstamp = episode.optString("airstamp")
            val runtime = episode.optInt("runtime")
            val image = episode.optJSONObject("image")
            val summary = episode.optString("summary")

            var _episode: Episode? = null
            var _image: Image? = null

            image?.let {
              _image = Image(it.optString("medium"), it.optString("original"))
            }
            _image?.let {
              _episode = Episode(
                airdate,
                airstamp,
                airtime,
                id,
                it,
                name,
                number,
                runtime,
                season,
                summary,
                url
              )
            }
            _episode?.let {
              episodeList.add(it)
            }
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
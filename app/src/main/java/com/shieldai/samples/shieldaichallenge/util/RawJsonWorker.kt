package com.shieldai.samples.shieldaichallenge.util

import android.content.Context
import android.widget.Toast
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.shieldai.samples.shieldaichallenge.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

class RawJsonWorker(val context: Context, workerParams: WorkerParameters)
    : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val jsonString = readFileFromRawDirectory(R.raw.game_of_thrones_episodes)
            GlobalScope.launch {
                withContext(Dispatchers.Main){
                    Toast.makeText(context, jsonString?.substring(0, 20), Toast.LENGTH_SHORT).show()
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

}
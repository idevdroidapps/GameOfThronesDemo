package com.shieldai.samples.shieldaichallenge.util

import android.content.Context
import android.widget.Toast
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RawJsonWorker(val context: Context, workerParams: WorkerParameters)
    : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {

        try {
            GlobalScope.launch {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "WORKER WORKED", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            Result.failure()
        }

        Result.success()
    }

}
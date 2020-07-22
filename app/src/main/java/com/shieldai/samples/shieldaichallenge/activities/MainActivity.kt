package com.shieldai.samples.shieldaichallenge.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.shieldai.samples.shieldaichallenge.R
import com.shieldai.samples.shieldaichallenge.util.RawJsonWorker
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        WorkManager.getInstance(this).enqueue(getWorkRequest())

    }

    private fun getWorkRequest(): OneTimeWorkRequest {
        val constraints = Constraints.Builder()
            .build()
        return OneTimeWorkRequestBuilder<RawJsonWorker>()
            .setConstraints(constraints)
            .build()
    }
}

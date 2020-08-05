package com.shieldai.samples.shieldaichallenge.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.shieldai.samples.shieldaichallenge.data.models.Episode
import com.shieldai.samples.shieldaichallenge.data.workers.RawJsonWorker

@Database(entities = [Episode::class], version = 1, exportSchema = false)
abstract class EpisodeDatabase: RoomDatabase() {

  abstract val dao: EpisodeDao

  companion object{

    @Volatile
    private var INSTANCE: EpisodeDatabase? = null

    fun getInstance(context: Context): EpisodeDatabase {
      return INSTANCE ?: synchronized(this) {
        INSTANCE ?: buildRoomDatabase(context).also { INSTANCE = it }
      }
    }

    private fun buildRoomDatabase(context: Context): EpisodeDatabase {
      return Room.databaseBuilder(context, EpisodeDatabase::class.java, "episode_database")
        .addCallback(EpisodeDatabaseCallback(context))
        .build()
    }

  }

  private class EpisodeDatabaseCallback(val context: Context) : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
      super.onCreate(db)
      val constraints = Constraints.Builder()
        .build()
      val oneTimeWorkRequest = OneTimeWorkRequestBuilder<RawJsonWorker>()
        .setConstraints(constraints)
        .build()
      WorkManager.getInstance(context).enqueue(oneTimeWorkRequest)
    }
  }

}
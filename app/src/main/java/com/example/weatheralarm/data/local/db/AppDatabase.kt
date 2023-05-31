package com.example.weatheralarm.data.local.db

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import androidx.room.*
import com.example.weatheralarm.data.local.userDataDao
import com.example.weatheralarm.data.model.userData


@Database(entities = [userData::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun dao(): userDataDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "database"
                )   .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}
package com.example.weatheralarm.model

import androidx.lifecycle.LiveData

class Repository(mDatabase: AppDatabase) {

    private val dao = mDatabase.dao()
    val allUsers: LiveData<List<userData>> = dao.getAll()

    companion object {
        private var sInstance: Repository? = null
        fun getInstance(database: AppDatabase): Repository {
            return sInstance
                ?: synchronized(this) {
                    val instance = Repository(database)
                    sInstance = instance
                    instance
                }
        }
    }

    suspend fun insert(entity: userData) {
        dao.insert(entity)
    }

    suspend fun delete(entity: userData) {
        dao.delete(entity)
    }

}
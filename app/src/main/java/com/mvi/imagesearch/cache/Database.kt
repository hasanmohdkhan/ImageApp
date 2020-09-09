package com.mvi.imagesearch.cache

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.mvi.imagesearch.constants.Constant.Companion.DATABASE_NAME
import com.mvi.imagesearch.model.ApiModel


@Database(entities = [ApiModel::class], version = 2)
abstract class LiveDatabase : RoomDatabase() {
    abstract val dao: LiveDao
}

private lateinit var INSTANCE: LiveDatabase

fun getDatabase(context: Context): LiveDatabase {

    synchronized(LiveDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                LiveDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
    }

    return INSTANCE
}
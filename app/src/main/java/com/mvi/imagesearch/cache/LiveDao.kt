package com.mvi.imagesearch.cache

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mvi.imagesearch.model.ApiModel

@Dao
interface LiveDao {
    @Query("SELECT * FROM api  ")
    fun getList(): LiveData<List<ApiModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list : List<ApiModel>)

    @Query("SELECT * FROM api WHERE keyword=:query ")
    fun searchKeyWord(query :String):List<ApiModel>


}
package com.dicoding.asclepius.view.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CancerHistoryDao {
    @Insert
    suspend fun addToHistory(cancerHistory: DataCancer)

    @Query("SELECT * FROM cancer_history")
    fun getHistoryCancer(): LiveData<List<DataCancer>>

    @Query("SELECT count(*) FROM cancer_history WHERE cancer_history.id = :id")
    suspend fun checkHistoryCancer(id: Int): Int

    @Query("DELETE FROM cancer_history WHERE cancer_history.id = :id")
    suspend fun removeHistoryCancer(id: Int): Int
}
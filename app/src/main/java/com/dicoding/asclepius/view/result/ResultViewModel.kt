package com.dicoding.asclepius.view.result

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.dicoding.asclepius.view.data.CancerDatabase
import com.dicoding.asclepius.view.data.CancerHistoryDao
import com.dicoding.asclepius.view.data.DataCancer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ResultViewModel(application: Application) : AndroidViewModel(application) {

    private var cancerDao: CancerHistoryDao?
    private var cancerDb: CancerDatabase?

    init {
        cancerDb = CancerDatabase.getDatabase(application)
        cancerDao = cancerDb?.cancerHistoryDao()
    }

    fun addToHistory(id: Int, result: String, score: Float, image: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val cancer = DataCancer(
                id,
                result,
                score,
                image
            )
            cancerDao?.addToHistory(cancer)
        }
    }

    fun removeDataFromHistory(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            cancerDao?.removeHistoryCancer(id)
        }
    }
}
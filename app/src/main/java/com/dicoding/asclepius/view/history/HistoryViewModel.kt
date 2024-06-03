package com.dicoding.asclepius.view.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dicoding.asclepius.view.data.CancerDatabase
import com.dicoding.asclepius.view.data.CancerHistoryDao
import com.dicoding.asclepius.view.data.DataCancer

class HistoryViewModel(application: Application): AndroidViewModel(application) {

        private var userDao: CancerHistoryDao?
        private var userDb: CancerDatabase?

        init {
            userDb = CancerDatabase.getDatabase(application)
            userDao = userDb?.cancerHistoryDao()
        }

        fun getCancerHistory(): LiveData<List<DataCancer>>? {
            return userDao?.getHistoryCancer()
        }
}
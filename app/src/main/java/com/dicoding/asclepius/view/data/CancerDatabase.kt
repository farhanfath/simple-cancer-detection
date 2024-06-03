package com.dicoding.asclepius.view.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [DataCancer::class],
    version = 1
)
abstract class CancerDatabase: RoomDatabase(){
    companion object {
        private var Instance: CancerDatabase? = null

        fun getDatabase(context: Context): CancerDatabase?{
            if (Instance ==null) {
                synchronized(CancerDatabase::class){
                    Instance = Room.databaseBuilder(context.applicationContext, CancerDatabase::class.java, "cancer_history").build()
                }
            }
            return Instance
        }
    }

    abstract fun cancerHistoryDao(): CancerHistoryDao
}
package com.dicoding.asclepius.view.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "cancer_history")
data class DataCancer(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val result: String,
    val score: Float,
    val image: String
): Serializable

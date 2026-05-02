package com.example.appcombncc.data.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SerieDao {
    @Query("SELECT codigo FROM serie ORDER BY codigo")
    fun getAllSeries(): Flow<List<String>>
}
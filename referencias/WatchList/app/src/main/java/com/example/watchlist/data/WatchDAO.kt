package com.example.watchlist.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchDAO {
    @Insert
    suspend fun insert(watchEntity: WatchEntity)

    @Update
    suspend fun update(watchEntity: WatchEntity)

    @Delete
    suspend fun delete(watchEntity: WatchEntity)

    @Query("SELECT * FROM watchTb ORDER BY title")
    fun getAllTitles(): Flow<List<WatchEntity>>

    @Query("SELECT * FROM watchTb WHERE id=:id")
    fun getTitlesById(id: Int): Flow<WatchEntity>
}
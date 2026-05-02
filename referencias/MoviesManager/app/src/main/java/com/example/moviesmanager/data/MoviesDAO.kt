package com.example.moviesmanager.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDAO {
    @Insert
    suspend fun insert(moviesEntity: MoviesEntity)

    @Update
    suspend fun update(moviesEntity: MoviesEntity)

    @Delete
    suspend fun delete(moviesEntity: MoviesEntity)

    @Query("SELECT * FROM moviesTb ORDER BY title")
    fun getAllTitles(): Flow<List<MoviesEntity>>

    @Query("SELECT * FROM moviesTb WHERE id=:id")
    fun getTitlesById(id: Int): Flow<MoviesEntity>

    @Query("SELECT * FROM moviesTb ORDER BY title ASC")
    fun getAllTitlesSorted(): Flow<List<MoviesEntity>>

    @Query("SELECT * FROM moviesTb ORDER BY rating DESC")
    fun getAllRatingsSorted(): Flow<List<MoviesEntity>>

    @Query("SELECT COUNT(*) > 0 FROM moviesTb WHERE title = :title")
    suspend fun isTitleExists(title: String): Boolean

    @Query("SELECT * FROM moviesTb WHERE watched = 1")
    fun getWatchedMovies(): Flow<List<MoviesEntity>>

    @Query("SELECT * FROM moviesTb WHERE watched = 0")
    fun getUnwatchedMovies(): Flow<List<MoviesEntity>>

}
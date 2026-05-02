package com.example.moviesmanager.repository

import com.example.moviesmanager.data.MoviesDAO
import com.example.moviesmanager.domain.Movies
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

class MoviesRepository(private val moviesDAO: MoviesDAO) {

    suspend fun insert(movies: Movies): Boolean {
        if (moviesDAO.isTitleExists(movies.title)) {
            return false
        }
        moviesDAO.insert(movies.toEntity())
        return true
    }

    suspend fun update(movies: Movies) {
        moviesDAO.update(movies.toEntity())
    }

    suspend fun delete(movies: Movies) {
        moviesDAO.delete(movies.toEntity())
    }

    fun getAllTitles(): Flow<List<Movies>> {
        return moviesDAO.getAllTitles().map { list ->
            list.map {
                it.toDomain()
            }
        }
    }

    fun getTitlesById(id: Int): Flow<Movies> {
        return moviesDAO.getTitlesById(id).filterNotNull().map { it.toDomain() }
    }

    fun getAllTitlesSorted(): Flow<List<Movies>> {
        return moviesDAO.getAllTitlesSorted().map { list ->
            list.map { it.toDomain() }
        }
    }

    fun getAllRatingsSorted(): Flow<List<Movies>> {
        return moviesDAO.getAllRatingsSorted().map { list ->
            list.map { it.toDomain() }
        }
    }
    fun getWatchedMovies(): Flow<List<Movies>> {
        return moviesDAO.getWatchedMovies().map { list ->
            list.map { it.toDomain() }
        }
    }

    fun getUnwatchedMovies(): Flow<List<Movies>> {
        return moviesDAO.getUnwatchedMovies().map { list ->
            list.map { it.toDomain() }
        }
    }
}
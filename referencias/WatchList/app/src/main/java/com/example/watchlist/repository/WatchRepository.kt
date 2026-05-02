package com.example.watchlist.repository

import com.example.watchlist.data.WatchDAO
import com.example.watchlist.domain.Watch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

class WatchRepository(private val watchDAO: WatchDAO) {

    suspend fun insert(watch: Watch) {
        watchDAO.insert(watch.toEntity())
    }

    suspend fun update(watch: Watch) {
        watchDAO.update(watch.toEntity())
    }

    suspend fun delete(watch: Watch) {
        watchDAO.delete(watch.toEntity())
    }

    fun getAllTitles(): Flow<List<Watch>> {
        return watchDAO.getAllTitles().map { list ->
            list.map {
                it.toDomain()
            }
        }
    }

    fun getTitlesById(id: Int): Flow<Watch> {
        return watchDAO.getTitlesById(id).filterNotNull().map { it.toDomain() }
    }
}
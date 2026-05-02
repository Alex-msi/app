package com.example.watchlist.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.watchlist.domain.Watch


@Entity(tableName = "watchTb")
data class WatchEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val description: String,
    val status: String
) {
    fun toDomain(): Watch {
        return Watch(id, title, description, status)
    }
}


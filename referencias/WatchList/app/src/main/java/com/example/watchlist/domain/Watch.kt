package com.example.watchlist.domain

import com.example.watchlist.data.WatchEntity

data class Watch(
    var id: Int = 0,
    var title: String,
    var description: String,
    var status: String

) {
    fun toEntity(): WatchEntity {
        return WatchEntity(id, title, description, status)
    }
}

package com.example.watchlist

import android.app.Application
import com.example.watchlist.data.WatchDatabase
import com.example.watchlist.repository.WatchRepository

class WatchApplication:Application() {
    val database by lazy { WatchDatabase.getDatabase(this) }
    val repository by lazy { WatchRepository(database.watchDAO()) }
}
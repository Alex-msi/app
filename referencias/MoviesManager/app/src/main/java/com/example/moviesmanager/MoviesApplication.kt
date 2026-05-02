package com.example.moviesmanager

import android.app.Application
import com.example.moviesmanager.data.MoviesDatabase
import com.example.moviesmanager.repository.MoviesRepository

class MoviesApplication:Application() {
    val database by lazy { MoviesDatabase.getDatabase(this) }
    val repository by lazy { MoviesRepository(database.MoviesDAO()) }
}
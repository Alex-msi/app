package com.example.moviesmanager.domain

import com.example.moviesmanager.data.MoviesEntity

data class Movies(
    var id: Int = 0,
    var title: String,
    var year: Int,
    var studio: String,
    var duration: Int,
    var watched: Boolean,
    var rating: Float?,
    var genre: String

) {
    fun toEntity(): MoviesEntity {
        return MoviesEntity(id, title, year, studio, duration, watched, rating, genre)
    }
}

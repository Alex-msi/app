package com.example.moviesmanager.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.moviesmanager.domain.Movies


//@Entity(tableName = "watchTb")
@Entity(tableName = "moviesTb",
       indices = [Index(value = ["title"], unique = true)]
)
data class MoviesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val year: Int,
    val studio: String,
    val duration: Int,
    val watched: Boolean = false,
    val rating: Float? = null,
    val genre: String
) {
    fun toDomain(): Movies {
        return Movies(id, title, year, studio, duration, watched, rating, genre)
    }
}


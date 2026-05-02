package com.example.watchlist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [WatchEntity::class], version = 1, exportSchema = false)
abstract class WatchDatabase : RoomDatabase() {
    abstract fun watchDAO(): WatchDAO

    companion object {
        @Volatile
        private var INSTANCE: WatchDatabase? = null

        fun getDatabase(context: Context): WatchDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WatchDatabase::class.java,
                    "watchroom.db" // agendaroom
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
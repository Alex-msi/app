package com.example.appcombncc.repository

import com.example.appcombncc.data.dao.SerieDao

class SerieRepository(private val serieDao: SerieDao) {
    fun getAllSeries() = serieDao.getAllSeries()
}
package com.example.appcombncc.repository

import com.example.appcombncc.data.dao.EtapaDao

class EtapaRepository(private val etapaDao: EtapaDao) {
    fun getAllEtapas() = etapaDao.getAll()

    suspend fun countEtapas(): Int = etapaDao.countEtapas()
}
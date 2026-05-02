package com.example.appcombncc.repository

import com.example.appcombncc.data.dao.CompetenciaDao
import com.example.appcombncc.data.dao.EixoDao

class EixoCompetenciaRepository(
    private val eixoDao: EixoDao,
    private val competenciaDao: CompetenciaDao
) {
    fun getEixosBySerie(serieCodigo: String) = eixoDao.getBySerie(serieCodigo)
    fun getCompetenciasByEtapa(etapaCodigo: String) = competenciaDao.getByEtapa(etapaCodigo)
}
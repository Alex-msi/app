package com.example.appcombncc.repository

import com.example.appcombncc.data.dao.CompetenciaDao
import com.example.appcombncc.data.dao.EixoDao

class EixoCompetenciaRepository(
    private val eixoDao: EixoDao,
    private val competenciaDao: CompetenciaDao
) {
    fun getEixosBySerie(serieCodigo: String) = eixoDao.getBySerie(serieCodigo)
    fun getResumoEixosPorSerie(serieCodigo: String) = eixoDao.getResumoEixosPorSerie(serieCodigo)
    fun getResumoEixosPorEtapa(etapaCodigo: String, habilidadeLike: String) =
        eixoDao.getResumoEixosPorEtapa(etapaCodigo, habilidadeLike)
    fun getResumoObjetosPorSerieEixo(serieCodigo: String, eixoDescricao: String) =
        eixoDao.getResumoObjetosPorSerieEixo(serieCodigo, eixoDescricao)
    fun getResumoObjetosPorEtapaEixo(etapaCodigo: String, habilidadeLike: String, eixoDescricao: String) =
        eixoDao.getResumoObjetosPorEtapaEixo(etapaCodigo, habilidadeLike, eixoDescricao)
    fun getCompetenciasByEtapa(etapaCodigo: String) = competenciaDao.getByEtapa(etapaCodigo)

}
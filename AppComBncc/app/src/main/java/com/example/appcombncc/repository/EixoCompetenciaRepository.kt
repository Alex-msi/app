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
    fun getResumoObjetosPorSerieEixo(serieCodigo: String, eixoCodigo: String) =
        eixoDao.getResumoObjetosPorSerieEixo(serieCodigo, eixoCodigo)
    fun getResumoObjetosPorEtapaEixo(etapaCodigo: String, habilidadeLike: String, eixoCodigo: String) =
        eixoDao.getResumoObjetosPorEtapaEixo(etapaCodigo, habilidadeLike, eixoCodigo)
    fun getHabilidadesPorSerieEixoObjeto(serieCodigo: String, eixoCodigo: String, objetoId: Long) =
        eixoDao.getHabilidadesPorSerieEixoObjeto(serieCodigo, eixoCodigo, objetoId)
    fun getHabilidadesPorEtapaEixoObjeto(
        etapaCodigo: String,
        habilidadeLike: String,
        eixoCodigo: String,
        objetoId: Long
    ) = eixoDao.getHabilidadesPorEtapaEixoObjeto(etapaCodigo, habilidadeLike, eixoCodigo, objetoId)
    fun getHabilidadesPorSerieEixo(serieCodigo: String, eixoCodigo: String) =
        eixoDao.getHabilidadesPorSerieEixo(serieCodigo, eixoCodigo)
    fun getHabilidadesPorEtapaEixo(etapaCodigo: String, habilidadeLike: String, eixoCodigo: String) =
        eixoDao.getHabilidadesPorEtapaEixo(etapaCodigo, habilidadeLike, eixoCodigo)
    fun getHabilidadesPorCompetenciaEtapa(etapaCodigo: String) =
        eixoDao.getHabilidadesPorCompetenciaEtapa(etapaCodigo)
    fun getCompetenciasByEtapa(etapaCodigo: String) = competenciaDao.getByEtapa(etapaCodigo)
}
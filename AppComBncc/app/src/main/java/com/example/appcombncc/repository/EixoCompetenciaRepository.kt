package com.example.appcombncc.repository

import com.example.appcombncc.data.dao.CompetenciaDao
import com.example.appcombncc.data.dao.EixoDao

class EixoCompetenciaRepository(
    private val eixoDao: EixoDao,
    private val competenciaDao: CompetenciaDao
) {
    fun getEixosBySerie(serieCodigo: String) =
        eixoDao.getBySerie(serieCodigo)

    fun getResumoEixosPorSerie(habilidadeLike: String) =
        eixoDao.getResumoEixosPorSerie(habilidadeLike)

    fun getResumoEixosPorEtapa(habilidadeLike: String) =
        eixoDao.getResumoEixosPorEtapa(habilidadeLike)

    fun getResumoObjetosPorSerieEixo(habilidadeLike: String, eixoCodigo: String) =
        eixoDao.getResumoObjetosPorSerieEixo(habilidadeLike, eixoCodigo)

    fun getResumoObjetosPorEtapaEixo(habilidadeLike: String, eixoCodigo: String) =
        eixoDao.getResumoObjetosPorEtapaEixo(habilidadeLike, eixoCodigo)

    fun getHabilidadesPorSerieEixoObjeto(
        habilidadeLike: String,
        eixoCodigo: String,
        objetoId: Long
    ) = eixoDao.getHabilidadesPorSerieEixoObjeto(
        habilidadeLike,
        eixoCodigo,
        objetoId
    )

    fun getHabilidadesPorEtapaEixoObjeto(
        habilidadeLike: String,
        eixoCodigo: String,
        objetoId: Long
    ) = eixoDao.getHabilidadesPorEtapaEixoObjeto(
        habilidadeLike,
        eixoCodigo,
        objetoId
    )

    fun getHabilidadesPorSerieEixo(habilidadeLike: String, eixoCodigo: String) =
        eixoDao.getHabilidadesPorSerieEixo(habilidadeLike, eixoCodigo)

    fun getHabilidadesPorEtapaEixo(habilidadeLike: String, eixoCodigo: String) =
        eixoDao.getHabilidadesPorEtapaEixo(habilidadeLike, eixoCodigo)

    fun getHabilidadesPorCompetenciaEtapa(etapaCodigo: String) =
        eixoDao.getHabilidadesPorCompetenciaEtapa(etapaCodigo)

    fun getResumoCompetenciasPorEtapa(etapaCodigo: String) =
        eixoDao.getResumoCompetenciasPorEtapa(etapaCodigo)

    fun getCompetenciasByEtapa(etapaCodigo: String) =
        competenciaDao.getByEtapa(etapaCodigo)
}
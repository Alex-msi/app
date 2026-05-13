package com.example.appcombncc.repository

import com.example.appcombncc.data.dao.CompetenciaDao
import com.example.appcombncc.data.dao.EixoDao

class EixoCompetenciaRepository(
    private val eixoDao: EixoDao,
    private val competenciaDao: CompetenciaDao
) {
    fun getEixosBySerie(serieCodigo: String) =
        eixoDao.getBySerie(serieCodigo)

    fun getResumoEixos(habilidadeLike: String) =
        eixoDao.getResumoEixos(habilidadeLike)

    fun getResumoObjetosPorEixo(habilidadeLike: String, eixoCodigo: String) =
        eixoDao.getResumoObjetosPorEixo(habilidadeLike, eixoCodigo)

    fun getHabilidadesPorEixoObjeto(
        habilidadeLike: String,
        eixoCodigo: String,
        objetoId: Long
    ) = eixoDao.getHabilidadesPorEixoObjeto(
        habilidadeLike,
        eixoCodigo,
        objetoId
    )

    fun getHabilidadesPorEixo(
        habilidadeLike: String,
        eixoCodigo: String
    ) = eixoDao.getHabilidadesPorEixo(habilidadeLike, eixoCodigo)

    fun getHabilidadesPorCompetencia(
        competenciaCodigo: String
    ) = eixoDao.getHabilidadesPorCompetencia(competenciaCodigo)

    fun getResumoCompetenciasPorEtapa(etapaCodigo: String) =
        eixoDao.getResumoCompetenciasPorEtapa(etapaCodigo)

    fun getCompetenciasByEtapa(etapaCodigo: String) =
        competenciaDao.getByEtapa(etapaCodigo)
}
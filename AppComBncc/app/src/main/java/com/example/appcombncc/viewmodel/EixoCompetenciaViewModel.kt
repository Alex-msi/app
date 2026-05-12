package com.example.appcombncc.viewmodel

import androidx.lifecycle.ViewModel
import com.example.appcombncc.repository.EixoCompetenciaRepository

class EixoCompetenciaViewModel(
    private val repository: EixoCompetenciaRepository
) : ViewModel() {

    fun getEixosBySerie(
        serieCodigo: String
    ) = repository.getEixosBySerie(serieCodigo)

    fun getResumoEixosPorSerie(
        habilidadeLike: String
    ) = repository.getResumoEixosPorSerie(habilidadeLike)

    fun getResumoEixosPorEtapa(
        habilidadeLike: String
    ) = repository.getResumoEixosPorEtapa(habilidadeLike)

    fun getResumoObjetosPorSerieEixo(
        habilidadeLike: String,
        eixoCodigo: String
    ) = repository.getResumoObjetosPorSerieEixo(
        habilidadeLike,
        eixoCodigo
    )

    fun getResumoObjetosPorEtapaEixo(
        habilidadeLike: String,
        eixoCodigo: String
    ) = repository.getResumoObjetosPorEtapaEixo(
        habilidadeLike,
        eixoCodigo
    )

    fun getHabilidadesPorSerieEixoObjeto(
        habilidadeLike: String,
        eixoCodigo: String,
        objetoId: Long
    ) = repository.getHabilidadesPorSerieEixoObjeto(
        habilidadeLike,
        eixoCodigo,
        objetoId
    )

    fun getHabilidadesPorEtapaEixoObjeto(
        habilidadeLike: String,
        eixoCodigo: String,
        objetoId: Long
    ) = repository.getHabilidadesPorEtapaEixoObjeto(
        habilidadeLike,
        eixoCodigo,
        objetoId
    )

    fun getHabilidadesPorSerieEixo(
        habilidadeLike: String,
        eixoCodigo: String
    ) = repository.getHabilidadesPorSerieEixo(
        habilidadeLike,
        eixoCodigo
    )

    fun getHabilidadesPorEtapaEixo(
        habilidadeLike: String,
        eixoCodigo: String
    ) = repository.getHabilidadesPorEtapaEixo(
        habilidadeLike,
        eixoCodigo
    )

    fun getHabilidadesPorCompetenciaEtapa(
        etapaCodigo: String
    ) = repository.getHabilidadesPorCompetenciaEtapa(etapaCodigo)

    fun getResumoCompetenciasPorEtapa(
        etapaCodigo: String
    ) = repository.getResumoCompetenciasPorEtapa(etapaCodigo)

    fun getCompetenciasByEtapa(
        etapaCodigo: String
    ) = repository.getCompetenciasByEtapa(etapaCodigo)
}
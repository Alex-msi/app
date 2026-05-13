package com.example.appcombncc.viewmodel

import androidx.lifecycle.ViewModel
import com.example.appcombncc.repository.EixoCompetenciaRepository

class EixoCompetenciaViewModel(
    private val repository: EixoCompetenciaRepository
) : ViewModel() {

    fun getEixosBySerie(
        serieCodigo: String
    ) = repository.getEixosBySerie(serieCodigo)

    fun getResumoEixos(habilidadeLike: String) =
        repository.getResumoEixos(habilidadeLike)

    fun getResumoObjetosPorEixo(
        habilidadeLike: String,
        eixoCodigo: String
    ) = repository.getResumoObjetosPorEixo(habilidadeLike, eixoCodigo)

    fun getHabilidadesPorEixoObjeto(
        habilidadeLike: String,
        eixoCodigo: String,
        objetoId: Long
    ) = repository.getHabilidadesPorEixoObjeto(
        habilidadeLike,
        eixoCodigo,
        objetoId
    )

    fun getHabilidadesPorEixo(
        habilidadeLike: String,
        eixoCodigo: String
    ) = repository.getHabilidadesPorEixo(habilidadeLike, eixoCodigo)

    fun getHabilidadesPorCompetencia(competenciaCodigo: String) =
        repository.getHabilidadesPorCompetencia(competenciaCodigo)

    fun getResumoCompetenciasPorEtapa(
        etapaCodigo: String
    ) = repository.getResumoCompetenciasPorEtapa(etapaCodigo)

    fun getCompetenciasByEtapa(
        etapaCodigo: String
    ) = repository.getCompetenciasByEtapa(etapaCodigo)
}
package com.example.appcombncc.viewmodel

import androidx.lifecycle.ViewModel
import com.example.appcombncc.repository.EixoCompetenciaRepository

class EixoCompetenciaViewModel(
    private val repository: EixoCompetenciaRepository
) : ViewModel() {

    fun getEixosBySerie(serieCodigo: String) = repository.getEixosBySerie(serieCodigo)
    fun getResumoEixosPorSerie(serieCodigo: String) = repository.getResumoEixosPorSerie(serieCodigo)
    fun getResumoEixosPorEtapa(etapaCodigo: String, habilidadeLike: String) =
        repository.getResumoEixosPorEtapa(etapaCodigo, habilidadeLike)
    fun getResumoObjetosPorSerieEixo(serieCodigo: String, eixoCodigo: String) =
        repository.getResumoObjetosPorSerieEixo(serieCodigo, eixoCodigo)
    fun getResumoObjetosPorEtapaEixo(etapaCodigo: String, habilidadeLike: String, eixoCodigo: String) =
        repository.getResumoObjetosPorEtapaEixo(etapaCodigo, habilidadeLike, eixoCodigo)
    fun getHabilidadesPorSerieEixoObjeto(serieCodigo: String, eixoCodigo: String, objetoId: Long) =
        repository.getHabilidadesPorSerieEixoObjeto(serieCodigo, eixoCodigo, objetoId)
    fun getHabilidadesPorEtapaEixoObjeto(
        etapaCodigo: String,
        habilidadeLike: String,
        eixoCodigo: String,
        objetoId: Long
    ) = repository.getHabilidadesPorEtapaEixoObjeto(etapaCodigo, habilidadeLike, eixoCodigo, objetoId)
    fun getHabilidadesPorSerieEixo(serieCodigo: String, eixoCodigo: String) =
        repository.getHabilidadesPorSerieEixo(serieCodigo, eixoCodigo)
    fun getHabilidadesPorEtapaEixo(etapaCodigo: String, habilidadeLike: String, eixoCodigo: String) =
        repository.getHabilidadesPorEtapaEixo(etapaCodigo, habilidadeLike, eixoCodigo)
    fun getCompetenciasByEtapa(etapaCodigo: String) = repository.getCompetenciasByEtapa(etapaCodigo)
}
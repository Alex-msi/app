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
    fun getResumoObjetosPorSerieEixo(serieCodigo: String, eixoDescricao: String) =
        repository.getResumoObjetosPorSerieEixo(serieCodigo, eixoDescricao)
    fun getResumoObjetosPorEtapaEixo(etapaCodigo: String, habilidadeLike: String, eixoDescricao: String) =
        repository.getResumoObjetosPorEtapaEixo(etapaCodigo, habilidadeLike, eixoDescricao)
    fun getCompetenciasByEtapa(etapaCodigo: String) = repository.getCompetenciasByEtapa(etapaCodigo)
}
package com.example.appcombncc.viewmodel

import androidx.lifecycle.ViewModel
import com.example.appcombncc.repository.EixoCompetenciaRepository

class EixoCompetenciaViewModel(
    private val repository: EixoCompetenciaRepository
) : ViewModel() {
    fun getEixosBySerie(serieCodigo: String) = repository.getEixosBySerie(serieCodigo)
    fun getCompetenciasByEtapa(etapaCodigo: String) = repository.getCompetenciasByEtapa(etapaCodigo)
}
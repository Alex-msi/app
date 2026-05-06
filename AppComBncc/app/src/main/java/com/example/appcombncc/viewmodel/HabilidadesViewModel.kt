package com.example.appcombncc.viewmodel

import androidx.lifecycle.ViewModel
import com.example.appcombncc.repository.HabilidadeRepository

class HabilidadesViewModel(
    private val repository: HabilidadeRepository
) : ViewModel() {
    fun getByEixo(eixoCodigo: String) = repository.getByEixo(eixoCodigo)
    fun getByCompetencia(competenciaCodigo: String) = repository.getByCompetencia(competenciaCodigo)
    fun search(busca: String) = repository.search(busca)
    fun getByCodigo(codigo: String) = repository.getByCodigo(codigo)
}
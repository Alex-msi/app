package com.example.appcombncc.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appcombncc.repository.EixoCompetenciaRepository

class EixoCompetenciaViewModelFactory(
    private val repository: EixoCompetenciaRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EixoCompetenciaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EixoCompetenciaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
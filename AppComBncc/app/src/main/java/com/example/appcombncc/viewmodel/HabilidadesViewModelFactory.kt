package com.example.appcombncc.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appcombncc.repository.HabilidadeRepository

class HabilidadesViewModelFactory(
    private val repository: HabilidadeRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HabilidadesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HabilidadesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
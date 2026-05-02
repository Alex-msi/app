package com.example.appcombncc.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appcombncc.repository.SerieRepository

class SerieViewModelFactory(
    private val repository: SerieRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SerieViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SerieViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
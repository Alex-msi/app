package com.example.appcombncc.viewmodel

import androidx.lifecycle.ViewModel
import com.example.appcombncc.repository.SerieRepository

class SerieViewModel(private val repository: SerieRepository) : ViewModel() {
    val series = repository.getAllSeries()
}
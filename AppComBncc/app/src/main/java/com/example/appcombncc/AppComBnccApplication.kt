package com.example.appcombncc

import android.app.Application
import com.example.appcombncc.data.database.AppComBnccDatabase
import com.example.appcombncc.repository.EtapaRepository
import com.example.appcombncc.repository.EixoCompetenciaRepository
import com.example.appcombncc.repository.HabilidadeRepository
import com.example.appcombncc.repository.SerieRepository

class AppComBnccApplication : Application() {
    val database by lazy { AppComBnccDatabase.getDatabase(this) }

    val etapaRepository by lazy { EtapaRepository(database.etapaDao()) }
    val eixoCompetenciaRepository by lazy {
        EixoCompetenciaRepository(database.eixoDao(), database.competenciaDao())
    }
    val habilidadeRepository by lazy { HabilidadeRepository(database.habilidadeDao()) }
    val serieRepository by lazy { SerieRepository(database.serieDao()) }
}

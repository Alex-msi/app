package com.example.appcombncc.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.appcombncc.data.entity.CompetenciaEspecificaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CompetenciaDao {
    @Query("SELECT * FROM competencia_especifica WHERE etapa_codigo = :etapaCodigo ORDER BY codigo")
    fun getByEtapa(etapaCodigo: String): Flow<List<CompetenciaEspecificaEntity>>
}
package com.example.appcombncc.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.appcombncc.data.entity.EixoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EixoDao {
    @Query("SELECT * FROM eixo ORDER BY descricao")
    fun getAll(): Flow<List<EixoEntity>>

    @Query("SELECT * FROM eixo WHERE codigo IN (SELECT codigo_eixo FROM serie_eixo WHERE codigo_serie = :serieCodigo) ORDER BY descricao")
    fun getBySerie(serieCodigo: String): Flow<List<EixoEntity>>
}

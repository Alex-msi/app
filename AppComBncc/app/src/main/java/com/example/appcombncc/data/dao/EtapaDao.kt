package com.example.appcombncc.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.appcombncc.data.entity.EtapaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EtapaDao {
    @Query("SELECT * FROM etapa ORDER BY nome")
    fun getAll(): Flow<List<EtapaEntity>>

    @Query("SELECT COUNT(*) FROM etapa")
    suspend fun countEtapas(): Int
}
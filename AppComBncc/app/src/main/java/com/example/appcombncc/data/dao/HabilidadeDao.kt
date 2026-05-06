package com.example.appcombncc.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.appcombncc.data.entity.HabilidadeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HabilidadeDao {

    @Query("SELECT * FROM habilidade WHERE eixo_codigo = :eixoCodigo ORDER BY codigo")
    fun getByEixo(eixoCodigo: String): Flow<List<HabilidadeEntity>>

    @Query("SELECT * FROM habilidade WHERE competencia_codigo = :competenciaCodigo ORDER BY codigo")
    fun getByCompetencia(competenciaCodigo: String): Flow<List<HabilidadeEntity>>

    @Query("SELECT * FROM habilidade WHERE codigo LIKE '%' || :busca || '%' OR descricao LIKE '%' || :busca || '%' ORDER BY codigo")
    fun searchByCodigoOrDescricao(busca: String): Flow<List<HabilidadeEntity>>

    @Query("SELECT * FROM habilidade WHERE codigo = :codigo LIMIT 1")
    fun getByCodigo(codigo: String): Flow<HabilidadeEntity?>
}
package com.example.appcombncc.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.appcombncc.data.entity.EixoEntity
import com.example.appcombncc.data.model.EixoHabilidadeCount
import kotlinx.coroutines.flow.Flow

@Dao
interface EixoDao {
    @Query("SELECT * FROM eixo ORDER BY descricao")
    fun getAll(): Flow<List<EixoEntity>>

    @Query("SELECT * FROM eixo WHERE codigo IN (SELECT codigo_eixo FROM serie_eixo WHERE codigo_serie = :serieCodigo) ORDER BY descricao")
    fun getBySerie(serieCodigo: String): Flow<List<EixoEntity>>

    @Query(
        """
        SELECT 
            e.descricao AS eixo,
            COUNT(h.codigo) AS totalHabilidades
        FROM serie s
        JOIN habilidade h
            ON h.codigo LIKE 'EF' || printf('%02d', CAST(substr(s.codigo, 1, instr(s.codigo, '_') - 1) AS INTEGER)) || '%'
        JOIN eixo e
            ON e.codigo = h.eixo_codigo
        WHERE s.codigo = :serieCodigo
        GROUP BY e.codigo, e.descricao
        ORDER BY totalHabilidades DESC
        """
    )
    fun getResumoEixosPorSerie(serieCodigo: String): Flow<List<EixoHabilidadeCount>>
}
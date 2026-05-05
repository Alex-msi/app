package com.example.appcombncc.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.appcombncc.data.entity.EixoEntity
import com.example.appcombncc.data.model.EixoHabilidadeCount
import com.example.appcombncc.data.model.ObjetoHabilidadeCount
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

    @Query(
        """
        SELECT
            et.codigo AS etapaCodigo,
            et.nome AS etapaNome,
            e.descricao AS eixo,
            COUNT(h.codigo) AS totalHabilidades
        FROM etapa et
        JOIN habilidade h
            ON h.codigo LIKE :habilidadeLike
        JOIN eixo e
            ON e.codigo = h.eixo_codigo
        WHERE et.codigo = :etapaCodigo
        GROUP BY et.codigo, et.nome, e.codigo, e.descricao
        ORDER BY totalHabilidades DESC
        """
    )
    fun getResumoEixosPorEtapa(etapaCodigo: String, habilidadeLike: String): Flow<List<EixoHabilidadeCount>>

    @Query(
        """
        SELECT
            oc.nome AS objeto,
            COUNT(h.codigo) AS totalHabilidades
        FROM serie s
        JOIN habilidade h
            ON h.codigo LIKE 'EF' || printf('%02d', CAST(substr(s.codigo, 1, instr(s.codigo, '_') - 1) AS INTEGER)) || '%'
        JOIN eixo e
            ON e.codigo = h.eixo_codigo
        JOIN objeto_conhecimento oc
            ON oc.id = h.objeto_conhecimento_id
        WHERE s.codigo = :serieCodigo
          AND e.descricao = :eixoDescricao
        GROUP BY oc.id, oc.nome
        ORDER BY totalHabilidades DESC
        """
    )
    fun getResumoObjetosPorSerieEixo(serieCodigo: String, eixoDescricao: String): Flow<List<ObjetoHabilidadeCount>>

    @Query(
        """
        SELECT
            oc.nome AS objeto,
            COUNT(h.codigo) AS totalHabilidades
        FROM etapa et
        JOIN habilidade h
            ON h.codigo LIKE :habilidadeLike
        JOIN eixo e
            ON e.codigo = h.eixo_codigo
        JOIN objeto_conhecimento oc
            ON oc.id = h.objeto_conhecimento_id
        WHERE et.codigo = :etapaCodigo
          AND e.descricao = :eixoDescricao
        GROUP BY oc.id, oc.nome
        ORDER BY totalHabilidades DESC
        """
    )
    fun getResumoObjetosPorEtapaEixo(
        etapaCodigo: String,
        habilidadeLike: String,
        eixoDescricao: String
    ): Flow<List<ObjetoHabilidadeCount>>
}
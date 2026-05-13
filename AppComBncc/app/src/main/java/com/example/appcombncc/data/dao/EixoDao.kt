package com.example.appcombncc.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.appcombncc.data.entity.EixoEntity
import com.example.appcombncc.data.model.CompetenciaHabilidadeItem
import com.example.appcombncc.data.model.CompetenciaResumoItem
import com.example.appcombncc.data.model.EixoHabilidadeCount
import com.example.appcombncc.data.model.HabilidadeListaItem
import com.example.appcombncc.data.model.ObjetoHabilidadeCount
import kotlinx.coroutines.flow.Flow

@Dao
interface EixoDao {

    @Query("SELECT * FROM eixo ORDER BY descricao")
    fun getAll(): Flow<List<EixoEntity>>

    @Query("""
        SELECT * 
        FROM eixo 
        WHERE codigo IN (
            SELECT codigo_eixo 
            FROM serie_eixo 
            WHERE codigo_serie = :serieCodigo
        ) 
        ORDER BY descricao
    """)
    fun getBySerie(serieCodigo: String): Flow<List<EixoEntity>>

    @Query("""
SELECT
    e.codigo AS eixoCodigo,
    e.descricao AS eixo,
    COUNT(h.codigo) AS totalHabilidades
FROM habilidade h
JOIN eixo e ON e.codigo = h.eixo_codigo
WHERE h.codigo LIKE :habilidadeLike
GROUP BY e.codigo, e.descricao
ORDER BY e.descricao
""")
    fun getResumoEixos(
        habilidadeLike: String
    ): Flow<List<EixoHabilidadeCount>>

    @Query("""
SELECT
    oc.id AS objetoId,
    oc.nome AS objeto,
    COUNT(h.codigo) AS totalHabilidades
FROM habilidade h
JOIN objeto_conhecimento oc ON oc.id = h.objeto_conhecimento_id
WHERE h.codigo LIKE :habilidadeLike
AND h.eixo_codigo = :eixoCodigo
GROUP BY oc.id, oc.nome
ORDER BY oc.nome
""")
    fun getResumoObjetosPorEixo(
        habilidadeLike: String,
        eixoCodigo: String
    ): Flow<List<ObjetoHabilidadeCount>>

    @Query("""
SELECT
    h.codigo AS codigo,
    h.descricao AS descricao
FROM habilidade h
WHERE h.codigo LIKE :habilidadeLike
AND h.eixo_codigo = :eixoCodigo
AND h.objeto_conhecimento_id = :objetoId
ORDER BY h.codigo
""")
    fun getHabilidadesPorEixoObjeto(
        habilidadeLike: String,
        eixoCodigo: String,
        objetoId: Long
    ): Flow<List<HabilidadeListaItem>>

    @Query("""
SELECT
    h.codigo AS codigo,
    h.descricao AS descricao
FROM habilidade h
WHERE h.codigo LIKE :habilidadeLike
AND h.eixo_codigo = :eixoCodigo
ORDER BY h.codigo
""")
    fun getHabilidadesPorEixo(
        habilidadeLike: String,
        eixoCodigo: String
    ): Flow<List<HabilidadeListaItem>>

    @Query("""
SELECT
    h.codigo AS codigo,
    h.descricao AS descricao
FROM habilidade h
WHERE h.competencia_codigo = :competenciaCodigo
ORDER BY h.codigo
""")
    fun getHabilidadesPorCompetencia(
        competenciaCodigo: String
    ): Flow<List<HabilidadeListaItem>>

    @Query("""
        SELECT
            ce.codigo AS competenciaCodigo,
            ce.descricao AS competenciaDescricao,
            COUNT(h.codigo) AS totalHabilidades
        FROM competencia_especifica ce
        JOIN habilidade h ON h.competencia_codigo = ce.codigo
        WHERE ce.etapa_codigo = :etapaCodigo
        GROUP BY ce.codigo, ce.descricao
        ORDER BY ce.codigo
    """)
    fun getResumoCompetenciasPorEtapa(
        etapaCodigo: String
    ): Flow<List<CompetenciaResumoItem>>
}
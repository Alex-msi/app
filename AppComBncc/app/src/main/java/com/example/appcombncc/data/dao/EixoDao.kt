package com.example.appcombncc.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.appcombncc.data.entity.EixoEntity
import com.example.appcombncc.data.model.EixoHabilidadeCount
import com.example.appcombncc.data.model.CompetenciaHabilidadeItem
import com.example.appcombncc.data.model.CompetenciaResumoItem
import com.example.appcombncc.data.model.HabilidadeListaItem
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
            e.codigo AS eixoCodigo,
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
            e.codigo AS eixoCodigo,
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
            oc.id AS objetoId,
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
          AND e.codigo = :eixoCodigo
        GROUP BY oc.id, oc.nome
        ORDER BY totalHabilidades DESC
        """
    )
    fun getResumoObjetosPorSerieEixo(serieCodigo: String, eixoCodigo: String): Flow<List<ObjetoHabilidadeCount>>

    @Query(
        """
        SELECT
            oc.id AS objetoId,
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
          AND e.codigo = :eixoCodigo
        GROUP BY oc.id, oc.nome
        ORDER BY totalHabilidades DESC
        """
    )
    fun getResumoObjetosPorEtapaEixo(
        etapaCodigo: String,
        habilidadeLike: String,
        eixoCodigo: String
    ): Flow<List<ObjetoHabilidadeCount>>

    @Query(
        """
        SELECT h.codigo AS codigo, h.descricao AS descricao
        FROM serie s
        JOIN habilidade h
            ON h.codigo LIKE 'EF' || printf('%02d', CAST(substr(s.codigo, 1, instr(s.codigo, '_') - 1) AS INTEGER)) || '%'
        JOIN eixo e
            ON e.codigo = h.eixo_codigo
        JOIN objeto_conhecimento oc
            ON oc.id = h.objeto_conhecimento_id
        WHERE s.codigo = :serieCodigo
          AND e.codigo = :eixoCodigo
          AND oc.id = :objetoId
        ORDER BY h.codigo
        """
    )
    fun getHabilidadesPorSerieEixoObjeto(
        serieCodigo: String,
        eixoCodigo: String,
        objetoId: Long
    ): Flow<List<HabilidadeListaItem>>

    @Query(
        """
        SELECT h.codigo AS codigo, h.descricao AS descricao
        FROM etapa et
        JOIN habilidade h
            ON h.codigo LIKE :habilidadeLike
        JOIN eixo e
            ON e.codigo = h.eixo_codigo
        JOIN objeto_conhecimento oc
            ON oc.id = h.objeto_conhecimento_id
        WHERE et.codigo = :etapaCodigo
          AND e.codigo = :eixoCodigo
          AND oc.id = :objetoId
        ORDER BY h.codigo
        """
    )
    fun getHabilidadesPorEtapaEixoObjeto(
        etapaCodigo: String,
        habilidadeLike: String,
        eixoCodigo: String,
        objetoId: Long
    ): Flow<List<HabilidadeListaItem>>

    @Query(
        """
        SELECT h.codigo AS codigo, h.descricao AS descricao
        FROM serie s
        JOIN habilidade h
            ON h.codigo LIKE 'EF' || printf('%02d', CAST(substr(s.codigo, 1, instr(s.codigo, '_') - 1) AS INTEGER)) || '%'
        JOIN eixo e
            ON e.codigo = h.eixo_codigo
        WHERE s.codigo = :serieCodigo
          AND e.codigo = :eixoCodigo
        ORDER BY h.codigo
        """
    )
    fun getHabilidadesPorSerieEixo(serieCodigo: String, eixoCodigo: String): Flow<List<HabilidadeListaItem>>

    @Query(
        """
        SELECT h.codigo AS codigo, h.descricao AS descricao
        FROM etapa et
        JOIN habilidade h
            ON h.codigo LIKE :habilidadeLike
        JOIN eixo e
            ON e.codigo = h.eixo_codigo
        WHERE et.codigo = :etapaCodigo
          AND e.codigo = :eixoCodigo
        ORDER BY h.codigo
        """
    )
    fun getHabilidadesPorEtapaEixo(
        etapaCodigo: String,
        habilidadeLike: String,
        eixoCodigo: String
    ): Flow<List<HabilidadeListaItem>>

    @Query(
        """
        SELECT
            ce.codigo AS competenciaCodigo,
            ce.descricao AS competenciaDescricao,
            h.codigo AS habilidadeCodigo,
            h.descricao AS habilidadeDescricao
        FROM competencia_especifica ce
        JOIN habilidade h ON h.competencia_codigo = ce.codigo
        WHERE ce.etapa_codigo = :etapaCodigo
        ORDER BY ce.codigo, h.codigo
        """
    )
    fun getHabilidadesPorCompetenciaEtapa(etapaCodigo: String): Flow<List<CompetenciaHabilidadeItem>>

    @Query(
        """
        SELECT
            ce.codigo AS competenciaCodigo,
            ce.descricao AS competenciaDescricao,
            COUNT(h.codigo) AS totalHabilidades
        FROM competencia_especifica ce
        JOIN habilidade h ON h.competencia_codigo = ce.codigo
        WHERE ce.etapa_codigo = :etapaCodigo
        GROUP BY ce.codigo, ce.descricao
        ORDER BY ce.codigo
        """
    )
    fun getResumoCompetenciasPorEtapa(etapaCodigo: String): Flow<List<CompetenciaResumoItem>>
}
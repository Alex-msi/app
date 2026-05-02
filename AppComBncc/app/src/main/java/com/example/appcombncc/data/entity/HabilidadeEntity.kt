package com.example.appcombncc.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "habilidade",
    foreignKeys = [
        ForeignKey(
            entity = EixoEntity::class,
            parentColumns = ["codigo"],
            childColumns = ["eixo_codigo"]
        ),
        ForeignKey(
            entity = ObjetoConhecimentoEntity::class,
            parentColumns = ["id"],
            childColumns = ["objeto_conhecimento_id"]
        ),
        ForeignKey(
            entity = ConceitoHabilidadeEntity::class,
            parentColumns = ["codigo"],
            childColumns = ["conceito_codigo"]
        ),
        ForeignKey(
            entity = CompetenciaEspecificaEntity::class,
            parentColumns = ["codigo"],
            childColumns = ["competencia_codigo"]
        )
    ]
)
data class HabilidadeEntity(
    @PrimaryKey
    val codigo: String,
    val descricao: String,
    val explicacao: String?,
    val exemplo: String?,
    @ColumnInfo(name = "eixo_codigo")
    val eixoCodigo: String?,
    @ColumnInfo(name = "objeto_conhecimento_id")
    val objetoConhecimentoId: Long?,
    @ColumnInfo(name = "conceito_codigo")
    val conceitoCodigo: String?,
    @ColumnInfo(name = "competencia_codigo")
    val competenciaCodigo: String?
)

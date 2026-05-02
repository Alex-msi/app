package com.example.appcombncc.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habilidade")
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
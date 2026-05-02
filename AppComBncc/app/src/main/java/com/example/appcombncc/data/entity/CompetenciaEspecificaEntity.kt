package com.example.appcombncc.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "competencia_especifica")
data class CompetenciaEspecificaEntity(
    @PrimaryKey
    val codigo: String,
    val descricao: String,
    @ColumnInfo(name = "etapa_codigo")
    val etapaCodigo: String
)
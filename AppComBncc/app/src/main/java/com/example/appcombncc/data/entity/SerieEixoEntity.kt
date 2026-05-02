package com.example.appcombncc.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "serie_eixo",
    primaryKeys = ["codigo_serie", "codigo_eixo"]
)
data class SerieEixoEntity(
    @ColumnInfo(name = "codigo_serie")
    val codigoSerie: String,
    @ColumnInfo(name = "codigo_eixo")
    val codigoEixo: String
)
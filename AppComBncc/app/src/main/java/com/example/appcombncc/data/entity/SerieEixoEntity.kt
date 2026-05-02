package com.example.appcombncc.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "serie_eixo",
    primaryKeys = ["codigo_serie", "codigo_eixo"],
    foreignKeys = [
        ForeignKey(
            entity = SerieEntity::class,
            parentColumns = ["codigo"],
            childColumns = ["codigo_serie"]
        ),
        ForeignKey(
            entity = EixoEntity::class,
            parentColumns = ["codigo"],
            childColumns = ["codigo_eixo"]
        )
    ]
)
data class SerieEixoEntity(
    @ColumnInfo(name = "codigo_serie")
    val codigoSerie: String,
    @ColumnInfo(name = "codigo_eixo")
    val codigoEixo: String
)

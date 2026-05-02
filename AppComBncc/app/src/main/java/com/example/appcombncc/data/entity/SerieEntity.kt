package com.example.appcombncc.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "serie",
    foreignKeys = [
        ForeignKey(
            entity = EtapaEntity::class,
            parentColumns = ["codigo"],
            childColumns = ["etapa_codigo"]
        )
    ]
)
data class SerieEntity(
    @PrimaryKey
    val codigo: String,
    val nome: String,
    @ColumnInfo(name = "etapa_codigo")
    val etapaCodigo: String
)
package com.example.appcombncc.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "objeto_conhecimento",
    foreignKeys = [
        ForeignKey(
            entity = ObjetoConhecimentoEntity::class,
            parentColumns = ["id"],
            childColumns = ["objeto_pai_id"]
        )
    ]
)
data class ObjetoConhecimentoEntity(
    @PrimaryKey
    val id: Long,
    val nome: String,
    @ColumnInfo(name = "objeto_pai_id")
    val objetoPaiId: Long?
)
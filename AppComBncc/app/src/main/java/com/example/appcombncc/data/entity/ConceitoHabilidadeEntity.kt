package com.example.appcombncc.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "conceito_habilidade")
data class ConceitoHabilidadeEntity(
    @PrimaryKey
    val codigo: String,
    val descricao: String
)
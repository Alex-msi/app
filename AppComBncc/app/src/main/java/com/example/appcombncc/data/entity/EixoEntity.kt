package com.example.appcombncc.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "eixo")
data class EixoEntity(
    @PrimaryKey
    val codigo: String,
    val descricao: String
)
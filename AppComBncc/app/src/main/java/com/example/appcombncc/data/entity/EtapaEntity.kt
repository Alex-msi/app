package com.example.appcombncc.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "etapa")
data class EtapaEntity(
    @PrimaryKey
    val codigo: String,
    val nome: String
)
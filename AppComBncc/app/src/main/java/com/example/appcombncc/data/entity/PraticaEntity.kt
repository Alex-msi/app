package com.example.appcombncc.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "pratica",
    indices = [
        Index(value = ["usuario_id"]),
        Index(value = ["habilidade_codigo"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = ["id"],
            childColumns = ["usuario_id"]
        ),
        ForeignKey(
            entity = HabilidadeEntity::class,
            parentColumns = ["codigo"],
            childColumns = ["habilidade_codigo"]
        )
    ]
)
data class PraticaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "usuario_id")
    val usuarioId: Long,
    @ColumnInfo(name = "habilidade_codigo")
    val habilidadeCodigo: String,
    val titulo: String?,
    val objetivo: String?,
    val materiais: String?,
    val introducao: String?,
    val desenvolvimento: String?,
    @ColumnInfo(name = "atividade_pratica")
    val atividadePratica: String?,
    val encerramento: String?,
    val tempo: Int?,
    val tipo: String?,
    val status: String?,
    @ColumnInfo(name = "criado_em")
    val criadoEm: Long?,
    @ColumnInfo(name = "atualizado_em")
    val atualizadoEm: Long?,
    val sincronizado: Int = 0
)

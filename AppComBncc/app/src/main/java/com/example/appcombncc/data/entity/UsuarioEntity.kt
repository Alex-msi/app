package com.example.appcombncc.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuario")
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long? = null,
    @ColumnInfo(name = "google_uid")
    val googleUid: String?,
    val nome: String?,
    val email: String?,
    @ColumnInfo(name = "autenticado", defaultValue = "0")
    val autenticado: Int = 0,
    @ColumnInfo(name = "ultimo_login_em")
    val ultimoLoginEm: Long?,
    @ColumnInfo(name = "criado_em")
    val criadoEm: Long?,
    @ColumnInfo(name = "atualizado_em")
    val atualizadoEm: Long?,
    val tipo: String?
)

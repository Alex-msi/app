package com.example.appcombncc.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.appcombncc.data.entity.UsuarioEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserir(usuario: UsuarioEntity): Long

    @Update
    suspend fun atualizar(usuario: UsuarioEntity)

    @Query("SELECT * FROM usuario WHERE id = :id LIMIT 1")
    fun getById(id: Long): Flow<UsuarioEntity?>

    @Query("SELECT * FROM usuario WHERE LOWER(email) = LOWER(:email) ORDER BY id DESC LIMIT 1")
    fun getByEmail(email: String): Flow<UsuarioEntity?>

    @Query("SELECT * FROM usuario WHERE LOWER(email) = LOWER(:email) ORDER BY id DESC LIMIT 1")
    suspend fun getByEmailSnapshot(email: String): UsuarioEntity?

    @Query("UPDATE usuario SET nome = :nome, atualizado_em = :atualizadoEm WHERE id = :usuarioId")
    suspend fun atualizarNomeById(usuarioId: Long, nome: String?, atualizadoEm: Long)
}

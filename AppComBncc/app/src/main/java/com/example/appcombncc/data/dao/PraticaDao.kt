package com.example.appcombncc.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.appcombncc.data.entity.PraticaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PraticaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserir(pratica: PraticaEntity): Long

    @Update
    suspend fun atualizar(pratica: PraticaEntity)

    @Query("DELETE FROM pratica WHERE id = :id")
    suspend fun remover(id: Long)

    @Query("SELECT * FROM pratica WHERE id = :id LIMIT 1")
    fun getById(id: Long): Flow<PraticaEntity?>

    @Query("SELECT * FROM pratica WHERE usuario_id = :usuarioId ORDER BY atualizado_em DESC, criado_em DESC, id DESC")
    fun getByUsuario(usuarioId: Long): Flow<List<PraticaEntity>>
}

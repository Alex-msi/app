package com.example.appcombncc.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.appcombncc.data.dao.CompetenciaDao
import com.example.appcombncc.data.dao.EixoDao
import com.example.appcombncc.data.dao.EtapaDao
import com.example.appcombncc.data.dao.HabilidadeDao
import com.example.appcombncc.data.entity.CompetenciaEspecificaEntity
import com.example.appcombncc.data.entity.EixoEntity
import com.example.appcombncc.data.entity.EtapaEntity
import com.example.appcombncc.data.entity.HabilidadeEntity
import com.example.appcombncc.data.entity.SerieEixoEntity

@Database(entities = [
                        EtapaEntity::class,
                        EixoEntity::class,
                        CompetenciaEspecificaEntity::class,
                        HabilidadeEntity::class,
                        SerieEixoEntity::class], version = 1, exportSchema = false)

abstract class AppComBnccDatabase : RoomDatabase() {


    abstract fun etapaDao(): EtapaDao
    abstract fun eixoDao(): EixoDao
    abstract fun competenciaDao(): CompetenciaDao
    abstract fun habilidadeDao(): HabilidadeDao

    companion object {
        @Volatile
        private var INSTANCE: AppComBnccDatabase? = null

        fun getDatabase(context: Context): AppComBnccDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppComBnccDatabase::class.java,
                    "bncc_computacao.db"
                )
                    .createFromAsset("bncc_computacao.db")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
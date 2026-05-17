package com.example.appcombncc.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.appcombncc.data.dao.CompetenciaDao
import com.example.appcombncc.data.dao.EixoDao
import com.example.appcombncc.data.dao.EtapaDao
import com.example.appcombncc.data.dao.HabilidadeDao
import com.example.appcombncc.data.dao.PraticaDao
import com.example.appcombncc.data.dao.SerieDao
import com.example.appcombncc.data.dao.UsuarioDao
import com.example.appcombncc.data.entity.CompetenciaEspecificaEntity
import com.example.appcombncc.data.entity.ConceitoHabilidadeEntity
import com.example.appcombncc.data.entity.EixoEntity
import com.example.appcombncc.data.entity.EtapaEntity
import com.example.appcombncc.data.entity.HabilidadeEntity
import com.example.appcombncc.data.entity.ObjetoConhecimentoEntity
import com.example.appcombncc.data.entity.PraticaEntity
import com.example.appcombncc.data.entity.SerieEntity
import com.example.appcombncc.data.entity.SerieEixoEntity
import com.example.appcombncc.data.entity.UsuarioEntity

@Database(
    entities = [
        EtapaEntity::class,
        SerieEntity::class,
        EixoEntity::class,
        ObjetoConhecimentoEntity::class,
        ConceitoHabilidadeEntity::class,
        CompetenciaEspecificaEntity::class,
        HabilidadeEntity::class,
        SerieEixoEntity::class,
        UsuarioEntity::class,
        PraticaEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppComBnccDatabase : RoomDatabase() {

    abstract fun etapaDao(): EtapaDao
    abstract fun eixoDao(): EixoDao
    abstract fun competenciaDao(): CompetenciaDao
    abstract fun habilidadeDao(): HabilidadeDao
    abstract fun serieDao(): SerieDao
    abstract fun usuarioDao(): UsuarioDao
    abstract fun praticaDao(): PraticaDao

    companion object {
        @Volatile
        private var INSTANCE: AppComBnccDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS usuario (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        google_uid TEXT,
                        nome TEXT,
                        email TEXT,
                        autenticado INTEGER NOT NULL DEFAULT 0,
                        ultimo_login_em INTEGER,
                        criado_em INTEGER,
                        atualizado_em INTEGER,
                        tipo TEXT
                    )
                    """.trimIndent()
                )

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS pratica (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        usuario_id INTEGER NOT NULL,
                        habilidade_codigo TEXT NOT NULL,
                        titulo TEXT,
                        objetivo TEXT,
                        materiais TEXT,
                        introducao TEXT,
                        desenvolvimento TEXT,
                        atividade_pratica TEXT,
                        encerramento TEXT,
                        tempo INTEGER,
                        tipo TEXT,
                        status TEXT,
                        criado_em INTEGER,
                        atualizado_em INTEGER,
                        sincronizado INTEGER NOT NULL DEFAULT 0,
                        FOREIGN KEY (usuario_id) REFERENCES usuario(id),
                        FOREIGN KEY (habilidade_codigo) REFERENCES habilidade(codigo)
                    )
                    """.trimIndent()
                )

                db.execSQL("CREATE INDEX IF NOT EXISTS index_pratica_usuario_id ON pratica(usuario_id)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_pratica_habilidade_codigo ON pratica(habilidade_codigo)")
            }
        }

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
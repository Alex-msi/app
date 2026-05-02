# Fase 0 — Configuração de Navigation Component e Room (para incluir no projeto Android Studio)

## Contexto

Este documento foi ajustado para o projeto já criado no Android Studio com:
- **Nome do app:** `AppComBncc`
- **Namespace:** `com.example.appcombncc`

A ideia é você copiar e colar os trechos abaixo no seu projeto local.

Objetivo desta fase:
1. Configurar **Navigation Component**.
2. Configurar **Room**.
3. Integrar com o banco já existente/populado para iniciar a Fase 1.

---

## 1) Dependências Gradle (Kotlin DSL) — AppComBncc

No `app/build.gradle.kts`, garanta:

```kotlin
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
}

android {
    // ... configuração padrão do seu projeto
}

dependencies {
    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.9.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.9.0")

    // Room
    implementation("androidx.room:room-runtime:2.8.0")
    implementation("androidx.room:room-ktx:2.8.0")
    kapt("androidx.room:room-compiler:2.8.0")

    // Lifecycle/ViewModel (base)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.9.0")
}
```

Se seu projeto usa **Version Catalog** (`libs.versions.toml`), registre versões equivalentes e use `libs.xxx`.

> Observação: se seu projeto já estiver com AGP/Kotlin recentes, mantenha as versões do catálogo alinhadas com o que o Android Studio sugerir para evitar conflito de resolução.

---

## 2) Configuração base de Navigation Component

## 2.1 Arquivo de navegação

Criar: `app/src/main/res/navigation/nav_graph.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/nav_graph"
    app:startDestination="@id/homeFragment">

    <fragment
        android:id="@+id/homeFragment"
        android:name="com.example.appcombncc.ui.fragment.HomeFragment"
        android:label="Home" />
</navigation>
```

## 2.2 Layout da MainActivity com NavHostFragment

Criar/ajustar: `app/src/main/res/layout/activity_main.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.example.appcombncc.ui.activity.MainActivity">

    <androidx.fragment.app.FragmentContainerView
        android:id="@+id/navHostFragment"
        android:name="androidx.navigation.fragment.NavHostFragment"
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:defaultNavHost="true"
        app:navGraph="@navigation/nav_graph"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

</androidx.constraintlayout.widget.ConstraintLayout>
```

## 2.3 MainActivity

```kotlin
package com.example.appcombncc.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appcombncc.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
```

## 2.4 Fragment inicial (Home)

Criar `HomeFragment.kt` em `ui/fragment` e layout correspondente (`fragment_home.xml`) para validar navegação básica.

---

## 3) Configuração base de Room

Pacotes sugeridos:
- `data/entity`
- `data/dao`
- `data/database`
- `model/domain`

## 3.1 Exemplo mínimo de Entity

Arquivo: `data/entity/EtapaEntity.kt`

```kotlin
package com.example.appcombncc.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "etapa")
data class EtapaEntity(
    @PrimaryKey
    val codigo: String,
    val nome: String
)
```

## 3.2 Exemplo mínimo de DAO (somente leitura inicial)

Arquivo: `data/dao/EtapaDao.kt`

```kotlin
package com.example.appcombncc.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.appcombncc.data.entity.EtapaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EtapaDao {
    @Query("SELECT * FROM etapa ORDER BY nome")
    fun getAll(): Flow<List<EtapaEntity>>
}
```

## 3.3 Database (reaproveitar a classe existente)

Como você informou que o banco **já existe e já está populado**, não é necessário criar uma nova classe de database nesta fase.

Faça apenas estas verificações no projeto:

- a classe existente de database está no pacote `data/database`;
- ela expõe o `EtapaDao` (ou DAO equivalente da etapa);
- o método singleton (`getDatabase(...)` ou equivalente) está funcionando na `Application`;
- o nome do arquivo `.db` e versão (`version`) estão coerentes com o banco já distribuído no app.

Como você informou:
- **nome do arquivo:** `bncc_computacao.db`
- **origem no projeto:** `assets/bncc_computacao.db` (projeto Android real)

a configuração de asset no Room deve apontar para esse caminho relativo em `assets`, por exemplo:

```kotlin
.createFromAsset("bncc_computacao.db")
```

> Se sua classe atual não se chamar `AppComBnccDatabase`, mantenha o nome existente e ajuste apenas os imports nos exemplos abaixo.

---


## 3.4 Classe `AppComBnccDatabase` atualizada (com banco pré-populado)

Sobre sua dúvida:
- **classe Kotlin (`AppComBnccDatabase`)** fica em `data/database`;
- **arquivo físico `.db`** não fica em pacote Java/Kotlin, e sim em `app/src/main/assets/bncc_computacao.db`.

Exemplo atualizado da classe:

```kotlin
package com.example.appcombncc.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.appcombncc.data.dao.EtapaDao
import com.example.appcombncc.data.entity.EtapaEntity

@Database(entities = [EtapaEntity::class], version = 1, exportSchema = false)
abstract class AppComBnccDatabase : RoomDatabase() {

    abstract fun etapaDao(): EtapaDao

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
```

## 4) Integração mínima (Application + Repository + ViewModel)

## 4.1 Application

Arquivo: `AppComBnccApplication.kt`

```kotlin
package com.example.appcombncc

import android.app.Application
import com.example.appcombncc.data.database.AppComBnccDatabase
import com.example.appcombncc.repository.EtapaRepository

class AppComBnccApplication : Application() {
    val database by lazy { AppComBnccDatabase.getDatabase(this) }
    val etapaRepository by lazy { EtapaRepository(database.etapaDao()) }
}
```

No `AndroidManifest.xml`, declarar:

```xml
<application
    android:name=".AppComBnccApplication"
    ... >
```

## 4.2 Repository

Arquivo: `repository/EtapaRepository.kt`

```kotlin
package com.example.appcombncc.repository

import com.example.appcombncc.data.dao.EtapaDao

class EtapaRepository(private val etapaDao: EtapaDao) {
    fun getAllEtapas() = etapaDao.getAll()
}
```

## 4.3 ViewModel (exemplo simples)

Arquivo: `viewmodel/HomeViewModel.kt`

```kotlin
package com.example.appcombncc.viewmodel

import androidx.lifecycle.ViewModel
import com.example.appcombncc.repository.EtapaRepository

class HomeViewModel(private val repository: EtapaRepository) : ViewModel() {
    val etapas = repository.getAllEtapas()
}
```

---

## 5) Checklist de validação da Fase 0

- [ ] App compila com dependências de Navigation e Room.
- [ ] `MainActivity` abre com `NavHostFragment` carregando `homeFragment`.
- [ ] Database existente abre sem crash (sem recriar schema).
- [ ] DAO retorna fluxo de etapas.
- [ ] Dados já populados são lidos corretamente pelo DAO.

---

## 6) Testes rápidos (2 minutos) para validar banco pré-populado

### Teste 1 — Banco abre sem recriar schema
1. Rode o app uma vez.
2. Abra o **Logcat** e confirme que não aparece erro de schema/migration do Room.
3. Confirme que não há lógica de seed rodando no startup.

### Teste 2 — Contagem de registros da tabela etapa
Adicione temporariamente no `EtapaDao`:

```kotlin
@Query("SELECT COUNT(*) FROM etapa")
suspend fun countEtapas(): Int
```

No `EtapaRepository` (temporário):

```kotlin
suspend fun countEtapas(): Int = etapaDao.countEtapas()
```

No `HomeViewModel` (temporário):

```kotlin
fun debugCountEtapas() = viewModelScope.launch {
    val total = repository.countEtapas()
    Log.d("DB_CHECK", "Total etapas: $total")
}
```

Critério de sucesso: total **= 6** no Logcat (conforme base validada nesta etapa).

### Teste 3 — Leitura real na UI
1. Colete `etapas` no Fragment/Home.
2. Exiba em lista simples (RecyclerView/TextView).
3. Verifique se os dados aparecem sem inserir nada.

Critério de sucesso: dados carregam diretamente do `bncc_computacao.db`.

---

## 7) Schema do banco validado (referência da Fase 0)

Nesta etapa, o schema validado do banco `bncc_computacao.db` é:

```sql
BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "competencia_especifica" (
	"codigo"	TEXT NOT NULL,
	"descricao"	TEXT NOT NULL,
	"etapa_codigo"	TEXT NOT NULL,
	PRIMARY KEY("codigo"),
	FOREIGN KEY("etapa_codigo") REFERENCES "etapa"("codigo")
);
CREATE TABLE IF NOT EXISTS "conceito_habilidade" (
	"codigo"	TEXT NOT NULL,
	"descricao"	TEXT NOT NULL,
	PRIMARY KEY("codigo")
);
CREATE TABLE IF NOT EXISTS "eixo" (
	"codigo"	TEXT NOT NULL,
	"descricao"	TEXT NOT NULL,
	PRIMARY KEY("codigo")
);
CREATE TABLE IF NOT EXISTS "etapa" (
	"codigo"	TEXT NOT NULL,
	"nome"	TEXT NOT NULL,
	PRIMARY KEY("codigo")
);
CREATE TABLE IF NOT EXISTS "habilidade" (
	"codigo"	TEXT NOT NULL,
	"descricao"	TEXT NOT NULL,
	"explicacao"	TEXT,
	"exemplo"	TEXT,
	"eixo_codigo"	TEXT,
	"objeto_conhecimento_id"	INTEGER,
	"conceito_codigo"	TEXT,
	"competencia_codigo"	TEXT,
	PRIMARY KEY("codigo"),
	FOREIGN KEY("competencia_codigo") REFERENCES "competencia_especifica"("codigo"),
	FOREIGN KEY("conceito_codigo") REFERENCES "conceito_habilidade"("codigo"),
	FOREIGN KEY("eixo_codigo") REFERENCES "eixo"("codigo"),
	FOREIGN KEY("objeto_conhecimento_id") REFERENCES "objeto_conhecimento"("id")
);
CREATE TABLE IF NOT EXISTS "importacao_bncc" (
	"id"	INTEGER,
	"origem_aba"	TEXT,
	"etapa"	TEXT,
	"serie"	TEXT,
	"eixo"	TEXT,
	"objeto_conhecimento"	TEXT,
	"objeto_conhecimento_filho"	TEXT,
	"conceito_habilidade"	TEXT,
	"competencia_especifica"	TEXT,
	"codigo"	TEXT,
	"descricao"	TEXT,
	"explicacao"	TEXT,
	"exemplo"	TEXT,
	PRIMARY KEY("id" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "objeto_conhecimento" (
	"id"	INTEGER,
	"nome"	TEXT NOT NULL,
	"objeto_pai_id"	INTEGER,
	PRIMARY KEY("id" AUTOINCREMENT),
	FOREIGN KEY("objeto_pai_id") REFERENCES "objeto_conhecimento"("id")
);
CREATE TABLE IF NOT EXISTS "serie" (
	"codigo"	TEXT NOT NULL,
	"nome"	TEXT NOT NULL,
	"etapa_codigo"	TEXT NOT NULL,
	PRIMARY KEY("codigo"),
	FOREIGN KEY("etapa_codigo") REFERENCES "etapa"("codigo")
);
CREATE TABLE IF NOT EXISTS "serie_eixo" (
	"codigo_serie"	TEXT NOT NULL,
	"codigo_eixo"	TEXT NOT NULL,
	PRIMARY KEY("codigo_serie","codigo_eixo"),
	FOREIGN KEY("codigo_eixo") REFERENCES "eixo"("codigo"),
	FOREIGN KEY("codigo_serie") REFERENCES "serie"("codigo")
);
COMMIT;
```

---

## 8) Próxima tarefa após incluir no projeto

Depois de aplicar este setup, a próxima entrega é:
1. criar tela Home listando Etapas;
2. navegar para tela de Eixos/Competências;
3. replicar o padrão `Entity -> Dao -> Repository -> ViewModel -> UI` para as demais entidades do MVP.

# Fase 2 — Implementação do fluxo de Habilidades (mesmo padrão da Fase 0 e 1)

## Contexto
Este guia segue o **mesmo formato operacional** das fases anteriores: passo a passo por arquivo, com trechos prontos para copiar/colar.

Objetivo da Fase 2:
1. ligar Série/Etapa ao fluxo de Eixos/Competências;
2. substituir listas estáticas por leitura guiada no banco;
3. implementar tela de habilidades com filtros combinados.

---

## 1) Navegação (nav_graph)

Arquivo: `app/src/main/res/navigation/nav_graph.xml`

Adicionar fragments e ações:

```xml
<fragment
    android:id="@+id/serieFragment"
    android:name="com.example.appcombncc.ui.fragment.SerieFragment"
    android:label="Séries">

    <action
        android:id="@+id/action_serieFragment_to_eixoCompetenciaFragment"
        app:destination="@id/eixoCompetenciaFragment" />
</fragment>

<fragment
    android:id="@+id/eixoCompetenciaFragment"
    android:name="com.example.appcombncc.ui.fragment.EixoCompetenciaFragment"
    android:label="Eixos/Competências">

    <action
        android:id="@+id/action_eixoCompetenciaFragment_to_habilidadesFragment"
        app:destination="@id/habilidadesFragment" />
</fragment>

<fragment
    android:id="@+id/habilidadesFragment"
    android:name="com.example.appcombncc.ui.fragment.HabilidadesFragment"
    android:label="Habilidades" />
```

---

## 2) DAO — Eixo

Arquivo: `app/src/main/java/com/example/appcombncc/data/dao/EixoDao.kt`

```kotlin
package com.example.appcombncc.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.appcombncc.data.entity.EixoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EixoDao {
    @Query("SELECT * FROM eixo ORDER BY descricao")
    fun getAll(): Flow<List<EixoEntity>>

    @Query("SELECT * FROM eixo WHERE codigo IN (SELECT codigo_eixo FROM serie_eixo WHERE codigo_serie = :serieCodigo) ORDER BY descricao")
    fun getBySerie(serieCodigo: String): Flow<List<EixoEntity>>
}
```

---

## 3) DAO — Competência

Arquivo: `app/src/main/java/com/example/appcombncc/data/dao/CompetenciaDao.kt`

```kotlin
package com.example.appcombncc.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.appcombncc.data.entity.CompetenciaEspecificaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CompetenciaDao {
    @Query("SELECT * FROM competencia_especifica WHERE etapa_codigo = :etapaCodigo ORDER BY codigo")
    fun getByEtapa(etapaCodigo: String): Flow<List<CompetenciaEspecificaEntity>>
}
```

---

## 4) DAO — Habilidade

Arquivo: `app/src/main/java/com/example/appcombncc/data/dao/HabilidadeDao.kt`

```kotlin
package com.example.appcombncc.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.appcombncc.data.entity.HabilidadeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HabilidadeDao {

    @Query("SELECT * FROM habilidade WHERE eixo_codigo = :eixoCodigo ORDER BY codigo")
    fun getByEixo(eixoCodigo: String): Flow<List<HabilidadeEntity>>

    @Query("SELECT * FROM habilidade WHERE competencia_codigo = :competenciaCodigo ORDER BY codigo")
    fun getByCompetencia(competenciaCodigo: String): Flow<List<HabilidadeEntity>>

    @Query("SELECT * FROM habilidade WHERE codigo LIKE '%' || :busca || '%' OR descricao LIKE '%' || :busca || '%' ORDER BY codigo")
    fun searchByCodigoOrDescricao(busca: String): Flow<List<HabilidadeEntity>>
}
```

---

## 5) Repository — Eixo/Competência

Arquivo: `app/src/main/java/com/example/appcombncc/repository/EixoCompetenciaRepository.kt`

```kotlin
package com.example.appcombncc.repository

import com.example.appcombncc.data.dao.CompetenciaDao
import com.example.appcombncc.data.dao.EixoDao

class EixoCompetenciaRepository(
    private val eixoDao: EixoDao,
    private val competenciaDao: CompetenciaDao
) {
    fun getEixosBySerie(serieCodigo: String) = eixoDao.getBySerie(serieCodigo)
    fun getCompetenciasByEtapa(etapaCodigo: String) = competenciaDao.getByEtapa(etapaCodigo)
}
```

---

## 6) Repository — Habilidades

Arquivo: `app/src/main/java/com/example/appcombncc/repository/HabilidadeRepository.kt`

```kotlin
package com.example.appcombncc.repository

import com.example.appcombncc.data.dao.HabilidadeDao

class HabilidadeRepository(private val habilidadeDao: HabilidadeDao) {
    fun getByEixo(eixoCodigo: String) = habilidadeDao.getByEixo(eixoCodigo)
    fun getByCompetencia(competenciaCodigo: String) = habilidadeDao.getByCompetencia(competenciaCodigo)
    fun search(busca: String) = habilidadeDao.searchByCodigoOrDescricao(busca)
}
```

---

## 7) ViewModel — Eixo/Competência

Arquivo: `app/src/main/java/com/example/appcombncc/viewmodel/EixoCompetenciaViewModel.kt`

```kotlin
package com.example.appcombncc.viewmodel

import androidx.lifecycle.ViewModel
import com.example.appcombncc.repository.EixoCompetenciaRepository

class EixoCompetenciaViewModel(
    private val repository: EixoCompetenciaRepository
) : ViewModel() {
    fun getEixosBySerie(serieCodigo: String) = repository.getEixosBySerie(serieCodigo)
    fun getCompetenciasByEtapa(etapaCodigo: String) = repository.getCompetenciasByEtapa(etapaCodigo)
}
```

---

## 8) ViewModel — Habilidades

Arquivo: `app/src/main/java/com/example/appcombncc/viewmodel/HabilidadesViewModel.kt`

```kotlin
package com.example.appcombncc.viewmodel

import androidx.lifecycle.ViewModel
import com.example.appcombncc.repository.HabilidadeRepository

class HabilidadesViewModel(
    private val repository: HabilidadeRepository
) : ViewModel() {
    fun getByEixo(eixoCodigo: String) = repository.getByEixo(eixoCodigo)
    fun getByCompetencia(competenciaCodigo: String) = repository.getByCompetencia(competenciaCodigo)
    fun search(busca: String) = repository.search(busca)
}
```

---

## 9) Layout — Eixo/Competência

Arquivo: `app/src/main/res/layout/fragment_eixo_competencia.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp">

    <TextView
        android:id="@+id/tituloTv"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Selecione Eixo/Competência" />

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/eixoCompetenciaRv"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_marginTop="12dp" />
</LinearLayout>
```

---

## 10) Fragment — Eixo/Competência

Arquivo: `app/src/main/java/com/example/appcombncc/ui/fragment/EixoCompetenciaFragment.kt`

```kotlin
class EixoCompetenciaFragment : Fragment(R.layout.fragment_eixo_competencia) {
    // Ler etapa/série dos argumentos
    // Se etapa == "EM" -> carregar competências
    // Senão -> carregar eixos
    // Ao clicar item, navegar para HabilidadesFragment com código selecionado
}
```

---

## 11) Layout — Habilidades

Arquivo: `app/src/main/res/layout/fragment_habilidades.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp">

    <EditText
        android:id="@+id/buscaEt"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Buscar por código ou descrição" />

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/habilidadesRv"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_marginTop="12dp" />
</LinearLayout>
```

---

## 12) Fragment — Habilidades

Arquivo: `app/src/main/java/com/example/appcombncc/ui/fragment/HabilidadesFragment.kt`

```kotlin
class HabilidadesFragment : Fragment(R.layout.fragment_habilidades) {
    // Receber argumentos (etapa, série, eixo/competência)
    // Executar consulta adequada via ViewModel
    // Aplicar busca textual (código/descrição)
    // Renderizar lista no RecyclerView
}
```

---

## 13) Substituição de listas estáticas

Nesta fase, remover hardcode de:
- eixos;
- competências;
- habilidades.

Substituir por leitura via DAO/Repository/ViewModel.

---

## 14) Checklist de validação da Fase 2

- [ ] Série/Etapa navega para Eixo/Competência sem crash.
- [ ] Regra de etapa funciona (`EM` -> competência; `EI/EF` -> eixo).
- [ ] Tela de Habilidades aplica filtros combinados.
- [ ] Busca por código/descrição retorna resultados esperados.
- [ ] Nenhuma lista principal depende mais de hardcode.

---

## 15) Definição de pronto da Fase 2

A Fase 2 estará concluída quando:
- fluxo Série/Etapa -> Eixo/Competência -> Habilidades estiver funcional;
- filtros combinados de habilidades estiverem ativos e validados;
- dados de listas vierem do banco (não estáticos);
- navegação e estados da UI estiverem estáveis.


---

## 16) Pacote completo da Fase 2 (Entities + Database + Application)

### 16.1 Entity — `HabilidadeEntity.kt`
Arquivo: `app/src/main/java/com/example/appcombncc/data/entity/HabilidadeEntity.kt`

```kotlin
package com.example.appcombncc.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habilidade")
data class HabilidadeEntity(
    @PrimaryKey
    val codigo: String,
    val descricao: String,
    val explicacao: String?,
    val exemplo: String?,
    @ColumnInfo(name = "eixo_codigo")
    val eixoCodigo: String?,
    @ColumnInfo(name = "objeto_conhecimento_id")
    val objetoConhecimentoId: Long?,
    @ColumnInfo(name = "conceito_codigo")
    val conceitoCodigo: String?,
    @ColumnInfo(name = "competencia_codigo")
    val competenciaCodigo: String?
)
```

### 16.2 Entity — `EixoEntity.kt`
Arquivo: `app/src/main/java/com/example/appcombncc/data/entity/EixoEntity.kt`

```kotlin
package com.example.appcombncc.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "eixo")
data class EixoEntity(
    @PrimaryKey
    val codigo: String,
    val descricao: String
)
```

### 16.3 Entity — `CompetenciaEspecificaEntity.kt`
Arquivo: `app/src/main/java/com/example/appcombncc/data/entity/CompetenciaEspecificaEntity.kt`

```kotlin
package com.example.appcombncc.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "competencia_especifica")
data class CompetenciaEspecificaEntity(
    @PrimaryKey
    val codigo: String,
    val descricao: String,
    @ColumnInfo(name = "etapa_codigo")
    val etapaCodigo: String
)
```

### 16.4 Entity — `SerieEixoEntity.kt` (tabela de associação)
Arquivo: `app/src/main/java/com/example/appcombncc/data/entity/SerieEixoEntity.kt`

```kotlin
package com.example.appcombncc.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "serie_eixo",
    primaryKeys = ["codigo_serie", "codigo_eixo"]
)
data class SerieEixoEntity(
    @ColumnInfo(name = "codigo_serie")
    val codigoSerie: String,
    @ColumnInfo(name = "codigo_eixo")
    val codigoEixo: String
)
```

### 16.5 Atualizar `AppComBnccDatabase.kt`
Arquivo: `app/src/main/java/com/example/appcombncc/data/database/AppComBnccDatabase.kt`

Adicionar as entities e DAOs da Fase 2:

```kotlin
@Database(
    entities = [
        EtapaEntity::class,
        EixoEntity::class,
        CompetenciaEspecificaEntity::class,
        HabilidadeEntity::class,
        SerieEixoEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppComBnccDatabase : RoomDatabase() {
    abstract fun etapaDao(): EtapaDao
    abstract fun eixoDao(): EixoDao
    abstract fun competenciaDao(): CompetenciaDao
    abstract fun habilidadeDao(): HabilidadeDao
}
```

### 16.6 Atualizar `AppComBnccApplication.kt`
Arquivo: `app/src/main/java/com/example/appcombncc/AppComBnccApplication.kt`

Registrar repositórios da Fase 2:

```kotlin
class AppComBnccApplication : Application() {
    val database by lazy { AppComBnccDatabase.getDatabase(this) }

    val etapaRepository by lazy { EtapaRepository(database.etapaDao()) }
    val eixoCompetenciaRepository by lazy {
        EixoCompetenciaRepository(database.eixoDao(), database.competenciaDao())
    }
    val habilidadeRepository by lazy { HabilidadeRepository(database.habilidadeDao()) }
}
```

### 16.7 Factories de ViewModel (quando usar construtor com dependência)

Arquivo: `app/src/main/java/com/example/appcombncc/viewmodel/EixoCompetenciaViewModelFactory.kt`

```kotlin
class EixoCompetenciaViewModelFactory(
    private val repository: EixoCompetenciaRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EixoCompetenciaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EixoCompetenciaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
```

Arquivo: `app/src/main/java/com/example/appcombncc/viewmodel/HabilidadesViewModelFactory.kt`

```kotlin
class HabilidadesViewModelFactory(
    private val repository: HabilidadeRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HabilidadesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HabilidadesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
```


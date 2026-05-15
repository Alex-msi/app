# Mapa técnico atual — AppComBncc

Este documento registra o estado atual do projeto `AppComBncc`, incluindo arquitetura, navegação, autenticação Google, política de sessão local e telas de prática/autenticada já implementadas.

## 1) Estrutura geral (módulo app)

```text
app/src/main/java/com/example/appcombncc
├── AppComBnccApplication.kt
├── data
│   ├── dao
│   ├── database
│   ├── entity
│   └── model
├── repository
├── ui
│   ├── activity
│   └── fragment
├── util
└── viewmodel
```

## 2) Camadas e responsabilidades

### Application
- `AppComBnccApplication.kt`
  - Classe `Application` do projeto.
  - Inicializa o Room e expõe repositórios principais.

### Data

#### `data/entity`
Entidades persistidas no Room:
- `EtapaEntity`
- `SerieEntity`
- `EixoEntity`
- `CompetenciaEspecificaEntity`
- `HabilidadeEntity`
- `ObjetoConhecimentoEntity`
- `ConceitoHabilidadeEntity`
- `SerieEixoEntity`

#### `data/dao`
Interfaces de acesso ao banco de dados:
- `EtapaDao`
- `SerieDao`
- `EixoDao`
- `CompetenciaDao`
- `HabilidadeDao`

#### `data/database`
- `AppComBnccDatabase`
  - Classe principal do Room Database.
  - Criação a partir do asset `bncc_computacao.db`.

#### `data/model`
Modelos auxiliares para projeções e UI:
- `EixoHabilidadeCount`
- `ObjetoHabilidadeCount`
- `CompetenciaResumoItem`
- `CompetenciaHabilidadeItem`
- `HabilidadeListaItem`
- `PraticaUsuarioItem`

### Repository
Camada de orquestração de dados entre DAO e ViewModel:
- `EtapaRepository`
- `SerieRepository`
- `EixoCompetenciaRepository`
- `HabilidadeRepository`

### ViewModel
Estado e regras de apresentação por fluxo:
- `HomeViewModel`
- `SerieViewModel`
- `EixoCompetenciaViewModel`
- `HabilidadesViewModel`

Fábricas e suporte:
- `HomeViewModelFactory`
- `SerieViewModelFactory`
- `EixoCompetenciaViewModelFactory`
- `HabilidadesViewModelFactory`
- `AppViewModelFactory`
- `ViewModelHelpers`

### UI

#### `ui/activity`
- `MainActivity`
  - Host da navegação (`NavHostFragment`).
  - Integração da TopAppBar com `NavController`.
  - Menu superior com ações `Home` e `Login`.
  - Fluxo de autenticação Google (`GoogleSignInClient`).
  - Regra de sessão local no startup (preserva Home como entrada pública).

#### `ui/fragment`
Telas existentes:
- `HomeFragment`
- `SerieFragment`
- `EixoCompetenciaFragment`
- `ObjetoConceitoFragment`
- `ListaHabilidadeFragment`
- `HabilidadesFragment`
- `PraticaFragment`
- `ListaAutenticadaFragment`

#### `ui` (adapters)
Adapters de RecyclerView e listas:
- `SimpleTextAdapter`
- `EixoResumoAdapter`
- `CompetenciaResumoAdapter`
- `ObjetoResumoAdapter`
- `HabilidadeListaAdapter`
- `PraticaUsuarioAdapter`

### Util
- `HabilidadeFiltroUtils`
- `PdfDownloadUtil`
- `SessionManager`
  - Persistência local de sessão em `SharedPreferences`.
  - Campos atuais de sessão:
    - `autenticado`
    - `googleUid`
    - `email`
    - `nome`
    - `ultimoLoginEm`
    - `tokenValidoAte` (opcional)
  - Política de validade de sessão local + expiração por token (quando informado).

## 3) Navegação implementada

Fluxo principal registrado no `nav_graph`:

1. `homeFragment` (início)
2. `serieFragment`
3. `eixoCompetenciaFragment`
4. `objetoConceitoFragment`
5. `listaHabilidadeFragment`
6. `habilidadesFragment`
7. `praticaFragment`

Fluxo autenticado:
- `listaAutenticadaFragment` (aberta a partir do login Google na toolbar).

## 4) Autenticação e sessão (estado atual)

### Autenticação
- Login Google via `play-services-auth`.
- Resultado tratado em `MainActivity` com feedback por `Toast`.

### Sessão local
- Sessão salva localmente após autenticação bem-sucedida e também ao detectar conta já autenticada.
- Na inicialização:
  - sessão válida: mantém usuário autenticado, sem redirecionamento automático (Home continua sendo entrada);
  - sessão inválida/revogada: limpa sessão local e mantém funcionalidades públicas.

## 5) Tela autenticada (`ListaAutenticadaFragment`)

Tela dividida em 2 partes:
1. Dados do usuário:
   - ID (somente visualização)
   - Nome
   - Email (proveniente do login Google)
   - Tipo
2. Lista de práticas do usuário (`RecyclerView`) contendo:
   - título da prática
   - código da habilidade
   - série/etapa

## 6) Recursos visuais e layouts relevantes

- `activity_main.xml` com `MaterialToolbar` e menu `top_app_bar_menu.xml`.
- Ícones de menu:
  - `ic_home.xml`
  - `ic_login.xml`
- Layouts de prática/autenticada:
  - `fragment_pratica.xml`
  - `fragment_lista_autenticada.xml`
  - `item_pratica_usuario.xml`
- Layouts de item renomeados:
  - `item_habilidade_simple_text.xml`
  - `item_eixo_competencia_resumo.xml`

## 7) Stack adotada

- Kotlin (Android)
- ViewBinding
- AndroidX Navigation
- AndroidX Lifecycle/ViewModel
- Room + KSP
- Kotlin Coroutines
- RecyclerView + SwipeRefreshLayout
- Google Play Services Auth (`play-services-auth`)

## 8) Assets locais

Em `app/src/main/assets`:
- `bncc_computacao.db`
- `BNCCComputaoCompletodiagramado.pdf`

---

Documento atualizado para refletir o estado funcional atual do projeto e servir de base para as próximas evoluções.

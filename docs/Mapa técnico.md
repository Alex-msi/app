# Mapa técnico atual — AppComBncc

Este documento registra o estado atual do projeto `AppComBncc`, incluindo arquitetura, persistência, autenticação/sessão, navegação e política visual implementadas no app.

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
│   ├── adapter
│   └── fragment
├── util
└── viewmodel
```

## 2) Camadas e responsabilidades

### Application
- `AppComBnccApplication.kt`
  - Classe `Application` do projeto.
  - Inicializa o Room e expõe repositórios principais.
  - Fornece `usuarioRepository` e fábrica de `sessionRepository(...)`.

### Data

#### `data/entity`
Entidades persistidas no Room:
- `EtapaEntity`
- `SerieEntity
- `EixoEntity`
- `CompetenciaEspecificaEntity`
- `HabilidadeEntity`
- `ObjetoConhecimentoEntity`
- `ConceitoHabilidadeEntity`
- `SerieEixoEntity`
- `UsuarioEntity`
- `PraticaEntity`

#### `data/dao`
Interfaces de acesso ao banco de dados:
- `EtapaDao`
- `SerieDao`
- `EixoDao`
- `CompetenciaDao`
- `HabilidadeDao`
- `UsuarioDao`
- busca por email (`getByEmail`, `getByEmailSnapshot`)
- atualização de nome (`atualizarNomeById`)
- `PraticaDao`

#### `data/database`
- `AppComBnccDatabase`
  - Classe principal do Room Database.
  - Criação a partir do asset `bncc_computacao.db`.
  - `version = 2`.
  - Migração explícita `MIGRATION_1_2` para criar `usuario` e `pratica` sem destruição de dados.
  - **Sem** `fallbackToDestructiveMigration`.

#### `data/model`
Modelos auxiliares para projeções e UI:
- `EixoHabilidadeCount`
- `ObjetoHabilidadeCount`
- `CompetenciaResumoItem`
- `CompetenciaHabilidadeItem`
- `HabilidadeListaItem`
- `PraticaUsuarioItem` 

### Repository
Camada de orquestração de dados entre DAO/Session e ViewModel:
- `EtapaRepository`
- `SerieRepository`
- `EixoCompetenciaRepository`
- `HabilidadeRepository`
- `UsuarioRepository`
  - sincroniza usuário no primeiro login e logins subsequentes
- `SessionRepository`
  - encapsula persistência de sessão local

### ViewModel
Estado e regras de apresentação por fluxo:
- `HomeViewModel`
- `SerieViewModel`
- `EixoCompetenciaViewModel`
- `HabilidadesViewModel`
-  `AuthViewModel`
- processa sucesso/falha do login
- sincroniza usuário com/sem navegação
- `UsuarioViewModel`

Fábricas e suporte:
- `HomeViewModelFactory`
- `SerieViewModelFactory`
- `EixoCompetenciaViewModelFactory`
- `HabilidadesViewModelFactory`
- `AuthViewModelFactory`
- `UsuarioViewModelFactory`
- `AppViewModelFactory`
- `ViewModelHelpers`

### UI

#### `ui/activity`
- `MainActivity`
  - Host da navegação (`NavHostFragment`).
  - Integração da `MaterialToolbar` com `NavController`.
  - Suporte a `navigateUp()` na toolbar.
  - Home como destino de topo.
  - Integra login Google com `AuthViewModel`.

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
  - Campos de sessão:
    - `autenticado`
    - `googleUid`
    - `email`
    - `nome`
    - `ultimoLoginEm`
    - `tokenValidoAte` (opcional)
          
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
`listaAutenticadaFragment`

## 4) Autenticação e sessão (estado atual)

### Autenticação
- Login Google via `play-services-auth`.
- Resultado tratado no fluxo de `AuthViewModel` e refletido na UI por `AuthResult`.

### Sessão local
- Sessão salva localmente após autenticação.
- Na inicialização:
  - sessão válida: sincroniza usuário no banco sem navegação automática;
  - sessão inválida/revogada: limpa sessão local e mantém funcionalidades públicas.
 
### Persistência de usuário (`usuario`)
- No primeiro login, cria usuário com:
  - `nome = null`
  - `tipo = professor`
  - `autenticado = 1`
- Nos logins seguintes:
  - atualiza `google_uid`, `autenticado`, `ultimo_login_em`, `atualizado_em`
  - preserva `nome` já salvo pelo usuário.

## 5) Tela autenticada (`ListaAutenticadaFragment`)

Tela dividida em 2 partes:
1. Dados do usuário:
   - ID (somente visualização)
   - Nome (editável sob ação de botão)
   - Email
   - Tipo (somente leitura)
   - Botões:
     - `Alterar` (habilita edição do nome)
     - `Salvar` (persiste nome e volta para leitura)
2. Lista de práticas do usuário (`RecyclerView`) — atualmente com dados mock.

Comportamentos de lifecycle aplicados:
- Coleta de dados com `viewLifecycleOwner.repeatOnLifecycle`.
- Proteção contra acesso a binding após `onDestroyView`.

## 6) Recursos visuais e layouts relevantes

- `activity_main.xml` com `MaterialToolbar`.
- Home com layout revisado, seções claras e ações:
  - busca de habilidade (`buscaHabilidadeEt` + `procurarTv`);
  - botão `Favoritos`;
  - botão `PDF Computação Complemento a BNCC`.
  - Home com ações:
  - busca de habilidade (`buscaHabilidadeEt` + `procurarTv`)
  - botão `Favoritos`
  - botão `PDF Computação Complemento a BNCC`
- Tela autenticada e prática com cards e padronização visual:
  - `fragment_lista_autenticada.xml`
  - `fragment_pratica.xml`
  - `item_pratica_usuario.xml`
- Itens de lista padronizados com cards:
  - `item_simple_text.xml`
  - `item_eixo_resumo.xml`
  - `item_objeto_resumo.xml`
- Fragments com padronização visual (fundo, espaçamento, tipografia):
  - `fragment_home.xml`
  - `fragment_serie.xml`
  - `fragment_eixo_competencia.xml`
  - `fragment_objeto_conceito.xml`
  - `fragment_lista_habilidade.xml`
  - `fragment_habilidades.xml`

## 7) Padronização visual aplicada (Roteiro)
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

- Tokens de cor em `colors.xml`.
- Escalas em `dimens.xml`.
- Estilos reutilizáveis em `styles.xml`:
  - `AppTitleText`
  - `SectionTitleText`
  - `BodyText`
  - `SecondaryText`
  - `CardContainer`
- Manutenção das cores já definidas dos botões EI, EF e EM.

## 8) Stack adotada

- Kotlin (Android)
- ViewBinding
- AndroidX Navigation
- AndroidX Lifecycle/ViewModel
- Room + KSP
- Kotlin Coroutines / Flow
- RecyclerView + SwipeRefreshLayout
- Google Play Services Auth (`play-services-auth`) quando habilitado
- Material Components (`MaterialToolbar`, `MaterialCardView`)

## 9) Assets locais

Em `app/src/main/assets`:
- `bncc_computacao.db`
- `BNCCComputaoCompletodiagramado.pdf`

---

Documento atualizado para refletir o estado funcional atual do projeto e servir de base para as próximas evoluções.

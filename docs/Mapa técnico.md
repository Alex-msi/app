# Mapa técnico atual — AppComBncc

Este documento registra o estado atual do projeto `AppComBncc`, incluindo arquitetura, navegação, política visual e os fluxos implementados no app.

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

### Data

#### `data/entity`
Entidades persistidas no Room:
- `EtapaEntity`
- `SerieEntity` (quando presente no mapeamento atual do banco)
- `EixoEntity`
- `CompetenciaEspecificaEntity`
- `HabilidadeEntity`
- `ObjetoConhecimentoEntity` (quando presente no mapeamento atual do banco)
- `ConceitoHabilidadeEntity` (quando presente no mapeamento atual do banco)
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
- `PraticaUsuarioItem` (manter quando utilizado em evoluções)

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
- `AppViewModelFactory` (manter quando utilizado no projeto)
- `ViewModelHelpers` (manter quando utilizado no projeto)

### UI

#### `ui/activity`
- `MainActivity`
  - Host da navegação (`NavHostFragment`).
  - Integração da `MaterialToolbar` com `NavController`.
  - Suporte a `navigateUp()` na toolbar para retorno entre telas.
  - Home como destino de topo.

#### `ui/fragment`
Telas existentes:
- `HomeFragment`
- `SerieFragment`
- `EixoCompetenciaFragment`
- `ObjetoConceitoFragment`
- `ListaHabilidadeFragment`
- `HabilidadesFragment`
- `PraticaFragment` (manter quando disponível no fluxo do projeto)
- `ListaAutenticadaFragment` (manter quando disponível no fluxo do projeto)

#### `ui` (adapters)
Adapters de RecyclerView e listas:
- `SimpleTextAdapter`
- `EixoResumoAdapter`
- `CompetenciaResumoAdapter`
- `ObjetoResumoAdapter`
- `HabilidadeListaAdapter`
- `PraticaUsuarioAdapter` (manter quando presente)

### Util
- `HabilidadeFiltroUtils` (manter quando presente)
- `PdfDownloadUtil` (quando utilizado) / lógica de download no `HomeFragment`

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
7. `praticaFragment` (quando aplicável)

Fluxo autenticado:
- `listaAutenticadaFragment` (quando aplicável na versão com autenticação).

### Observações de fluxo implementadas neste ciclo
- Fluxo **EM**:
  - Em `EixoCompetenciaFragment`, exibição de competências com total de habilidades.
  - Item de competência com quebra de linha no total (melhor legibilidade).
  - Ao clicar em competência, navega para `ListaHabilidadeFragment` com contexto da competência.
- Em `ListaHabilidadeFragment` (EM):
  - título contextual: **“Lista de Habilidades da Competência:”**;
  - label **“Competência”** acima da descrição selecionada;
  - listagem de habilidades filtradas pela competência selecionada.

## 4) Autenticação e sessão (estado atual)

### Autenticação
- Login Google via `play-services-auth` (quando esta feature está habilitada na branch/versão).
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

- `activity_main.xml` com `MaterialToolbar`.
- Home com layout revisado, seções claras e ações:
  - busca de habilidade (`buscaHabilidadeEt` + `procurarTv`);
  - botão `Favoritos`;
  - botão `PDF Computação Complemento a BNCC`.
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

- Tokens de cor adicionados em `colors.xml` (paleta institucional + textos/superfícies).
- Escalas em `dimens.xml` (espaçamento, tipografia e área mínima de toque).
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
- Kotlin Coroutines
- RecyclerView + SwipeRefreshLayout
- Google Play Services Auth (`play-services-auth`) quando habilitado
- Material Components (`MaterialToolbar`, `MaterialCardView`)

## 9) Assets locais

Em `app/src/main/assets`:
- `bncc_computacao.db`
- `BNCCComputaoCompletodiagramado.pdf`

---

Documento atualizado para refletir o estado funcional atual do projeto e servir de base para as próximas evoluções.

# Arquitetura Inicial do AppBNCC

## Objetivo
Propor uma arquitetura **simples e evolutiva**, alinhada aos padrões já usados nos projetos anteriores (camadas diretas com `ui`, `viewmodel`, `repository`, `data`, `adapter`), adequada para desenvolvimento de TCC e compatível com Android Studio.
Propor uma arquitetura **simples e evolutiva**, alinhada aos padrões já usados nos projetos anteriores (camadas diretas com `ui`, `viewmodel`, `repository`, `data`, `model` e `model/domain`, além de `adapter`), adequada para desenvolvimento de TCC e compatível com Android Studio.

---

## 1) Estrutura de pacotes

Sugestão de pacote base:

`br.edu.ifsp.scl.sdm.appbncc`

Estrutura inicial:

- `model`
  - Classes de dados e estruturas compartilhadas no app.
  - `domain`: classes de domínio usadas no app (`Etapa`, `Serie`, `Eixo`, `ObjetoConhecimento`, `ConceitoHabilidade`, `Habilidade`, `CompetenciaEspecifica`, `Usuario`, `Pratica`).
- `data`
  - `entity`: entidades Room (separadas das classes de domínio quando necessário).
  - `dao`: interfaces `@Dao` com consultas.
  - `database`: classe `AppBnccDatabase`.
  - `mapper`: conversões entre `entity` e `model/domain`.
- `repository`
  - Repositórios para encapsular acesso aos DAOs e regras simples de consulta.
- `viewmodel`
  - ViewModels por tela/fluxo, expondo `LiveData` ou `StateFlow` para a UI.
- `ui`
  - `activity`: `MainActivity`.
  - `fragment`: telas de lista, filtro e detalhe.
  - `navigation`: grafo de navegação (`nav_graph.xml`).
- `adapter`
  - Adapters de RecyclerView para exibir listas (etapas, séries, eixos, habilidades etc.).

> Observação: para manter coerência com as referências, adotar desde o início `model` com subpacote `domain` (ex.: `model/domain`), evitando mudanças estruturais posteriores.

---

## 2) Responsabilidade de cada camada

### UI (`ui` + `adapter`)
- Renderizar telas e interações do usuário.
- Encaminhar eventos para a ViewModel (clique, busca, seleção de filtros).
- Não acessar DAO/database diretamente.

### ViewModel (`viewmodel`)
- Manter estado da tela (lista, loading, erro, filtro ativo).
- Orquestrar chamadas ao repository.
- Expor dados observáveis para a UI.

### Repository (`repository`)
- Centralizar regras de leitura e filtragem de dados BNCC.
- Encapsular origem dos dados (inicialmente Room/local).
- Isolar a UI de mudanças na persistência.

### Data (`data`)
- Definir estrutura de persistência local com Room (`Entity`, `Dao`, `Database`).
- Executar consultas por etapa, série, eixo, objeto, conceito e habilidade.
- Mapear entidades de banco para modelos do app.

### Model (`model` + `model/domain`)
- `model/domain`: representar conceitos pedagógicos da BNCC com classes simples de domínio.
- `model` (demais subpastas): concentrar estruturas de dados auxiliares quando necessário.
- Servir como contrato de dados para as camadas superiores.

---

## 3) Principais telas

1. **Tela Inicial / Home**
   - Entrada para navegação por etapa de ensino.
   - Exibe lista/botões de etapas: EI, EF e EM.
   - Opção para procurar habilidade por código.
   - Atalho para favoritos.
   - Ícone de configurações.
   - Botão para baixar PDF da Computação na BNCC.
   - Ícone para login.

2. **Tela de Séries (quando aplicável)**
   - Exibida para etapas com divisão por ano e/ou por etapa.
   - Contém um **dropdown de séries** com 1º ao 9º ano.
   - Contém um **dropdown de etapas agrupadas** com opções: 1º ao 5º e 6º ao 9º.

3. **Tela de Eixos / Competências**
   - Para EI e EF: navegação por eixos.
   - Para EM: navegação por competências específicas.

4. **Tela de Objetos de Conhecimento**
   - Lista objetos e subobjetos (hierarquia).

5. **Tela de Conceitos/Habilidades**
   - Lista habilidades associadas aos filtros selecionados.

6. **Tela de Detalhe da Habilidade**
   - Exibe código e descrição.
   - Botão/área expansível para explicação.
   - Botão/área expansível para exemplo.
   - Botão para criar prática (**evolução**).
   - Ícone para favoritar.

7. **Tela de Login**
   - Campos: id (auto-incremento não visível), nome, email, senha e tipo.
   - Tipos previstos: professor, coordenador, professor_revisor e admin.
   - Ações: cadastrar, atualizar, visualizar e remover.

8. **Tela de Prática (evolução)**
   - Dividida em duas partes.
   - Parte 1: código da habilidade, descrição resumida, série, tempo e tipo (tempo/tipo com interação), botão criar prática e botão criar prática com IA (evolução).
   - Parte 2: título, objetivo, matérias, introdução, desenvolvimento, prática, encerramento e status.
   - id auto-incremento não visível.
   - Ações: salvar, atualizar, visualizar e remover.

9. **Tela de Busca/Filtro (opcional inicial, recomendada para evolução)**
   - Busca textual por código/descrição e filtros combinados.

---

## 4) Principais entidades

Com base no diagrama UML e na atualização enviada, as entidades centrais do app serão:

- **Etapa**
  - `codigo`, `nome`
- **Serie**
  - `codigo`, `nome`, `etapaCodigo`
- **Eixo**
  - `codigo`, `descricao`, `etapaCodigo` (quando aplicável)
- **CompetenciaEspecifica**
  - `codigo`, `descricao`, `etapaCodigo`
- **ObjetoConhecimento**
  - `id`, `nome`, `paiId` (hierarquia)
- **ConceitoHabilidade**
  - `codigo`, `descricao`, `objetoId`
- **Habilidade**
  - `codigo`, `descricao`, `explicacao`, `exemplo`
  - chaves de ligação: `etapaCodigo`, `serieCodigo?`, `eixoCodigo?`, `objetoId?`, `conceitoCodigo?`, `competenciaCodigo?`

### Entidades de gestão pedagógica (nova atualização)

- **Usuario**
  - `id: Long`
  - `nome: String`
  - `email: String`
  - `senha: String`
  - `tipo: String` (professor, coordenador, professor_revisor, admin)
  - operações previstas na camada de serviço/repositório: cadastrar, atualizar, remover, visualizar.

- **Pratica**
  - `id: Long`
  - `tempo: Integer`
  - `tipo: String`
  - `titulo: String`
  - `objetivo: String`
  - `materias: String`
  - `introducao: String`
  - `desenvolvimento: String`
  - `pratica: String`
  - `encerramento: String`
  - `status: String`
  - operações previstas na camada de serviço/repositório: cadastrar, atualizar, remover, visualizar e validarStatus.

### Relacionamento entre entidades novas

- **Usuario 1 : N Pratica**
  - Um usuário pode criar várias práticas.
  - Cada prática pertence a um único usuário.
  - Campo recomendado em `Pratica`: `usuarioId` (FK).

---

## 5) Fluxo de navegação

Fluxo principal sugerido com Navigation Component:

**Home → Etapas → Séries** (apenas quando a etapa exigir) **→ Eixos (EI/EF) ou Competências (EM) → Objetos de Conhecimento → Lista de Habilidades → Detalhe da Habilidade**

Fluxos alternativos importantes:

- **Busca direta**: Home → Busca → Código da Habilidade → Detalhe.
- **Busca direta (evolução)**: Home → Busca → Lista de Habilidades → Detalhe.
- **Navegação sem série (EI/alguns casos)**: Etapa → Eixo/Objeto → Habilidades.

Diretriz de implementação de navegação:
- `MainActivity` com `NavHostFragment`.
- Fragments por tela.
- Ações nomeadas no padrão `action_origem_to_destino`.
- Argumentos seguros para passar filtros entre telas (id/código).

---


## 6) Ajuste de escopo da Fase 1 (implementação inicial de UI)

Nesta revisão, a Fase 1 ficará focada em **duas telas**:
1. **Home (Inicial)** com botões de etapas.
2. **Tela de Séries** com os dois dropdowns definidos.

O restante da Fase 1 (demais fluxos de dados/telas) fica para uma próxima etapa.

### Padrão de cores da implementação inicial

- **Educação Infantil (verde):** `#2E7D32`
- **Ensino Fundamental (azul):** `#1976D2`
- **Ensino Médio (amarelo):** `#FBC02D`
- **Fundo branco:** `#FFFFFF`
- **Cinza de ícones/textos secundários:** `#9E9E9E`
- **Cinza claro dos cards:** `#F5F5F5`

## Resumo da proposta

A arquitetura inicial adota o padrão já consolidado nos seus projetos recentes:

**UI (Fragment/Activity) → ViewModel → Repository → DAO/Room**

É uma estrutura simples, didática e adequada para TCC: facilita manutenção, permite crescimento incremental e evita complexidade desnecessária no início
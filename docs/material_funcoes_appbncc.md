# Material Explicativo das Funções do Projeto AppBNCC

## Visão Geral

O aplicativo AppBNCC utiliza a mesma estrutura de telas para diferentes fluxos da BNCC Computação:

- Educação Infantil
- Ensino Fundamental por série
- Ensino Fundamental por etapa consolidada
- Ensino Médio
- Busca geral

As diferenças entre os fluxos acontecem principalmente pelos parâmetros enviados entre as telas.

---

# 1. Fluxo Educação Infantil

Fluxo:

Home → Eixos → Lista de habilidades → Detalhes da habilidade

## HomeFragment

### navigateToEixoInfantil(etapaCor: String)

Finalidade:
Inicia o fluxo da Educação Infantil.

Parâmetros enviados:

```kotlin
etapaSelecionada = "EI"
habilidadeLike = "EI%"
```

---

## EixoCompetenciaFragment

### getResumoEixos(habilidadeLike)

Finalidade:
Busca os eixos e a quantidade de habilidades por eixo.

Exemplo de uso:

```kotlin
habilidadeLike = "EI%"
```

Resultado esperado:

- Pensamento Computacional
- Mundo Digital
- Cultura Digital

com suas respectivas quantidades de habilidades.

---

## ListaHabilidadeFragment

### getHabilidadesPorEixo(habilidadeLike, eixoSelecionado)

Finalidade:
Lista as habilidades da Educação Infantil pertencentes ao eixo selecionado.

---

## HabilidadesFragment

### getByCodigo(codigo)

Finalidade:
Carrega os detalhes completos da habilidade selecionada.

Informações exibidas:

- código
- descrição
- explicação
- exemplo

---

# 2. Fluxo Ensino Fundamental por série

Fluxo:

Home → Série → Eixos → Objetos → Lista de habilidades → Detalhes

## SerieFragment

### viewModel.series

Finalidade:
Carrega as séries disponíveis para o Spinner.

Exemplo:

- 1_ANO
- 2_ANO
- 3_ANO
- ...
- 9_ANO

---

### HabilidadeFiltroUtils.gerarHabilidadeLikePorSerie()

Finalidade:
Transforma a série selecionada em filtro SQL.

Exemplo:

```kotlin
"1_ANO" -> "EF01%"
"2_ANO" -> "EF02%"
```

---

## EixoCompetenciaFragment

### getResumoEixos(habilidadeLike)

Finalidade:
Lista os eixos e a quantidade de habilidades da série escolhida.

---

## ObjetoConceitoFragment

### getResumoObjetosPorEixo(habilidadeLike, eixoSelecionado)

Finalidade:
Lista os objetos de conhecimento relacionados ao eixo selecionado.

---

## ListaHabilidadeFragment

### getHabilidadesPorEixoObjeto()

Finalidade:
Lista habilidades filtradas por:

- série
- eixo
- objeto de conhecimento

---

### getHabilidadesPorEixo()

Finalidade:
Lista habilidades filtradas apenas por eixo.

Utilizado quando o usuário não seleciona objeto.

---

## HabilidadesFragment

### getByCodigo(codigo)

Finalidade:
Carrega os detalhes completos da habilidade selecionada.

---

# 3. Fluxo Ensino Fundamental consolidado

Fluxo:

Home → 1º ao 5º ou 6º ao 9º → Eixos → Objetos → Lista → Detalhes

## SerieFragment

### etapa1a5Bt

Finalidade:
Inicia o fluxo consolidado do 1º ao 5º ano.

Parâmetros:

```kotlin
etapaSelecionada = "EFI_ETAPA"
habilidadeLike = "EF15%"
```

---

### etapa6a9Bt

Finalidade:
Inicia o fluxo consolidado do 6º ao 9º ano.

Parâmetros:

```kotlin
etapaSelecionada = "EFII_ETAPA"
habilidadeLike = "EF69%"
```

---

## EixoCompetenciaFragment

### getResumoEixos(habilidadeLike)

Finalidade:
Lista os eixos e a quantidade de habilidades da etapa consolidada.

---

## ObjetoConceitoFragment

### getResumoObjetosPorEixo()

Finalidade:
Lista os objetos de conhecimento relacionados ao eixo escolhido.

---

## ListaHabilidadeFragment

### getHabilidadesPorEixoObjeto()

Finalidade:
Lista habilidades por:

- etapa consolidada
- eixo
- objeto

---

### getHabilidadesPorEixo()

Finalidade:
Lista habilidades apenas pelo eixo.

---

# 4. Fluxo Ensino Médio

Fluxo:

Home → Competências → Lista de habilidades → Detalhes

## HomeFragment

### navigateToEixoMedio()

Finalidade:
Inicia o fluxo do Ensino Médio.

Parâmetros enviados:

```kotlin
etapaSelecionada = "EM"
```

---

## EixoCompetenciaFragment

### getResumoCompetenciasPorEtapa("EM")

Finalidade:
Lista as competências específicas e a quantidade de habilidades de cada competência.

---

## ListaHabilidadeFragment

### getHabilidadesPorCompetencia(competenciaCodigo)

Finalidade:
Lista as habilidades pertencentes à competência selecionada.

---

## HabilidadesFragment

### getByCodigo(codigo)

Finalidade:
Carrega os detalhes completos da habilidade selecionada.

---

# 5. Fluxo de Busca

Fluxo:

Home → Busca → Lista de habilidades → Detalhes

## HomeFragment

### buscaHabilidadeEt.doAfterTextChanged

Finalidade:
Habilita ou desabilita o botão de busca conforme o usuário digita.

---

### procurarTv.setOnClickListener

Finalidade:
Envia o texto digitado para a tela de lista de habilidades.

---

## ListaHabilidadeFragment

### search(buscaTexto)

Finalidade:
Realiza busca nas habilidades.

Campos consultados:

- código
- descrição
- explicação
- exemplo

---

## HabilidadesFragment

### getByCodigo(codigo)

Finalidade:
Carrega os detalhes completos da habilidade selecionada.

---

# Funções principais dos DAOs

## getResumoEixos(habilidadeLike)

Usada em:

- Educação Infantil
- Ensino Fundamental por série
- Ensino Fundamental consolidado

Finalidade:
Contar habilidades por eixo.

---

## getResumoObjetosPorEixo(habilidadeLike, eixoCodigo)

Usada em:

- Ensino Fundamental por série
- Ensino Fundamental consolidado

Finalidade:
Contar habilidades por objeto de conhecimento.

---

## getHabilidadesPorEixo(habilidadeLike, eixoCodigo)

Usada em:

- Educação Infantil
- Fluxos sem objeto de conhecimento

Finalidade:
Listar habilidades por eixo.

---

## getHabilidadesPorEixoObjeto(habilidadeLike, eixoCodigo, objetoId)

Usada em:

- Ensino Fundamental por série
- Ensino Fundamental consolidado

Finalidade:
Listar habilidades por eixo e objeto.

---

## getResumoCompetenciasPorEtapa(etapaCodigo)

Usada em:

- Ensino Médio

Finalidade:
Contar habilidades por competência específica.

---

## getHabilidadesPorCompetencia(competenciaCodigo)

Usada em:

- Ensino Médio

Finalidade:
Listar habilidades de uma competência específica.

---

## searchByCodigoOrDescricao(busca)

Usada em:

- Busca geral

Finalidade:
Localizar habilidades pelo texto digitado.

---

## getByCodigo(codigo)

Usada em:

- Tela de detalhes

Finalidade:
Carregar todos os dados da habilidade selecionada.

---

# Resumo Final

O projeto utiliza reutilização de telas e consultas parametrizadas para atender diferentes fluxos da BNCC Computação.

Os filtros principais utilizados no sistema são:

```kotlin
EI%
EF01% até EF09%
EF15%
EF69%
EM
```

Esses filtros permitem reutilizar:

- Fragments
- Adapters
- ViewModels
- DAOs

mudando apenas os parâmetros enviados entre as telas.

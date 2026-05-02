@@ -10,70 +10,70 @@ Este memorial descritivo apresenta a modelagem do diagrama de classes UML do App
Nesta atualização, o documento mantém a estrutura original e incorpora o refinamento recente: além da base normativa da BNCC, passa a documentar também a base colaborativa do aplicativo (usuários e práticas pedagógicas).

---

## 2. OBJETIVO

O diagrama de classes tem como objetivos:

- Representar a estrutura curricular da BNCC em modelo orientado a objetos
- Mapear relacionamentos entre etapas, séries, eixos, objetos de conhecimento e habilidades
- Incluir a camada colaborativa de autoria e curadoria de práticas
- Servir como base conceitual para desenvolvimento de software
- Facilitar comunicação entre equipe técnica e orientadores pedagógicos
- Documentar hierarquia e cardinalidades dos relacionamentos

---

## 3. ENTIDADES DO MODELO

O diagrama é composto por **9 entidades**: **7 da base normativa BNCC** e **2 da base colaborativa**.

### 3.1 Tabela de Entidades

| Entidade | Atributos | Descrição |
|----------|-----------|-----------|
| **ETAPA** | - codigo : String<br>- nome : String | Níveis de ensino (EI, EF I, EF II, EM) |
| **SERIE** | - codigo : String<br>- nome : String | Anos escolares dentro de uma etapa |
| **EIXO** | - codigo : String<br>- descricao : String | Eixos temáticos que organizam habilidades |
| **OBJETO_CONHECIMENTO** | - id : Long<br>- nome : String | Conteúdos a serem trabalhados; hierárquicos |
| **CONCEITO_HABILIDADE** | - codigo : String<br>- descricao : String | Conceito pedagógico que contextualiza habilidades |
| **HABILIDADE** | - codigo : String<br>- descricao : String<br>- explicacao : String<br>- exemplo : String | Entidade central das habilidades da BNCC |
| **COMPETENCIA_ESPECIFICA** | - codigo : String<br>- descricao : String | Competências específicas (principalmente EM) |
| **USUARIO** | - id : Long<br>- nome : String<br>- email : String<br>- senha : String<br>- tipo : TipoUsuario | Autenticação, perfil e autoria de práticas |
| **PRATICA** | - id : Long<br>- tempo : Integer<br>- tipo : String<br>- titulo : String<br>- objetivo : String<br>- materiais : String<br>- introducao : String<br>- desenvolvimento : String<br>- pratica : String<br>- encerramento : String<br>- status : StatusPratica | Atividade pedagógica criada a partir de habilidade |

### 3.2 Entidade Central: HABILIDADE

A entidade **HABILIDADE** continua sendo o ponto focal do diagrama, articulando a estrutura curricular e, agora, também servindo de referência para criação de práticas colaborativas.

### 3.3 Enumerações da Camada Colaborativa

**TipoUsuario:**
- PROFESSOR
- COORDENADOR
- PROFESSOR_REVISOR
- ADMIN

**StatusPratica:**
- RASCUNHO
- EM_ANALISE
- APROVADA
- REJEITADA

---

## 4. RELACIONAMENTOS E CARDINALIDADES

O diagrama possui **12 relacionamentos** com cardinalidades definidas segundo notação UML padrão.

### 4.1 Tabela de Relacionamentos

| # | Origem | Destino | Tipo | Cardinalidade Origem | Cardinalidade Destino | Leitura |
|---|--------|---------|------|---------------------|----------------------|---------|
| 1 | ETAPA | SERIE | Agregação (◇) | 1 | 0..N | Uma etapa possui zero ou N séries |
| 2 | ETAPA | COMPETENCIA_ESPECIFICA | Agregação (◇) | 1 | 0..N | Uma etapa possui zero ou N competências |
| 3 | ETAPA | EIXO | Associação (→) | 1 | 0..N | Uma etapa possui zero ou N eixos |
| 4 | SERIE | EIXO | Associação (→) | 1 | 0..1 | Uma série possui zero ou um eixo |
| 5 | EIXO | HABILIDADE | Associação (→) | 1 | 0..N | Um eixo possui zero ou N habilidades |
| 6 | COMPETENCIA_ESPECIFICA | HABILIDADE | Associação (→) | 1 | 0..N | Uma competência possui zero ou N habilidades |
| 7 | OBJETO_CONHECIMENTO | OBJETO_CONHECIMENTO | Agregação (◇) | 1 | 0..N | Um objeto subdivide-se em zero ou N objetos |
@@ -92,72 +92,81 @@ O diagrama possui **12 relacionamentos** com cardinalidades definidas segundo no

## 5. TIPOS DE RELACIONAMENTO

### 5.1 Agregação (◇ Diamante Vazio)

**Utilização:**
- ETAPA ◇→ SERIE
- ETAPA ◇→ COMPETENCIA_ESPECIFICA
- OBJETO_CONHECIMENTO ◇→ OBJETO_CONHECIMENTO

### 5.2 Associação Simples (→)

**Utilização:**
- Demais relacionamentos, incluindo os colaborativos:
  - USUARIO → PRATICA
  - HABILIDADE → PRATICA

---

## 6. FLUXOS HIERÁRQUICOS DO DOMÍNIO

### 6.1 Fluxo Curricular (BNCC)

`ETAPA → SERIE → OBJETO_CONHECIMENTO → CONCEITO_HABILIDADE → HABILIDADE`


### 6.1.1 Fluxo incremental de implementação (UI atual)

Para coerência com a implementação incremental do AppComBncc:
- **Fase 1 (UI):** Home com `EI`, `EF`, `EM` + Tela de Série com dropdowns.
- **Fase 2 (fluxo):** Série/Etapa → Eixos/Competências → Habilidades com filtros combinados.

> Observação: essa divisão é de implementação e não altera o modelo conceitual do domínio.

### 6.2 Fluxo Colaborativo (Aplicativo)

`PROFESSOR/COORDENADOR/PROFESSOR_REVISOR/ADMIN → PRATICA → HABILIDADE`

---

## 7. PUBLICAÇÃO COLABORATIVA DE PRÁTICAS

O compartilhamento público é controlado pelo campo `status` da entidade PRATICA:

- **RASCUNHO**: somente autor visualiza
- **EM_ANALISE**: aguardando revisão
- **APROVADA**: visível para todos os usuários
- **REJEITADA**: retorna ao autor

A curadoria pode ser executada por perfis **COORDENADOR**, **PROFESSOR_REVISOR** e **ADMIN**, sem criação de novas entidades neste momento.

---

## 8. VALIDAÇÃO COM DADOS REAIS

Baseado na planilha BNCC_Computacao_ESTRUTURA_FINAL.xlsx e nas validações de integração do AppComBncc, o modelo segue válido para a base normativa e para o uso colaborativo no aplicativo.

---

## 9. CONSIDERAÇÕES DE IMPLEMENTAÇÃO

### 9.1 Chaves Primárias (PKs)

- ETAPA.codigo
- SERIE.codigo
- EIXO.codigo
- OBJETO_CONHECIMENTO.id
- CONCEITO_HABILIDADE.codigo
- HABILIDADE.codigo
- COMPETENCIA_ESPECIFICA.codigo
- USUARIO.id
- PRATICA.id

### 9.2 Chaves Estrangeiras (FKs) adicionais

- PRATICA.usuario_id (FK → USUARIO.id)
- PRATICA.habilidade_id (FK → HABILIDADE.codigo)

---

## 10. RESULTADO FINAL
@@ -165,27 +174,27 @@ Baseado na planilha BNCC_Computacao_ESTRUTURA_FINAL.xlsx, o modelo segue válido
O modelo atual separa claramente:

### Base normativa BNCC
ETAPA, SERIE, EIXO, OBJETO_CONHECIMENTO, CONCEITO_HABILIDADE, HABILIDADE, COMPETENCIA_ESPECIFICA

### Base colaborativa do aplicativo
USUARIO, PRATICA

Isso fortalece o projeto como sistema de apoio docente, não apenas consulta curricular.

---

## 11. CONSIDERAÇÕES FINAIS

O diagrama de classes UML resultante:

✅ Mantém a coerência estrutural do documento original  
✅ Incorpora entidades e regras colaborativas sem complexidade excessiva  
✅ Preserva foco acadêmico no diagrama principal  

---

**Arquivo:** bncc_classes_v2__2_.drawio  
**Ferramenta:** draw.io (diagrams.net)  
**Notação:** UML 2.5  
**Versão:** 2.5  
**Data:** Maio de 2026
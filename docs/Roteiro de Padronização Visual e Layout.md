# Roteiro de Padronização Visual e Layout
## Aplicativo AppBNCC Computação
### Fundamentação Teórica para TCC

> *“A interface do aplicativo foi desenvolvida com base nas diretrizes do Material Design, associadas aos princípios ergonômicos da ISO 9241 e aos critérios de acessibilidade da WCAG, visando garantir padronização visual, legibilidade, usabilidade e acessibilidade no contexto educacional.”*

Este roteiro define uma base de padronização para o desenvolvimento da interface do aplicativo AppBNCC Computação, considerando:

- Material Design
- International Organization for Standardization ISO 9241
- WCAG (acessibilidade)
- padrões modernos de UX/UI para Android
- identidade visual inspirada nas cores institucionais do Ministério da Educação

## 1. Objetivo do Layout

O layout deve:

- facilitar a navegação;
- permitir leitura confortável;
- reduzir esforço cognitivo;
- manter padronização visual;
- garantir acessibilidade;
- funcionar em diferentes tamanhos de tela;
- transmitir aparência institucional e educacional.

## 2. Diretrizes Gerais

### Base Visual

Utilizar:

- Material Design 3;
- layout responsivo;
- navegação simples;
- poucos elementos por tela;
- hierarquia visual clara.

## 3. Paleta de Cores

### Referência Visual MEC

As cores do MEC normalmente utilizam:

- azul institucional;
- verde institucional;
- branco;
- cinza neutro.

### Paleta Recomendada

| Função | Cor | Hex |
|--------|-----|-----|
| Primária | Azul MEC | #0D47A1 |
| Primária Clara | Azul médio | #1565C0 |
| Secundária | Verde MEC | #2E7D32 |
| Fundo | Branco | #FFFFFF |
| Superfície | Cinza claro | #F5F5F5 |
| Texto Principal | Preto suave | #212121 |
| Texto Secundário | Cinza escuro | #616161 |
| Erro | Vermelho | #D32F2F |
| Sucesso | Verde | #388E3C |
| Divisórias | Cinza | #E0E0E0 |

## 4. Regras de Contraste (WCAG)

### Contraste mínimo

| Tipo | Contraste |
|------|-----------|
| Texto normal | 4.5:1 |
| Texto grande | 3:1 |

### Regras importantes

**Nunca usar:**
- texto azul sobre fundo verde;
- cinza claro em fundo branco;
- fonte pequena em cores claras.

**Preferir:**
- fundo claro com texto escuro;
- destaque visual apenas no essencial;
- poucas cores simultaneamente.

## 5. Tipografia

### Fonte Recomendada

Android:
- Roboto
- ou Noto Sans

### Tamanhos Padronizados

| Elemento | Tamanho |
|----------|---------|
| Título principal | 24sp |
| Título de seção | 20sp |
| Subtítulo | 18sp |
| Texto comum | 16sp |
| Texto secundário | 14sp |
| Texto auxiliar | 12sp |
| Botões | 14sp–16sp |

### Peso das Fontes

| Uso | Peso |
|-----|------|
| Título | Bold |
| Subtítulo | Medium |
| Texto comum | Regular |

## 6. Espaçamentos

### Base Material Design

Utilizar múltiplos de:

- 4dp
- 8dp

### Padrões Recomendados

| Elemento | Medida |
|----------|--------|
| Padding lateral da tela | 16dp |
| Espaço entre cards | 12dp |
| Espaço entre seções | 24dp |
| Padding interno card | 16dp |
| Espaço entre ícone e texto | 8dp |

## 7. Componentes

### Botões

**Medidas**

| Item | Medida |
|------|--------|
| Altura mínima | 48dp |
| Raio borda | 12dp |
| Padding horizontal | 16dp |

### Cards

**Estrutura recomendada**
- bordas arredondadas;
- sombra leve;
- padding interno;
- título destacado;
- informações organizadas verticalmente.

**Medidas**

| Item | Valor |
|------|-------|
| Radius | 16dp |
| Elevação | 2dp–4dp |
| Padding interno | 16dp |

### Ícones

| Item | Medida |
|------|--------|
| Ícone padrão | 24dp |
| Ícone pequeno | 20dp |
| Ícone grande | 32dp |

## 8. Estrutura das Telas

### Tela Inicial

**Componentes**
- logo/título;
- descrição curta;
- opções principais;
- navegação simples.

### Barra Superior - AppBar

| Item | Valor |
|------|-------|
| Altura | 56dp |
| Ícone voltar | 24dp |
| Título | 20sp |

## 9. Acessibilidade

### ISO 9241 + WCAG

O aplicativo deve:
- possuir textos legíveis;
- possuir contraste adequado;
- evitar excesso de informação;
- ter elementos clicáveis grandes;
- permitir leitura confortável;
- evitar poluição visual.

### Área de Toque

| Item | Mínimo |
|------|--------|
| Área clicável | 48dp x 48dp |

### Recomendações

**Sempre:**
- usar descrição em ícones;
- usar feedback visual;
- indicar seleção ativa;
- manter padrão visual.

**Evitar:**
- textos longos centralizados;
- excesso de cores;
- menus escondidos;
- excesso de informações na mesma tela.

## 10. Responsividade

### Compatibilidade

O layout deve funcionar em:

- celulares pequenos;
- celulares grandes;
- tablets;
- modo paisagem.

### Estratégias

Utilizar:
- ConstraintLayout;
- ScrollView;
- RecyclerView;
- dimens em dp/sp.

## 11. Organização Visual Recomendada

### Hierarquia

**Ordem de destaque**
1. título;
2. conteúdo principal;
3. ações;
4. informações secundárias.

### Regra visual

O usuário deve identificar rapidamente:
- onde está;
- o que pode clicar;
- qual é a ação principal;
- como voltar.

## 12. Recomendações para o AppBNCC

### Ideal para seu caso

Usar:
- cards para habilidades;
- chips para eixos;
- cores suaves;
- azul como principal;
- verde apenas para destaque;
- listas organizadas;
- RecyclerView em praticamente todas as telas.

### Sugestão Visual

| Elemento | Cor |
|----------|-----|
| Toolbar | Azul MEC |
| Botão principal | Verde MEC |
| Fundo | Branco |
| Cards | Branco com sombra leve |
| Texto | Preto suave |

Para os botoes da home manter essas cores
Ensino infantil <color name="etapa_ei">#2E7D32</color>
Ensino Fundamental <color name="etapa_ef">#1976D2</color>
Ensino medio <color name="etapa_em">#FBC02D</color>
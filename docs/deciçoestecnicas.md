# Decisões Técnicas

## 2026-04-27 — Coerência com projetos de referência

- Decisão 1: adotar `model` com subpacote `domain` no AppBNCC (ex.: `model/domain`) para alinhar com referências que usam `model/domain` e também com projetos recentes que valorizam classes de domínio explícitas.
- Decisão 2: corrigir o fluxo simplificado para `Etapa > Eixo > Habilidade`, mantendo coerência entre texto e relacionamento UML.
- Decisão 3: padronizar os tipos de usuário para `professor`, `coordenador`, `professor_revisor`, `admin` em toda a documentação.

## 2026-04-27 — Documento de inicialização da implementação

- Decisão 4: criar o documento `docs/plano-inicial-implementacao.md` como guia de execução por fases (MVP primeiro), alinhado ao escopo e à arquitetura definida para o AppBNCC.

## 2026-04-27 — Setup da Fase 0 (Navigation + Room)

- Decisão 5: registrar um guia operacional em `docs/fase-0-configuracao-navigation-room.md` com dependências, arquivos base e checklist para incluir diretamente no projeto Android Studio já criado.

## 2026-04-27 — Ajuste da Fase 0 para projeto real

- Decisão 6: alinhar o guia da Fase 0 ao projeto criado no Android Studio com nome `AppComBncc` e namespace `com.example.appcombncc`, para aplicação direta dos arquivos de Navigation e Room.

## 2026-04-28 — Reuso de banco já existente

- Decisão 7: na Fase 0 do AppComBncc, não criar nova classe de database/seed quando o banco já existir e estiver populado; focar em integração (DAO/Repository/ViewModel) com o database atual.

## 2026-04-28 — Testes rápidos de validação do banco pré-populado

- Decisão 8: complementar a Fase 0 com testes rápidos (abertura do banco, contagem de etapas e leitura em UI) para confirmar integração do `bncc_computacao.db` sem seed.

## 2026-04-28 — Localização da classe database e do arquivo .db

- Decisão 9: manter a classe `AppComBnccDatabase` no pacote `data/database` e o arquivo físico `bncc_computacao.db` em `app/src/main/assets`, usando `createFromAsset("bncc_computacao.db")`.

## 2026-04-28 — Mapa didático da Fase 0

- Decisão 10: criar `docs/mapa-didatico-fase-0.md` para apoiar apresentação ao orientador, explicando função de cada arquivo da Fase 0 e o vínculo com os projetos de referência.

## 2026-04-29 — Fluxo de instanciação no mapa didático

- Decisão 11: complementar `docs/mapa-didatico-fase-0.md` com fluxo de instanciação detalhado (MainActivity → NavHost → HomeFragment → ViewModelFactory → Repository → Room/Flow → UI) para facilitar apresentação didática ao orientador.

## 2026-04-29 — Fluxo de instanciação expandido (nível apresentação)

- Decisão 12: expandir o fluxo de instanciação da Fase 0 em formato narrativo detalhado (boot, lifecycle, lazy init, abertura do Room pré-populado, coleta de Flow e atualização de UI) para apoiar defesa técnica com o orientador.

## 2026-05-01 — Ajustes pós-conclusão da Etapa 0

- Decisão 13: validar contagem esperada de etapas como **6** no teste rápido da Fase 0.
- Decisão 14: padronizar `createFromAsset("bncc_computacao.db")` com arquivo em `app/src/main/assets`.
- Decisão 15: na Fase 1, substituir orientação de seed local por integração consultiva com banco pré-populado; seed passa a ser opcional para cenários futuros.
- Decisão 16: registrar no guia da Fase 0 o schema SQL validado do banco atual para rastreabilidade.

## 2026-05-01 — Documento operacional da Fase 1

- Decisão 17: criar `docs/fase-1-implementacao-base-dados.md` com plano executável da Etapa/Fase 1, definindo escopo consultivo, DAOs/repositories/ViewModels mínimos, checklist, riscos e definição de pronto.

## 2026-05-01 — Revisão de escopo da Fase 1 (Home + Série)

- Decisão 18: ajustar Home para etapas `EI`, `EF` e `EM` (em vez de EI/EF1/EF2/EM na UI inicial).
- Decisão 19: definir Tela de Série com dois dropdowns: séries (1º-9º) e grupos (1º-5º / 6º-9º).
- Decisão 20: reduzir escopo da Fase 1 para implementação de Home + Série com paleta visual definida; demais itens de dados avançados ficam para etapa seguinte.

## 2026-05-01 — Fase 1 no mesmo formato operacional da Fase 0

- Decisão 21: reescrever `docs/fase-1-implementacao-base-dados.md` no formato prático por arquivo (layout, fragment, navegação, cores e checklist), no estilo copy/paste utilizado na Fase 0.

## 2026-05-01 — Priorização da próxima etapa

- Decisão 22: priorizar imediatamente a preparação da tela de habilidades com filtros combinados.
- Decisão 23: postergar, por ora, a ligação Série/Etapa → Eixos/Competências e a substituição total de listas estáticas por leitura guiada no banco.

## 2026-05-01 — Correção da priorização pós-Fase 1 e criação da Fase 2

- Decisão 24: após a Fase 1, além da tela de habilidades com filtros combinados, implementar também a ligação Série/Etapa -> Eixos/Competências.
- Decisão 25: após a Fase 1, substituir listas estáticas por leitura guiada no banco conforme regra de etapa.
- Decisão 26: criar `docs/fase-2-implementacao-fluxo-habilidades.md` no formato operacional (igual Fase 0/1) com passos por arquivo e checklist de validação.

## 2026-05-01 — Ajuste de formato do guia da Fase 2

- Decisão 27: reescrever `docs/fase-2-implementacao-fluxo-habilidades.md` no mesmo padrão operacional das Fases 0 e 1, com instruções por arquivo e snippets copy/paste.

## 2026-05-01 — Pacote completo da Fase 2

- Decisão 28: complementar o guia da Fase 2 com pacote completo de arquivos-base (entities, DAOs, database, application e factories de ViewModel) para execução direta no projeto Android real.


# Fase 1 — Implementação prática (mesmo formato da Fase 0)

## Contexto
Este documento segue o **mesmo estilo operacional da Fase 0**: orientações por arquivo, com foco em copiar/colar e executar.

Escopo atual da Fase 1 (revisado):
1. Tela **Home** com botões de etapas (`EI`, `EF`, `EM`).
2. Tela **Série** com dois dropdowns:
   - séries de 1º ao 9º;
   - grupos 1º-5º e 6º-9º.

> O restante (fluxos avançados de dados e demais telas) fica para próxima etapa.

---

## 1) Cores da etapa (arquivo de recursos)

Arquivo: `app/src/main/res/values/colors.xml`

Adicionar/garantir:

```xml
<resources>
    <color name="etapa_ei">#2E7D32</color>
    <color name="etapa_ef">#1976D2</color>
    <color name="etapa_em">#FBC02D</color>

    <color name="bg_white">#FFFFFF</color>
    <color name="text_secondary_gray">#9E9E9E</color>
    <color name="card_gray_light">#F5F5F5</color>
</resources>
```

---

## 2) Atualizar navegação (nav_graph)

Arquivo: `app/src/main/res/navigation/nav_graph.xml`

Adicionar `SerieFragment` e ação da Home para Série:

```xml
<fragment
    android:id="@+id/homeFragment"
    android:name="com.example.appcombncc.ui.fragment.HomeFragment"
    android:label="Home">

    <action
        android:id="@+id/action_homeFragment_to_serieFragment"
        app:destination="@id/serieFragment" />
</fragment>

<fragment
    android:id="@+id/serieFragment"
    android:name="com.example.appcombncc.ui.fragment.SerieFragment"
    android:label="Séries" />
```

---

## 3) Layout da Home

Arquivo: `app/src/main/res/layout/fragment_home.xml`

Estrutura mínima recomendada (botões por etapa):

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:background="@color/bg_white"
    android:padding="16dp">

    <Button
        android:id="@+id/etapaEiBt"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Educação Infantil"
        android:backgroundTint="@color/etapa_ei"
        android:textColor="@android:color/white" />

    <Button
        android:id="@+id/etapaEfBt"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="12dp"
        android:text="Ensino Fundamental"
        android:backgroundTint="@color/etapa_ef"
        android:textColor="@android:color/white" />

    <Button
        android:id="@+id/etapaEmBt"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="12dp"
        android:text="Ensino Médio"
        android:backgroundTint="@color/etapa_em"
        android:textColor="@android:color/black" />
</LinearLayout>
```

---

## 4) Fragment da Home

Arquivo: `app/src/main/java/com/example/appcombncc/ui/fragment/HomeFragment.kt`

Comportamento mínimo:
- botões levam para `SerieFragment`;
- opcional: enviar etapa selecionada via `Bundle`.

```kotlin
class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val eiBt = view.findViewById<Button>(R.id.etapaEiBt)
        val efBt = view.findViewById<Button>(R.id.etapaEfBt)
        val emBt = view.findViewById<Button>(R.id.etapaEmBt)

        eiBt.setOnClickListener { navigateToSerie("EI") }
        efBt.setOnClickListener { navigateToSerie("EF") }
        emBt.setOnClickListener { navigateToSerie("EM") }
    }

    private fun navigateToSerie(etapa: String) {
        val bundle = Bundle().apply { putString("etapaSelecionada", etapa) }
        findNavController().navigate(R.id.action_homeFragment_to_serieFragment, bundle)
    }
}
```

---

## 5) Layout da Tela de Série

Arquivo: `app/src/main/res/layout/fragment_serie.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:background="@color/bg_white"
    android:padding="16dp">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Selecione a série"
        android:textColor="@color/text_secondary_gray" />

    <Spinner
        android:id="@+id/seriesSp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="8dp" />

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="16dp"
        android:text="Selecione o agrupamento"
        android:textColor="@color/text_secondary_gray" />

    <Spinner
        android:id="@+id/grupoSp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="8dp" />
</LinearLayout>
```

---

## 6) Fragment da Tela de Série

Arquivo: `app/src/main/java/com/example/appcombncc/ui/fragment/SerieFragment.kt`

```kotlin
class SerieFragment : Fragment(R.layout.fragment_serie) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val seriesSp = view.findViewById<Spinner>(R.id.seriesSp)
        val grupoSp = view.findViewById<Spinner>(R.id.grupoSp)

        val series = listOf("1º ano", "2º ano", "3º ano", "4º ano", "5º ano", "6º ano", "7º ano", "8º ano", "9º ano")
        val grupos = listOf("1º ao 5º", "6º ao 9º")

        seriesSp.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, series)
        grupoSp.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, grupos)
    }
}
```

---

## 7) Opcional: ViewModel da Série (se quiser preparar evolução)

Arquivo: `app/src/main/java/com/example/appcombncc/viewmodel/SerieViewModel.kt`

Uso opcional nesta etapa. Pode ficar simples e estático (listas locais) e, depois, evoluir para consulta no banco.

---

## 8) Checklist de validação da Fase 1

- [ ] Home abre com 3 botões: EI, EF e EM.
- [ ] Cores dos botões seguem o padrão da etapa.
- [ ] Clique em qualquer botão navega para Tela de Série.
- [ ] Tela de Série exibe spinner de 1º ao 9º.
- [ ] Tela de Série exibe spinner de grupos (1º-5º / 6º-9º).
- [ ] Não há crash de navegação.

---

## 9) Definição de pronto da Fase 1

A Fase 1 estará pronta quando:
- Home + Série estiverem implementadas e navegáveis;
- paleta visual definida estiver aplicada;
- comportamento mínimo dos dropdowns estiver funcionando.

---

## 10) Próximo passo (após Fase 1)

### Próximo passo da implementação (após Fase 1)
- **Implementar a ligação Série/Etapa ao fluxo de Eixos/Competências**.
- **Substituir listas estáticas por leitura guiada no banco conforme regra de etapa**.

### Escopo recomendado para a tela de habilidades
- filtro por etapa (`EI`, `EF`, `EM`);
- filtro por série (1º ao 9º e grupos quando aplicável);
- filtro por eixo **ou** competência (dependendo da etapa);



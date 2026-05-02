package com.example.appcombncc.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.appcombncc.R

class HabilidadesFragment : Fragment(R.layout.fragment_habilidades) {
    // Receber argumentos (etapa, série, eixo/competência)
    // Executar consulta adequada via ViewModel
    // Aplicar busca textual (código/descrição)
    // Renderizar lista no RecyclerView
//}
override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val codigoTv = view.findViewById<TextView>(R.id.codigoHabilidadeTv)
    val serieEtapaTv = view.findViewById<TextView>(R.id.serieEtapaTv)
    val descricaoTv = view.findViewById<TextView>(R.id.descricaoTv)
    val explicacaoTv = view.findViewById<TextView>(R.id.explicacaoTv)
    val exemploTv = view.findViewById<TextView>(R.id.exemploTv)
    val expandirExplicacaoBt = view.findViewById<Button>(R.id.expandirExplicacaoBt)
    val expandirExemploBt = view.findViewById<Button>(R.id.expandirExemploBt)
    val favoritarBt = view.findViewById<ImageButton>(R.id.favoritarBt)

    codigoTv.text = "Código: "
    serieEtapaTv.text = "Série/Etapa: "
    descricaoTv.text = "Descrição: "
    explicacaoTv.text = "Explicação: "
    exemploTv.text = "Exemplo: "

    expandirExplicacaoBt.setOnClickListener {
        explicacaoTv.visibility = if (explicacaoTv.visibility == View.VISIBLE) View.GONE else View.VISIBLE
    }

    expandirExemploBt.setOnClickListener {
        exemploTv.visibility = if (exemploTv.visibility == View.VISIBLE) View.GONE else View.VISIBLE
    }

    favoritarBt.setOnClickListener {
        val favorito = favoritarBt.tag == true
        favoritarBt.setImageResource(
            if (favorito) android.R.drawable.btn_star_big_off else android.R.drawable.btn_star_big_on
        )
        favoritarBt.tag = !favorito
    }
}
}
package com.example.appcombncc.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.appcombncc.R

class EixoCompetenciaFragment : Fragment(R.layout.fragment_eixo_competencia) {
    // Ler etapa/série dos argumentos
    // Se etapa == "EM" -> carregar competências
    // Senão -> carregar eixos
    // Ao clicar item, navegar para HabilidadesFragment com código selecionado

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val continuarBt = view.findViewById<Button>(R.id.continuarEixoBt)
        continuarBt.setOnClickListener {
            val bundle = Bundle().apply {
                putString("eixoCompetenciaSelecionado", "")
                putString("quantidadeHabilidades", "")
            }
            findNavController().navigate(R.id.habilidadesFragment, bundle)
        }
    }
}
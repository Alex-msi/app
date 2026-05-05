package com.example.appcombncc.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.appcombncc.R

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
        findNavController().navigate(R.id.serieFragment, bundle)
    }

// Primeiro teste apagar depois
//        val tv = view.findViewById<TextView>(R.id.homeTextTv)
//
//        // contando etapas teste
//        viewModel.debugCountEtapas()  // ← ISSO AQUI!
//        //
//
//        viewLifecycleOwner.lifecycleScope.launch {
//            viewModel.etapas.collect { lista ->
//                tv.text = if (lista.isEmpty()) {
//                    "Sem etapas"
//                } else {
//                    lista.joinToString("\n") { "${it.codigo} - ${it.nome}" }
//                }
//            }
//        }
}
package com.example.appcombncc.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.appcombncc.AppComBnccApplication
import com.example.appcombncc.R
import com.example.appcombncc.viewmodel.HomeViewModel
import com.example.appcombncc.viewmodel.HomeViewModelFactory
import kotlinx.coroutines.launch

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(
            (requireActivity().application as AppComBnccApplication).etapaRepository
        )
    }

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
}
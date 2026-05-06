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

        eiBt.setOnClickListener { navigateToSerie("EI", "#4CAF50") }
        efBt.setOnClickListener { navigateToSerie("EF", "#1976D2") }
        emBt.setOnClickListener { navigateToSerie("EM", "#FFCA28") }
    }
    private fun navigateToSerie(etapa: String, etapaCor: String) {
        val bundle = Bundle().apply {
            putString("etapaSelecionada", etapa)
            putString("etapaCor", etapaCor)
        }
        findNavController().navigate(R.id.serieFragment, bundle)
    }
}
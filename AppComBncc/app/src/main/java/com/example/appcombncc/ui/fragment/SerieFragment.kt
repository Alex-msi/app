package com.example.appcombncc.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.appcombncc.AppComBnccApplication
import com.example.appcombncc.R
import com.example.appcombncc.viewmodel.SerieViewModel
import com.example.appcombncc.viewmodel.SerieViewModelFactory
import kotlinx.coroutines.launch

class SerieFragment : Fragment(R.layout.fragment_serie) {
    private val viewModel: SerieViewModel by viewModels {
        SerieViewModelFactory(
            (requireActivity().application as AppComBnccApplication).serieRepository
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val seriesSp = view.findViewById<Spinner>(R.id.seriesSp)
        val etapa1a5Bt = view.findViewById<Button>(R.id.etapa1a5Bt)
        val etapa6a9Bt = view.findViewById<Button>(R.id.etapa6a9Bt)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.series.collect { series ->
                seriesSp.adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    series
                )
            }
        }

        etapa1a5Bt.setOnClickListener {
            val bundle = Bundle().apply {
                putString("serieSelecionada", "")
                putString("etapaSelecionada", "")
            }
            findNavController().navigate(R.id.eixoCompetenciaFragment, bundle)
        }

        etapa6a9Bt.setOnClickListener {
            val bundle = Bundle().apply {
                putString("serieSelecionada", "")
                putString("etapaSelecionada", "")
            }
            findNavController().navigate(R.id.eixoCompetenciaFragment, bundle)
        }
    }
}
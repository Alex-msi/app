package com.example.appcombncc.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appcombncc.R
import com.example.appcombncc.AppComBnccApplication
import com.example.appcombncc.data.model.EixoHabilidadeCount
import com.example.appcombncc.ui.adapter.EixoResumoAdapter
import com.example.appcombncc.viewmodel.EixoCompetenciaViewModel
import com.example.appcombncc.viewmodel.EixoCompetenciaViewModelFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class EixoCompetenciaFragment : Fragment(R.layout.fragment_eixo_competencia) {
    private val viewModel: EixoCompetenciaViewModel by viewModels {
        EixoCompetenciaViewModelFactory(
            (requireActivity().application as AppComBnccApplication).eixoCompetenciaRepository
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val serieSelecionada = arguments?.getString("serieSelecionada").orEmpty()
        val recyclerView = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.eixoCompetenciaRv)
        val adapter = EixoResumoAdapter()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        val resumoFlow: Flow<List<EixoHabilidadeCount>> =
            viewModel.getResumoEixosPorSerie(serieSelecionada)

        viewLifecycleOwner.lifecycleScope.launch {
            resumoFlow.collect { lista ->
                adapter.submitList(lista)
            }
        }
    }
}
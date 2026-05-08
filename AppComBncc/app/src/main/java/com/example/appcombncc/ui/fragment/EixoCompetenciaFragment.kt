package com.example.appcombncc.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appcombncc.AppComBnccApplication
import com.example.appcombncc.R
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
        val etapaSelecionada = arguments?.getString("etapaSelecionada").orEmpty()
        val habilidadeLike = arguments?.getString("habilidadeLike").orEmpty()
        val etapaCor = arguments?.getString("etapaCor").orEmpty()
        val recyclerView = view.findViewById<RecyclerView>(R.id.eixoCompetenciaRv)
        val adapter = EixoResumoAdapter { itemSelecionado ->
            val bundle = Bundle().apply {
                putString("serieSelecionada", serieSelecionada)
                putString("etapaSelecionada", etapaSelecionada)
                putString("habilidadeLike", habilidadeLike)
                putString("eixoSelecionado", itemSelecionado.eixoCodigo)
                putString("etapaCor", etapaCor)
                putLong("objetoSelecionadoId", -1L)
            }

            if (etapaSelecionada == "EI") {
                findNavController().navigate(R.id.listaHabilidadeFragment, bundle)
            } else {
                findNavController().navigate(
                    R.id.action_eixoCompetenciaFragment_to_objetoConceitoFragment,
                    bundle
                )
            }
        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        val resumoFlow: Flow<List<EixoHabilidadeCount>> =
            if (serieSelecionada.isNotEmpty()) {
                viewModel.getResumoEixosPorSerie(serieSelecionada)
            } else {
                viewModel.getResumoEixosPorEtapa(etapaSelecionada, habilidadeLike)
            }

        viewLifecycleOwner.lifecycleScope.launch {
            resumoFlow.collect { lista ->
                adapter.submitList(lista)
            }
        }

    }
}
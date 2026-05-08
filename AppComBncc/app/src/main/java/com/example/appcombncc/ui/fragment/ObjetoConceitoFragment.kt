package com.example.appcombncc.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.graphics.toColorInt
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appcombncc.AppComBnccApplication
import com.example.appcombncc.R
import com.example.appcombncc.data.model.ObjetoHabilidadeCount
import com.example.appcombncc.ui.adapter.ObjetoResumoAdapter
import com.example.appcombncc.viewmodel.EixoCompetenciaViewModel
import com.example.appcombncc.viewmodel.EixoCompetenciaViewModelFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ObjetoConceitoFragment : Fragment(R.layout.fragment_objeto_conceito) {
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
        val eixoSelecionado = arguments?.getString("eixoSelecionado").orEmpty()
        val etapaCor = arguments?.getString("etapaCor").orEmpty()

        val recyclerView = view.findViewById<RecyclerView>(R.id.objetoConceitoRv)
        val continuarBt = view.findViewById<Button>(R.id.continuarObjetoBt)
        if (etapaCor.isNotEmpty()) {
            continuarBt.setBackgroundColor(etapaCor.toColorInt())
        }

        val adapter = ObjetoResumoAdapter { objetoSelecionado ->
            val bundle = Bundle().apply {
                putString("serieSelecionada", serieSelecionada)
                putString("etapaSelecionada", etapaSelecionada)
                putString("habilidadeLike", habilidadeLike)
                putString("eixoSelecionado", eixoSelecionado)
                putLong("objetoSelecionadoId", objetoSelecionado.objetoId)
                putString("etapaCor", etapaCor)
            }
            findNavController().navigate(R.id.action_objetoConceitoFragment_to_listaHabilidadeFragment, bundle)
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        val resumoFlow: Flow<List<ObjetoHabilidadeCount>> =
            if (serieSelecionada.isNotEmpty()) {
                viewModel.getResumoObjetosPorSerieEixo(serieSelecionada, eixoSelecionado)
            } else {
                viewModel.getResumoObjetosPorEtapaEixo(etapaSelecionada, habilidadeLike, eixoSelecionado)
            }

        viewLifecycleOwner.lifecycleScope.launch {
            resumoFlow.collect { lista ->
                adapter.submitList(lista)
            }
        }

        continuarBt.setOnClickListener {
            val bundle = Bundle().apply {
                putString("serieSelecionada", serieSelecionada)
                putString("etapaSelecionada", etapaSelecionada)
                putString("habilidadeLike", habilidadeLike)
                putString("eixoSelecionado", eixoSelecionado)
                putLong("objetoSelecionadoId", -1L)
                putString("etapaCor", etapaCor)
            }
            findNavController().navigate(R.id.action_objetoConceitoFragment_to_listaHabilidadeFragment, bundle)
        }
    }
}

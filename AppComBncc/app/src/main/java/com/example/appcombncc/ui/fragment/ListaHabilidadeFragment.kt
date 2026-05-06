package com.example.appcombncc.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.fragment.findNavController
import com.example.appcombncc.AppComBnccApplication
import com.example.appcombncc.R
import com.example.appcombncc.data.model.HabilidadeListaItem
import com.example.appcombncc.ui.adapter.HabilidadeListaAdapter
import com.example.appcombncc.viewmodel.EixoCompetenciaViewModel
import com.example.appcombncc.viewmodel.EixoCompetenciaViewModelFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ListaHabilidadeFragment : Fragment(R.layout.fragment_lista_habilidade) {
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
        val objetoSelecionadoId = arguments?.getLong("objetoSelecionadoId", -1L) ?: -1L

        val recyclerView = view.findViewById<RecyclerView>(R.id.listaHabilidadeRv)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = HabilidadeListaAdapter { habilidade ->
            val bundle = Bundle().apply {
                putString("habilidadeCodigo", habilidade.codigo)
                putString("habilidadeDescricao", habilidade.descricao)
                putString("serieSelecionada", serieSelecionada)
                putString("etapaSelecionada", etapaSelecionada)
            }
            findNavController().navigate(R.id.action_listaHabilidadeFragment_to_habilidadesFragment, bundle)
        }
        recyclerView.adapter = adapter

        val habilidadesFlow: Flow<List<HabilidadeListaItem>> =
            if (objetoSelecionadoId > 0L) {
                if (serieSelecionada.isNotEmpty()) {
                    viewModel.getHabilidadesPorSerieEixoObjeto(serieSelecionada, eixoSelecionado, objetoSelecionadoId)
                } else {
                    viewModel.getHabilidadesPorEtapaEixoObjeto(
                        etapaSelecionada,
                        habilidadeLike,
                        eixoSelecionado,
                        objetoSelecionadoId
                    )
                }
            } else {
                if (serieSelecionada.isNotEmpty()) {
                    viewModel.getHabilidadesPorSerieEixo(serieSelecionada, eixoSelecionado)
                } else {
                    viewModel.getHabilidadesPorEtapaEixo(etapaSelecionada, habilidadeLike, eixoSelecionado)
                }
            }

        viewLifecycleOwner.lifecycleScope.launch {
            habilidadesFlow.collect { lista ->
                adapter.submitList(lista)
            }
        }
    }
}

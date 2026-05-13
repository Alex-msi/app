package com.example.appcombncc.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appcombncc.AppComBnccApplication
import com.example.appcombncc.R
import com.example.appcombncc.data.model.EixoHabilidadeCount
import com.example.appcombncc.databinding.FragmentEixoCompetenciaBinding
import com.example.appcombncc.ui.adapter.CompetenciaResumoAdapter
import com.example.appcombncc.ui.adapter.EixoResumoAdapter
import com.example.appcombncc.viewmodel.EixoCompetenciaViewModel
import com.example.appcombncc.viewmodel.appViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class EixoCompetenciaFragment : Fragment(R.layout.fragment_eixo_competencia) {
    private var _binding: FragmentEixoCompetenciaBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EixoCompetenciaViewModel by appViewModel {
        EixoCompetenciaViewModel(
            (requireActivity().application as AppComBnccApplication).eixoCompetenciaRepository
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEixoCompetenciaBinding.bind(view)

        val serieSelecionada = arguments?.getString("serieSelecionada").orEmpty()
        val etapaSelecionada = arguments?.getString("etapaSelecionada").orEmpty()
        val habilidadeLike = arguments?.getString("habilidadeLike").orEmpty()
        val etapaCor = arguments?.getString("etapaCor").orEmpty()

        if (etapaSelecionada == "EM") {
            val competenciaAdapter = CompetenciaResumoAdapter { competenciaSelecionada ->
                val bundle = Bundle().apply {
                    putString("etapaSelecionada", "EM")
                    putString("competenciaSelecionada", competenciaSelecionada.competenciaCodigo)
                    putString("competenciaDescricao", competenciaSelecionada.competenciaDescricao)
                    putString("etapaCor", etapaCor)
                }

                findNavController().navigate(R.id.listaHabilidadeFragment, bundle)
            }

            binding.eixoCompetenciaRv.layoutManager = LinearLayoutManager(requireContext())
            binding.eixoCompetenciaRv.adapter = competenciaAdapter

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.getResumoCompetenciasPorEtapa("EM").collect { lista ->
                    competenciaAdapter.submitList(lista)
                }
            }

            return
        }

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

        binding.eixoCompetenciaRv.layoutManager = LinearLayoutManager(requireContext())
        binding.eixoCompetenciaRv.adapter = adapter

        val resumoFlow: Flow<List<EixoHabilidadeCount>> =
            viewModel.getResumoEixos(habilidadeLike)

        viewLifecycleOwner.lifecycleScope.launch {
            resumoFlow.collect { lista ->
                adapter.submitList(lista)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
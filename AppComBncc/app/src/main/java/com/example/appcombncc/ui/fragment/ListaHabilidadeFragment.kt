package com.example.appcombncc.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.navigation.fragment.findNavController
import com.example.appcombncc.AppComBnccApplication
import com.example.appcombncc.R
import com.example.appcombncc.databinding.FragmentListaHabilidadeBinding
import com.example.appcombncc.data.model.CompetenciaHabilidadeItem
import com.example.appcombncc.data.model.HabilidadeListaItem
import com.example.appcombncc.ui.adapter.HabilidadeListaAdapter
import com.example.appcombncc.viewmodel.HabilidadesViewModel
import com.example.appcombncc.viewmodel.EixoCompetenciaViewModel
import com.example.appcombncc.viewmodel.EixoCompetenciaViewModelFactory
import com.example.appcombncc.viewmodel.HabilidadesViewModelFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ListaHabilidadeFragment : Fragment(R.layout.fragment_lista_habilidade) {
    private var _binding: FragmentListaHabilidadeBinding? = null
    private val binding get() = _binding!!
    private val habilidadesViewModel: HabilidadesViewModel by viewModels {
        HabilidadesViewModelFactory(
            (requireActivity().application as AppComBnccApplication).habilidadeRepository
        )
    }
    private val viewModel: EixoCompetenciaViewModel by viewModels {
        EixoCompetenciaViewModelFactory(
            (requireActivity().application as AppComBnccApplication).eixoCompetenciaRepository
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentListaHabilidadeBinding.bind(view)

        val serieSelecionada = arguments?.getString("serieSelecionada").orEmpty()
        val etapaSelecionada = arguments?.getString("etapaSelecionada").orEmpty()
        val habilidadeLike = arguments?.getString("habilidadeLike").orEmpty()
        val eixoSelecionado = arguments?.getString("eixoSelecionado").orEmpty()
        val objetoSelecionadoId = arguments?.getLong("objetoSelecionadoId", -1L) ?: -1L
        val competenciaSelecionada = arguments?.getString("competenciaSelecionada").orEmpty()
        val competenciaDescricao = arguments?.getString("competenciaDescricao").orEmpty()
        val etapaCor = arguments?.getString("etapaCor").orEmpty()
        val buscaTexto = arguments?.getString("buscaTexto").orEmpty()
        binding.listaHabilidadeRv.layoutManager = LinearLayoutManager(requireContext())
        val adapter = HabilidadeListaAdapter { habilidade ->
            val bundle = Bundle().apply {
                putString("habilidadeCodigo", habilidade.codigo)
                putString("habilidadeDescricao", habilidade.descricao)
                putString("serieSelecionada", serieSelecionada)
                putString("etapaSelecionada", etapaSelecionada)
                putString("etapaCor", etapaCor)
            }
            findNavController().navigate(R.id.action_listaHabilidadeFragment_to_habilidadesFragment, bundle)
        }
        binding.listaHabilidadeRv.adapter = adapter

        if (buscaTexto.isNotEmpty()) {
            binding.listaHabilidadesTituloTv.text = "Resultados da busca"
            binding.competenciaDescricaoTv.visibility = View.VISIBLE
            binding.competenciaDescricaoTv.text = "Busca por: $buscaTexto"

            viewLifecycleOwner.lifecycleScope.launch {
                habilidadesViewModel.search(buscaTexto).collect { listaBusca ->
                    val itensBusca = listaBusca.map {
                        HabilidadeListaItem(codigo = it.codigo, descricao = it.descricao)
                    }
                    adapter.submitList(itensBusca)
                }
            }
            return
        }

        if (etapaSelecionada == "EM") {
            viewLifecycleOwner.lifecycleScope.launch {
                val competenciasFlow: Flow<List<CompetenciaHabilidadeItem>> =
                    viewModel.getHabilidadesPorCompetenciaEtapa("EM")

                competenciasFlow.collect { lista ->
                    val habilidadesFiltradas = lista
                        .filter { it.competenciaCodigo == competenciaSelecionada }
                        .map { HabilidadeListaItem(codigo = it.habilidadeCodigo, descricao = it.habilidadeDescricao) }

                    binding.listaHabilidadesTituloTv.text = "Lista de Habilidades da Competência:"
                    binding.listaHabilidadesTituloTv.visibility = View.VISIBLE
                    binding.competenciaDescricaoTv.visibility = View.VISIBLE
                    binding.competenciaDescricaoTv.text = competenciaDescricao
                    adapter.submitList(habilidadesFiltradas)
                }
            }
        } else {
            binding.listaHabilidadesTituloTv.text = "Lista de Habilidades"
            binding.competenciaDescricaoTv.visibility = View.GONE
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

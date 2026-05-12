package com.example.appcombncc.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.graphics.toColorInt
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.appcombncc.AppComBnccApplication
import com.example.appcombncc.R
import com.example.appcombncc.databinding.FragmentSerieBinding
import com.example.appcombncc.viewmodel.SerieViewModel
import com.example.appcombncc.viewmodel.SerieViewModelFactory
import kotlinx.coroutines.launch

class SerieFragment : Fragment(R.layout.fragment_serie) {
    private var _binding: FragmentSerieBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SerieViewModel by viewModels {
        SerieViewModelFactory(
            (requireActivity().application as AppComBnccApplication).serieRepository
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSerieBinding.bind(view)

        val etapaCor = arguments?.getString("etapaCor").orEmpty()

        if (etapaCor.isNotEmpty()) {
            binding.etapa1a5Bt.setBackgroundColor(etapaCor.toColorInt())
            binding.etapa6a9Bt.setBackgroundColor(etapaCor.toColorInt())
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.series.collect { series ->
                val itensSpinner = listOf("Selecione uma série") + series

                binding.seriesSp.adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    itensSpinner
                )
            }
        }

        binding.seriesSp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                selectedView: View?,
                position: Int,
                id: Long
            ) {
                if (position == 0) return

                val serieSelecionada = parent
                    ?.getItemAtPosition(position)
                    ?.toString()
                    .orEmpty()

                val habilidadeLike = gerarHabilidadeLikePorSerie(serieSelecionada)

                val bundle = Bundle().apply {
                    putString("serieSelecionada", serieSelecionada)
                    putString("habilidadeLike", habilidadeLike)
                    putString("etapaCor", etapaCor)
                }

                findNavController().navigate(R.id.eixoCompetenciaFragment, bundle)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }

        binding.etapa1a5Bt.setOnClickListener {
            val bundle = Bundle().apply {
                putString("serieSelecionada", "")
                putString("etapaSelecionada", "EFI_ETAPA")
                putString("habilidadeLike", "EF15%")
                putString("etapaCor", etapaCor)
            }

            findNavController().navigate(R.id.eixoCompetenciaFragment, bundle)
        }

        binding.etapa6a9Bt.setOnClickListener {
            val bundle = Bundle().apply {
                putString("serieSelecionada", "")
                putString("etapaSelecionada", "EFII_ETAPA")
                putString("habilidadeLike", "EF69%")
                putString("etapaCor", etapaCor)
            }

            findNavController().navigate(R.id.eixoCompetenciaFragment, bundle)
        }
    }

    private fun gerarHabilidadeLikePorSerie(serieCodigo: String): String {
        return when (serieCodigo) {
            "1_ANO" -> "EF01%"
            "2_ANO" -> "EF02%"
            "3_ANO" -> "EF03%"
            "4_ANO" -> "EF04%"
            "5_ANO" -> "EF05%"
            "6_ANO" -> "EF06%"
            "7_ANO" -> "EF07%"
            "8_ANO" -> "EF08%"
            "9_ANO" -> "EF09%"
            else -> ""
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
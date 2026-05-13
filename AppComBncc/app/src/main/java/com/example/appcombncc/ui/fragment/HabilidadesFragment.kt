package com.example.appcombncc.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.graphics.toColorInt
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.appcombncc.AppComBnccApplication
import com.example.appcombncc.R
import com.example.appcombncc.databinding.FragmentHabilidadesBinding
import com.example.appcombncc.viewmodel.HabilidadesViewModel
import com.example.appcombncc.viewmodel.appViewModel
import kotlinx.coroutines.launch

class HabilidadesFragment : Fragment(R.layout.fragment_habilidades) {
    private var _binding: FragmentHabilidadesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HabilidadesViewModel by appViewModel {
        HabilidadesViewModel(
            (requireActivity().application as AppComBnccApplication).habilidadeRepository
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHabilidadesBinding.bind(view)

        val codigo = arguments?.getString("habilidadeCodigo").orEmpty()
        val descricao = arguments?.getString("habilidadeDescricao").orEmpty()
        val serieSelecionada = arguments?.getString("serieSelecionada").orEmpty()
        val etapaSelecionada = arguments?.getString("etapaSelecionada").orEmpty()
        val etapaCor = arguments?.getString("etapaCor").orEmpty()

        binding.codigoHabilidadeTv.text = "Código: $codigo"
        binding.serieEtapaTv.text = "Série/Etapa: ${resolverSerieEtapa(serieSelecionada, etapaSelecionada, codigo)}"
        binding.descricaoTv.text = "Descrição: $descricao"
        binding.explicacaoTv.text = "Explicação: "
        binding.exemploTv.text = "Exemplo: "

        if (etapaCor.isNotEmpty()) {
            val textColor = if (etapaSelecionada == "EM") android.graphics.Color.BLACK else android.graphics.Color.WHITE
            binding.expandirExplicacaoBt.setBackgroundColor(etapaCor.toColorInt())
            binding.expandirExemploBt.setBackgroundColor(etapaCor.toColorInt())
            binding.criarPraticaBt.setBackgroundColor(etapaCor.toColorInt())
            binding.expandirExplicacaoBt.setTextColor(textColor)
            binding.expandirExemploBt.setTextColor(textColor)
            binding.criarPraticaBt.setTextColor(textColor)
        }

        if (etapaSelecionada == "EI") {
            binding.expandirExplicacaoBt.visibility = View.GONE
            binding.explicacaoTv.visibility = View.GONE
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getByCodigo(codigo).collect { habilidade ->
                val explicacao = habilidade?.explicacao.orEmpty()
                val exemplo = habilidade?.exemplo.orEmpty()

                binding.explicacaoTv.text = "Explicação: ${if (explicacao.isEmpty()) "Não informada" else explicacao}"
                binding.exemploTv.text = "Exemplo: ${if (exemplo.isEmpty()) "Não informado" else exemplo}"
            }
        }

        binding.expandirExplicacaoBt.setOnClickListener {
            binding.explicacaoTv.visibility = if (binding.explicacaoTv.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }

        binding.expandirExemploBt.setOnClickListener {
            binding.exemploTv.visibility = if (binding.exemploTv.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }

        binding.favoritarBt.setOnClickListener {
            val favorito = binding.favoritarBt.tag == true
            binding.favoritarBt.setImageResource(
                if (favorito) android.R.drawable.btn_star_big_off else android.R.drawable.btn_star_big_on
            )
            binding.favoritarBt.tag = !favorito
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun resolverSerieEtapa(serieSelecionada: String, etapaSelecionada: String, codigoHabilidade: String): String {
        if (serieSelecionada.isNotEmpty()) return serieSelecionada
        if (etapaSelecionada.isNotEmpty() && etapaSelecionada != "BUSCA") return etapaSelecionada

        val codigo = codigoHabilidade.uppercase()
        return when {
            codigo.startsWith("EI") -> "Educação Infantil"
            codigo.startsWith("EM") -> "Ensino Médio"
            codigo.startsWith("EF") -> "Ensino Fundamental"
            else -> "Não identificada"
        }
    }
}
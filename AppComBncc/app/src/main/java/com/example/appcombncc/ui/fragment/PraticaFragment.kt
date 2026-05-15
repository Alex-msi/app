package com.example.appcombncc.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.graphics.toColorInt
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.appcombncc.R
import com.example.appcombncc.databinding.FragmentPraticaBinding

class PraticaFragment : Fragment(R.layout.fragment_pratica) {
    private var _binding: FragmentPraticaBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPraticaBinding.bind(view)

        val codigo = arguments?.getString("habilidadeCodigo").orEmpty()
        val descricao = arguments?.getString("habilidadeDescricao").orEmpty()
        val serie = arguments?.getString("serieSelecionada").orEmpty()
        val etapaCor = arguments?.getString("etapaCor").orEmpty()
        val etapaSelecionada = arguments?.getString("etapaSelecionada").orEmpty()

        binding.habilidadeTv.text = "Habilidade: ${if (codigo.isBlank()) "-" else codigo}"
        binding.descricaoTv.text = "Descrição: ${if (descricao.isBlank()) "-" else descricao}"
        binding.serieTv.text = "Série: ${if (serie.isBlank()) "-" else serie}"

        if (etapaCor.isNotEmpty()) {
            val textColor = if (etapaSelecionada == "EM") android.graphics.Color.BLACK else android.graphics.Color.WHITE
            val backgroundColor = etapaCor.toColorInt()

            binding.tituloTelaTv.setBackgroundColor(backgroundColor)
            binding.gerarComIaBt.setBackgroundColor(backgroundColor)
            //binding.gerarSemIaBt.setBackgroundColor(backgroundColor)
            binding.editarBt.setBackgroundColor(backgroundColor)
            binding.salvarBt.setBackgroundColor(backgroundColor)
            binding.visualizarBt.setBackgroundColor(backgroundColor)
            binding.removerBt.setBackgroundColor(backgroundColor)
            binding.sairBt.setBackgroundColor(backgroundColor)

            binding.tituloTelaTv.setTextColor(textColor)
            binding.gerarComIaBt.setTextColor(textColor)
            //binding.gerarSemIaBt.setTextColor(textColor)
            binding.editarBt.setTextColor(textColor)
            binding.salvarBt.setTextColor(textColor)
            binding.visualizarBt.setTextColor(textColor)
            binding.removerBt.setTextColor(textColor)
            binding.sairBt.setTextColor(textColor)
        }

        val tempos = listOf("30 min", "40 min", "50 min", "60 min", "90 min")
        binding.tempoSpinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            tempos
        ).apply { setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }

        binding.desplugadaCb.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) binding.plugadaCb.isChecked = false
        }

        binding.plugadaCb.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) binding.desplugadaCb.isChecked = false
        }

        binding.sairBt.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

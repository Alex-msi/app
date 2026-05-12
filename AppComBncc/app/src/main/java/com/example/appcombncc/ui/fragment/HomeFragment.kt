package com.example.appcombncc.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.appcombncc.R
import com.example.appcombncc.databinding.FragmentHomeBinding
import com.example.appcombncc.util.PdfDownloadUtil


class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        binding.procurarTv.isEnabled = false
        binding.procurarTv.alpha = 0.5f

        binding.buscaHabilidadeEt.doAfterTextChanged { texto ->
            val habilitado = !texto.isNullOrBlank()
            binding.procurarTv.isEnabled = habilitado
            binding.procurarTv.alpha = if (habilitado) 1f else 0.5f
        }

        binding.procurarTv.setOnClickListener {
            val busca = binding.buscaHabilidadeEt.text.toString().trim()
            if (busca.isEmpty()) return@setOnClickListener
            val bundle = Bundle().apply {
                putString("buscaTexto", busca)
                putString("etapaCor", "#1565C0")
            }
            findNavController().navigate(R.id.listaHabilidadeFragment, bundle)
        }

        val etapaEiCor = colorHex(R.color.etapa_ei)
        val etapaEfCor = colorHex(R.color.etapa_ef)
        val etapaEmCor = colorHex(R.color.etapa_em)

        binding.etapaEiBt.setOnClickListener { navigateToEixoInfantil(etapaEiCor) }
        binding.etapaEfBt.setOnClickListener { navigateToSerie("EF", etapaEfCor) }
        binding.etapaEmBt.setOnClickListener { navigateToEixoMedio(etapaEmCor) }
        binding.pdfBnccBt.setOnClickListener { baixarPdfBncc() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun colorHex(colorRes: Int): String {
        val color = ContextCompat.getColor(requireContext(), colorRes)
        return String.format("#%06X", 0xFFFFFF and color)
    }

    private fun navigateToSerie(etapa: String, etapaCor: String) {
        val bundle = Bundle().apply {
            putString("etapaSelecionada", etapa)
            putString("etapaCor", etapaCor)
        }
        findNavController().navigate(R.id.serieFragment, bundle)
    }

    private fun navigateToEixoInfantil(etapaCor: String) {
        val bundle = Bundle().apply {
            putString("serieSelecionada", "")
            putString("etapaSelecionada", "EI")
            putString("habilidadeLike", "EI%")
            putString("etapaCor", etapaCor)
        }
        findNavController().navigate(R.id.eixoCompetenciaFragment, bundle)
    }

    private fun navigateToEixoMedio(etapaCor: String) {
        val bundle = Bundle().apply {
            putString("serieSelecionada", "")
            putString("etapaSelecionada", "EM")
            putString("habilidadeLike", "")
            putString("etapaCor", etapaCor)
        }
        findNavController().navigate(R.id.eixoCompetenciaFragment, bundle)
    }

    private fun baixarPdfBncc() {
        val nomeArquivo = "BNCCComputaoCompletodiagramado.pdf"
        val resultado = PdfDownloadUtil.salvarAssetEmDownloads(requireContext(), nomeArquivo)
        if (resultado.isSuccess) {
            Toast.makeText(
                requireContext(),
                "PDF salvo em: ${resultado.getOrNull()}",
                Toast.LENGTH_LONG
            ).show()
        } else {
            Toast.makeText(
                requireContext(),
                "Falha ao baixar PDF: ${resultado.exceptionOrNull()?.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
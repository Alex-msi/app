package com.example.appcombncc.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.appcombncc.R
import android.widget.Toast
import android.os.Environment
import java.io.File
import java.io.FileOutputStream

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val eiBt = view.findViewById<Button>(R.id.etapaEiBt)
        val efBt = view.findViewById<Button>(R.id.etapaEfBt)
        val emBt = view.findViewById<Button>(R.id.etapaEmBt)
        val pdfBt = view.findViewById<Button>(R.id.pdfBnccBt)

        eiBt.setOnClickListener { navigateToEixoInfantil("#4CAF50") }
        efBt.setOnClickListener { navigateToSerie("EF", "#1976D2") }
        emBt.setOnClickListener { navigateToEixoMedio("#FFCA28") }
        pdfBt.setOnClickListener { baixarPdfBncc() }
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
        val downloadsDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val destino = File(downloadsDir, nomeArquivo)

        try {
            requireContext().assets.open(nomeArquivo).use { input ->
                FileOutputStream(destino).use { output ->
                    input.copyTo(output)
                }
            }
            Toast.makeText(
                requireContext(),
                "PDF salvo em: ${destino.absolutePath}",
                Toast.LENGTH_LONG
            ).show()
        } catch (e: Exception) {
            Toast.makeText(
                requireContext(),
                "Falha ao baixar PDF: ${e.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}

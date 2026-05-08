package com.example.appcombncc.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.appcombncc.AppComBnccApplication
import com.example.appcombncc.R
import com.example.appcombncc.viewmodel.HabilidadesViewModel
import com.example.appcombncc.viewmodel.HabilidadesViewModelFactory
import kotlinx.coroutines.launch

class HabilidadesFragment : Fragment(R.layout.fragment_habilidades) {
    private val viewModel: HabilidadesViewModel by viewModels {
        HabilidadesViewModelFactory(
            (requireActivity().application as AppComBnccApplication).habilidadeRepository
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val codigo = arguments?.getString("habilidadeCodigo").orEmpty()
        val descricao = arguments?.getString("habilidadeDescricao").orEmpty()
        val serieSelecionada = arguments?.getString("serieSelecionada").orEmpty()
        val etapaSelecionada = arguments?.getString("etapaSelecionada").orEmpty()

        val codigoTv = view.findViewById<TextView>(R.id.codigoHabilidadeTv)
        val serieEtapaTv = view.findViewById<TextView>(R.id.serieEtapaTv)
        val descricaoTv = view.findViewById<TextView>(R.id.descricaoTv)
        val explicacaoTv = view.findViewById<TextView>(R.id.explicacaoTv)
        val exemploTv = view.findViewById<TextView>(R.id.exemploTv)
        val expandirExplicacaoBt = view.findViewById<Button>(R.id.expandirExplicacaoBt)
        val expandirExemploBt = view.findViewById<Button>(R.id.expandirExemploBt)
        val favoritarBt = view.findViewById<ImageButton>(R.id.favoritarBt)

        codigoTv.text = "Código: $codigo"
        serieEtapaTv.text = "Série/Etapa: ${serieSelecionada.ifEmpty { etapaSelecionada }}"
        descricaoTv.text = "Descrição: $descricao"
        explicacaoTv.text = "Explicação: "
        exemploTv.text = "Exemplo: "

        if (etapaSelecionada == "EI") {
            expandirExplicacaoBt.visibility = View.GONE
            explicacaoTv.visibility = View.GONE
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getByCodigo(codigo).collect { habilidade ->
                val explicacao = habilidade?.explicacao.orEmpty()
                val exemplo = habilidade?.exemplo.orEmpty()

                explicacaoTv.text = "Explicação: ${if (explicacao.isEmpty()) "Não informada" else explicacao}"
                exemploTv.text = "Exemplo: ${if (exemplo.isEmpty()) "Não informado" else exemplo}"
            }
        }

        expandirExplicacaoBt.setOnClickListener {
            explicacaoTv.visibility = if (explicacaoTv.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }

        expandirExemploBt.setOnClickListener {
            exemploTv.visibility = if (exemploTv.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }

        favoritarBt.setOnClickListener {
            val favorito = favoritarBt.tag == true
            favoritarBt.setImageResource(
                if (favorito) android.R.drawable.btn_star_big_off else android.R.drawable.btn_star_big_on
            )
            favoritarBt.tag = !favorito
        }
    }
}
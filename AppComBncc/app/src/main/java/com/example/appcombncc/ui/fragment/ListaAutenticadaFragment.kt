package com.example.appcombncc.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appcombncc.AppComBnccApplication
import com.example.appcombncc.R
import com.example.appcombncc.data.entity.UsuarioEntity
import com.example.appcombncc.data.model.PraticaUsuarioItem
import com.example.appcombncc.databinding.FragmentListaAutenticadaBinding
import com.example.appcombncc.ui.adapter.PraticaUsuarioAdapter
import com.example.appcombncc.util.SessionManager
import kotlinx.coroutines.launch

class ListaAutenticadaFragment : Fragment(R.layout.fragment_lista_autenticada) {
    private var _binding: FragmentListaAutenticadaBinding? = null
    private val binding get() = _binding!!

    private var usuarioAtual: UsuarioEntity? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentListaAutenticadaBinding.bind(view)

        configurarCamposSomenteLeitura()
        carregarDadosUsuario()
        configurarBotoesEdicao()
        configurarListaPraticasMock()
    }

    private fun configurarCamposSomenteLeitura() {
        binding.nomeEt.isEnabled = false
        binding.tipoEt.isEnabled = false
        binding.salvarBt.isEnabled = false
    }

    private fun carregarDadosUsuario() {
        val emailArgumento = arguments?.getString("email_usuario").orEmpty().trim()
        val emailSessao = SessionManager(requireContext()).obterSessao().email.trim()
        val email = emailArgumento.ifBlank { emailSessao }.trim().lowercase()

        binding.emailUsuarioTv.text = if (email.isBlank()) "-" else email

        if (email.isBlank()) {
            binding.idUsuarioTv.text = "-"
            binding.nomeEt.setText("")
            binding.tipoEt.setText("-")
            return
        }

        val app = requireActivity().application as AppComBnccApplication
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                app.database.usuarioDao().getByEmail(email).collect { usuario ->
                    val safeBinding = _binding ?: return@collect
                    usuarioAtual = usuario

                    safeBinding.idUsuarioTv.text = usuario?.id?.toString() ?: "-"
                    safeBinding.nomeEt.setText(usuario?.nome.orEmpty())
                    safeBinding.tipoEt.setText(usuario?.tipo ?: "-")
                }
            }
        }
    }

    private fun configurarBotoesEdicao() {
        binding.alterarBt.setOnClickListener {
            binding.nomeEt.isEnabled = true
            binding.nomeEt.requestFocus()
            binding.salvarBt.isEnabled = true
        }

        binding.salvarBt.setOnClickListener {
            val usuario = usuarioAtual
            val usuarioId = usuario?.id

            if (usuarioId == null) {
                Toast.makeText(requireContext(), "Usuário não encontrado", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val novoNome = binding.nomeEt.text.toString().trim().ifBlank { null }
            val app = requireActivity().application as AppComBnccApplication

            viewLifecycleOwner.lifecycleScope.launch {
                app.database.usuarioDao().atualizarNomeById(
                    usuarioId = usuarioId,
                    nome = novoNome,
                    atualizadoEm = System.currentTimeMillis()
                )

                usuarioAtual = usuario.copy(
                    nome = novoNome,
                    atualizadoEm = System.currentTimeMillis()
                )

                _binding?.let { safeBinding ->
                    safeBinding.nomeEt.isEnabled = false
                    safeBinding.salvarBt.isEnabled = false
                }

                Toast.makeText(requireContext(), "Nome atualizado com sucesso", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun configurarListaPraticasMock() {
        val adapter = PraticaUsuarioAdapter()
        binding.praticasRv.layoutManager = LinearLayoutManager(requireContext())
        binding.praticasRv.adapter = adapter

        adapter.submitList(
            listOf(
                PraticaUsuarioItem("Prática 1", "EF06MA01", "6º ano"),
                PraticaUsuarioItem("Prática 2", "EF15MA07", "EF 1-5")
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
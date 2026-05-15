package com.example.appcombncc.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appcombncc.R
import com.example.appcombncc.data.model.PraticaUsuarioItem
import com.example.appcombncc.databinding.FragmentListaAutenticadaBinding
import com.example.appcombncc.ui.adapter.PraticaUsuarioAdapter

class ListaAutenticadaFragment : Fragment(R.layout.fragment_lista_autenticada) {
    private var _binding: FragmentListaAutenticadaBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentListaAutenticadaBinding.bind(view)

        val email = arguments?.getString("email_usuario").orEmpty()

        binding.idUsuarioTv.text = "1"
        binding.emailUsuarioTv.text = if (email.isBlank()) "-" else email

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

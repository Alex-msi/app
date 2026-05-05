package com.example.appcombncc.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appcombncc.R
import com.example.appcombncc.ui.adapter.SimpleTextAdapter

class ListaHabilidadeFragment : Fragment(R.layout.fragment_lista_habilidade) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.listaHabilidadeRv)

        val itensMock = listOf(
            "Habilidade 1",
            "Habilidade 2",
            "Habilidade 3"
        )

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = SimpleTextAdapter(itensMock)
    }
}

package com.example.appcombncc.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.appcombncc.R

class SerieFragment : Fragment(R.layout.fragment_serie) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val seriesSp = view.findViewById<Spinner>(R.id.seriesSp)
        val grupoSp = view.findViewById<Spinner>(R.id.grupoSp)

        val series = listOf("1º ano", "2º ano", "3º ano", "4º ano", "5º ano", "6º ano", "7º ano", "8º ano", "9º ano")
        val grupos = listOf("1º ao 5º", "6º ao 9º")

        seriesSp.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, series)
        grupoSp.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, grupos)
    }
}
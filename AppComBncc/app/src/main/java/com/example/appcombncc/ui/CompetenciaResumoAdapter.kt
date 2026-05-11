package com.example.appcombncc.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appcombncc.R
import com.example.appcombncc.data.model.CompetenciaResumoItem

class CompetenciaResumoAdapter(
    private val onItemClick: (CompetenciaResumoItem) -> Unit
) : RecyclerView.Adapter<CompetenciaResumoAdapter.CompetenciaResumoViewHolder>() {
    private val itens = mutableListOf<CompetenciaResumoItem>()

    fun submitList(novosItens: List<CompetenciaResumoItem>) {
        itens.clear()
        itens.addAll(novosItens)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompetenciaResumoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_simple_text, parent, false)
        return CompetenciaResumoViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: CompetenciaResumoViewHolder, position: Int) {
        holder.bind(itens[position])
    }

    override fun getItemCount(): Int = itens.size

    class CompetenciaResumoViewHolder(
        itemView: View,
        private val onItemClick: (CompetenciaResumoItem) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val simpleItemTv: TextView = itemView.findViewById(R.id.simpleItemTv)

        fun bind(item: CompetenciaResumoItem) {
            simpleItemTv.text = "${item.competenciaDescricao}\nTotal de habilidades: ${item.totalHabilidades}"
            itemView.setOnClickListener { onItemClick(item) }
        }
    }
}

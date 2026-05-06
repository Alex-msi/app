package com.example.appcombncc.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appcombncc.R
import com.example.appcombncc.data.model.ObjetoHabilidadeCount

class ObjetoResumoAdapter(
    private val onItemClick: (ObjetoHabilidadeCount) -> Unit
) : RecyclerView.Adapter<ObjetoResumoAdapter.ObjetoResumoViewHolder>() {
    private val itens = mutableListOf<ObjetoHabilidadeCount>()

    fun submitList(novosItens: List<ObjetoHabilidadeCount>) {
        itens.clear()
        itens.addAll(novosItens)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObjetoResumoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_objeto_resumo, parent, false)
        return ObjetoResumoViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: ObjetoResumoViewHolder, position: Int) {
        holder.bind(itens[position])
    }

    override fun getItemCount(): Int = itens.size

    class ObjetoResumoViewHolder(
        itemView: View,
        private val onItemClick: (ObjetoHabilidadeCount) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val objetoDescricaoTv: TextView = itemView.findViewById(R.id.objetoDescricaoTv)
        private val totalHabilidadesTv: TextView = itemView.findViewById(R.id.totalHabilidadesObjetoTv)

        fun bind(item: ObjetoHabilidadeCount) {
            objetoDescricaoTv.text = item.objeto
            totalHabilidadesTv.text = "Total de habilidades: ${item.totalHabilidades}"
            itemView.setOnClickListener { onItemClick(item) }
        }
    }
}

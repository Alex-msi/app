package com.example.appcombncc.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appcombncc.R
import com.example.appcombncc.data.model.EixoHabilidadeCount

class EixoResumoAdapter : RecyclerView.Adapter<EixoResumoAdapter.EixoResumoViewHolder>() {
    private val itens = mutableListOf<EixoHabilidadeCount>()

    fun submitList(novosItens: List<EixoHabilidadeCount>) {
        itens.clear()
        itens.addAll(novosItens)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EixoResumoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_eixo_resumo, parent, false)
        return EixoResumoViewHolder(view)
    }

    override fun onBindViewHolder(holder: EixoResumoViewHolder, position: Int) {
        holder.bind(itens[position])
    }

    override fun getItemCount(): Int = itens.size

    class EixoResumoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val eixoDescricaoTv: TextView = itemView.findViewById(R.id.eixoDescricaoTv)
        private val totalHabilidadesTv: TextView = itemView.findViewById(R.id.totalHabilidadesTv)

        fun bind(item: EixoHabilidadeCount) {
            eixoDescricaoTv.text = item.eixo
            totalHabilidadesTv.text = "Total de habilidades: ${item.totalHabilidades}"
        }
    }
}
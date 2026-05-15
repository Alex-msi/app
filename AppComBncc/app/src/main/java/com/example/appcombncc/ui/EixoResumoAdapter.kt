package com.example.appcombncc.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appcombncc.data.model.EixoHabilidadeCount
import com.example.appcombncc.databinding.ItemEixoCompetenciaResumoBinding


class EixoResumoAdapter(
    private val onItemClick: (EixoHabilidadeCount) -> Unit
) : RecyclerView.Adapter<EixoResumoAdapter.EixoResumoViewHolder>() {
    private val itens = mutableListOf<EixoHabilidadeCount>()

    fun submitList(novosItens: List<EixoHabilidadeCount>) {
        itens.clear()
        itens.addAll(novosItens)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EixoResumoViewHolder {
        val binding = ItemEixoCompetenciaResumoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EixoResumoViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: EixoResumoViewHolder, position: Int) {
        holder.bind(itens[position])
    }

    override fun getItemCount(): Int = itens.size

    class EixoResumoViewHolder(
        private val binding: ItemEixoCompetenciaResumoBinding,
        private val onItemClick: (EixoHabilidadeCount) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {


        fun bind(item: EixoHabilidadeCount) {
            binding.eixoDescricaoTv.text = item.eixo
            binding.totalHabilidadesTv.text = "Total de habilidades: ${item.totalHabilidades}"
            binding.root.setOnClickListener { onItemClick(item) }
        }
    }
}

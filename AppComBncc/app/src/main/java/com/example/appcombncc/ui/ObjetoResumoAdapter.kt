package com.example.appcombncc.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appcombncc.data.model.ObjetoHabilidadeCount
import com.example.appcombncc.databinding.ItemObjetoResumoBinding

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
        val binding = ItemObjetoResumoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ObjetoResumoViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: ObjetoResumoViewHolder, position: Int) {
        holder.bind(itens[position])
    }

    override fun getItemCount(): Int = itens.size

    class ObjetoResumoViewHolder(
        private val binding: ItemObjetoResumoBinding,
        private val onItemClick: (ObjetoHabilidadeCount) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ObjetoHabilidadeCount) {
            binding.objetoDescricaoTv.text = item.objeto
            binding.totalHabilidadesObjetoTv.text = "Total de habilidades: ${item.totalHabilidades}"
            binding.root.setOnClickListener { onItemClick(item) }
        }
    }
}

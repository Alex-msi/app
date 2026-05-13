package com.example.appcombncc.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appcombncc.data.model.CompetenciaResumoItem
import com.example.appcombncc.databinding.ItemSimpleTextBinding

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
        val binding = ItemSimpleTextBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CompetenciaResumoViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: CompetenciaResumoViewHolder, position: Int) {
        holder.bind(itens[position])
    }

    override fun getItemCount(): Int = itens.size

    class CompetenciaResumoViewHolder(
        private val binding: ItemSimpleTextBinding,
        private val onItemClick: (CompetenciaResumoItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CompetenciaResumoItem) {
            binding.simpleItemTv.text = "${item.competenciaDescricao}\nTotal de habilidades: ${item.totalHabilidades}"
            binding.root.setOnClickListener { onItemClick(item) }
        }
    }
}
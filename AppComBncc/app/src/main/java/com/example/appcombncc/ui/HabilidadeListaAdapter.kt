package com.example.appcombncc.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appcombncc.data.model.HabilidadeListaItem
import com.example.appcombncc.databinding.ItemSimpleTextBinding

class HabilidadeListaAdapter(
    private val onItemClick: (HabilidadeListaItem) -> Unit
) : RecyclerView.Adapter<HabilidadeListaAdapter.HabilidadeViewHolder>() {

    private val itens = mutableListOf<HabilidadeListaItem>()

    fun submitList(novosItens: List<HabilidadeListaItem>) {
        itens.clear()
        itens.addAll(novosItens)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabilidadeViewHolder {
        val binding = ItemSimpleTextBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HabilidadeViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: HabilidadeViewHolder, position: Int) {
        holder.bind(itens[position])
    }

    override fun getItemCount(): Int = itens.size

    class HabilidadeViewHolder(
        private val binding: ItemSimpleTextBinding,
        private val onItemClick: (HabilidadeListaItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HabilidadeListaItem) {
            binding.simpleItemTv.text = "${item.codigo} - ${item.descricao}"
            binding.root.setOnClickListener { onItemClick(item) }
        }
    }
}

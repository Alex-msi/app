package com.example.appcombncc.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appcombncc.R
import com.example.appcombncc.data.model.HabilidadeListaItem

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
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_simple_text, parent, false)
        return HabilidadeViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: HabilidadeViewHolder, position: Int) {
        holder.bind(itens[position])
    }

    override fun getItemCount(): Int = itens.size

    class HabilidadeViewHolder(
        itemView: View,
        private val onItemClick: (HabilidadeListaItem) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.simpleItemTv)

        fun bind(item: HabilidadeListaItem) {
            textView.text = "${item.codigo} - ${item.descricao}"
            itemView.setOnClickListener { onItemClick(item) }
        }
    }
}

package com.example.appcombncc.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appcombncc.data.model.PraticaUsuarioItem
import com.example.appcombncc.databinding.ItemPraticaUsuarioBinding

class PraticaUsuarioAdapter : RecyclerView.Adapter<PraticaUsuarioAdapter.PraticaUsuarioViewHolder>() {
    private val itens = mutableListOf<PraticaUsuarioItem>()

    fun submitList(novosItens: List<PraticaUsuarioItem>) {
        itens.clear()
        itens.addAll(novosItens)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PraticaUsuarioViewHolder {
        val binding = ItemPraticaUsuarioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PraticaUsuarioViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PraticaUsuarioViewHolder, position: Int) {
        holder.bind(itens[position])
    }

    override fun getItemCount(): Int = itens.size

    class PraticaUsuarioViewHolder(
        private val binding: ItemPraticaUsuarioBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PraticaUsuarioItem) {
            binding.tituloPraticaTv.text = item.tituloPratica
            binding.codigoHabilidadeTv.text = "Código da habilidade: ${item.codigoHabilidade}"
            binding.serieEtapaTv.text = "Série/Etapa: ${item.serieEtapa}"
        }
    }
}

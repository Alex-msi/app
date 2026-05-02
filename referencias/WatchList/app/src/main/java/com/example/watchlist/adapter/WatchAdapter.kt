package com.example.watchlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.watchlist.databinding.WatchCelulaBinding
import com.example.watchlist.domain.Watch

class WatchAdapter : RecyclerView.Adapter<WatchAdapter.WatchViewHolder>(),
    Filterable {

    var onItemClick: ((Watch) -> Unit)? = null

    var titlesLista = ArrayList<Watch>()
    var titlesListaFilterable = ArrayList<Watch>()

    private lateinit var binding: WatchCelulaBinding

    fun updateList(newList: List<Watch>) {
        titlesLista = ArrayList(newList)
        titlesListaFilterable = titlesLista
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WatchViewHolder {
        binding = WatchCelulaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WatchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WatchViewHolder, position: Int) {
        holder.titleVH.text = titlesListaFilterable[position].title
        holder.statusVH.text = titlesListaFilterable[position].status
    }

    override fun getItemCount(): Int {
        return titlesListaFilterable.size
    }

    inner class WatchViewHolder(view: WatchCelulaBinding) : RecyclerView.ViewHolder(view.root) {
        val titleVH = view.title
        val statusVH = view.status

        init {
            view.root.setOnClickListener {
                onItemClick?.invoke(titlesListaFilterable[adapterPosition])
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val resultList = if (p0.toString().isEmpty()) {
                    ArrayList(titlesLista)
                } else {
                    val filteredList = ArrayList<Watch>()
                    for (row in titlesLista) {
                        if (row.title.lowercase().contains(p0.toString().lowercase()) ||
                            row.status.lowercase().contains(p0.toString().lowercase())
                        ) {
                            filteredList.add(row)
                        }
                    }
                    filteredList
                }

                return FilterResults().apply {
                    values = resultList
                }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                results?.values?.let { values ->
                    @Suppress("UNCHECKED_CAST")
                    titlesListaFilterable = values as? ArrayList<Watch> ?: ArrayList()
                    notifyDataSetChanged()
                }
            }
        }
    }
}
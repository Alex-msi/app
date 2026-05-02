package com.example.moviesmanager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesmanager.databinding.MoviesCelulaBinding
import com.example.moviesmanager.domain.Movies


class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.WatchViewHolder>(),
    Filterable {

    var onItemClick: ((Movies) -> Unit)? = null

    var titlesLista = ArrayList<Movies>()
    var titlesListaFilterable = ArrayList<Movies>()

    private lateinit var binding: MoviesCelulaBinding

    fun updateList(newList: List<Movies>) {
        titlesLista = ArrayList(newList)
        titlesListaFilterable = titlesLista
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WatchViewHolder {
        binding = MoviesCelulaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WatchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WatchViewHolder, position: Int) {
        holder.titleVH.text = titlesListaFilterable[position].title
        holder.ratingVH.text = String.format("%.1f", titlesListaFilterable[position].rating)
    }

    override fun getItemCount(): Int {
        return titlesListaFilterable.size

    }

    inner class WatchViewHolder(view: MoviesCelulaBinding) : RecyclerView.ViewHolder(view.root) {
        val titleVH = view.title
        val ratingVH = view.rating

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
                    val filteredList = ArrayList<Movies>()
                    for (row in titlesLista) {
                        if (row.title.lowercase().contains(p0.toString().lowercase()) ||
                            row.rating.toString().lowercase().contains(p0.toString().lowercase())
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
                    titlesListaFilterable = values as? ArrayList<Movies> ?: ArrayList()
                    notifyDataSetChanged()
                }
            }
        }
    }
}
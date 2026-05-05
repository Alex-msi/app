package com.example.appcombncc.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appcombncc.R

class SimpleTextAdapter(
    private val items: List<String>
) : RecyclerView.Adapter<SimpleTextAdapter.SimpleTextViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleTextViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_simple_text, parent, false)
        return SimpleTextViewHolder(view)
    }

    override fun onBindViewHolder(holder: SimpleTextViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class SimpleTextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.simpleItemTv)

        fun bind(text: String) {
            textView.text = text
        }
    }
}

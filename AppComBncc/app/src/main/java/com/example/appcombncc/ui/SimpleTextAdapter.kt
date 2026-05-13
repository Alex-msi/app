package com.example.appcombncc.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appcombncc.databinding.ItemSimpleTextBinding

class SimpleTextAdapter(
    private val items: List<String>
) : RecyclerView.Adapter<SimpleTextAdapter.SimpleTextViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleTextViewHolder {
        val binding = ItemSimpleTextBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SimpleTextViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SimpleTextViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

        class SimpleTextViewHolder(
            private val binding: ItemSimpleTextBinding
        ) : RecyclerView.ViewHolder(binding.root) {

            fun bind(text: String) {
                binding.simpleItemTv.text = text
            }
        }
    }
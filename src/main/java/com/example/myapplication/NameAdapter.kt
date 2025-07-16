package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 *  RecyclerView
 * Advanced, flexible version of ListView.
 * Efficient because it reuses (recycles) views.
 * Needs:
 * Adapter (binds data)
 * ViewHolder (holds views)
 * LayoutManager (sets layout style)**/
class NameAdapter(
    private val nameList: MutableList<Name>,
    private val onItemLongClick: (Int) -> Unit
) : RecyclerView.Adapter<NameAdapter.NameViewHolder>() {

    inner class NameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameText: TextView = itemView.findViewById(R.id.nameText)
        init {
            itemView.setOnLongClickListener {
                onItemLongClick(adapterPosition)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_name, parent, false)
        return NameViewHolder(view)
    }

    override fun onBindViewHolder(holder: NameViewHolder, position: Int) {
        holder.nameText.text = nameList[position].fullName
    }

    override fun getItemCount(): Int = nameList.size
}

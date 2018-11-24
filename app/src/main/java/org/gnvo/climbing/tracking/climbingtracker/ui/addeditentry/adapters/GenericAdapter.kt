package org.gnvo.climbing.tracking.climbingtracker.ui.addeditentry.adapters

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.simple_recycler_view_item_1.view.*
import org.gnvo.climbing.tracking.climbingtracker.R

class GenericAdapter<T> : RecyclerView.Adapter<GenericAdapter<T>.ViewHolder>() {

    private var selected: Int? = null
    private var selectedItem: T? = null
    private var items = ArrayList<T>()

    fun setItems(items: List<T>) {
        this.items = ArrayList(items)
        selected = items.indexOf(selectedItem)
        notifyDataSetChanged()
    }

    fun getSelected(): T? {
        return selected?.let{items[it]}
    }

    fun setSelected(item: T?) {
        selectedItem = item
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.simple_recycler_view_item_1, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    open inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int) {
            val item = items[position]
            itemView.text_view_1.text = item.toString()
            when (position){
                selected -> itemView.text_view_1.setBackgroundColor(Color.LTGRAY)
                else -> itemView.text_view_1.setBackgroundColor(Color.TRANSPARENT)//TODO: this is a hack, better color handling
            }

            itemView.setOnClickListener {view ->
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    selected?.let{ notifyItemChanged(it) } //Uncheck old selection
                    selected = position
                    selected?.let{ notifyItemChanged(it) } //check new selection
                }
            }
        }
    }
}

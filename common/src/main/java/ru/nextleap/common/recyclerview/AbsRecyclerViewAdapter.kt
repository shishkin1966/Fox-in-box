package ru.nextleap.common.recyclerview

import androidx.recyclerview.widget.RecyclerView
import ru.nextleap.common.ApplicationUtils

abstract class AbsRecyclerViewAdapter<E, VH : RecyclerView.ViewHolder> : IRecyclerViewAdapter<E>,
    RecyclerView.Adapter<VH>() {
    private var items: MutableList<E> = ArrayList()

    override fun getItemCount(): Int = items.size

    override fun setItems(items: List<E>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun add(e: E) {
        items.add(e)
        notifyDataSetChanged()
    }

    override fun add(position: Int, e: E) {
        items.add(position, e)
        notifyDataSetChanged()
    }

    override fun addAll(items: List<E>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun move(fromPosition: Int, toPosition: Int) {
        val e = items.removeAt(fromPosition)
        items.add(toPosition, e)
        notifyDataSetChanged()
    }

    override fun remove(position: Int): E {
        val e = items.removeAt(position)
        notifyDataSetChanged()
        return e
    }

    override fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    override fun isEmpty(): Boolean {
        return items.isEmpty()
    }

    override fun getItem(position: Int): E {
        return items[position]
    }

    override fun getItems(): List<E> {
        return items
    }
}

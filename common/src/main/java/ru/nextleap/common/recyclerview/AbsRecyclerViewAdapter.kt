package ru.nextleap.common.recyclerview

import androidx.recyclerview.widget.RecyclerView

abstract class AbsRecyclerViewAdapter<E, VH : RecyclerView.ViewHolder> : IRecyclerViewAdapter<E>,
    RecyclerView.Adapter<VH>() {
    private var items: MutableList<E> = ArrayList()

    override fun getItemCount(): Int = items.size

    override fun setItem(position: Int, item: E) {
        this.items[position] = item
        notifyItemChanged(position)
    }

    override fun setItems(items: List<E>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun add(e: E) {
        if (items.add(e)) {
            val position = items.size - 1;
            notifyItemInserted(position);
        }
    }

    override fun add(position: Int, e: E) {
        items.add(position, e);
        notifyItemInserted(position);
    }

    override fun addAll(items: List<E>) {
        val positionStart = this.items.size;
        if (this.items.addAll(items)) {
            notifyItemRangeInserted(positionStart, items.size);
        }
    }

    override fun move(fromPosition: Int, toPosition: Int) {
        val e = items.removeAt(fromPosition)
        items.add(toPosition, e)
        notifyItemMoved(fromPosition, toPosition);
    }

    override fun remove(position: Int): E {
        val e = items.removeAt(position)
        notifyItemRemoved(position);
        return e
    }

    override fun clear() {
        val itemCount = items.size;
        items.clear();
        notifyItemRangeRemoved(0, itemCount);
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

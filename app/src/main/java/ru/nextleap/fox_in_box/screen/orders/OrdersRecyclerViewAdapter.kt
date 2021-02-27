package ru.nextleap.fox_in_box.screen.orders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import ru.nextleap.common.ApplicationUtils
import ru.nextleap.common.recyclerview.AbsRecyclerViewAdapter
import ru.nextleap.fox_in_box.ApplicationSingleton
import ru.nextleap.fox_in_box.GlideApp
import ru.nextleap.fox_in_box.R
import ru.nextleap.fox_in_box.data.Orders
import ru.nextleap.sl.provider.ApplicationProvider


class OrdersRecyclerViewAdapter :
    AbsRecyclerViewAdapter<Orders, OrdersRecyclerViewAdapter.ViewHolder>() {
    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).Id.toLong()
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)

        holder.recycle()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var image: ImageView = itemView.findViewById(R.id.image)
        private var card: MaterialCardView = itemView.findViewById(R.id.card)

        fun bind(position: Int, count: Int, item: Orders) {
            card.tag = item
            if (position == 0) {
                val params = card.layoutParams as ViewGroup.MarginLayoutParams?
                params?.topMargin =
                    ApplicationProvider.appContext.resources.getDimension(R.dimen.dimen_16dp)
                        .toInt()
                card.layoutParams = params
            }
            if (position == count - 1) {
                val params = card.layoutParams as ViewGroup.MarginLayoutParams?
                params?.bottomMargin =
                    ApplicationProvider.appContext.resources.getDimension(R.dimen.dimen_16dp)
                        .toInt()
                card.layoutParams = params
            }
            card.setOnClickListener(this::onClick)
        }

        fun recycle() {
            GlideApp.with(ApplicationProvider.appContext).clear(image)
        }

        private fun onClick(v: View?) {
            ApplicationUtils.showToast(ApplicationProvider.appContext, v?.tag.toString())
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                ApplicationSingleton.instance.desktopProvider.getLayoutId(
                    "list_item_orders",
                    R.layout.list_item_orders
                ),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position, itemCount, getItem(position))
    }
}
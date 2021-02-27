package ru.nextleap.fox_in_box.screen.aggregated

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import ru.nextleap.common.ApplicationUtils
import ru.nextleap.common.recyclerview.AbsRecyclerViewAdapter
import ru.nextleap.fox_in_box.ApplicationSingleton
import ru.nextleap.fox_in_box.GlideApp
import ru.nextleap.fox_in_box.R
import ru.nextleap.fox_in_box.action.ImageAction
import ru.nextleap.fox_in_box.data.Aggregated
import ru.nextleap.sl.provider.ApplicationProvider


class AggregatedRecyclerViewAdapter :
    AbsRecyclerViewAdapter<Aggregated, AggregatedRecyclerViewAdapter.ViewHolder>() {
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
        private var text: TextView = itemView.findViewById(R.id.text)
        private var desc: TextView = itemView.findViewById(R.id.desc)

        fun bind(position: Int, count: Int, item: Aggregated) {
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
            if (!item.Image.isNullOrEmpty()) {
                val url = ApplicationSingleton.instance.netProvider.getBaseUrl() + item.Image
                ApplicationSingleton.instance.netImageProvider.downloadImage(
                    AggregatedPresenter.NAME,
                    ImageAction(
                        image,
                        url,
                        ApplicationUtils.getScreenWidth(ApplicationProvider.appContext) - ApplicationUtils.dp2px(
                            ApplicationProvider.appContext,
                            32f
                        ).toInt(),
                        ApplicationUtils.dp2px(ApplicationProvider.appContext, 400f).toInt()
                    )
                )
                image.visibility = View.VISIBLE
            } else {
                image.visibility = View.GONE
            }
            if (!item.Name.isNullOrEmpty()) {
                text.text = ApplicationUtils.fromHtml(item.Name)
                text.visibility = View.VISIBLE
            } else {
                text.visibility = View.GONE
            }
            if (!item.Description.isNullOrEmpty()) {
                desc.text = ApplicationUtils.fromHtml(item.Description)
                desc.visibility = View.VISIBLE
            } else {
                desc.visibility = View.GONE
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
                    "list_item_aggregated",
                    R.layout.list_item_aggregated
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
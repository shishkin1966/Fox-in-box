package ru.nextleap.fox_in_box.screen.materials

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
import ru.nextleap.fox_in_box.data.Materials
import ru.nextleap.sl.provider.ApplicationProvider

class MaterialsRecyclerViewAdapter :
    AbsRecyclerViewAdapter<Materials, MaterialsRecyclerViewAdapter.ViewHolder>() {
    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).Id.toLong()
    }

    override fun onViewRecycled(holder: MaterialsRecyclerViewAdapter.ViewHolder) {
        super.onViewRecycled(holder)

        holder.recycle()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var card: MaterialCardView = itemView.findViewById(R.id.card)
        private var image: ImageView = itemView.findViewById(R.id.image)
        private var text: TextView = itemView.findViewById(R.id.text)

        fun bind(position: Int, count: Int, item: Materials) {
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
            if (!item.ImagePath.isNullOrEmpty()) {
                if (item.ImagePath!!.endsWith(".jpg")) {
                    val url = item.ImagePath!!
                    ApplicationSingleton.instance.netImageProvider.downloadImage(
                        MaterialsPresenter.NAME,
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
                } else {
                    image.visibility = View.GONE
                }
            } else {
                image.visibility = View.GONE
            }
            if (!item.Description.isNullOrEmpty()) {
                try {
                    text.text = ApplicationUtils.fromHtml(item.Description)
                } catch (e: Exception) {
                }
            }
        }

        private fun onClick(v: View?) {
        }

        fun recycle() {
            GlideApp.with(ApplicationProvider.appContext).clear(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                ApplicationSingleton.instance.desktopProvider.getLayoutId(
                    "list_item_materials",
                    R.layout.list_item_materials
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
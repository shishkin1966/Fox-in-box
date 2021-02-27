package ru.nextleap.fox_in_box.screen.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import ru.nextleap.common.ApplicationUtils
import ru.nextleap.common.formatShortDate
import ru.nextleap.common.recyclerview.AbsComparableRecyclerViewAdapter
import ru.nextleap.fox_in_box.ApplicationSingleton
import ru.nextleap.fox_in_box.GlideApp
import ru.nextleap.fox_in_box.R
import ru.nextleap.fox_in_box.action.ImageAction
import ru.nextleap.fox_in_box.data.News
import ru.nextleap.fox_in_box.screen.main.MainPresenter
import ru.nextleap.sl.action.DataAction
import ru.nextleap.sl.provider.ApplicationProvider


class NewsRecyclerViewAdapter :
    AbsComparableRecyclerViewAdapter<News, NewsRecyclerViewAdapter.ViewHolder>() {
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
        private var startDate: TextView = itemView.findViewById(R.id.startDate)
        private var like: TextView = itemView.findViewById(R.id.like)
        private var likeImage: ImageView = itemView.findViewById(R.id.likeImage)
        private var comment: TextView = itemView.findViewById(R.id.comment)
        private var display: TextView = itemView.findViewById(R.id.display)

        fun bind(position: Int, count: Int, item: News) {
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
            if (item.Pinned) {
                card.setCardBackgroundColor(ContextCompat.getColor(ApplicationProvider.appContext, R.color.background_new))
            } else {
                card.setCardBackgroundColor(ContextCompat.getColor(ApplicationProvider.appContext, R.color.white))
            }
            if (!item.Image.isNullOrEmpty()) {
                val url = ApplicationSingleton.instance.netProvider.getBaseUrl() + item.Image
                ApplicationSingleton.instance.netImageProvider.downloadImage(
                    NewsPresenter.NAME,
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
            text.text = ApplicationUtils.fromHtml(item.Preview)
            startDate.text = item.StartDate?.formatShortDate()
            like.text = item.LikesCount?.toString()
            comment.text = item.CommentsCount?.toString()
            display.text = item.ViewsCount?.toString()
            if (item.MyVote) {
                likeImage.setImageResource(R.drawable.ic_like_orange)
            } else {
                likeImage.setImageResource(R.drawable.ic_like)
            }
        }

        fun recycle() {
            GlideApp.with(ApplicationProvider.appContext).clear(image)
        }

        private fun onClick(v: View?) {
            if (v != null && v.tag != null) {
                val news = v.tag
                val presenter =
                    ApplicationSingleton.instance.getPresenter<MainPresenter>(MainPresenter.NAME)
                presenter?.addAction(DataAction(MainPresenter.ShowViewNewsFragment, news))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                ApplicationSingleton.instance.desktopProvider.getLayoutId(
                    "list_item_news",
                    R.layout.list_item_news
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
package ru.nextleap.fox_in_box.screen.view_news

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import ru.nextleap.common.ApplicationUtils
import ru.nextleap.common.formatShortDate
import ru.nextleap.fox_in_box.ApplicationSingleton
import ru.nextleap.fox_in_box.R
import ru.nextleap.fox_in_box.action.Actions
import ru.nextleap.fox_in_box.action.ImageAction
import ru.nextleap.fox_in_box.action.OnClickAction
import ru.nextleap.fox_in_box.data.News
import ru.nextleap.fox_in_box.screen.AbsDesktopFragment
import ru.nextleap.sl.action.DataAction
import ru.nextleap.sl.action.IAction
import ru.nextleap.sl.model.IModel
import ru.nextleap.sl.provider.ApplicationProvider


class ViewNewsFragment : AbsDesktopFragment(
    "fragment_news_view",
    R.layout.fragment_news_view
) {

    companion object {
        const val NAME = "ViewNewsFragment"

        fun newInstance(news: News): ViewNewsFragment {
            val f = ViewNewsFragment()
            val bundle = Bundle()
            bundle.putParcelable(NAME, news)
            f.arguments = bundle
            return f
        }
    }

    private lateinit var image: ImageView
    private lateinit var text: TextView
    private lateinit var title: TextView
    private lateinit var startDate: TextView
    private lateinit var like: TextView
    private lateinit var likeImage: ImageView
    private lateinit var comment: TextView
    private lateinit var display: TextView

    override fun createModel(): IModel {
        return ViewNewsModel(this)
    }

    override fun onAction(action: IAction): Boolean {
        if (!isValid()) return false

        if (action is DataAction<*>) {
            when (action.getName()) {
                Actions.SetItem -> {
                    setNews(action.getData() as News)
                    return true
                }
                Actions.ResponseOnClick -> {
                    responseOnClick(action.getData() as Int)
                    return true
                }
            }
        }

        if (super.onAction(action)) return true

        return false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        image = view.findViewById(R.id.image)
        title = view.findViewById(R.id.title)
        text = view.findViewById(R.id.text)
        startDate = view.findViewById(R.id.startDate)
        like = view.findViewById(R.id.like)
        likeImage = view.findViewById(R.id.likeImage)
        likeImage.setOnClickListener(this::onClick)
        comment = view.findViewById(R.id.comment)
        display = view.findViewById(R.id.display)

        val news = arguments?.getParcelable(NAME) as News?
        if (news != null) {
            if (!news.Image.isNullOrEmpty()) {
                val url = ApplicationSingleton.instance.netProvider.getBaseUrl() + news.Image
                ApplicationSingleton.instance.netImageProvider.downloadImage(
                    ViewNewsPresenter.NAME,
                    ImageAction(
                        image,
                        url,
                        ApplicationUtils.getScreenWidth(ApplicationProvider.appContext),
                        ApplicationUtils.dp2px(ApplicationProvider.appContext, 400f).toInt()
                    )
                )
                image.visibility = View.VISIBLE
            } else {
                image.visibility = View.GONE
            }
            setNews(news)

            getModel<ViewNewsModel>().getPresenter<ViewNewsPresenter>()
                .setNews(news)
        }
    }

    private fun onClick(v: View?) {
        getModel<ViewNewsModel>().getPresenter<ViewNewsPresenter>().addAction(OnClickAction(v!!.id))
    }

    override fun isTop(): Boolean {
        return false
    }

    override fun getName(): String {
        return NAME
    }

    private fun setNews(news: News) {
        title.text = ApplicationUtils.fromHtml(news.Title)
        text.text = ApplicationUtils.fromHtml(news.Body)
        startDate.text = news.StartDate?.formatShortDate()
        like.text = news.LikesCount?.toString()
        comment.text = news.CommentsCount?.toString()
        display.text = news.ViewsCount?.toString()
        if (news.MyVote) {
            likeImage.setImageResource(R.drawable.ic_like_orange)
        } else {
            likeImage.setImageResource(R.drawable.ic_like)
        }
    }

    private fun responseOnClick(id: Int) {
        val news = getModel<ViewNewsModel>().getPresenter<ViewNewsPresenter>().getNews()
        when (id) {
            R.id.likeImage -> {
                if (news!!.MyVote) {
                    likeImage.setImageResource(R.drawable.ic_like_orange)
                } else {
                    likeImage.setImageResource(R.drawable.ic_like)
                }
            }
        }
    }

}

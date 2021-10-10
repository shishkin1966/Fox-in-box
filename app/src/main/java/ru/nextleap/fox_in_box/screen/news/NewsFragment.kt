package ru.nextleap.fox_in_box.screen.news

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import ru.nextleap.fox_in_box.ApplicationSingleton
import ru.nextleap.fox_in_box.R
import ru.nextleap.fox_in_box.action.Actions
import ru.nextleap.fox_in_box.data.News
import ru.nextleap.fox_in_box.screen.AbsDesktopFragment
import ru.nextleap.fox_in_box.screen.IItemsList
import ru.nextleap.sl.PreferencesUtils
import ru.nextleap.sl.action.ApplicationAction
import ru.nextleap.sl.action.DataAction
import ru.nextleap.sl.action.IAction
import ru.nextleap.sl.model.IModel
import ru.nextleap.sl.provider.ApplicationProvider


@Suppress("UNCHECKED_CAST")
class NewsFragment : AbsDesktopFragment("fragment_news",
    R.layout.fragment_news), ChipGroup.OnCheckedChangeListener,
    SwipeRefreshLayout.OnRefreshListener {

    companion object {
        const val NAME = "NewsFragment"
        const val CHIP_POSITION = "$NAME.CHIP_POSITION"

        fun newInstance(): NewsFragment {
            return NewsFragment()
        }
    }

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var chipGroup: ChipGroup
    private lateinit var chip1: Chip
    private lateinit var chip2: Chip
    private lateinit var chip3: Chip
    private lateinit var chip4: Chip
    private var position: Int =
        PreferencesUtils.getInt(ApplicationProvider.appContext, CHIP_POSITION, 1)
    val adapter  = NewsRecyclerViewAdapter()

    override fun createModel(): IModel {
        return NewsModel(this)
    }

    override fun onAction(action: IAction): Boolean {
        if (!isValid()) return false

        if (action is ApplicationAction) {
            when (action.getName()) {
                Actions.ClearItems -> {
                    getModel<NewsModel>().clearItems()
                    return true
                }
            }
        }

        if (action is DataAction<*>) {
            when (action.getName()) {
                Actions.AddAllItems -> {
                    getModel<NewsModel>().addAllItems(action.getData() as ArrayList<News>)
                    return true
                }
                Actions.AddItems -> {
                    getModel<NewsModel>().addItems(action.getData() as ArrayList<News>)
                    return true
                }
            }
        }

        if (super.onAction(action)) return true

        return false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setColorSchemeResources(R.color.blue)
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.pressed)
        swipeRefreshLayout.setOnRefreshListener(this)

        recyclerView = view.findViewById(R.id.list)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = adapter

        chipGroup = view.findViewById(R.id.chipGroup)
        chipGroup.setOnCheckedChangeListener(this)

        chip1 = view.findViewById(R.id.chip1)
        chip2 = view.findViewById(R.id.chip2)
        chip3 = view.findViewById(R.id.chip3)
        chip4 = view.findViewById(R.id.chip4)

        (view.findViewById(R.id.fio) as TextView).text =
            ApplicationSingleton.instance.sessionProvider.getFio()

        setPosition()
    }

    private fun setPosition() {
        when (position) {
            1 -> {
                chip1.isChecked = true
            }
            2 -> {
                chip2.isChecked = true
            }
            3 -> {
                chip3.isChecked = true
            }
            4 -> {
                chip4.isChecked = true
            }
        }
    }

    override fun isTop(): Boolean {
        return false
    }

    override fun getName(): String {
        return NAME
    }

    override fun onDestroyView() {
        actionHandler.hideProgressBar()
        recyclerView.adapter = null
        if (ApplicationProvider.instance.isExit()) {
            position = 1
        }
        PreferencesUtils.putInt(ApplicationProvider.appContext, CHIP_POSITION, position)

        super.onDestroyView()
    }

    override fun onCheckedChanged(group: ChipGroup?, checkedId: Int) {
        when (checkedId) {
            R.id.chip1 -> {
                position = 1
            }
            R.id.chip2 -> {
                position = 2
            }
            R.id.chip3 -> {
                position = 3

            }
            R.id.chip4 -> {
                position = 4
            }
        }
    }

    override fun onRefresh() {
        if (swipeRefreshLayout.isRefreshing) {
            swipeRefreshLayout.isRefreshing = false
        }
        getModel<NewsModel>().getPresenter<NewsPresenter>()
            .addAction(ApplicationAction(Actions.OnSwipeRefresh))
    }
}

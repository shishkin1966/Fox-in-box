package ru.nextleap.fox_in_box.screen.aggregated

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ru.nextleap.common.OnSwipeTouchListener
import ru.nextleap.fox_in_box.R
import ru.nextleap.fox_in_box.action.Actions
import ru.nextleap.fox_in_box.data.Aggregated
import ru.nextleap.fox_in_box.screen.AbsDesktopFragment
import ru.nextleap.sl.action.ApplicationAction
import ru.nextleap.sl.action.DataAction
import ru.nextleap.sl.action.IAction
import ru.nextleap.sl.model.IModel


@Suppress("UNCHECKED_CAST")
class AggregatedFragment : AbsDesktopFragment(
    "fragment_aggregated",
    R.layout.fragment_aggregated
), SwipeRefreshLayout.OnRefreshListener {

    companion object {
        const val NAME = "AggregatedFragment"

        fun newInstance(): AggregatedFragment {
            return AggregatedFragment()
        }
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    val adapter = AggregatedRecyclerViewAdapter()
    private lateinit var error: TextView

    override fun createModel(): IModel {
        return AggregatedModel(this)
    }

    override fun onAction(action: IAction): Boolean {
        if (!isValid()) return false

        if (action is ApplicationAction) {
            when (action.getName()) {
                Actions.ClearItems -> {
                    getModel<AggregatedModel>().clearItems()
                    return true
                }
            }
        }

        if (action is DataAction<*>) {
            when (action.getName()) {
                Actions.AddAllItems -> {
                    getModel<AggregatedModel>().addAllItems(action.getData() as ArrayList<Aggregated>)
                    return true
                }
                Actions.AddItems -> {
                    getModel<AggregatedModel>().addItems(action.getData() as ArrayList<Aggregated>)
                    return true
                }
            }
        }

        if (super.onAction(action)) return true

        return false
    }

    @SuppressLint("ClickableViewAccessibility")
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

        error = view.findViewById(R.id.errorMessage)
        error.setOnTouchListener(object : OnSwipeTouchListener(error.context) {
            override fun onSwipeRight() {
                val animation: Animation =
                    AnimationUtils.loadAnimation(error.context, R.anim.slide)
                error.startAnimation(animation)
            }
        })
    }

    override fun isTop(): Boolean {
        return false
    }

    override fun getName(): String {
        return NAME
    }

    override fun onRefresh() {
        if (swipeRefreshLayout.isRefreshing) {
            swipeRefreshLayout.isRefreshing = false
        }
        getModel<AggregatedModel>().getPresenter<AggregatedPresenter>()
            .addAction(ApplicationAction(Actions.OnSwipeRefresh))
    }

    override fun onDestroyView() {
        actionHandler.hideProgressBar()
        recyclerView.adapter = null

        super.onDestroyView()
    }

}

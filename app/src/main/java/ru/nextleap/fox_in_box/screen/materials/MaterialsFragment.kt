package ru.nextleap.fox_in_box.screen.materials

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ru.nextleap.fox_in_box.R
import ru.nextleap.fox_in_box.action.Actions
import ru.nextleap.fox_in_box.data.Materials
import ru.nextleap.fox_in_box.screen.AbsDesktopFragment
import ru.nextleap.sl.action.ApplicationAction
import ru.nextleap.sl.action.DataAction
import ru.nextleap.sl.action.IAction
import ru.nextleap.sl.model.IModel


@Suppress("UNCHECKED_CAST")
class MaterialsFragment : AbsDesktopFragment(
    "fragment_materials",
    R.layout.fragment_materials
), SwipeRefreshLayout.OnRefreshListener {

    companion object {
        const val NAME = "MaterialsFragment"

        fun newInstance(): MaterialsFragment {
            return MaterialsFragment()
        }
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    val adapter = MaterialsRecyclerViewAdapter()

    override fun createModel(): IModel {
        return MaterialsModel(this)
    }

    override fun onAction(action: IAction): Boolean {
        if (!isValid()) return false

        if (action is ApplicationAction) {
            when (action.getName()) {
                Actions.ClearItems -> {
                    getModel<MaterialsModel>().clearItems()
                    return true
                }
            }
        }

        if (action is DataAction<*>) {
            when (action.getName()) {
                Actions.AddAllItems -> {
                    getModel<MaterialsModel>().addAllItems(action.getData() as ArrayList<Materials>)
                    return true
                }
                Actions.AddItems -> {
                    getModel<MaterialsModel>().addItems(action.getData() as ArrayList<Materials>)
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
    }

    override fun onDestroyView() {
        actionHandler.hideProgressBar()
        recyclerView.adapter = null

        super.onDestroyView()
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
        getModel<MaterialsModel>().getPresenter<MaterialsPresenter>()
            .addAction(ApplicationAction(Actions.OnSwipeRefresh))
    }

}

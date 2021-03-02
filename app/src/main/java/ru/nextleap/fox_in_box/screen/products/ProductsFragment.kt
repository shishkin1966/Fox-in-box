package ru.nextleap.fox_in_box.screen.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ru.nextleap.fox_in_box.ApplicationSingleton
import ru.nextleap.fox_in_box.R
import ru.nextleap.fox_in_box.action.Actions
import ru.nextleap.fox_in_box.data.SKU
import ru.nextleap.fox_in_box.screen.IItemsList
import ru.nextleap.fox_in_box.screen.home.HomePresenter
import ru.nextleap.sl.action.ApplicationAction
import ru.nextleap.sl.action.DataAction
import ru.nextleap.sl.action.IAction
import ru.nextleap.sl.model.IModel
import ru.nextleap.sl.ui.AbsContentFragment


@Suppress("UNCHECKED_CAST")
class ProductsFragment : AbsContentFragment(), SwipeRefreshLayout.OnRefreshListener,
    IItemsList<SKU> {

    companion object {
        const val NAME = "ProductsFragment"

        fun newInstance(): ProductsFragment {
            return ProductsFragment()
        }
    }

    private lateinit var back: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private val adapter: SKURecyclerViewAdapter = SKURecyclerViewAdapter()

    override fun createModel(): IModel {
        return ProductsModel(this)
    }

    override fun onAction(action: IAction): Boolean {
        if (!isValid()) return false

        if (action is ApplicationAction) {
            when (action.getName()) {
                Actions.ClearItems -> {
                    clearItems()
                    return true
                }
            }
        }

        if (action is DataAction<*>) {
            when (action.getName()) {
                Actions.AddAllItems -> {
                    addAllItems(action.getData() as ArrayList<SKU>)
                    return true
                }
                Actions.AddItems -> {
                    addItems(action.getData() as ArrayList<SKU>)
                    return true
                }
            }
        }

        if (super.onAction(action)) return true

        return false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            ApplicationSingleton.instance.desktopProvider.getLayoutId(
                "fragment_products",
                R.layout.fragment_products
            ), container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        back = view.findViewById(R.id.back)
        back.setOnClickListener(this::onClick)

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setColorSchemeResources(R.color.blue)
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.pressed)
        swipeRefreshLayout.setOnRefreshListener(this)

        recyclerView = view.findViewById(R.id.list)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = adapter

    }

    private fun onClick(v: View?) {
        when (v?.id) {
            R.id.back -> {
                val presenter =
                    ApplicationSingleton.instance.getPresenter<HomePresenter>(HomePresenter.NAME)
                val router = ApplicationSingleton.instance.routerProvider
                router.switchToTopFragment()
                presenter?.addAction(ApplicationAction(HomePresenter.ShowNews))
            }
        }
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
        getModel<ProductsModel>().getPresenter<ProductsPresenter>()
            .addAction(ApplicationAction(Actions.OnSwipeRefresh))
    }

    override fun addItems(data: ArrayList<SKU>) {
        adapter.addAll(data)
    }

    override fun addAllItems(data: ArrayList<SKU>) {
        adapter.setItems(data)
    }

    override fun clearItems() {
        adapter.clear()
    }

    fun dataChanged() {
        adapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        actionHandler.hideProgressBar()
        recyclerView.adapter = null

        super.onDestroyView()
    }

}

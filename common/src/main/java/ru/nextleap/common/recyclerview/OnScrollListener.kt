package ru.nextleap.common.recyclerview

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OnScrollListener(private val recyclerViewScrollListener: IRecyclerViewScrollListener) :
    RecyclerView.OnScrollListener() {

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            recyclerViewScrollListener.idle(recyclerView)
        } else {
            if (recyclerView.adapter != null) {
                if ((recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition() != recyclerView.adapter!!.itemCount - 1) {
                    recyclerViewScrollListener.scrolled(recyclerView)
                }
            }
        }
    }
}

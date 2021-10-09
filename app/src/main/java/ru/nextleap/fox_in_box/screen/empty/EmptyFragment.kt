package ru.nextleap.fox_in_box.screen.empty

import ru.nextleap.fox_in_box.R
import ru.nextleap.fox_in_box.screen.AbsDesktopFragment
import ru.nextleap.sl.model.IModel


class EmptyFragment : AbsDesktopFragment(
    "fragment_empty",
    R.layout.fragment_empty
) {

    companion object {
        const val NAME = "EmptyFragment"

        fun newInstance(): EmptyFragment {
            return EmptyFragment()
        }
    }

    override fun createModel(): IModel {
        return EmptyModel(this)
    }

    override fun isTop(): Boolean {
        return false
    }

    override fun getName(): String {
        return NAME
    }

}

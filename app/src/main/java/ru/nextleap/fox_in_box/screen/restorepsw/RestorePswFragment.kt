package ru.nextleap.fox_in_box.screen.restorepsw

import ru.nextleap.fox_in_box.R
import ru.nextleap.fox_in_box.screen.AbsDesktopFragment
import ru.nextleap.sl.model.IModel


class RestorePswFragment : AbsDesktopFragment(
    "fragment_restorepsw",
    R.layout.fragment_restorepsw
) {

    companion object {
        const val NAME = "RestorePswFragment"

        fun newInstance(): RestorePswFragment {
            return RestorePswFragment()
        }
    }

    override fun createModel(): IModel {
        return RestorePswModel(this)
    }

    override fun isTop(): Boolean {
        return true
    }

    override fun getName(): String {
        return NAME
    }

}

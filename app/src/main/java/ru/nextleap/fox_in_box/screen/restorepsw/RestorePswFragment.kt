package ru.nextleap.fox_in_box.screen.restorepsw

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.nextleap.fox_in_box.ApplicationSingleton
import ru.nextleap.fox_in_box.R
import ru.nextleap.sl.model.IModel
import ru.nextleap.sl.ui.AbsContentFragment


class RestorePswFragment : AbsContentFragment() {

    companion object {
        const val NAME = "RestorePswFragment"

        fun newInstance(): RestorePswFragment {
            return RestorePswFragment()
        }
    }

    override fun createModel(): IModel {
        return RestorePswModel(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            ApplicationSingleton.instance.desktopProvider.getLayoutId(
                "fragment_restorepsw",
                R.layout.fragment_restorepsw
            ), container, false
        )
    }

    override fun isTop(): Boolean {
        return true
    }

    override fun getName(): String {
        return NAME
    }

}

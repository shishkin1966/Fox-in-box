package ru.nextleap.fox_in_box.screen.authorization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.nextleap.fox_in_box.ApplicationSingleton
import ru.nextleap.fox_in_box.R
import ru.nextleap.sl.model.IModel
import ru.nextleap.sl.ui.AbsContentFragment


class AuthorizationFragment : AbsContentFragment() {

    companion object {
        const val NAME = "AuthorizationFragment"

        fun newInstance(): AuthorizationFragment {
            return AuthorizationFragment()
        }
    }

    override fun createModel(): IModel {
        return AuthorizationModel(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            ApplicationSingleton.instance.desktopProvider.getLayoutId(
                "fragment_authorization",
                R.layout.fragment_authorization
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

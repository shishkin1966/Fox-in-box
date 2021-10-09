package ru.nextleap.fox_in_box.screen.authorization

import ru.nextleap.fox_in_box.R
import ru.nextleap.fox_in_box.screen.AbsDesktopFragment
import ru.nextleap.sl.model.IModel


class AuthorizationFragment : AbsDesktopFragment(
    "fragment_authorization",
    R.layout.fragment_authorization
) {

    companion object {
        const val NAME = "AuthorizationFragment"

        fun newInstance(): AuthorizationFragment {
            return AuthorizationFragment()
        }
    }

    override fun createModel(): IModel {
        return AuthorizationModel(this)
    }

    override fun isTop(): Boolean {
        return true
    }

    override fun getName(): String {
        return NAME
    }
}

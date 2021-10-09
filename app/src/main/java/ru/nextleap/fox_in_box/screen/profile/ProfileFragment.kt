package ru.nextleap.fox_in_box.screen.profile

import ru.nextleap.fox_in_box.R
import ru.nextleap.fox_in_box.action.Actions
import ru.nextleap.fox_in_box.data.Profile
import ru.nextleap.fox_in_box.screen.AbsDesktopFragment
import ru.nextleap.sl.action.DataAction
import ru.nextleap.sl.action.IAction
import ru.nextleap.sl.model.IModel


class ProfileFragment : AbsDesktopFragment(
    "fragment_profile",
    R.layout.fragment_profile
) {

    companion object {
        const val NAME = "ProfileFragment"

        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }

    override fun createModel(): IModel {
        return ProfileModel(this)
    }

    override fun onAction(action: IAction): Boolean {
        if (!isValid()) return false

        if (action is DataAction<*>) {
            when (action.getName()) {
                Actions.SetItem -> {
                    setProfile(action.getData() as Profile)
                    return true
                }
            }
        }

        if (super.onAction(action)) return true

        return false
    }

    override fun isTop(): Boolean {
        return false
    }

    override fun getName(): String {
        return NAME
    }

    private fun setProfile(profile: Profile) {
    }

}

package ru.nextleap.fox_in_box.screen.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.nextleap.fox_in_box.ApplicationSingleton
import ru.nextleap.fox_in_box.R
import ru.nextleap.fox_in_box.action.Actions
import ru.nextleap.fox_in_box.data.Profile
import ru.nextleap.sl.action.DataAction
import ru.nextleap.sl.action.IAction
import ru.nextleap.sl.action.handler.FragmentActionHandler
import ru.nextleap.sl.model.IModel
import ru.nextleap.sl.ui.AbsContentFragment


class ProfileFragment : AbsContentFragment() {

    companion object {
        const val NAME = "ProfileFragment"

        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }

    private val actionHandler = FragmentActionHandler(this)

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

        if (actionHandler.onAction(action)) return true

        return false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            ApplicationSingleton.instance.desktopProvider.getLayoutId(
                "fragment_profile",
                R.layout.fragment_profile
            ), container, false
        )
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

package ru.nextleap.fox_in_box.screen.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ru.nextleap.common.ApplicationUtils
import ru.nextleap.fox_in_box.ApplicationSingleton
import ru.nextleap.fox_in_box.R
import ru.nextleap.fox_in_box.screen.AbsDesktopFragment
import ru.nextleap.sl.model.IModel
import ru.nextleap.sl.provider.ApplicationProvider
import ru.nextleap.sl.ui.AbsContentFragment


class RegistrationFragment : AbsDesktopFragment("fragment_registration",
    R.layout.fragment_registration) {

    companion object {
        const val NAME = "RegistrationFragment"

        fun newInstance(): RegistrationFragment {
            return RegistrationFragment()
        }
    }

    private lateinit var next: TextView

    override fun createModel(): IModel {
        return RegistrationModel(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        next = view.findViewById(R.id.next)
        next.setOnClickListener(this::onClick)
    }

    override fun isTop(): Boolean {
        return true
    }

    override fun getName(): String {
        return NAME
    }

    private fun onClick(v: View?) {
        ApplicationUtils.showToast(ApplicationProvider.appContext, "onClick")
    }

}

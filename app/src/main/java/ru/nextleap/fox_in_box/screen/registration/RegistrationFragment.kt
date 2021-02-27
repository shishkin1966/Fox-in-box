package ru.nextleap.fox_in_box.screen.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ru.nextleap.common.ApplicationUtils
import ru.nextleap.fox_in_box.ApplicationSingleton
import ru.nextleap.fox_in_box.R
import ru.nextleap.sl.action.IAction
import ru.nextleap.sl.action.handler.FragmentActionHandler
import ru.nextleap.sl.model.IModel
import ru.nextleap.sl.provider.ApplicationProvider
import ru.nextleap.sl.ui.AbsContentFragment


class RegistrationFragment : AbsContentFragment() {

    companion object {
        const val NAME = "RegistrationFragment"

        fun newInstance(): RegistrationFragment {
            return RegistrationFragment()
        }
    }

    private val actionHandler = FragmentActionHandler(this)
    private lateinit var next: TextView

    override fun createModel(): IModel {
        return RegistrationModel(this)
    }

    override fun onAction(action: IAction): Boolean {
        if (!isValid()) return false

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
                "fragment_registration",
                R.layout.fragment_registration
            ), container, false
        )
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

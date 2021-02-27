package ru.nextleap.fox_in_box.screen.terms_promotions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ru.nextleap.fox_in_box.ApplicationSingleton
import ru.nextleap.fox_in_box.R
import ru.nextleap.fox_in_box.screen.home.HomePresenter
import ru.nextleap.sl.action.ApplicationAction
import ru.nextleap.sl.action.IAction
import ru.nextleap.sl.action.handler.FragmentActionHandler
import ru.nextleap.sl.model.IModel
import ru.nextleap.sl.ui.AbsContentFragment


class TermsPromotionsFragment : AbsContentFragment() {

    companion object {
        const val NAME = "TermsPromotionsFragment"

        fun newInstance(): TermsPromotionsFragment {
            return TermsPromotionsFragment()
        }
    }

    private val actionHandler = FragmentActionHandler(this)
    private lateinit var back: TextView

    override fun createModel(): IModel {
        return TermsPromotionsModel(this)
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
                "fragment_term_promotions",
                R.layout.fragment_term_promotions
            ), container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        back = view.findViewById(R.id.back)
        back.setOnClickListener(this::onClick)

    }

    private fun onClick(v: View?) {
        when (v?.id) {
            R.id.back -> {
                val presenter =
                    ApplicationSingleton.instance.getPresenter<HomePresenter>(HomePresenter.NAME)
                val router = ApplicationSingleton.instance.routerProvider
                router.switchToTopFragment()
                presenter?.addAction(ApplicationAction(HomePresenter.ShowNews))
            }
        }
    }

    override fun isTop(): Boolean {
        return false
    }

    override fun getName(): String {
        return NAME
    }

}

package ru.nextleap.fox_in_box.screen.terms_promotions

import android.os.Bundle
import android.view.View
import android.widget.TextView
import ru.nextleap.fox_in_box.ApplicationSingleton
import ru.nextleap.fox_in_box.R
import ru.nextleap.fox_in_box.screen.AbsDesktopFragment
import ru.nextleap.fox_in_box.screen.home.HomePresenter
import ru.nextleap.sl.action.ApplicationAction
import ru.nextleap.sl.model.IModel


class TermsPromotionsFragment : AbsDesktopFragment(
    "fragment_term_promotions",
    R.layout.fragment_term_promotions
) {

    companion object {
        const val NAME = "TermsPromotionsFragment"

        fun newInstance(): TermsPromotionsFragment {
            return TermsPromotionsFragment()
        }
    }

    private lateinit var back: TextView

    override fun createModel(): IModel {
        return TermsPromotionsModel(this)
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

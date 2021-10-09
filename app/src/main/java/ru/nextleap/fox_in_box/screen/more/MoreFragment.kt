package ru.nextleap.fox_in_box.screen.more

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import ru.nextleap.common.formatDateShortRu
import ru.nextleap.fox_in_box.ApplicationSingleton
import ru.nextleap.fox_in_box.BuildConfig
import ru.nextleap.fox_in_box.R
import ru.nextleap.fox_in_box.screen.AbsDesktopFragment
import ru.nextleap.fox_in_box.screen.main.MainPresenter
import ru.nextleap.sl.action.ApplicationAction
import ru.nextleap.sl.action.IAction
import ru.nextleap.sl.model.IModel
import ru.nextleap.sl.provider.ApplicationProvider


class MoreFragment : AbsDesktopFragment(
    "fragment_more",
    R.layout.fragment_more
) {

    companion object {
        const val NAME = "MoreFragment"

        fun newInstance(): MoreFragment {
            return MoreFragment()
        }
    }

    private lateinit var menuTermsPromotions: TextView
    private lateinit var menuProducts: TextView
    private lateinit var menuOrderingPrizes: TextView
    private lateinit var menuRatingParticipants: TextView
    private lateinit var menuReports: TextView
    private lateinit var menuAnalytics: TextView
    private lateinit var menuFaq: TextView
    private lateinit var version: TextView

    override fun createModel(): IModel {
        return MoreModel(this)
    }

    override fun onAction(action: IAction): Boolean {
        if (!isValid()) return false

        if (actionHandler.onAction(action)) return true

        return false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        menuTermsPromotions = view.findViewById(R.id.menu_terms_promotions)
        menuTermsPromotions.setOnClickListener(this::onClick)

        menuProducts = view.findViewById(R.id.menu_products)
        menuProducts.setOnClickListener(this::onClick)

        menuOrderingPrizes = view.findViewById(R.id.menu_ordering_prizes)
        menuOrderingPrizes.setOnClickListener(this::onClick)

        menuRatingParticipants = view.findViewById(R.id.menu_rating_participants)
        menuRatingParticipants.setOnClickListener(this::onClick)

        menuReports = view.findViewById(R.id.menu_reports)
        menuReports.setOnClickListener(this::onClick)

        menuAnalytics = view.findViewById(R.id.menu_analytics)
        menuAnalytics.setOnClickListener(this::onClick)

        menuFaq = view.findViewById(R.id.menu_faq)
        menuFaq.setOnClickListener(this::onClick)

        version = view.findViewById(R.id.version)
        var date = ""
        try {
            val packageInfo: PackageInfo =
                ApplicationProvider.appContext.packageManager.getPackageInfo(
                    ApplicationProvider.appContext.packageName,
                    0
                )
            date = packageInfo.lastUpdateTime.formatDateShortRu()
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        version.text =
            getString(R.string.version) + " " + BuildConfig.VERSION_NAME + " " + getString(
                R.string.from
            ) + " " + date
    }

    private fun onClick(v: View?) {
        val presenter =
            ApplicationSingleton.instance.getPresenter<MainPresenter>(MainPresenter.NAME)
        when (v?.id) {
            R.id.menu_terms_promotions -> {
                presenter?.addAction(ApplicationAction(MainPresenter.ShowTermsPromotionsFragment))
            }
            R.id.menu_products -> {
                presenter?.addAction(ApplicationAction(MainPresenter.ShowProductsFragment))
            }
            R.id.menu_ordering_prizes -> {
                presenter?.addAction(ApplicationAction(MainPresenter.ShowOrdersFragment))
            }
            R.id.menu_rating_participants -> {
            }
            R.id.menu_reports -> {
            }
            R.id.menu_analytics -> {
            }
            R.id.menu_faq -> {
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

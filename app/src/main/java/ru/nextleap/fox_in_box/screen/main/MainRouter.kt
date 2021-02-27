package ru.nextleap.fox_in_box.screen.main

import ru.nextleap.fox_in_box.R
import ru.nextleap.fox_in_box.data.News
import ru.nextleap.fox_in_box.screen.authorization.AuthorizationFragment
import ru.nextleap.fox_in_box.screen.home.HomeFragment
import ru.nextleap.fox_in_box.screen.orders.OrdersFragment
import ru.nextleap.fox_in_box.screen.products.ProductsFragment
import ru.nextleap.fox_in_box.screen.registration.RegistrationFragment
import ru.nextleap.fox_in_box.screen.restorepsw.RestorePswFragment
import ru.nextleap.fox_in_box.screen.terms_promotions.TermsPromotionsFragment
import ru.nextleap.fox_in_box.screen.view_news.ViewNewsFragment
import ru.nextleap.sl.provider.IRouterProvider

class MainRouter(private val activity: IRouterProvider) {
    fun showAuthorizationFragment() {
        if (activity.isValid()) {
            activity.clearBackStack()
            activity.showFragment(AuthorizationFragment.newInstance(), true)
        }
    }

    fun showRegistrationFragment() {
        if (activity.isValid()) {
            activity.clearBackStack()
            activity.showFragment(RegistrationFragment.newInstance(), true)
        }
    }

    fun showRestorePswFragment() {
        if (activity.isValid()) {
            activity.clearBackStack()
            activity.showFragment(RestorePswFragment.newInstance(), true)
        }
    }

    fun showTermsPromotionsFragment() {
        if (activity.isValid()) {
            activity.showFragment(TermsPromotionsFragment.newInstance(), true)
        }
    }

    fun showViewNewsFragment(news: News) {
        if (activity.isValid()) {
            activity.showFragment(ViewNewsFragment.newInstance(news), true, R.anim.slide_up)
        }
    }

    fun showHomeFragment() {
        if (activity.isValid()) {
            activity.clearBackStack()
            activity.showFragment(HomeFragment.newInstance(), true)
        }
    }

    fun showProductsFragment() {
        if (activity.isValid()) {
            activity.showFragment(ProductsFragment.newInstance(), true)
        }
    }

    fun showOrdersFragment() {
        if (activity.isValid()) {
            activity.showFragment(OrdersFragment.newInstance(), true)
        }
    }
}
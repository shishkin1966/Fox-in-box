package ru.nextleap.fox_in_box.provider

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import ru.nextleap.sl.AbsProvider
import ru.nextleap.sl.IProvider
import ru.nextleap.sl.provider.ApplicationProvider

class AnalyticsProvider : AbsProvider() , IAnalyticsProvider {
    companion object {
        const val NAME = "AnalyticsProvider"
    }

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun getName(): String {
        return NAME
    }

    override fun compareTo(other: IProvider): Int {
        return if (other is IAnalyticsProvider) 0 else 1
    }

    override fun onRegister() {
        firebaseAnalytics = FirebaseAnalytics.getInstance(ApplicationProvider.appContext)
    }

    override fun eventContent(id: String, name: String) {
        if (::firebaseAnalytics.isInitialized) {
            val bundle = Bundle()
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id)
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name)
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
        }
    }

    override fun eventLogin() {
        if (::firebaseAnalytics.isInitialized) {
            val bundle = Bundle()
            bundle.putString(FirebaseAnalytics.Param.METHOD, "mobile")
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle)
        }
    }

    override fun eventScreenView(name: String) {
        if (::firebaseAnalytics.isInitialized) {
            val bundle = Bundle()
            bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, name)
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
        }
    }

    override fun event(id: String, key: String, value: String) {
        if (::firebaseAnalytics.isInitialized) {
            val bundle = Bundle()
            bundle.putString(key, value)
            firebaseAnalytics.logEvent(id, bundle)
        }
    }

}
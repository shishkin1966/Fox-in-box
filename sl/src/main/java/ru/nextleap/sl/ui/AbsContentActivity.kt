package ru.nextleap.sl.ui

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import ru.nextleap.common.ApplicationUtils
import ru.nextleap.sl.R
import ru.nextleap.sl.action.HideKeyboardAction
import ru.nextleap.sl.action.IAction
import ru.nextleap.sl.action.handler.ActivityActionHandler
import ru.nextleap.sl.observe.NetObservable
import ru.nextleap.sl.provider.IObservableSubscriber
import ru.nextleap.sl.provider.IRouterProvider
import ru.nextleap.sl.provider.ObservableUnion

abstract class AbsContentActivity : AbsModelActivity(), IRouterProvider,
    IObservableSubscriber {

    val actionHandler = ActivityActionHandler(this)

    private var snackbar: Snackbar? = null

    override fun onAction(action: IAction): Boolean {
        if (!isValid()) return false

        if (actionHandler.onAction(action)) return true

        return false
    }

    override fun onPause() {
        super.onPause()

        addAction(HideKeyboardAction())
    }

    override fun showFragment(fragment: Fragment) {
        showFragment(
            fragment,
            addToBackStack = true,
            clearBackStack = false,
            animate = true,
            allowingStateLoss = false,
            transition = null
        )
    }

    override fun showFragment(fragment: Fragment, allowingStateLoss: Boolean) {
        showFragment(
            fragment,
            addToBackStack = true,
            clearBackStack = false,
            animate = true,
            allowingStateLoss = allowingStateLoss,
            transition = null
        )
    }

    override fun showFragment(fragment: Fragment, allowingStateLoss: Boolean, transition: Int?) {
        showFragment(
            fragment,
            addToBackStack = true,
            clearBackStack = false,
            animate = true,
            allowingStateLoss = allowingStateLoss,
            transition = transition
        )
    }

    override fun showFragment(
        fragment: Fragment, addToBackStack: Boolean,
        clearBackStack: Boolean,
        animate: Boolean, allowingStateLoss: Boolean,
        transition: Int?
    ) {

        BackStack.showFragment(
            this,
            getContentResId(),
            fragment,
            addToBackStack,
            clearBackStack,
            animate,
            allowingStateLoss,
            transition
        )
    }

    override fun switchToFragment(name: String): Boolean {
        return BackStack.switchToFragment(this, name)
    }

    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate. Notice that you should add any [Fragment] that implements
     * [OnBackPressListener] to the back stack if you want [OnBackPressListener.onBackPressed]
     * to be invoked.
     */
    override fun onBackPressed() {
        BackStack.onBackPressed(this)
    }

    override fun switchToTopFragment() {
        BackStack.switchToTopFragment(this)
    }

    override fun hasTopFragment(): Boolean {
        return BackStack.hasTopFragment(this)
    }

    override fun isBackStackEmpty(): Boolean {
        return BackStack.isBackStackEmpty(this)
    }

    override fun getBackStackEntryCount(): Int {
        return BackStack.getBackStackEntryCount(this)
    }

    override fun <F> getContentFragment(cls: Class<F>): F? {
        return getFragment(cls, getContentResId())
    }

    override fun <F> getFragment(cls: Class<F>, id: Int): F? {
        return BackStack.getFragment(this, cls, id)
    }

    override fun onPermissionGranted(permission: String) {
        super.onPermissionGranted(permission)

        val fragment = getContentFragment(AbsFragment::class.java)
        fragment?.onPermissionGranted(permission)
    }

    override fun onPermissionDenied(permission: String) {
        super.onPermissionDenied(permission)

        val fragment = getContentFragment(AbsFragment::class.java)
        fragment?.onPermissionDenied(permission)
    }

    override fun getProviderSubscription(): List<String> {
        val list = ArrayList<String>()
        list.addAll(super.getProviderSubscription())
        list.add(ObservableUnion.NAME)
        return list
    }

    override fun getObservable(): List<String> {
        return listOf(NetObservable.NAME)
    }

    override fun onChange(name: String, obj: Any) {
        if (name == NetObservable.NAME) {
            if (obj == true) {
                onNetworkConnected()
            } else if (obj == false) {
                onNetworkDisConnected()
            }
        }
    }

    open fun onNetworkConnected() {
        if (snackbar?.isShown == true) {
            snackbar?.dismiss()
            snackbar = null
        }
    }

    open fun onNetworkDisConnected() {
        if (snackbar != null) {
            snackbar?.dismiss()
            snackbar = null
        }
        snackbar = ApplicationUtils.showSnackbar(
            getRootView(),
            getString(R.string.network_disconnected),
            Snackbar.LENGTH_INDEFINITE,
            ApplicationUtils.MESSAGE_TYPE_WARNING
        )
    }

    override fun getContentResId(): Int {
        return R.id.content
    }

    override fun isCurrentFragment(name: String): Boolean {
        return BackStack.isCurrentFragment(this, name)
    }

    override fun clearBackStack() {
        BackStack.clearBackStack(this)
    }

}

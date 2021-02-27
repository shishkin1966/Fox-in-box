package ru.nextleap.sl.provider

import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import ru.nextleap.common.ApplicationUtils
import ru.nextleap.sl.AbsUnion
import ru.nextleap.sl.IProvider
import ru.nextleap.sl.R
import ru.nextleap.sl.action.ShowDialogAction
import ru.nextleap.sl.lifecycle.Lifecycle
import ru.nextleap.sl.ui.AbsActivity
import ru.nextleap.sl.ui.AbsContentActivity
import ru.nextleap.sl.ui.BackStack
import ru.nextleap.sl.ui.IActivity
import java.lang.ref.WeakReference
import java.util.*
import kotlin.collections.ArrayList

@Suppress("UNCHECKED_CAST")
class ActivityUnion : AbsUnion<IActivity>(), IActivityUnion {
    companion object {
        const val NAME = "ActivityUnion"
    }

    private val activities = Collections.synchronizedList(ArrayList<WeakReference<IActivity>>())

    override fun register(subscriber: IActivity): Boolean {
        if (super.register(subscriber)) {
            for (i in activities.size - 1 downTo 0) {
                if (activities[i] == null) {
                    activities.removeAt(i)
                    continue
                }
                if (activities[i].get() == null) {
                    activities.removeAt(i)
                    continue
                }

                if (activities[i].get()!!.getName() == subscriber.getName()) {
                    if (activities[i].get()!! != subscriber) {
                        activities[i].get()!!.stop()
                    }
                    activities.removeAt(i)
                }
            }

            activities.add(WeakReference(subscriber))
            return true
        }
        return false
    }

    override fun unregister(subscriber: IActivity) {
        super.unregister(subscriber)

        for (i in activities.size - 1 downTo 0) {
            if (activities[i] == null) {
                activities.removeAt(i)
                continue
            }
            if (activities[i].get() == null) {
                activities.removeAt(i)
                continue
            }

            if (activities[i].get()!! == subscriber) {
                activities.removeAt(i)
            }
        }
    }

    override fun getName(): String {
        return NAME
    }

    override fun stop() {
        for (ref in activities) {
            if (ref?.get() != null) {
                ref.get()!!.stop()
            }
        }
    }

    override fun onUnRegisterLastSubscriber() {
        //if (ApplicationProvider.instance.isExit()) {
        //    android.os.Process.killProcess(android.os.Process.myPid())
        //}
    }

    override fun <F> getFragment(cls: Class<F>, id: Int): F? {
        val activity = getActivity<AppCompatActivity>()
        return if (activity != null) {
            BackStack.getFragment(activity, cls, id)
        } else null
    }

    override fun <F> getCurrentFragment(): F? {
        val activity = getActivity<AppCompatActivity>()
        return if (activity != null) {
            BackStack.getCurrentFragment(activity)
        } else null
    }

    override fun isBackStackEmpty(): Boolean {
        val activity = getActivity<AppCompatActivity>()
        return if (activity != null) {
            BackStack.isBackStackEmpty(activity)
        } else true
    }

    override fun getBackStackEntryCount(): Int {
        val activity = getActivity<AppCompatActivity>()
        if (activity != null) {
            BackStack.getBackStackEntryCount(activity)
        }
        return 0
    }

    override fun switchToTopFragment() {
        val activity = getActivity<AppCompatActivity>()
        if (activity != null) {
            BackStack.switchToTopFragment(activity)
        }
    }

    override fun hasTop(): Boolean {
        val activity = getActivity<AppCompatActivity>()
        return if (activity != null) {
            BackStack.hasTopFragment(activity)
        } else false
    }

    override fun <C> getActivity(): C? {
        var subscriber: IActivity? = getCurrentSubscriber()
        if (subscriber != null) {
            return subscriber as C?
        }
        if (getSubscribers().size == 1) {
            subscriber = getSubscribers()[0]
            return subscriber as C?
        }
        return null
    }

    override fun <C> getActivity(name: String): C? {
        return getActivity<C>(name, false)
    }

    override fun <C> getActivity(name: String, validate: Boolean): C? {
        val subscriber = getSubscriber(name)
        if (subscriber != null) {
            if (!validate || validate && subscriber.isValid()) {
                return subscriber as C?
            }
        }
        return null
    }

    override fun switchToFragment(name: String) {
        val subscriber = getCurrentSubscriber()
        if (subscriber is AbsContentActivity) {
            subscriber.switchToFragment(name)
        }
    }

    override fun back() {
        val subscriber = getCurrentSubscriber()
        if (subscriber != null) {
            if (subscriber is AbsActivity) {
                subscriber.onBackPressed()
            }
        }
    }

    override fun grantPermission(listener: String, permission: String, helpMessage: String) {
        if (ApplicationUtils.hasMarshmallow()) {
            val subscriber = getCurrentSubscriber()
            if (subscriber != null && subscriber.isValid() && subscriber.getState() == Lifecycle.VIEW_READY) {
                val activity = subscriber as AppCompatActivity
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        activity,
                        permission
                    )
                ) {
                    subscriber.addAction(
                        ShowDialogAction(
                            R.id.dialog_request_permissions,
                            listener,
                            null,
                            helpMessage
                        ).setPositiveButton(R.string.setting).setNegativeButton(R.string.cancel)
                            .setCancelable(
                                false
                            )
                    )
                } else {
                    ActivityCompat.requestPermissions(
                        activity,
                        arrayOf(permission),
                        ApplicationUtils.REQUEST_PERMISSIONS
                    )
                }
            }
        }
    }

    override fun grantPermission(permission: String) {
        if (ApplicationUtils.hasMarshmallow()) {
            val subscriber = getCurrentSubscriber()
            if (subscriber != null && subscriber.isValid() && subscriber.getState() == Lifecycle.VIEW_READY) {
                val activity = subscriber as AppCompatActivity
                if (!ActivityCompat.shouldShowRequestPermissionRationale(
                        activity,
                        permission
                    )
                ) {
                    ActivityCompat.requestPermissions(
                        activity,
                        arrayOf(permission),
                        ApplicationUtils.REQUEST_PERMISSIONS
                    )
                }
            }
        }
    }

    override operator fun compareTo(other: IProvider): Int {
        return if (other is IActivityUnion) 0 else 1
    }

    override fun getRouter(): IRouterProvider? {
        val subscriber = getCurrentSubscriber()
        if (subscriber is IRouterProvider) {
            return subscriber
        }
        for (activity in getSubscribers()) {
            if (activity is IRouterProvider) {
                return activity
            }
        }
        return null
    }

}

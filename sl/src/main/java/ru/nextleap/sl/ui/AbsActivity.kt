package ru.nextleap.sl.ui

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import ru.nextleap.common.ApplicationUtils
import ru.nextleap.sl.IProvider
import ru.nextleap.sl.R
import ru.nextleap.sl.action.IAction
import ru.nextleap.sl.lifecycle.ILifecycle
import ru.nextleap.sl.lifecycle.Lifecycle
import ru.nextleap.sl.lifecycle.LifecycleObservable
import ru.nextleap.sl.provider.ActivityUnion
import ru.nextleap.sl.provider.ApplicationProvider
import java.util.*
import kotlin.collections.ArrayList


abstract class AbsActivity : AppCompatActivity(), IActivity {

    private val lifecycleObservable = LifecycleObservable(Lifecycle.VIEW_CREATE)
    private val actions = LinkedList<IAction>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        lifecycleObservable.setState(Lifecycle.VIEW_CREATE)
    }

    override fun <V : View> findView(@IdRes id: Int): V? {
        return ApplicationUtils.findView(this, id)
    }

    override fun onStart() {
        super.onStart()

        doActions()

        ApplicationProvider.serviceLocator?.registerSubscriber(this)

        lifecycleObservable.setState(Lifecycle.VIEW_READY)
    }

    override fun onDestroy() {
        super.onDestroy()

        lifecycleObservable.setState(Lifecycle.VIEW_DESTROY)

        ApplicationProvider.serviceLocator?.unregisterSubscriber(this)
    }

    override fun onResume() {
        super.onResume()

        ApplicationProvider.serviceLocator?.setCurrentSubscriber(this)
    }

    override fun getProviderSubscription(): List<String> {
        return listOf(ActivityUnion.NAME)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        for (i in permissions.indices) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                onPermissionGranted(permissions[i])
            } else if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                onPermissionDenied(permissions[i])
            }
        }
    }

    override fun getState(): Int {
        return lifecycleObservable.getState()
    }

    override fun setState(state: Int) = Unit

    override fun isValid(): Boolean {
        return getState() != Lifecycle.VIEW_DESTROY && !isFinishing
    }

    override fun stop() {
        ActivityCompat.finishAfterTransition(this)
    }

    override fun onPermissionGranted(permission: String) = Unit

    override fun onPermissionDenied(permission: String) = Unit

    override fun getRootView(): View {
        val view = findView<View>(R.id.root)
        return if (view != null) {
            return view
        } else (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0)
    }

    /**
     * Called when the activity has detected the user's press of the back
     * key. The default implementation simply finishes the current activity,
     * but you can override this to do whatever you want.
     */
    fun onActivityBackPressed() {
        super.onBackPressed()
    }

    override fun addLifecycleObserver(stateable: ILifecycle) {
        lifecycleObservable.addLifecycleObserver(stateable)
    }

    override fun removeLifecycleObserver(stateable: ILifecycle) {
        lifecycleObservable.removeLifecycleObserver(stateable)
    }

    override fun clearLifecycleObservers() {
        lifecycleObservable.clearLifecycleObservers()
    }

    override fun addAction(action: IAction) {
        when (getState()) {

            Lifecycle.VIEW_READY -> {
                if (!action.isRun()) {
                    actions.add(action)
                }
                doActions()
            }

            Lifecycle.VIEW_CREATE, Lifecycle.VIEW_NOT_READY -> {
                if (!action.isRun()) {
                    actions.add(action)
                }
            }
        }
    }

    private fun doActions() {
        val deleted = ArrayList<IAction>()
        for (i in actions.indices) {
            if (getState() != Lifecycle.VIEW_READY) {
                break
            }
            if (!actions[i].isRun()) {
                actions[i].setRun()
                onAction(actions[i])
                deleted.add(actions[i])
            }
        }
        for (action in deleted) {
            actions.remove(action)
        }
    }

    override fun onStopProvider(provider: IProvider) = Unit

    override fun getName(): String {
        return this::class.java.simpleName
    }

    override fun lockOrientation(orientation: Int) {
        requestedOrientation = orientation
    }


}

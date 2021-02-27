package ru.nextleap.sl.presenter

import ru.nextleap.sl.IProvider
import ru.nextleap.sl.action.IAction
import ru.nextleap.sl.lifecycle.Lifecycle
import ru.nextleap.sl.lifecycle.LifecycleObserver
import ru.nextleap.sl.message.IMessage
import ru.nextleap.sl.provider.ApplicationProvider
import ru.nextleap.sl.provider.IMessengerUnion
import ru.nextleap.sl.provider.MessengerUnion
import ru.nextleap.sl.provider.PresenterUnion
import java.util.*


abstract class AbsPresenter() : IPresenter {
    private val lifecycle = LifecycleObserver(this)
    private val actions = LinkedList<IAction>()

    override fun getState(): Int {
        return lifecycle.getState()
    }

    override fun setState(state: Int) {
        lifecycle.setState(state)
    }

    override fun onCreateView() = Unit

    override fun onReadyView() {
        ApplicationProvider.serviceLocator?.registerSubscriber(this)

        doActions()

        ApplicationProvider.serviceLocator?.get<IMessengerUnion>(
            MessengerUnion.NAME
        )
            ?.readMessages(this)

        onStart()
    }

    open fun onStart() = Unit

    override fun onDestroyView() {
        ApplicationProvider.serviceLocator?.unregisterSubscriber(this)
    }

    override fun isValid(): Boolean {
        return lifecycle.getState() != Lifecycle.VIEW_DESTROY
    }

    override fun getProviderSubscription(): List<String> {
        return listOf(PresenterUnion.NAME, MessengerUnion.NAME)
    }

    override fun onStopProvider(provider: IProvider) = Unit

    override fun read(message: IMessage) = Unit

    override fun addAction(action: IAction) {
        when (getState()) {
            Lifecycle.VIEW_DESTROY -> return

            Lifecycle.VIEW_CREATE, Lifecycle.VIEW_NOT_READY -> {
                if (!action.isRun()) {
                    actions.add(action)
                }
                return
            }

            else -> {
                if (!action.isRun()) {
                    actions.add(action)
                }
                doActions()
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

    override fun onAction(action: IAction): Boolean {
        return true
    }

    override fun stop() = Unit

}

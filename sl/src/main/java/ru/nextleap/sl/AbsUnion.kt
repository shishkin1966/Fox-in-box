package ru.nextleap.sl

import java.lang.ref.WeakReference

abstract class AbsUnion<T : IProviderSubscriber> : AbsSmallUnion<T>(), IUnion<T> {
    private var currentSubscriber: WeakReference<T>? = null

    override fun register(subscriber: T): Boolean {
        if (super.register(subscriber)) {
            if (currentSubscriber != null) {
                val oldSubscriber = currentSubscriber?.get()
                if (subscriber.getName() == (oldSubscriber?.getName())) {
                    currentSubscriber = WeakReference(subscriber)
                }
            }
            return true
        }
        return false
    }

    override fun unregister(subscriber: T) : Boolean {
        super.unregister(subscriber)

        if (currentSubscriber != null) {
            val oldSubscriber = currentSubscriber?.get()
            if (subscriber.getName() == (oldSubscriber?.getName())) {
                currentSubscriber?.clear()
                currentSubscriber = null
            }
        }
        return false
    }

    override fun setCurrentSubscriber(subscriber: T): Boolean {
        if (!subscriber.isValid()) return false

        if (!contains(subscriber)) {
            register(subscriber)
        }
        if (!contains(subscriber)) {
            return false
        }
        currentSubscriber = WeakReference(subscriber)
        return true
    }

    override fun getCurrentSubscriber(): T? {
        if (currentSubscriber != null) {
            return currentSubscriber?.get()
        }
        return null
    }

}

package ru.nextleap.sl

import java.lang.ref.WeakReference
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.collections.ArrayList


class RefSecretary<T> : ISecretary<T> {
    private val subscribers =
        Collections.synchronizedMap(ConcurrentHashMap<String, WeakReference<T>>())

    override fun remove(key: String): T? {
        checkNull()
        return subscribers.remove(key)?.get()
    }

    override fun size(): Int {
        checkNull()
        return subscribers.size
    }

    override fun put(key: String, value: T): T? {
        subscribers[key] = WeakReference(value)
        return get(key)
    }

    override fun containsKey(key: String): Boolean {
        checkNull()
        return subscribers.containsKey(key)
    }

    override operator fun get(key: String): T? {
        checkNull()
        return subscribers[key]?.get()
    }

    override fun values(): List<T> {
        checkNull()
        val list = ArrayList<T>()
        for (reference in subscribers.values) {
            if (reference != null) {
                val obj = reference.get()
                if (obj != null) {
                    list.add(obj)
                }
            }
        }
        return list
    }

    override fun isEmpty(): Boolean {
        checkNull()
        return subscribers.isEmpty()
    }

    private fun checkNull() {
        for (entry in subscribers.entries) {
            if (entry.value == null || entry.value.get() == null) {
                subscribers.remove(entry.key)
            }
        }
    }

    override fun clear() {
        subscribers.clear()
    }

    override fun keys(): Collection<String> {
        return subscribers.keys
    }
}

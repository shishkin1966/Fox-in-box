package ru.nextleap.sl

import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.collections.ArrayList


class Secretary<T> : ISecretary<T> {
    private val subscribers = Collections.synchronizedMap(ConcurrentHashMap<String, T>())

    override fun remove(key: String): T? {
        return subscribers.remove(key)
    }

    override fun size(): Int {
        return subscribers.size
    }

    override fun put(key: String, value: T): T? {
        return subscribers.put(key, value)
    }

    override fun containsKey(key: String): Boolean {
        return subscribers.containsKey(key)
    }

    override operator fun get(key: String): T? {
        return subscribers[key]
    }

    override fun values(): List<T> {
        return ArrayList(subscribers.values)
    }

    override fun isEmpty(): Boolean {
        return subscribers.isEmpty()
    }

    override fun clear() {
        subscribers.clear()
    }

    override fun keys(): Collection<String> {
        return subscribers.keys
    }


}

package ru.nextleap.fox_in_box.provider

import io.paperdb.Paper
import ru.nextleap.sl.AbsProvider
import ru.nextleap.sl.IProvider
import ru.nextleap.sl.provider.ApplicationProvider
import ru.nextleap.sl.provider.LogSingleton.instance
import java.io.Serializable
import java.util.*
import java.util.concurrent.locks.ReentrantLock

class StorageProvider : AbsProvider(), IStorageProvider {
    companion object {
        const val NAME = "StorageProvider"
        private const val TIME = "StorageProvider.time"
    }

    private val lock: ReentrantLock = ReentrantLock()

    override fun onRegister() {
        Paper.init(ApplicationProvider.appContext)
    }

    override fun getName(): String {
        return NAME
    }

    override fun put(key: String, value: Serializable) {
        if (key.isEmpty()) {
            return
        }
        lock.lock()
        try {
            Paper.book(NAME).write(key, value)
        } catch (e: Exception) {
            instance.onError(NAME, e)
        } finally {
            lock.unlock()
        }
    }

    override fun putList(key: String, values: List<Serializable>) {
        if (key.isEmpty()) {
            return
        }
        lock.lock()
        try {
            Paper.book(NAME).write(key, values)
        } catch (e: Exception) {
            instance.onError(NAME, e)
        } finally {
            lock.unlock()
        }
    }

    override fun put(key: String, value: Serializable, expired: Long) {
        if (key.isEmpty()) {
            return
        }
        if (expired < System.currentTimeMillis()) {
            return
        }
        lock.lock()
        try {
            Paper.book(NAME).write(key, value)
            Paper.book(TIME).write(key, expired)
        } catch (e: Exception) {
            instance.onError(NAME, e)
        } finally {
            lock.unlock()
        }
    }

    override fun putList(key: String, values: List<Serializable>, expired: Long) {
        if (key.isEmpty()) {
            return
        }
        if (expired < System.currentTimeMillis()) {
            return
        }
        lock.lock()
        try {
            Paper.book(NAME).write(key, values)
            Paper.book(TIME).write(key, expired)
        } catch (e: Exception) {
            instance.onError(NAME, e)
        } finally {
            lock.unlock()
        }
    }

    private fun deleteKeys(key: String) {
        if (Paper.book(NAME).contains(key)) {
            Paper.book(NAME).delete(key)
        }
        if (Paper.book(TIME).contains(key)) {
            Paper.book(TIME).delete(key)
        }
    }

    override fun get(key: String): Serializable? {
        if (key.isEmpty()) {
            return null
        }
        lock.lock()
        try {
            if (Paper.book(NAME).contains(key)) {
                if (Paper.book(TIME).contains(key)) {
                    val expired = Paper.book(TIME).read<Long>(key)
                    if (expired < System.currentTimeMillis()) {
                        deleteKeys(key)
                        return null
                    }
                }
                return Paper.book(NAME).read(key)
            } else {
                deleteKeys(key)
            }
        } catch (e: Exception) {
            instance.onError(NAME, e)
        } finally {
            lock.unlock()
        }
        return null
    }

    override fun getList(key: String): List<Serializable?>? {
        if (key.isEmpty()) {
            return null
        }
        lock.lock()
        try {
            if (Paper.book(NAME).contains(key)) {
                if (Paper.book(TIME).contains(key)) {
                    val expired = Paper.book(TIME).read<Long>(key)
                    if (expired < System.currentTimeMillis()) {
                        deleteKeys(key)
                        return null
                    }
                }
                val s = Paper.book(NAME).read<Serializable>(key)
                if (s != null) {
                    return serializableToList<Serializable>(s)
                }
            } else {
                deleteKeys(key)
            }
        } catch (e: Exception) {
            instance.onError(NAME, e)
        } finally {
            lock.unlock()
        }
        return null
    }

    override fun get(key: String, defaultValue: Serializable?): Serializable? {
        return get(key) ?: return defaultValue
    }

    override fun clear(key: String?) {
        if (key!!.isEmpty()) {
            return
        }
        lock.lock()
        try {
            deleteKeys(key)
        } catch (e: Exception) {
            instance.onError(NAME, e)
        } finally {
            lock.unlock()
        }
    }

    override fun clear() {
        lock.lock()
        try {
            Paper.book(NAME).destroy()
            Paper.book(TIME).destroy()
        } catch (e: Exception) {
            instance.onError(NAME, e)
        } finally {
            lock.unlock()
        }
    }

    override fun check() {
        lock.lock()
        try {
            val list = Paper.book(TIME).allKeys
            for (key in list) {
                val expired = Paper.book(TIME).read<Long>(key)
                if (expired < System.currentTimeMillis()) {
                    deleteKeys(key)
                }
            }
        } catch (e: Exception) {
            instance.onError(NAME, e)
        } finally {
            lock.unlock()
        }
    }

    override fun <T> toSerializable(list: List<T>): Serializable {
        val linkedList = LinkedList<T>()
        linkedList.addAll(list)
        return linkedList
    }

    override fun <T> serializableToList(value: Serializable): List<T>? {
        if (value is LinkedList<*>) {
            val list: MutableList<T> = ArrayList()
            list.addAll(value as List<T>)
            return list
        } else if (value is ArrayList<*>) {
            return value as List<T>
        }
        return null
    }

    override fun compareTo(other: IProvider): Int {
        return if (IStorageProvider::class.java.isInstance(other)) 0 else 1
    }

}
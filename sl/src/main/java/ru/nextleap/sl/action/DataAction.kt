package ru.nextleap.sl.action

open class DataAction<T>(name: String, private val data: T?) : ApplicationAction(name) {

    fun getData(): T? {
        return data
    }
}

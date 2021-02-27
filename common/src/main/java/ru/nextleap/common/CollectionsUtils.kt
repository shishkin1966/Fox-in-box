package ru.nextleap.common

import java.util.*

/**
 * `CollectionsExt` contains static methods which operate on
 * `Collection` classes.
 */
object CollectionsUtils {
    /**
     * Joins two or more [List]s together.
     *
     * @param lists The lists to be joined.
     * @return A non-null list that contains items for the initial lists.
     */
    @SafeVarargs
    fun <T> join(vararg lists: List<T>): List<T> {
        var totalSize = 0
        for (list in lists) {
            totalSize += list.size
        }
        val result: MutableList<T> = ArrayList(totalSize)
        for (list in lists) {
            result.addAll(list)
        }
        return result
    }

    fun <T> equals(a: Array<T>?, a2: Array<T>?): Boolean {
        if (a == null || a2 == null) return false
        val length = a.size
        if (a2.size != length) return false
        for (i in 0 until length) {
            val o1: T? = a[i]
            val o2: T? = a2[i]
            if (!(if (o1 == null) o2 == null else o1 == o2)) {
                return false
            }
        }
        return true
    }

    fun equals(a: ByteArray?, a2: ByteArray?): Boolean {
        if (a == null || a2 == null) return false
        val length = a.size
        if (a2.size != length) return false
        for (i in 0 until length) {
            if (a[i] != a2[i]) {
                return false
            }
        }
        return true
    }

    /**
     * Returns first item in the list if list is not empty.
     *
     * @param list The list to get first item from.
     */
    fun <T> first(list: List<T>?): T? {
        return if (list == null || list.isEmpty()) null else list[0]
    }
}
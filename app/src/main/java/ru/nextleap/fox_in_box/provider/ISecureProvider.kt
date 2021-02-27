package ru.nextleap.fox_in_box.provider

import ru.nextleap.sl.IProvider

interface ISecureProvider : IProvider {
    fun deleteKeyPair(): Boolean
    fun put(key: String, data: String?): Boolean
    operator fun get(key: String): String?
    fun clear(key: String)
}
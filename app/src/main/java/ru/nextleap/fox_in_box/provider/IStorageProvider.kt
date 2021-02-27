package ru.nextleap.fox_in_box.provider

import ru.nextleap.sl.IProvider
import java.io.Serializable
import java.lang.reflect.Type

interface IStorageProvider : IProvider {
    /**
     * Put value to storage.
     *
     * @param key     the key
     * @param value   the value
     * @param expired expired date
     */
    fun put(key: String, value: Serializable, expired: Long)

    /**
     * Check expired period all keys.
     */
    fun check()

    /**
     * Put value to storage.
     *
     * @param key   the key
     * @param value the value
     */
    fun put(key: String, value: Serializable)

    /**
     * Put values to storage.
     *
     * @param key    the key
     * @param values the list of values
     */
    fun putList(key: String, values: List<Serializable>)

    /**
     * Put values to storage.
     *
     * @param key    the key
     * @param values the list of values
     * @param expired expired date
     */
    fun putList(key: String, values: List<Serializable>, expired: Long)

    /**
     * Get value from storage.
     *
     * @param key the key
     * @return the value
     */
    operator fun get(key: String): Serializable?

    /**
     * Get values from storage.
     *
     * @param key the key
     * @return the list of values
     */
    fun getList(key: String): List<Serializable?>?

    /**
     * Get value from storage.
     *
     * @param key          the key
     * @param defaultValue the default value
     * @return the value
     */
    operator fun get(key: String, defaultValue: Serializable?): Serializable?

    /**
     * Clear value.
     *
     * @param key the key
     */
    fun clear(key: String?)

    /**
     * Clear all values.
     */
    fun clear()

    /**
     * Convert list to serializable
     *
     * @param list the list
     */
    fun <T> toSerializable(list: List<T>): Serializable?

    /**
     * Convert serializable to list
     *
     * @param value the serializable
     */
    fun <T> serializableToList(value: Serializable): List<T>?

    /**
     * Convert serializable to Json
     *
     * @param obj the object
     */
    fun <T> toJson(obj: T): Serializable?

    /**
     * Convert serializable to Json
     *
     * @param obj  the object
     * @param type the object type
     */
    fun <T> toJson(obj: T, type: Type?): Serializable?

    /**
     * Convert Json to serializable
     *
     * @param json the json
     * @param cl   the object class
     */
    fun <T> fromJson(json: String, cl: Class<T>): T

    /**
     * Convert Json to serializable
     *
     * @param json the json
     * @param type the object type
     */
    fun <T> fromJson(json: String, type: Type): T
}
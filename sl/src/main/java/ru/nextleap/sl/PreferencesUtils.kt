@file:Suppress("unused", "UNUSED_PARAMETER")

package ru.nextleap.sl

import android.content.Context
import androidx.preference.PreferenceManager

/**
 * Preferences приложения
 */
class PreferencesUtils {
    companion object {
        @JvmStatic
        fun putString(context: Context, key: String, value: String?) {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = preferences.edit()
            editor.putString(key, value).apply()
        }

        @JvmStatic
        fun getString(context: Context, key: String, defaultValue: String? = null): String? {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getString(key, defaultValue)
        }

        @JvmStatic
        fun putInt(context: Context, key: String, value: Int) {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = preferences.edit()
            editor.putInt(key, value).apply()
        }

        @JvmStatic
        fun getInt(context: Context, key: String, defaultValue: Int = -1): Int {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getInt(key, defaultValue)
        }

        @JvmStatic
        fun putLong(context: Context, key: String, value: Long) {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = preferences.edit()
            editor.putLong(key, value).apply()
        }

        @JvmStatic
        fun getLong(context: Context, key: String, defaultValue: Long = -1L): Long {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getLong(key, defaultValue)
        }

        @JvmStatic
        fun putFloat(context: Context, key: String, value: Float) {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = preferences.edit()
            editor.putFloat(key, value).apply()
        }

        @JvmStatic
        fun getFloat(context: Context, key: String, defaultValue: Float = -1f): Float {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getFloat(key, defaultValue)
        }

        @JvmStatic
        fun putBoolean(context: Context, key: String, value: Boolean) {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = preferences.edit()
            editor.putBoolean(key, value).apply()
        }

        @JvmStatic
        fun getBoolean(context: Context, key: String, defaultValue: Boolean = false): Boolean {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getBoolean(key, defaultValue)
        }

        @JvmStatic
        fun getAll(context: Context): Map<String, *>? {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.all
        }

        @JvmStatic
        fun remove(context: Context, key: String) {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = preferences.edit()
            editor.remove(key).apply()
        }

        @JvmStatic
        fun contains(context: Context, key: String): Boolean {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.contains(key)
        }

        @JvmStatic
        fun clear(context: Context) {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = preferences.edit()
            editor.clear().apply()
        }
    }
}


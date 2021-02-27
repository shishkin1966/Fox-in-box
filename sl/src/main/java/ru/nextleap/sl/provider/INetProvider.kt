package ru.nextleap.sl.provider

import okhttp3.OkHttpClient
import retrofit2.Converter


interface INetProvider<T> : IRequestProvider {
    fun getApiClass(): Class<T>

    fun getBaseUrl(): String

    fun getConverterFactory(): Converter.Factory

    fun setToken(token: String?)

    fun getToken(): String?

    fun getClient(): OkHttpClient
}

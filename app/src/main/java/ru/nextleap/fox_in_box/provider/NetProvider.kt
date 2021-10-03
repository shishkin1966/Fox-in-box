package ru.nextleap.fox_in_box.provider

import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import ru.nextleap.sl.IProvider
import ru.nextleap.sl.provider.AbsNetProvider
import ru.nextleap.sl.provider.INetProvider
import java.io.IOException
import java.util.concurrent.TimeUnit

class NetProvider : AbsNetProvider<NetApi>() {
    companion object {
        const val NAME = "NetProvider"
        const val URL = "http://promo.ofrs.su/" //Базовый адрес
        const val CONNECT_TIMEOUT: Long = 30 // 30 sec
        const val READ_TIMEOUT: Long = 10 // 10 min
    }

    private var token: String? = null

    override fun getApiClass(): Class<NetApi> {
        return NetApi::class.java
    }

    override fun getBaseUrl(): String {
        return URL
    }

    override fun getName(): String {
        return NAME
    }

    override fun compareTo(other: IProvider): Int {
        return if (other is INetProvider<*>) 0 else 1
    }

    override fun getOkHttpClient(): OkHttpClient {
        class RequestInterceptor : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                val originalRequest: Request = chain.request()
                if (getToken() != null) {
                    val tokenRequest: Request = originalRequest.newBuilder()
                        .header("Authorization", "Bearer " + getToken())
                        .build()
                    return chain.proceed(tokenRequest)
                } else {
                    return chain.proceed(originalRequest)
                }
            }
        }

        val builder = OkHttpClient.Builder()
        builder.addInterceptor(RequestInterceptor())
        //builder.cache(Cache(ApplicationProvider.appContext.cacheDir, 10 * 1024 * 1024)) // 100 MB
        //builder.addInterceptor(CacheInterceptor())
        builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.MINUTES)
            .followRedirects(false)
            .followSslRedirects(false)
            .retryOnConnectionFailure(false)

        builder.addInterceptor(OkHttpProfilerInterceptor())

        return builder.build()
    }

    fun setToken(token: String?) {
        this.token = token
    }

    fun getToken(): String? {
        return token
    }

}

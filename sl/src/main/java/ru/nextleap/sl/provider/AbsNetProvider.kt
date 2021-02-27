package ru.nextleap.sl.provider

import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.nextleap.sl.request.IRequest
import ru.nextleap.sl.task.NetExecutor
import java.io.IOException
import java.util.concurrent.TimeUnit


abstract class AbsNetProvider<T> : INetProvider<T> {

    private val CONNECT_TIMEOUT: Long = 30 // 30 sec
    private val READ_TIMEOUT: Long = 10 // 10 min
    private val retrofit: Retrofit
    private val api: T
    private var token: String? = null
    private val httpClient: OkHttpClient

    init {
        httpClient = getOkHttpClient()
        retrofit = Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient)
            .addConverterFactory(getConverterFactory())
            .build()
        api = retrofit.create<T>(getApiClass())
    }

    fun getApi(): T {
        return api
    }

    override fun getConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    override fun getClient(): OkHttpClient {
        return httpClient
    }

    private fun getOkHttpClient(): OkHttpClient {
        class RequestInterceptor : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                val originalRequest: Request = chain.request()
                if (token != null) {
                    val tokenRequest: Request = originalRequest.newBuilder()
                        .header("Authorization", "Bearer " + token)
                        .build()
                    return chain.proceed(tokenRequest)
                } else {
                    return chain.proceed(originalRequest)
                }
            }
        }

        val builder = OkHttpClient.Builder()
        //builder.cache(Cache(ApplicationProvider.appContext.cacheDir, 10 * 1024 * 1024)) // 100 MB
        builder.addInterceptor(RequestInterceptor())
        //builder.addInterceptor(CacheInterceptor())
        builder.addNetworkInterceptor(StethoInterceptor())
        builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.MINUTES)
            .followRedirects(false)
            .followSslRedirects(false)
            .retryOnConnectionFailure(false)
        return builder.build()
    }

    override fun request(request: IRequest) {
        if (isValid()) {
            val executor = ApplicationProvider.serviceLocator?.get<NetExecutor>(NetExecutor.NAME)
            executor?.execute(request)
        }
    }

    override fun isPersistent(): Boolean {
        return false
    }

    override fun onUnRegister() = Unit

    override fun onRegister() = Unit

    override fun stop() = Unit

    override fun isValid(): Boolean {
        return true
    }

    override fun setToken(token: String?) {
        this.token = token
    }

    override fun getToken(): String? {
        return token
    }


}

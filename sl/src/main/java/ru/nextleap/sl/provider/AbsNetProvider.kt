package ru.nextleap.sl.provider

import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.nextleap.sl.request.IRequest
import ru.nextleap.sl.task.NetExecutor


abstract class AbsNetProvider<T> : INetProvider<T> {

    private val retrofit: Retrofit
    private val api: T
    private val httpClient: OkHttpClient = getOkHttpClient()

    init {
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


}

package ru.nextleap.sl.provider

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class CacheInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
            .header("Cache-Control", "public, must-revalidate, max-age=" + 60 * 60 * 24 * 30)
            .build()
        return chain.proceed(request)
    }
}
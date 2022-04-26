package cn.shper.tknetwork.interceptor

import cn.shper.tknetwork.annotation.Timeout
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation
import java.util.concurrent.TimeUnit

/**
 * Author: Shper
 * Email: me@shper.cn
 * Version: V0.1 2022/4/25
 */
internal class TimeoutInterceptor : Interceptor {

    companion object {
        @JvmStatic
        fun create(): TimeoutInterceptor {
            return TimeoutInterceptor()
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val invocation = request.tag(Invocation::class.java)
        val timeoutAnnotation = invocation?.method()?.getAnnotation(Timeout::class.java)

        timeoutAnnotation?.let { timeout ->

            val connectTimeout = if (timeout.connectTimeout > 0)
                timeout.timeUnit.toMillis(timeout.connectTimeout.toLong()).toInt() else chain.connectTimeoutMillis()

            val readTimeout = if (timeout.readTimeout > 0)
                timeout.timeUnit.toMillis(timeout.readTimeout.toLong()).toInt() else chain.readTimeoutMillis()

            val writeTimeout = if (timeout.writeTimeout > 0)
                timeout.timeUnit.toMillis(timeout.writeTimeout.toLong()).toInt() else chain.writeTimeoutMillis()

            return chain.withConnectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
                .withReadTimeout(readTimeout, TimeUnit.MILLISECONDS)
                .withWriteTimeout(writeTimeout, TimeUnit.MILLISECONDS)
                .proceed(request)
        }

        return chain.proceed(request)
    }

}
package cn.shper.tknetwork.interceptor

import cn.shper.tknetwork.TKRetrofitClient
import cn.shper.tknetwork.annotation.Domain
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation

/**
 * Author: Shper
 * Email: me@shper.cn
 * Version: V0.1 2022/4/25
 */
class DomainInterceptor : Interceptor {

    companion object {
        @JvmStatic
        fun create(): DomainInterceptor {
            return DomainInterceptor()
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val invocation = request.tag(Invocation::class.java)
        val domainAnnotation = invocation?.method()?.getAnnotation(Domain::class.java)

        domainAnnotation?.let { domain ->

            TKRetrofitClient.getDomainUrlByName(domain.name)?.let { url ->

                HttpUrl.parse(url)?.let { httpUrl ->

                    val newHttpUrl = request.url().newBuilder()
                        .scheme(httpUrl.scheme())
                        .port(httpUrl.port())
                        .host(httpUrl.host())
                        .build()

                    request = request.newBuilder()
                        .url(newHttpUrl)
                        .build()
                }
            }
        }

        return chain.proceed(request)
    }

}
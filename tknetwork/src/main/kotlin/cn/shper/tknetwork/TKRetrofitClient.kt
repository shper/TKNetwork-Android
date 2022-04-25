package cn.shper.tknetwork

import cn.shper.tknetwork.interceptor.DomainInterceptor
import cn.shper.tknetwork.interceptor.TimeoutInterceptor
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

/**
 * Author: Shper
 * Email: me@shper.cn
 * Version: V0.1 2022/4/15
 */
object TKRetrofitClient {

    private lateinit var baseurl: String
    private var domainMap: MutableMap<String, String>? = null
    private var httpClient: OkHttpClient? = null
    private var converterFactoryList: MutableList<Converter.Factory>? = null
    private var callAdapterFactoryList: MutableList<CallAdapter.Factory>? = null

    private val retrofit: Retrofit by lazy {
        val builder = Retrofit.Builder()
            .baseUrl(baseurl)

        httpClient = httpClient ?: run {
            OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build()
        }

        httpClient = httpClient!!.newBuilder()
            .addInterceptor(DomainInterceptor.create())
            .addInterceptor(TimeoutInterceptor.create())
            .build()

        builder.client(httpClient!!)

        converterFactoryList?.forEach { converterFactory ->
            builder.addConverterFactory(converterFactory)
        }
        callAdapterFactoryList?.forEach { callAdapterFactory ->
            builder.addCallAdapterFactory(callAdapterFactory)
        }

        builder.build()
    }

    fun setup(baseurl: String): TKRetrofitClient {
        this.baseurl = baseurl

        return this
    }

    fun client(httpClient: OkHttpClient): TKRetrofitClient {
        this.httpClient = httpClient

        return this
    }

    fun addDomain(name: String, url: String): TKRetrofitClient {
        if (domainMap == null) {
            domainMap = mutableMapOf()
        }

        domainMap!![name] = url

        return this
    }

    internal fun getDomainUrlByName(name: String): String? {
        return domainMap?.let {
            it[name]
        }
    }

    fun addConverterFactory(converterFactory: Converter.Factory): TKRetrofitClient {
        if (converterFactoryList == null) {
            converterFactoryList = mutableListOf()
        }
        this.converterFactoryList!!.add(converterFactory)

        return this
    }

    fun addCallAdapterFactory(callAdapterFactory: CallAdapter.Factory): TKRetrofitClient {
        if (callAdapterFactoryList == null) {
            callAdapterFactoryList = mutableListOf()
        }
        this.callAdapterFactoryList!!.add(callAdapterFactory)

        return this
    }

    fun <T> create(serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }

}
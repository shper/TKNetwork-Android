package cn.shper.tknetwork.calladapter

import kotlinx.coroutines.Deferred
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Author: Shper
 * Email: me@shper.cn
 * Version: V0.1 2022/4/18
 */
class TKCoroutinesCallAdapterFactory : CallAdapter.Factory() {

    companion object {
        @JvmStatic
        fun create(): TKCoroutinesCallAdapterFactory {
            return TKCoroutinesCallAdapterFactory()
        }
    }

    override fun get(returnType: Type,
                     annotations: Array<out Annotation>,
                     retrofit: Retrofit): CallAdapter<*, *>? {
        val rawType = getRawType(returnType)
        if (Deferred::class.java != rawType) {
            return null
        }

        if (returnType !is ParameterizedType) {
            throw IllegalStateException("$returnType return type must be parameterized")
        }

        val responseType = getParameterUpperBound(0, returnType)
        return TKCoroutinesCallAdapter<Any>(responseType)
    }
}
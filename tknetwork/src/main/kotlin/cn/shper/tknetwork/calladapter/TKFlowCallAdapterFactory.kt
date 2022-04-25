package cn.shper.tknetwork.calladapter

import kotlinx.coroutines.flow.Flow
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Author: Shper
 * Email: me@shper.cn
 * Version: V0.1 2022/4/18
 */
class TKFlowCallAdapterFactory private constructor() : CallAdapter.Factory() {

    companion object {
        @JvmStatic
        fun create(): TKFlowCallAdapterFactory {
            return TKFlowCallAdapterFactory()
        }
    }

    override fun get(returnType: Type,
                     annotations: Array<out Annotation>,
                     retrofit: Retrofit): CallAdapter<*, *>? {
        val rawType = getRawType(returnType)
        if (Flow::class.java != rawType) {
            return null
        }

        check(returnType is ParameterizedType) { "Flow return type must be parameterized as Flow<Foo> or Flow<out Foo>" }

        val responseType = getParameterUpperBound(0, returnType)
        return TKFlowCallAdapter<Any>(responseType)
    }
}
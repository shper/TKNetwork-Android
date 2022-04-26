package cn.shper.tknetwork.calladapter

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Author: Shper
 * Email: me@shper.cn
 * Version: V0.1 2022/4/25
 */
class ResultCallAdapterFactory private constructor() : CallAdapter.Factory() {

    companion object {
        @JvmStatic
        fun create(): ResultCallAdapterFactory {
            return ResultCallAdapterFactory()
        }
    }

    override fun get(returnType: Type,
                     annotations: Array<out Annotation>,
                     retrofit: Retrofit): CallAdapter<*, *>? {

        val rawType = getRawType(returnType)
        if (Call::class.java != rawType) {
            return null
        }

        check(returnType is ParameterizedType) { "Result return type must be parameterized as Result<Foo> or Result<out Foo>" }

        val responseType = getParameterUpperBound(0, returnType)
        if (Result::class.java != getRawType(responseType)) {
            return null
        }

        return object : CallAdapter<Any, Call<Result<Any>>> {
            override fun responseType(): Type {
                return getParameterUpperBound(0, responseType as ParameterizedType)
            }

            override fun adapt(call: Call<Any>): Call<Result<Any>> = ResultCall(call)

        }
    }
}
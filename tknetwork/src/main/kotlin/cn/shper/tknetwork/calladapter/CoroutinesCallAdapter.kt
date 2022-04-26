package cn.shper.tknetwork.calladapter

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import retrofit2.*
import java.lang.reflect.Type

/**
 * Author: Shper
 * Email: me@shper.cn
 * Version: V0.1 2022/4/18
 */
class CoroutinesCallAdapter<T>(private val responseType: Type) : CallAdapter<T, Deferred<T>> {

    override fun responseType(): Type {
        return responseType
    }

    override fun adapt(call: Call<T>): Deferred<T> {
        val deferred = CompletableDeferred<T>()

        deferred.invokeOnCompletion {
            if (deferred.isCancelled) {
                call.cancel()
            }
        }

        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    deferred.complete(response.body()!!)
                } else {
                    deferred.completeExceptionally(HttpException(response))
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                deferred.completeExceptionally(t)
            }
        })
        return deferred
    }
}
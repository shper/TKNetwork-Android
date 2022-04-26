package cn.shper.tknetwork.calladapter

import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * Author: Shper
 * Email: me@shper.cn
 * Version: V0.1 2022/4/25
 */
class DownloadCallAdapterFactory : CallAdapter.Factory() {

    companion object {
        @JvmStatic
        fun create(): DownloadCallAdapterFactory {
            return DownloadCallAdapterFactory()
        }
    }

    override fun get(returnType: Type,
                     annotations: Array<out Annotation>,
                     retrofit: Retrofit): CallAdapter<*, *>? {
        val rawType = getRawType(returnType)
        if (DownloadResult::class.java != rawType) {
            return null
        }
        return DownloadCallAdapter()
    }

}
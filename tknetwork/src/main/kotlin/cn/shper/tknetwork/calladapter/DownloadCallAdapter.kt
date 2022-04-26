package cn.shper.tknetwork.calladapter

import okhttp3.ResponseBody
import retrofit2.*
import java.lang.reflect.Type

/**
 * Author: Shper
 * Email: me@shper.cn
 * Version: V0.1 2022/4/25
 */
class DownloadCallAdapter: CallAdapter<ResponseBody, DownloadResult> {

    override fun responseType(): Type {
        return ResponseBody::class.java
    }

    override fun adapt(call: Call<ResponseBody>): DownloadResult {
        val downloadResult = DownloadResult()

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    downloadResult.callStart()
                    downloadResult.callSuccess(response.body()!!)
                } else {
                    downloadResult.callFailure(response.code(), response.message())
                }
            }

            override fun onFailure(call: Call<ResponseBody>, throwable: Throwable) {
                downloadResult.callFailure(throwable = throwable)
            }
        })

        return downloadResult
    }
}
package cn.shper.tknetwork.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import cn.shper.tklogger.TKLogger
import cn.shper.tknetwork.TKNetworkClient
import cn.shper.tknetwork.example.repository.ExampleAPIService
import kotlinx.android.synthetic.main.activity_example.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.io.File

/**
 * Author : Shper
 * EMail : me@shper.cn
 * Date : 2020/6/10
 */
class ExampleActivity : AppCompatActivity() {

    private val service: ExampleAPIService by lazy {
        TKNetworkClient.create(ExampleAPIService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)

        setupClickListener()
    }

    private fun setupClickListener() {
        example_tkresponse_btn.setOnClickListener {
            TKLogger.d("onClick")

            lifecycleScope.launch {
                TKLogger.d("test: " + Thread.currentThread().name)

                val roundTableList = service.fetchRoundTablesBySuspendResult(20, 0).getOrElse {
                    TKLogger.w("Data is empty")
                    return@launch
                }

                example_txt.text = roundTableList.toString()
            }
        }

        example_deferred_btn.setOnClickListener {
            lifecycleScope.launch {
                try {
                    val roundTableList = service.fetchRoundTablesByDeferred(20, 0).await()

                    example_txt.text = roundTableList.toString()
                } catch (exception: Exception) {
                    TKLogger.e("exception: $exception")
                }
            }
        }

        example_flow_btn.setOnClickListener {
            MainScope().launch {
                service.fetchRoundTablesByFlow(20, 0)
                    .onStart {
                        TKLogger.d("onStart - ${Thread.currentThread().name}")
                    }.onEmpty {
                        TKLogger.d("onEmpty - ${Thread.currentThread().name}")
                    }.catch {
                        it.printStackTrace()
                    }.flowOn(Dispatchers.IO)
                    .onCompletion {
                        TKLogger.d("onCompletion - ${Thread.currentThread().name}")
                    }.collect { roundTableList ->
                        TKLogger.d("collect - ${Thread.currentThread().name}")

                        example_txt.text = roundTableList.toString()
                    }
            }
        }

        example_download_btn.setOnClickListener {
            val downloadUrl = "https://pic.3gbizhi.com/2021/1228/20211228042034875.png"
            val saveFile = File("${ExampleApplication.instance.getExternalFilesDir(null)}/1.png")

            lifecycleScope.launch {
                service.downloadSimple(downloadUrl)
                    .save(saveFile)
                    .onStart {
                        TKLogger.d("onStart")
                    }.onProgress { currentPercent, currentSize, totalSize ->
                        TKLogger.d("onProgress - currentPercent: $currentPercent ; currentSize: $currentSize; totalSize: $totalSize")
                    }.onFinish { filePath ->
                        TKLogger.d("onFinish - filePath: $filePath")
                    }.onFailure { code, message, throwable ->
                        TKLogger.e("onFailure - Code: $code; Message: $message; Throw ${throwable?.localizedMessage}")
                    }
            }
        }
    }

}
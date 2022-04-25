package cn.shper.tknetwork.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import cn.shper.tklogger.TKLogger
import cn.shper.tknetwork.TKRetrofitClient
import cn.shper.tknetwork.TKRetrofitClient.create
import cn.shper.tknetwork.example.repository.RoundTableService
import kotlinx.android.synthetic.main.activity_example.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * Author : Shper
 * EMail : me@shper.cn
 * Date : 2020/6/10
 */
class ExampleActivity : AppCompatActivity() {

    private val service: RoundTableService by lazy {
        TKRetrofitClient.create(RoundTableService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)

        example_tkresponse_btn.setOnClickListener {
            TKLogger.d("onClick")

//            service.fetchRoundTables(20, 0)
//                .onStart {
//                    TKLogger.d("onStart")
//                }
//                .onSuccess(Dispatchers.Main) { roundTableList ->
//                    example_txt.text = roundTableList.toString()
//                }
//                .onFailure { code, message, exception ->
//                    TKLogger.e("code: ${code}, message: ${message}, exception: ${exception.toString()}")
//                }
//                .onCompletion {
//                    TKLogger.d("onCompletion")
//                }
            lifecycleScope.launch {
                TKLogger.d("test: " + Thread.currentThread().name)

                val result = service.fetchRoundTables1(20, 0)

                when {
                    result.isFailure -> {

                    }
                    result.isSuccess -> {
                        example_txt.text = result.getOrNull().toString()
                    }
                }

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
    }

}
package cn.shper.tknetwork.example

import android.app.Application
import cn.shper.tklogger.TKLogger
import cn.shper.tklogger.destination.TKLogConsoleDestination
import cn.shper.tknetwork.converter.TKMoshiConverterFactory
import cn.shper.tknetwork.TKRetrofitClient
import cn.shper.tknetwork.calladapter.TKCoroutinesCallAdapterFactory
import cn.shper.tknetwork.calladapter.TKFlowCallAdapterFactory
import cn.shper.tknetwork.calladapter.TKResultCallAdapterFactory

/**
 * Author : Shper
 * EMail : me@shper.cn
 * Date : 2020/6/11
 */
class ExampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        setupLibrary()
    }

    private fun setupLibrary() {
        TKLogger.setup(tag = "TKNetwork_Example")
        TKLogger.addDestination(TKLogConsoleDestination())

        TKRetrofitClient.setup("https://www.shper.cn/")
            .addDomain("zhihu", "https://www.zhihu.com/")
            .addConverterFactory(TKMoshiConverterFactory.create())
            .addCallAdapterFactory(TKCoroutinesCallAdapterFactory.create())
            .addCallAdapterFactory(TKFlowCallAdapterFactory.create())
            .addCallAdapterFactory(TKResultCallAdapterFactory.create())
    }

}
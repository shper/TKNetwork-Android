package cn.shper.tknetwork.example

import android.app.Application
import cn.shper.tklogger.TKLogger
import cn.shper.tklogger.destination.TKLogConsoleDestination
import cn.shper.tknetwork.converter.MoshiConverterFactory
import cn.shper.tknetwork.TKNetworkClient
import cn.shper.tknetwork.calladapter.CoroutinesCallAdapterFactory
import cn.shper.tknetwork.calladapter.DownloadCallAdapterFactory
import cn.shper.tknetwork.calladapter.FlowCallAdapterFactory
import cn.shper.tknetwork.calladapter.ResultCallAdapterFactory
import kotlin.properties.Delegates

/**
 * Author : Shper
 * EMail : me@shper.cn
 * Date : 2020/6/11
 */
class ExampleApplication : Application() {

    companion object {
         var instance: ExampleApplication by Delegates.notNull()
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()

        setupLibrary()
    }

    private fun setupLibrary() {
        TKLogger.setup(tag = "TKNetwork_Example")
        TKLogger.addDestination(TKLogConsoleDestination())

        TKNetworkClient.setup("https://www.shper.cn/")
            .addDomain("zhihu", "https://www.zhihu.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutinesCallAdapterFactory.create())
            .addCallAdapterFactory(FlowCallAdapterFactory.create())
            .addCallAdapterFactory(ResultCallAdapterFactory.create())
            .addCallAdapterFactory(DownloadCallAdapterFactory.create())
    }

}
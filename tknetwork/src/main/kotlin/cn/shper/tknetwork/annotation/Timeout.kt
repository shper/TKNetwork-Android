package cn.shper.tknetwork.annotation

import java.util.concurrent.TimeUnit

/**
 * Author: Shper
 * Email: me@shper.cn
 * Version: V0.1 2022/4/25
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Timeout(
    // 单位，默认秒
    val timeUnit: TimeUnit = TimeUnit.SECONDS,
    // 连接超时 时长
    val connectTimeout: Int = 0,
    // 读取超时 时长
    val readTimeout: Int = 0,
    // 写入超时 时长
    val writeTimeout: Int = 0
)

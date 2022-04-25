package cn.shper.tknetwork.annotation

/**
 * Author: Shper
 * Email: me@shper.cn
 * Version: V0.1 2022/4/25
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Domain(
    val name: String
)

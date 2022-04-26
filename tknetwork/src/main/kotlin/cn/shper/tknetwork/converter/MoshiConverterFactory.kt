package cn.shper.tknetwork.converter

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Author: Shper
 * Email: me@shper.cn
 * Version: V0.1 2022/4/18
 */
class MoshiConverterFactory {

    companion object {
        @JvmStatic
        fun create(): MoshiConverterFactory {
            return MoshiConverterFactory.create(
                Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build())
        }
    }

}
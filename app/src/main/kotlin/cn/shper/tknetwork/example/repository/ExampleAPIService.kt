package cn.shper.tknetwork.example.repository

import cn.shper.tknetwork.annotation.Domain
import cn.shper.tknetwork.annotation.Timeout
import cn.shper.tknetwork.calladapter.DownloadResult
import cn.shper.tknetwork.example.model.RoundTableList
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import java.io.File

/**
 * Author: Shper
 * Email: me@shper.cn
 * Version: V0.1 2022/4/15
 */
interface ExampleAPIService {

    @GET("api/v4/roundtables")
    fun fetchRoundTablesByDeferred(@Query("limit") limit: Int,
                                   @Query("offset") offset: Int): Deferred<RoundTableList>

    @GET("api/v4/roundtables")
    fun fetchRoundTablesByFlow(@Query("limit") limit: Int,
                               @Query("offset") offset: Int): Flow<RoundTableList>

    @GET("api/v4/roundtables")
    fun fetchRoundTables(@Query("limit") limit: Int,
                         @Query("offset") offset: Int): Result<RoundTableList>

    @Domain("zhihu")
    @Timeout(connectTimeout = 5, readTimeout = 5)
    @GET("api/v4/roundtables")
    suspend fun fetchRoundTablesBySuspendResult(@Query("limit") limit: Int,
                                                @Query("offset") offset: Int): Result<RoundTableList>

    @GET
    @Streaming
    fun downloadSimple(@Url downloadUrl: String): DownloadResult
}
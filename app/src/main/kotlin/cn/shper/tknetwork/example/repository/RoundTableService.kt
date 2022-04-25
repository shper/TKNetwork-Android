package cn.shper.tknetwork.example.repository

import cn.shper.tknetwork.annotation.Domain
import cn.shper.tknetwork.annotation.Timeout
import cn.shper.tknetwork.example.model.RoundTableList
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Author: Shper
 * Email: me@shper.cn
 * Version: V0.1 2022/4/15
 */
interface RoundTableService {

    @GET("api/v4/roundtables")
    fun fetchRoundTables(@Query("limit") limit: Int,
                         @Query("offset") offset: Int): Result<RoundTableList>

    @Domain("zhihu")
    @Timeout(connectTimeout = 1, readTimeout = 1)
    @GET("api/v4/roundtables")
    suspend fun fetchRoundTables1(@Query("limit") limit: Int,
                         @Query("offset") offset: Int): Result<RoundTableList>

    @GET("api/v4/roundtables")
    fun fetchRoundTablesByDeferred(@Query("limit") limit: Int,
                                   @Query("offset") offset: Int): Deferred<RoundTableList>

    @GET("api/v4/roundtables")
    fun fetchRoundTablesByFlow(@Query("limit") limit: Int,
                                   @Query("offset") offset: Int): Flow<RoundTableList>
}
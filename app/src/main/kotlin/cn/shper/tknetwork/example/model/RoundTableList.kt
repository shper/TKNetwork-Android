package cn.shper.tknetwork.example.model

/**
 * Author: Shper
 * Email: me@shper.cn
 * Version: V0.1 2022/4/15
 */
data class RoundTableList(val paging: Paging,
                          val data: MutableList<RoundTable>)
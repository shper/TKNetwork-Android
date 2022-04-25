package cn.shper.tknetwork.example.model

/**
 * Author: Shper
 * Email: me@shper.cn
 * Version: V0.1 2022/4/15
 */
data class Paging(var is_start: Boolean,
                  var is_end: Boolean,
                  var totals: Int,
                  var previous: String,
                  var next: String)

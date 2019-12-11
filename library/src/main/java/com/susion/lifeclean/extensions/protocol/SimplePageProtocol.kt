package com.susion.lifeclean.extensions.protocol

/**
 * susionwang at 2019-12-11
 *
 * 比较通用的页面刷新协议
 *
 */
interface SimplePageProtocol {
    fun refreshDatas(datas: List<Any>, isLoadMore: Boolean)

    fun refreshPageStatus(status: String)
}
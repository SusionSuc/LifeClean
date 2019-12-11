package com.susion.lifeclean.arc


/**
 * create by susionwang at 2019-12-08
 */
interface GitHubPageProtocol  {

    fun refreshDatas(datas: List<Any>, isLoadMore: Boolean)

    fun refreshPageStatus(status: String)

}
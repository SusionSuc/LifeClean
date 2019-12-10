package com.susion.lifeclean.page

import com.susion.lifeclean.core.LifePage


/**
 * create by susionwang at 2019-12-08
 */
interface SimpleGitHubPageProtocol : LifePage {

    fun refreshDatas(datas: List<Any>, isLoadMore: Boolean)

    fun refreshPageStatus(status: String)

}
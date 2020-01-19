package com.susion.lifeclean.common.protocol

import com.susion.lifeclean.Action
import com.susion.lifeclean.State

/**
 * susionwang at 2019-12-11
 * 通用的基于RecyclerView的页面刷新协议
 */
interface SimpleRvPageProtocol {

    /**
     * 该页面发出的事件
     * */
    class LoadData(val searchWord: String, val isLoadMore: Boolean) : Action

    /**
     * 该页面需要的状态
     * */
    class PageState(val currentPageSize: Int) : State

    fun refreshDatas(datas: List<Any>, isLoadMore: Boolean = false, extra: Any = Any())

    fun refreshPageStatus(status: String, extra: Any = Any())

}
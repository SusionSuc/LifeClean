package com.susion.lifeclean.common

/**
 * susionwang at 2019-12-10
 * 通用的页面状态
 */
object PageStatus {

    private val status = HashMap<String, String>()

    //一些常用的页面状态
    val START_LOAD_MORE = "start_load_more"
    val END_LOAD_MORE = "end_load_more"
    val START_LOAD_PAGE_DATA = "start_load_page_data"
    val END_LOAD_PAGE_DATA = "end_load_page_data"
    val NO_MORE_DATA = "no_more_data"
    val EMPTY_DATA = "empty_data"
    val NET_ERROR = "net_error"
    val TOAST = "show_toast"
    val PRIVACY_DATA = "privacy_data"
    val CONTENT_DELETE = "content_delete"
    val ERROR = "error"
    val UNDEFINE = "undefine"


    /**
     * 扩展页面状态
     * */
    fun registerStatus(name: String, value: String) {
        status[name] = value
    }

    fun getPageStatus(name: String): String {
        return status[name] ?: UNDEFINE
    }

}
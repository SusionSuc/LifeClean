package com.susion.lifeclean.core

/**
 * susionwang at 2019-12-10
 */
interface Presenter {
    /**
     * UI 层发送 Action 通知 Presenter 事件
     * */
    fun dispatch(action: Action)

    /**
     * UI 层通过它来查询数据状态
     *
     * UI层应不包含任何数据
     *
     * 主要用来跨View的数据交互访问
     * */
    fun <T : State> getStatus(): T?

}
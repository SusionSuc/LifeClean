package com.susion.lifeclean.core

/**
 * susionwang at 2019-12-10
 */
interface Presenter {

    /**
     * UI发送Action, Presenter可以选择处理这个时间
     * */
    fun dispatch(action: Action)

    /**
     * UI希望获得的数据状态
     * UI层应不包含任何数据, 数据由Presenter持有
     * */
    fun <T : State> getStatus(): T?

}
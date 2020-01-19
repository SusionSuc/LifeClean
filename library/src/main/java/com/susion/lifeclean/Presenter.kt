package com.susion.lifeclean

/**
 * susionwang at 2019-12-10
 */
interface Presenter {

    /**
     * view 发送Action, Presenter可以选择处理这个时间
     * */
    fun dispatch(action: Action)

    /**
     * view 希望获得的数据状态
     * */
    fun <T : State> getState(): T? {
        return null
    }

}
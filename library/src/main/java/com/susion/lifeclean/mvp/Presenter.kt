package com.susion.lifeclean.mvp

import androidx.lifecycle.LifecycleObserver

/*
* create by susionwang at 2019-12-08
*
* 用法:
* 1. 最抽象的 Presenter, UI 只需要持有这个类型的Presenter 实例
* 2. UI与Presenter的交互应该通过 dispatch(action)
* 3. UI可以通过 queryStatus(status) 方法来查询数据状态
*
* 目标:
* 1. View 与 Presenter的解耦: View 不依赖与特定的 Presenter, 只派发 Action。
* 2. Presenter 是一个可以观察生命周期的组件，它可以更好的管理其负责的需要在生命周期内释放的资源
*/
abstract class Presenter : LifecycleObserver {

    /**
     * UI 层发送 Action 通知 Presenter 事件
     * */
    abstract fun dispatch(action: Action)

    /**
     * UI 层通过它来查询数据状态
     *
     * UI层应不包含任何数据
     *
     * 主要用来跨View的数据交互访问
     * */
    open fun <T : State> getStatus(): T? {
        return null
    }
}

/**
 * view 与 Presenter 交互的事件,  view派发的事件， presenter 处理
 * */
interface Action

/**
 * view 向 presenter 获取的状态,  view需要的数据， presenter 构造
 * */
interface State
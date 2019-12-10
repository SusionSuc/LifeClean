package com.susion.lifeclean.mvp

/**
 * create by susionwang at 2019-12-08
 *
 * @property view 这里可以面向接口编程, 也可以直接接受一个实例
 */
class SimplePresenter(val view: SimpleMvpActivity) : Presenter() {

    override fun dispatch(action: Action) {
        when (action) {
            is LoadData -> {
                view.refresh("hello simple mvp")
            }
        }
    }

    override fun <T : State> getStatus(): T? {
        return SimpleMvpStatus(javaClass.simpleName) as? T
    }

}

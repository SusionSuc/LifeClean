package com.susion.lifeclean.mvp

/**
 * create by susionwang at 2019-12-08
 */
class SimplePresenter2(val view: SimpleMvpView) : Presenter() {

    override fun dispatch(action: Action) {
        when (action) {
            is LoadData -> {
                view.refresh2("hello simple mvp")
            }
        }
    }

    override fun <T : State> getStatus(): T? {
        return SimpleMvpStatus(javaClass.simpleName) as? T
    }

}
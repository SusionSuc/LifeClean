package com.susion.lifeclean.mvp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.susion.lifeclean.R
import kotlinx.android.synthetic.main.activity_mvp.*

/**
 * 该页面发出的事件
 * */
class LoadData : Action

/**
 * 该页面需要的状态
 * */
class SimpleMvpStatus(val presenterName: String) : State

class SimpleMvpActivity : AppCompatActivity(), SimpleMvpView {

    private val presenter by lazy {
        //        SimplePresenter(this)
        SimplePresenter2(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvp)
        presenter.dispatch(LoadData())
    }

    fun refresh(str: String) {
        val presenterName = presenter.getStatus<SimpleMvpStatus>()?.presenterName ?: ""
        mvpAcTv.text = "$str load from $presenterName"
    }

    override fun refresh2(str: String) {
        val presenterName = presenter.getStatus<SimpleMvpStatus>()?.presenterName ?: ""
        mvpAcTv.text = "${str} load from $presenterName"
    }

}
